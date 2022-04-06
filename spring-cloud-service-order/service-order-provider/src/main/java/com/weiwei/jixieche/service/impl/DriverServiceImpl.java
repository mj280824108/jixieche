package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.generate.MD5Util;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.DriverService;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.util.AgeBirthdayUtil;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.JxcClockUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 司机管理
 * @author liuy
 * @date 2019-08-14 13:45
 */
@Service
public class DriverServiceImpl implements DriverService {

	@Resource
	private DriverMapper driverMapper;

	@Resource
	private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

	@Resource
	private JxcDriverRefOwnerMapper jxcDriverRefOwnerMapper;

	@Autowired
	private JxcUserFeign jxcUserFeign;

	@Resource
	private JxcMachineRouteMapper jxcMachineRouteMapper;

	@Resource
	private JxcClockPairMapper jxcClockPairMapper;

	@Resource
	private JxcClockInOutRecordMapper jxcClockInOutRecordMapper;

	@Resource
	private JxcClockUtils jxcClockUtils;

	@Autowired
	private JxcClockInOutRecordService jxcClockInOutRecordService;

	@Resource
	private JxcShortJobRefDriverMapper jxcShortJobRefDriverMapper;

	@Resource
	private JxcShortJobMapper jxcShortJobMapper;

	@Autowired
	private JxcProjectOrderMapper jxcProjectOrderMapper;

	@Autowired
	private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

	@Resource
	private RedisUtil redisUtil;

	@Autowired
	private JpushMsgFeign jpushMsgFeign;

	@Override
	/**
	 * 司机管理列表
	 * @author  liuy
	 * @param ownerUserId 机主用户ID
	 * @return
	 * @date    2019/8/14 13:44
	 */
	public ResponseMessage getAllDriverById(Integer ownerUserId, Integer pageNo, Integer pageSize, Integer flag) {
		PageHelper.startPage(pageNo, pageSize);
		List<DriverVo> list = new ArrayList<>();
		//司机管理列表
		List<DriverVo> driverList = driverMapper.getAllDriverById(ownerUserId, flag);
		if(!CollectionUtils.isEmpty(driverList)){
			for(DriverVo driverVo : driverList){
				ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(driverVo.getDriverId());
				if (userInfoVoResponseMessage != null) {
					UserInfoVo data = userInfoVoResponseMessage.getData();
					driverVo.setScore(data.getScore());
					if(StringUtils.isBlank(driverVo.getDriverPhone())){
						driverVo.setDriverPhone(data.getPhone());
					}
					if(StringUtils.isBlank(driverVo.getDriverName())){
						driverVo.setDriverName(data.getRealName());
					}
				}

				//司机类型: 1:我自己; 2:我的司机; 3:兼职司机
				if(DriverVo.driverType.SHORTDRIVER.equals(driverVo.getDriverType())){
					//兼职司机展示工作结束时间
					if(driverVo.getShortJobId() != null) {
						JxcShortJob jxcShortJob = jxcShortJobMapper.selectByPrimaryKey(driverVo.getShortJobId());
						driverVo.setStartDate(jxcShortJob.getWorkTimeStart());
						driverVo.setEndDate(jxcShortJob.getWorkTimeEnd());

						//查询是否有未支付的
						int count = jxcClockPairMapper.getCountPayByDriverId(driverVo.getDriverId(), driverVo.getShortJobId());
						if (count > 0) {
							//未支付
							driverVo.setPayState(0);
						}else {
							//已支付
							driverVo.setPayState(1);
						}
					}

				}

				//查询司机是否绑定车辆
				JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(driverVo.getDriverId());
				if(jxcMachine != null && StringUtils.isNotBlank(jxcMachine.getMachineCardNo())){
					driverVo.setMachineCardNo(jxcMachine.getMachineCardNo());
					driverVo.setBindState(1);
				}else{
					driverVo.setBindState(0);
				}

				//查询司机是否上班状态
				int resCount = driverMapper.getDriverWorkStateById(driverVo.getDriverId());
				if(resCount > 0){
					//上班中
					driverVo.setWorkState(1);
				}else{
					//未上班
					driverVo.setWorkState(0);
				}
			}
		}
		PageInfo<DriverVo> page = new PageInfo<>(driverList);
		return new ResponseMessage(new PageUtils<>(page).getPageViewDatatable());
	}

	/**
	 * 添加司机
	 * @author  liuy
	 * @param driverVo
	 * @return
	 * @date    2019/8/14 16:00
	 */
	@Override
	@Transactional
	public ResponseMessage addDriver(AuthorizationUser authorizationUser, DriverVo driverVo) {
		if(StringUtils.isBlank(driverVo.getDriverName())){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机称呼不能为空");
		}
		if(StringUtils.isBlank(driverVo.getDriverPhone())){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机手机号码不能为空");
		}
		//验证码
		if(StringUtils.isBlank(driverVo.getCode())){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "验证码不能为空");
		}
		String codeKey = RedisKeyConstants.SHORT_CODE_ADD_LONG_DRIVER.getPrefix() + driverVo.getDriverPhone();
		if(redisUtil.hasKey(codeKey)) {
			String code = redisUtil.get(codeKey).toString();
			//检查验证码是否正确
			if(!code.equals(driverVo.getCode())){
				return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "验证码不正确");
			}
		}else{
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "验证码不存在或已失效");
		}

		//检查是否注册过
		DriverVo driver = this.driverMapper.checkDriver(driverVo);
		if(driver != null){
			//已经注册过的用户，直接添加子账号与机主关联关系表
			JxcDriverRefOwner jxcChildRefOwner = new JxcDriverRefOwner();
			//司机用户ID
			jxcChildRefOwner.setDriverId(driver.getDriverId());
			//机主用户ID
			jxcChildRefOwner.setOwnerId(authorizationUser.getUserId());
			jxcChildRefOwner.setPhone(driverVo.getDriverPhone());
			jxcChildRefOwner.setRemarkName(driverVo.getDriverName());
			//我的司机
			jxcChildRefOwner.setDriverType(DriverVo.driverType.OWNERDRIVER);
			jxcChildRefOwner.setCreateTime(new Date());
			//判断机方与司机是否已经存在绑定关系
			JxcDriverRefOwner childRefOwner = new JxcDriverRefOwner();
			childRefOwner.setDelState(JxcDriverRefOwner.DriverDelState.NO_DELETE);
			childRefOwner.setDriverId(driver.getDriverId());
			List<JxcDriverRefOwner> jxcChildRefOwnerList = this.jxcDriverRefOwnerMapper.selectListByConditions(childRefOwner);
			if(!CollectionUtils.isEmpty(jxcChildRefOwnerList)){
				childRefOwner = jxcChildRefOwnerList.get(0);
				if(!childRefOwner.getOwnerId().equals(authorizationUser.getUserId())) {
					return new ResponseMessage(200, "这个司机的手机号码绑定在其他机主的车辆上,请先解绑后再进行绑定", "-1");
				}else{
					return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"手机号码已被添加过,请勿重复添加");
				}
			}
			//司机是否接过兼职职位
			Integer count = jxcShortJobRefDriverMapper.queryShortJobByDriverId(driver.getDriverId());
			if (count > 0) {
				return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "您已经接过兼职职位不可绑定为我的司机");
			}
			this.jxcDriverRefOwnerMapper.insertSelective(jxcChildRefOwner);
		}else {
			//没有注册过的就执行注册
			OwnerRegisterDriverVo ownerRegisterDriverVo = new OwnerRegisterDriverVo();
			ownerRegisterDriverVo.setPhone(driverVo.getDriverPhone());
			ownerRegisterDriverVo.setRealName(driverVo.getDriverName());
			//设置初始密码
			ownerRegisterDriverVo.setPassword(MD5Util.get32MD5(MD5Util.get32MD5("123456") + MD5Util.get32MD5(driverVo.getDriverPhone())));
			//调用用户模块注册接口
			ResponseMessage responseMessage = jxcUserFeign.ownerRegisterDriver(ownerRegisterDriverVo);
			Integer userId = Integer.parseInt(responseMessage.getData().toString());

			//添加子账号与机主关联关系
			JxcDriverRefOwner jxcChildRefOwner = new JxcDriverRefOwner();
			//机主用户ID
			jxcChildRefOwner.setOwnerId(authorizationUser.getUserId());
			jxcChildRefOwner.setPhone(driverVo.getDriverPhone());
			jxcChildRefOwner.setRemarkName(driverVo.getDriverName());
			jxcChildRefOwner.setDriverId(userId);
			//我的司机
			jxcChildRefOwner.setDriverType(DriverVo.driverType.OWNERDRIVER);
			jxcChildRefOwner.setCreateTime(new Date());
			this.jxcDriverRefOwnerMapper.insertSelective(jxcChildRefOwner);
			JpushMessageVo jpushTemplateVo = new JpushMessageVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(driver.getDriverId());
			if(userInfoVoResponseMessage1.getData() != null){
				UserInfoVo data = userInfoVoResponseMessage1.getData();
				if(data != null) {
					jpushTemplateVo.setAliases(data.getThirdId());
				}
			}
			jpushTemplateVo.setMessageTitle("刷新工作台");
			jpushTemplateVo.setMessageContent("刷新工作台");
			JSONObject js = new JSONObject();
			js.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
			jpushTemplateVo.setMessageExtraParams(js);
			jpushMsgFeign.jpushMessage(jpushTemplateVo);
		}
		return new ResponseMessage();
	}

	/**
	 * 司机-考勤记录
	 * @author  liuy
	 * @param attendanceRecordVo
	 * @return
	 * @date    2019/8/19 16:34
	 */
	@Override
	public ResponseMessage getDriverClockPair(AttendanceRecordVo attendanceRecordVo) {
		PageHelper.startPage(attendanceRecordVo.getPageNo(), attendanceRecordVo.getPageSize());
		List<AttendanceRecordVo> list = this.driverMapper.getDriverClockPair(attendanceRecordVo);
		PageInfo<AttendanceRecordVo> page = new PageInfo<>(list);

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("list",new PageUtils<>(page).getPageViewDatatable());
		//判断是否有查询条件
		if(attendanceRecordVo.getFlag() != null){
			   Map<String, Object> map = this.driverMapper.statisDriverWorkHours(attendanceRecordVo);
			   if (!CollectionUtils.isEmpty(map)) {
				   if (map.get("workDay") != null && map.get("totalWorkHours") != null) {
					   //总工时
					   double totalWorkHours = Double.parseDouble(map.get("totalWorkHours").toString());
					   //出勤总天数
					   Integer workDay = Integer.parseInt(map.get("workDay").toString());
					   //平均工时
					   double avgWork = totalWorkHours / workDay;
					   //能四舍五入,保留后1位小数
					   avgWork = Double.parseDouble(String.format("%.1f", avgWork));

					   resultMap.put("totalWorkHours", totalWorkHours);
					   resultMap.put("workDay", workDay);
					   resultMap.put("avgWork", avgWork);
				   }
		   }
		}
		return new ResponseMessage(resultMap);
	}

	/**
	 * 趟数记录
	 * @author  liuy
	 * @param jxcMachineRouteVo
	 * @return
	 * @date    2019/8/20 13:52
	 */
	@Override
	public ResponseMessage getMachineRouteList(JxcMachineRouteVo jxcMachineRouteVo) {
		Map<String,Object> resultMap = new HashMap<>();

		//兼职职位ID
		if(jxcMachineRouteVo.getShortJobId() != null && jxcMachineRouteVo.getShortJobId() != 0){
			JxcShortJob jxcShortJob = jxcShortJobMapper.selectByPrimaryKey(jxcMachineRouteVo.getShortJobId());
			//兼职职位开始时间
			Date startDate = jxcShortJob.getWorkTimeStart();
			//兼职职位结束时间
			Date endDate = jxcShortJob.getWorkTimeEnd();
			//
			if(jxcMachineRouteVo.getStartTime() != null){
				//传的参数时间
				Date paramStartDate = DateUtils.parseYMD(jxcMachineRouteVo.getStartTime());
				if(null == jxcMachineRouteVo.getEndTime()){
					if(paramStartDate.getTime() >= startDate.getTime() && paramStartDate.getTime() <= endDate.getTime()) {
						jxcMachineRouteVo.setEndTime(jxcMachineRouteVo.getStartTime());
					}else{
						resultMap.put("hasNextPage", 0);
						resultMap.put("totalCount", 0);
						resultMap.put("list", new ArrayList<>());
						return new ResponseMessage(resultMap);
					}
				}else {
					Date paramEndDate = DateUtils.parseYMD(jxcMachineRouteVo.getEndTime());
					if (paramStartDate.getTime() <= startDate.getTime() && paramEndDate.getTime() >= endDate.getTime()) {
						jxcMachineRouteVo.setStartTime(DateUtils.format(startDate));
						jxcMachineRouteVo.setEndTime(DateUtils.format(endDate));
					} else if (paramStartDate.getTime() >= startDate.getTime() && paramEndDate.getTime() <= endDate.getTime()) {
						jxcMachineRouteVo.setStartTime(DateUtils.format(paramStartDate));
						jxcMachineRouteVo.setEndTime(DateUtils.format(paramEndDate));
					} else if (paramStartDate.getTime() < startDate.getTime() && paramEndDate.getTime() >= startDate.getTime()) {
						jxcMachineRouteVo.setStartTime(DateUtils.format(startDate));
						jxcMachineRouteVo.setEndTime(DateUtils.format(paramEndDate));
					}else if(paramStartDate.getTime() >= endDate.getTime()){
						jxcMachineRouteVo.setStartTime(DateUtils.format(paramStartDate));
						jxcMachineRouteVo.setEndTime(DateUtils.format(endDate));
					}else{
						resultMap.put("hasNextPage", 0);
						resultMap.put("totalCount", 0);
						resultMap.put("list", new ArrayList<>());
						return new ResponseMessage(resultMap);
					}
				}
			}else{
				//mark 未传时间筛选条件时直接取兼职职位工期时间
				jxcMachineRouteVo.setStartTime(DateUtils.format(startDate));
				jxcMachineRouteVo.setEndTime(DateUtils.format(endDate));
			}
		}else {
			if (jxcMachineRouteVo.getEndTime() == null) {
				jxcMachineRouteVo.setEndTime(jxcMachineRouteVo.getStartTime());
			}
		}

		//总趟数
		Integer totalCount = jxcMachineRouteMapper.getTotalCountMachineRoute(jxcMachineRouteVo);
		List<JxcMachineRouteVo> machineRouteList = jxcMachineRouteMapper.getMachineRouteList(jxcMachineRouteVo);
		int hasNextPage = 0;
		if(!CollectionUtils.isEmpty(machineRouteList)){
			machineRouteList.stream().forEach(jxcMachineRouteVo1 -> {
				//第三方id(用户第三方鹰眼，极光推送唯一识别码)
				jxcMachineRouteVo1.setThirdId(jxcMachineRouteVo.getThirdId());

				if(jxcMachineRouteVo1.getAbnormalType() != 0) {
					//异常状态(0--异常申请待处理 1--处理完毕
					if (jxcMachineRouteVo1.getAbnormalStatus() == 1) {
						//已处理
						jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.PROCESSED);
					} else {
						jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.UNPROCESSED);
					}
				}else{
					if(jxcMachineRouteVo1.getPayState() == 0){
						//未打消纳场卡
						jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.NOCLOCK);
					}else {
						//正常
						jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.NORMAL);
					}
				}

				if(jxcMachineRouteVo1.getAmount() != null){
					jxcMachineRouteVo1.setAmount(DataTypeEmptyUtils.emptyBigDecimalMoney(jxcMachineRouteVo1.getAmount()));
				}

			});
			if(machineRouteList.size() == 10) {
				//mark 将最后一条数据作为下次查询的条件
				jxcMachineRouteVo.setLastPageLastId(machineRouteList.get(9).getRouteId());
				Long nextPageFirstId = this.jxcMachineRouteMapper.hasNextPage(jxcMachineRouteVo);
				if (null != nextPageFirstId) {
					hasNextPage = 1;
					resultMap.put("lastPageLastId", machineRouteList.get(9).getRouteId());
				}
			}
		}
		resultMap.put("hasNextPage", hasNextPage);
		resultMap.put("totalCount", totalCount);
		resultMap.put("list", machineRouteList);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 司机-详情
	 * @author  liuy
	 * @return
	 * @date    2019/9/3 16:06
	 */
	@Override
	public ResponseMessage getDriverById(Integer id) {
		DriverVo driverVo = driverMapper.getDriverById(id);
		if(driverVo != null){
			try {
				//司机年龄
				if(driverVo.getBirthday() != null){
					driverVo.setAge(AgeBirthdayUtil.getAgeByBirth(driverVo.getBirthday()));
				}
				//查询司机是否绑定车辆
				JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(driverVo.getDriverId());
				if (jxcMachine != null && StringUtils.isNotBlank(jxcMachine.getMachineCardNo())) {
					driverVo.setBindState(JxcMachineRefDriver.bindState.IS_BIND);
					driverVo.setMachineCardNo(jxcMachine.getMachineCardNo());
					driverVo.setMachineId(jxcMachine.getId());
				} else {
					driverVo.setBindState(JxcMachineRefDriver.bindState.NOT_BIND);
				}
				//司机职位开始时间
				if(driverVo.getShortJobId() != null){
					JxcShortJob jxcShortJob = jxcShortJobMapper.selectByPrimaryKey(driverVo.getShortJobId());
					if(jxcShortJob != null) {
						driverVo.setStartDate(jxcShortJob.getWorkTimeStart());
						driverVo.setEndDate(jxcShortJob.getWorkTimeEnd());
					}
				}

				//查询司机评分
				ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(driverVo.getDriverId());
				if (userInfoVoResponseMessage != null) {
					UserInfoVo data = userInfoVoResponseMessage.getData();
					driverVo.setScore(data.getScore());
					if(StringUtils.isBlank(driverVo.getDriverPhone())){
						driverVo.setDriverPhone(data.getPhone());
					}
					if(StringUtils.isBlank(driverVo.getDriverName())){
						driverVo.setDriverName(data.getRealName());
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return new ResponseMessage(driverVo);
	}

	/**
	 * 检查司机是否跑趟中或是正在上班中
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 15:28
	 */
	@Override
	public ResponseMessage checkDriverRunOrWorkIn(Integer driverId) {
		//检查司机是否正在跑趟中
		Long routeId = jxcMachineRouteMapper.checkDriverRun(driverId);
		if(routeId != null){
			return new ResponseMessage(ErrorCodeConstants.ORDER_DRIVER_RUN.getId(),ErrorCodeConstants.ORDER_DRIVER_RUN.getDescript());
		}
		//检查司机是否正在上班中
		Integer count = driverMapper.getDriverWorkStateById(driverId);
		if(count > 0){
			return new ResponseMessage(ErrorCodeConstants.ORDER_DRIVER_WORK_IN.getId(), ErrorCodeConstants.ORDER_DRIVER_WORK_IN.getDescript());
		}
		return new ResponseMessage();
	}

	/**
	 * 司机-删除 1:删除; 2:解雇
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 15:37
	 */
	@Override
	@Transactional
	public ResponseMessage delDriver(Integer ownerUserId, Integer driverId, Integer typeId, String reason, Integer shortJobId) {
		boolean flag = false;
		//检查司机是否正在跑趟中
		Long routeId = jxcMachineRouteMapper.checkDriverRun(driverId);
		if(routeId != null){
			return new ResponseMessage("-1");
		}
		//检查司机是否正在上班中
		Integer count = driverMapper.getDriverWorkStateById(driverId);
		if(count > 0){
			JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
			jxcClockInOutRecord.setShortJobId(shortJobId);
			jxcClockInOutRecord.setDriverId(driverId);
			jxcClockInOutRecord.setClockAddress("机主强制补下班卡");
			//查询司机是否绑定车辆
			JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(driverId);
			if(jxcMachine != null){
				jxcClockInOutRecord.setMachineId(jxcMachine.getId());
			}
			//下班打卡
			jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
		}
		JSONObject js = new JSONObject();
		//删除
		if(typeId == 1) {
			//先解绑机械与司机关系
			jxcMachineRefDriverMapper.updateByDriverId(driverId);

			//删除司机
			jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(ownerUserId, driverId, JxcDriverRefOwner.DriverDelState.IS_DELETE, null);

			JpushMessageVo jpushTemplateVo = new JpushMessageVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(driverId);
			if(userInfoVoResponseMessage1.getData() != null){
				UserInfoVo data = userInfoVoResponseMessage1.getData();
				if(data != null) {
					jpushTemplateVo.setAliases(data.getThirdId());
				}
			}
			jpushTemplateVo.setMessageTitle("刷新工作台");
			jpushTemplateVo.setMessageContent("刷新工作台");
			JSONObject js1 = new JSONObject();
			js1.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
			jpushTemplateVo.setMessageExtraParams(js1);
			jpushMsgFeign.jpushMessage(jpushTemplateVo);
		}
		//解雇
		else if(typeId == 2){
			if(null == shortJobId){
				return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "兼职职位ID");
			}
			//先解绑机械与司机关系
			jxcMachineRefDriverMapper.updateByDriverId(driverId);

			if(shortJobId != null){
				//更新兼职职位状态
				JxcShortJob jxcShortJob = jxcShortJobMapper.selectByPrimaryKey(shortJobId);
				js.put("startDate",DateUtils.format(jxcShortJob.getWorkTimeStart()));
				js.put("endDate",DateUtils.format(jxcShortJob.getWorkTimeEnd()));
				//已招满状态时解雇司机
				if(jxcShortJob != null && jxcShortJob.getStatus().equals(JxcShortJob.status.FULL)){
					jxcShortJob = new JxcShortJob();
					jxcShortJob.setId(shortJobId);
					jxcShortJob.setStatus(JxcShortJob.status.START);
					jxcShortJobMapper.updateByPrimaryKeySelective(jxcShortJob);
				}
				//更新兼职职位与司机的关联表状态
				//兼职职位工作未到达开始时间的
				if (DateUtils.differentDays(new Date(),jxcShortJob.getWorkTimeStart()) > 0){
					//直接删除数据
					jxcShortJobRefDriverMapper.delShortJobRefDriverById(shortJobId, driverId);
					//拿到信用分扣取规则
					JxcCreditScoreTemplate effectiveById = jxcProjectOrderMapper.getEffectiveById(1);
					if (effectiveById != null) {
						//拿到规则时间
						Integer condition = effectiveById.getCondition();
						if ((jxcShortJob.getWorkTimeStart().getTime() - System.currentTimeMillis()) < condition * 60 * 60 * 1000) {
							//添加信用分记录
							UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
							userCreditScoreVo.setUserId(ownerUserId);
							userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_OWNER_FIRE_DRIVER.getId());
							jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
						}
					}
				}else{
					//到达开始时间的
					//更新司机接的职位订单状态为被机主解雇
					jxcShortJobRefDriverMapper.updateShortJobDriverById(5, shortJobId, driverId, reason);
					//添加信用分记录
					UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
					userCreditScoreVo.setUserId(ownerUserId);
					userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_OWNER_FIRE_DRIVER.getId());
					jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
				}
			}
			//查询司机是否有未支付
			Integer countPayByDriverId = jxcClockPairMapper.getCountPayByDriverId(driverId, shortJobId);
			if(countPayByDriverId > 0){
				flag = true;
				//解雇司机
				jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(ownerUserId, driverId, JxcDriverRefOwner.DriverDelState.FIRE_DELETE, shortJobId);
			}else{
				//删除司机
				jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(ownerUserId, driverId, JxcDriverRefOwner.DriverDelState.IS_DELETE, shortJobId);
			}
		}

		if(typeId.equals(2)){
			//解雇推送
			JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
			jpushCustomMsgVo.setUserId(driverId);
			jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_FIRE_DRIVER.getId());
			js.put("fireReason",reason);
			jpushCustomMsgVo.setParam(js);
			jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(driverId);
			if(userInfoVoResponseMessage != null && userInfoVoResponseMessage.getData() != null){
				UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
				JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
				jpushTemplateVo.setAliases(userInfoVo.getThirdId());
				jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
				jpushTemplateVo.setParams(js);
				jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_FIRE_DRIVER.getId());
				jpushMsgFeign.jpushNotice(jpushTemplateVo);
			}
		}
		if(typeId.equals(1)){
			//删除长期司机
			JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
			jpushCustomMsgVo.setUserId(driverId);
			jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_FIRE_LONG_DRIVER.getId());
			js.put("fireReason",reason);
			jpushCustomMsgVo.setParam(js);
			jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(driverId);
			if(userInfoVoResponseMessage != null && userInfoVoResponseMessage.getData() != null){
				UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
				JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
				jpushTemplateVo.setAliases(userInfoVo.getThirdId());
				jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
				jpushTemplateVo.setParams(js);
				jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_FIRE_LONG_DRIVER.getId());
				jpushMsgFeign.jpushNotice(jpushTemplateVo);
			}
		}
		return new ResponseMessage();
	}

	/**
	 * 历史司机列表
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/21 16:42
	 */
	@Override
	public ResponseMessage getHistoryDriverList(Integer ownerUserId, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<DriverVo> list = this.driverMapper.getHistoryDriverList(ownerUserId);
		PageInfo<DriverVo> page = new PageInfo<>(list);
		return new ResponseMessage(new PageUtils<>(page).getPageViewDatatable());
	}

	/**
	 * 添加子账号与机主关联关系
	 * @author  liuy
	 * @param jxcDriverRefOwner
	 * @return
	 * @date    2019/8/29 11:05
	 */
	@Override
	public ResponseMessage addDriverRefOwner(JxcDriverRefOwner jxcDriverRefOwner) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = jxcDriverRefOwnerMapper.insertSelective(jxcDriverRefOwner);
		AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 考勤工资
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	@Override
	public ResponseMessage driverAttendancePay(Integer driverId, String lastPageLastId, Integer shortJobId) {
		//返回结果
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		List<ClockRecordVo> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null == driverId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机ID不能为空");
		}

		params.put("driverId", driverId);
		params.put("lastPageLastId", lastPageLastId);
		params.put("shortJobId", shortJobId);

		//司机考勤工资
		List<ClockRecordVo> clockRecordVoList = this.jxcClockPairMapper.getDriverClockDateById(params);
		int hasNextPage = 0;
		if (!com.weiwei.jixieche.verify.CollectionUtils.isEmpty(clockRecordVoList)) {
			//标识是否有下一页:1:是 0:否
			if (clockRecordVoList.size() == 10) {
				//mark 将最后一条数据作为下次查询的条件
				params.put("lastPageLastId", clockRecordVoList.get(9).getClockId());
				Long nextPageFirstId = this.jxcClockPairMapper.hasNextPage(params);
				if (null != nextPageFirstId) {
					hasNextPage = 1;
					resultMap.put("lastPageLastId", clockRecordVoList.get(9).getClockId());
				}
			}

			//司机打卡记录
			clockRecordVoList.stream().forEach(clockRecordVo -> {

				if(clockRecordVo.getFactAmount() != null){
					clockRecordVo.setFactAmount(DataTypeEmptyUtils.emptyBigDecimalMoney(clockRecordVo.getFactAmount()));
				}

				Map<String,Object> paramMap = new HashMap<>();
				paramMap.put("clockId", clockRecordVo.getClockId());
				List<ClockRecord> clockRecordList = jxcClockInOutRecordMapper.getClockRecordList(paramMap);
				clockRecordVo.setRecordList(clockRecordList);
			});
		}
		resultMap.put("hasNextPage", hasNextPage);
		resultMap.put("list", clockRecordVoList);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 考勤工资-详情
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	@Override
	public ResponseMessage driverAttendancePayDetail(Integer driverId, String clockId, String lastPageLastId) {
		//返回结果
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		params.put("driverUserId", driverId);
		params.put("clockId", clockId);
		//打卡日期分组查询每天打卡次数
		List<ClockRecordVo> clockRecordVoList = this.jxcClockPairMapper.getDriverClockDateById(params);
		ClockRecordVo clockRecordVo = new ClockRecordVo();
		if(!CollectionUtils.isEmpty(clockRecordVoList)){
			clockRecordVo = clockRecordVoList.get(0);

			//金额转换
			if(clockRecordVo.getFactAmount() != null){
				clockRecordVo.setFactAmount(DataTypeEmptyUtils.emptyBigDecimalMoney(clockRecordVo.getFactAmount()));
			}

			//司机打卡记录
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("clockId", clockId);
			List<ClockRecord> clockRecordList = jxcClockInOutRecordMapper.getClockRecordList(paramMap);
			clockRecordVo.setRecordList(clockRecordList);
		}
		resultMap.put("clockRecordVo", clockRecordVo);

		if(clockRecordVo != null) {
			//趟数记录
			JxcMachineRouteVo jxcMachineRouteVo = new JxcMachineRouteVo();
			jxcMachineRouteVo.setDriverId(driverId);
			if (StringUtils.isNotBlank(lastPageLastId)) {
				jxcMachineRouteVo.setLastPageLastId(Long.valueOf(lastPageLastId));
			}
			jxcMachineRouteVo.setStartTime(clockRecordVo.getClockDate());
			jxcMachineRouteVo.setEndTime(clockRecordVo.getClockDate());
			List<JxcMachineRouteVo> machineRouteList = jxcMachineRouteMapper.getMachineRouteList(jxcMachineRouteVo);
			int hasNextPage = 0;
			if (!CollectionUtils.isEmpty(machineRouteList)) {
				machineRouteList.stream().forEach(jxcMachineRouteVo1 -> {
					//第三方id(用户第三方鹰眼，极光推送唯一识别码)
					jxcMachineRouteVo1.setThirdId(jxcMachineRouteVo.getThirdId());

					//金额转换
					if(jxcMachineRouteVo1.getAmount() != null){
						jxcMachineRouteVo1.setAmount(DataTypeEmptyUtils.emptyBigDecimalMoney(jxcMachineRouteVo1.getAmount()));
					}

					if(jxcMachineRouteVo1.getAbnormalType() != 0) {
						//异常状态(0--异常申请待处理 1--处理完毕
						if (jxcMachineRouteVo1.getAbnormalStatus() == 1) {
							//已处理
							jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.PROCESSED);
						} else {
							jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.UNPROCESSED);
						}
					}else{
						if(jxcMachineRouteVo1.getPayState() == 0){
							//未打消纳场卡
							jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.NOCLOCK);
						}else {
							//正常
							jxcMachineRouteVo1.setStatus(JxcMachineRouteVo.RouteState.NORMAL);
						}
					}

				});
				if (machineRouteList.size() == 10) {
					if (StringUtils.isBlank(lastPageLastId)) {
						jxcMachineRouteVo.setLastPageLastId(machineRouteList.get(9).getRouteId());
					}
					//mark 将最后一条数据作为下次查询的条件
					Long nextPageFirstId = this.jxcMachineRouteMapper.hasNextPage(jxcMachineRouteVo);
					if (null != nextPageFirstId) {
						hasNextPage = 1;
						resultMap.put("lastPageLastId", machineRouteList.get(9).getRouteId());
					}
				}
			}
			resultMap.put("hasNextPage", hasNextPage);
			resultMap.put("list", machineRouteList);
		}
		return new ResponseMessage(resultMap);
	}

}
