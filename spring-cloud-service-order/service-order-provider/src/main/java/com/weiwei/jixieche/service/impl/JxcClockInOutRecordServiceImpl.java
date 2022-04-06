package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.utils.JxcClockUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("jxcClockInOutRecordService")
public class JxcClockInOutRecordServiceImpl implements JxcClockInOutRecordService {
	@Resource
	private JxcClockInOutRecordMapper jxcClockInOutRecordMapper;

	@Resource
	private JxcClockInOutPairMapper jxcClockInOutPairMapper;

	@Resource
	private JxcShortJobMapper jxcShortJobMapper;

	@Resource
	private JxcClockUtils jxcClockUtils;

	@Resource
	private JxcClockPairMapper jxcClockPairMapper;

	@Resource
	private JxcDriverHandoverMapper jxcDriverHandoverMapper;

	@Resource
	private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

	@Resource
	private JxcShortJobRefDriverMapper jxcShortJobRefDriverMapper;

	@Autowired
	private JxcUserFeign jxcUserFeign;

	@Resource
	private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

	//前端分页查询司机打卡记录表
	@Override
	public ResponseMessage<JxcClockInOutRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcClockInOutRecord jxcClockInOutRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(pageNo, pageSize);
		List<JxcClockInOutRecord> list = this.jxcClockInOutRecordMapper.selectListByConditions(jxcClockInOutRecord);
		PageInfo<JxcClockInOutRecord> page = new PageInfo<>(list);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	//添加司机打卡记录表
	@Override
	public ResponseMessage<JxcClockInOutRecord> addObj(JxcClockInOutRecord t) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcClockInOutRecordMapper.insertSelective(t);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	//修改司机打卡记录表
	@Override
	public ResponseMessage<JxcClockInOutRecord> modifyObj(JxcClockInOutRecord t) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcClockInOutRecordMapper.updateByPrimaryKeySelective(t);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
		return result;
	}

	//根据ID查询司机打卡记录表
	@Override
	public ResponseMessage<JxcClockInOutRecord> queryObjById(int id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcClockInOutRecord model = this.jxcClockInOutRecordMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
		result.setData(model);
		return result;
	}

	/**
	 * 上班打卡
	 *
	 * @param jxcClockInOutRecord
	 * @return
	 * @author liuy
	 * @date 2019/8/17 10:24
	 */
	@Override
	@Transactional
	public ResponseMessage clockIn(JxcClockInOutRecord jxcClockInOutRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcClockInOutRecord.setRecordId(IDGenerator.getInstance().next());
		jxcClockInOutRecord.setClockTime(new Date());
		jxcClockInOutRecord.setRecordType(JxcClockInOutRecord.recordType.CLOCKIN);
		//查询当前司机正在进行中的工作ID
		PageHelper.clearPage();
		Integer shortJobId = jxcShortJobRefDriverMapper.getShortJobIdByDriverId(jxcClockInOutRecord.getDriverId());
		jxcClockInOutRecord.setShortJobId(shortJobId);
		//防止表单重复提交以及同车司机同时进行上班打卡操作
		JxcClockInOutRecord jxcClockInOutRecord1 = jxcClockInOutRecordMapper.getJxcClockInOutRecord(jxcClockInOutRecord.getMachineId());
		if(jxcClockInOutRecord1 != null && jxcClockInOutRecord1.getRecordType().equals(1)){
			if(jxcClockInOutRecord1.getDriverId().equals(jxcClockInOutRecord.getDriverId())) {
				result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
				result.setMessage("您正在上班中，请勿重复进行上班操作！");
				return result;
			}else {
				result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
				result.setMessage("该机械有其他司机正在上班中，您不能上班！");
				return result;
			}
		}
		int res = this.jxcClockInOutRecordMapper.insertSelective(jxcClockInOutRecord);
		if (res > 0) {
			//司机台班打卡配对表
			JxcClockInOutPair jxcClockInOutPair = new JxcClockInOutPair();
			jxcClockInOutPair.setClockInId(jxcClockInOutRecord.getRecordId());
			jxcClockInOutPair.setShortJobId(jxcClockInOutRecord.getShortJobId());
			jxcClockInOutPair.setMachineId(jxcClockInOutRecord.getMachineId());
			jxcClockInOutPair.setDriverId(jxcClockInOutRecord.getDriverId());
			jxcClockInOutPair.setChildId(jxcClockInOutRecord.getChildId());
			//上班打卡时间
			jxcClockInOutPair.setClockInTime(new Date());
			jxcClockInOutPairMapper.insertSelective(jxcClockInOutPair);
			//返回上班打卡时间
			result.setData(jxcClockInOutPair.getClockInTime());
		}

		//查询机械未设置司机上班是否有待办事项
		JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
		jxcWaitHandleItems.setMachineId(jxcClockInOutRecord.getMachineId());
		jxcWaitHandleItems.setUserId(jxcClockInOutRecord.getOwnerUserId());
		//未添加司机
		jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE11);
		jxcWaitHandleItems.setIsDeleted(0);
		List<JxcWaitHandleItems> list = jxcWaitHandleItemsMapper.selectListByConditions(jxcWaitHandleItems);
		if(!CollectionUtils.isEmpty(list)){
			jxcWaitHandleItems = list.get(0);
			JxcWaitHandleItems waitHandleItems = new JxcWaitHandleItems();
			waitHandleItems.setId(jxcWaitHandleItems.getId());
			//更新为已处理状态
			waitHandleItems.setIsDeleted(1);
			jxcWaitHandleItemsMapper.updateByPrimaryKeySelective(waitHandleItems);
		}
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 下班打卡
	 *
	 * @param jxcClockInOutRecord
	 * @return
	 * @author liuy
	 * @date 2019/8/17 11:04
	 */
	@Override
	@Transactional
	public ResponseMessage clockOut(JxcClockInOutRecord jxcClockInOutRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcClockInOutRecord.setRecordId(IDGenerator.getInstance().next());
		jxcClockInOutRecord.setClockTime(new Date());
		jxcClockInOutRecord.setRecordType(JxcClockInOutRecord.recordType.CLOCKOUT);
		//写入下班打卡记录信息
		int res = this.jxcClockInOutRecordMapper.insertSelective(jxcClockInOutRecord);
		if (res > 0) {
			//司机台班打卡配对表最后一条上班记录ID
			JxcClockInOutPair jxcClockInOutPair = new JxcClockInOutPair();
			jxcClockInOutPair.setShortJobId(jxcClockInOutRecord.getShortJobId());
			jxcClockInOutPair.setMachineId(jxcClockInOutRecord.getMachineId());
			jxcClockInOutPair.setDriverId(jxcClockInOutRecord.getDriverId());
			jxcClockInOutPair.setChildId(jxcClockInOutRecord.getChildId());
			PageHelper.clearPage();
			Long clockIn = this.jxcClockInOutRecordMapper.getClockInId(jxcClockInOutPair);

			//更新下班打卡
			jxcClockInOutPair = new JxcClockInOutPair();
			//上班打卡记录ID
			jxcClockInOutPair.setClockInId(clockIn);
			//下班打卡记录ID
			jxcClockInOutPair.setClockOutId(jxcClockInOutRecord.getRecordId());
			//下班打卡时间
			jxcClockInOutPair.setClockOutTime(new Date());
			jxcClockInOutPairMapper.updateByPrimaryKeySelective(jxcClockInOutPair);
		}

		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 打卡记录
	 *
	 * @param clockRecordListVo 打卡日期
	 * @return
	 * @author liuy
	 * @date 2019/8/17 13:52
	 */
	@Override
	public ResponseMessage getClockListByDriver(ClockRecordListVo clockRecordListVo) {
		//返回结果
		Map<String, Object> resultMap = new HashMap<>();

		Map<String, Object> params = new HashMap<>();
		params.put("driverUserId", clockRecordListVo.getDriverUserId());
		params.put("shortJobId", clockRecordListVo.getShortJobId());
		params.put("clockDate", clockRecordListVo.getClockDate());
		params.put("lastPageLastId", clockRecordListVo.getLastPageLastId());
		//司机考勤工资
		List<ClockRecordVo> clockRecordVoList = this.jxcClockPairMapper.getDriverClockDate(params);
		int hasNextPage = 0;
		if (!CollectionUtils.isEmpty(clockRecordVoList)) {
			//标识是否有下一页:1:是 0:否
			if (clockRecordVoList.size() == 10) {
				//mark 将最后一条数据作为下次查询的条件
				params.put("lastPageLastId", clockRecordVoList.get(9).getClockId());
				Long nextPageFirstId = this.jxcClockPairMapper.hasNextPageDriverClockDate(params);
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
				List<ClockRecord> list = new ArrayList<>();
				if(clockRecordVo.getClockId() == null || clockRecordVo.getClockId() == 0) {
					params.put("clockDate", clockRecordVo.getClockDate());
					//第一次上班时间
					String startTime = jxcClockInOutRecordMapper.getClockInTime(params);
					//最后一次下班卡时间
					String endTime = jxcClockInOutRecordMapper.getClockOutTime(params);
					paramMap.put("startTime", startTime);
					paramMap.put("endTime", endTime);
					paramMap.put("driverUserId", clockRecordListVo.getDriverUserId());
					paramMap.put("shortJobId", clockRecordListVo.getShortJobId());
					//查询打卡次数
					Integer clockCount = jxcClockInOutRecordMapper.getClockCount(paramMap);
					clockRecordVo.setClockCount(clockCount);

					//计算工时
					double workHours = jxcClockInOutPairMapper.calculatingTotalWorkHour(paramMap);
					clockRecordVo.setWorkHours(workHours);
				}
				list = jxcClockInOutRecordMapper.getClockRecordList(paramMap);
				clockRecordVo.setRecordList(list);
			});
		}
		resultMap.put("hasNextPage", hasNextPage);
		resultMap.put("list", clockRecordVoList);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 司机跑趟中交接班
	 * @author  liuy
	 * @param jxcDriverChangeWorkVo
	 * @return
	 * @date    2019/8/24 14:43
	 */
	@Override
	@Transactional
	public ResponseMessage driverChangeWork(JxcDriverChangeWorkVo jxcDriverChangeWorkVo) {
		if(null == jxcDriverChangeWorkVo.getRouteId()){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数行程ID不能为空");
		}
		Long routeId = jxcDriverChangeWorkVo.getRouteId();
		JxcDriverHandover jxcDriverHandover = jxcDriverHandoverMapper.selectById(routeId);
		if(null == jxcDriverHandover){
			jxcDriverHandover = new JxcDriverHandover();
			jxcDriverHandover.setRouteId(routeId);
			//上班中司机的third
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcDriverChangeWorkVo.getUpDriverId());
			if(userInfoVoResponseMessage.getData() != null){
				UserInfoVo data = userInfoVoResponseMessage.getData();
				jxcDriverHandover.setThirdIdUp(data.getThirdId());
			}
			//接班的司机的third
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(jxcDriverChangeWorkVo.getDriverId());
			if(userInfoVoResponseMessage1.getData() != null){
				UserInfoVo data = userInfoVoResponseMessage1.getData();
				jxcDriverHandover.setThirdIdDown(data.getThirdId());
			}
			jxcDriverHandover.setCreateTime(new Date());
			jxcDriverHandoverMapper.insertSelective(jxcDriverHandover);

			//调用下班打卡(交接班司机下班)
			JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
			jxcClockInOutRecord.setDriverId(jxcDriverChangeWorkVo.getDriverId());
			JxcMachine jxcMachine = jxcMachineRefDriverMapper.getMachineCarNoById(jxcDriverChangeWorkVo.getUpDriverId());
			if(jxcMachine != null){
				jxcClockInOutRecord.setMachineId(jxcMachine.getId());
			}
			//查询司机职位ID
			JxcShortJobRefDriver jxcShortJobRefDriver = new JxcShortJobRefDriver();
			jxcShortJobRefDriver.setDriverId(jxcDriverChangeWorkVo.getDriverId());
			jxcShortJobRefDriver.setState(1);
			List<JxcShortJobRefDriver> jxcShortJobRefDrivers = jxcShortJobRefDriverMapper.selectListByConditions(jxcShortJobRefDriver);
			if(!CollectionUtils.isEmpty(jxcShortJobRefDrivers)){
				jxcShortJobRefDriver = jxcShortJobRefDrivers.get(0);
				jxcClockInOutRecord.setShortJobId(jxcShortJobRefDriver.getShortJobId());
			}
			jxcClockInOutRecord.setClockAddress(jxcDriverChangeWorkVo.getClockAddress());
			clockOut(jxcClockInOutRecord);

			//调用上班打卡(接班的司机上班)
			jxcClockInOutRecord = new JxcClockInOutRecord();
			jxcClockInOutRecord.setDriverId(jxcDriverChangeWorkVo.getUpDriverId());
			JxcMachine jxcMachine1 = jxcMachineRefDriverMapper.getMachineCarNoById(jxcDriverChangeWorkVo.getUpDriverId());
			if(jxcMachine1 != null){
				jxcClockInOutRecord.setMachineId(jxcMachine1.getId());
			}
			else{
				return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"司机所绑定的车辆不存在");
			}
			//查询司机职位ID
			JxcShortJobRefDriver jxcShortJobRefDriver1 = new JxcShortJobRefDriver();
			jxcShortJobRefDriver1.setDriverId(jxcDriverChangeWorkVo.getUpDriverId());
			jxcShortJobRefDriver1.setState(1);
			List<JxcShortJobRefDriver> jxcShortJobRefDrivers1 = jxcShortJobRefDriverMapper.selectListByConditions(jxcShortJobRefDriver);
			if(!CollectionUtils.isEmpty(jxcShortJobRefDrivers1)){
				jxcShortJobRefDriver1 = jxcShortJobRefDrivers1.get(0);
				jxcClockInOutRecord.setShortJobId(jxcShortJobRefDriver1.getShortJobId());
			}
			jxcClockInOutRecord.setClockAddress(jxcDriverChangeWorkVo.getClockAddress());
			clockIn(jxcClockInOutRecord);
		}else {
			//表示已经交接过一次
			return new ResponseMessage(ErrorCodeConstants.DRIVER_CHANGER_WORK.getId(), "同一趟不可两次交接班，请跑完当前趟再交接上下班");
		}
		return new ResponseMessage();
	}

	/**
	 * 机主设置司机上班
	 * @author  liuy
	 * @param jxcClockInOutRecord
	 * @return
	 * @date    2019/9/26 9:36
	 */
	@Override
	@Transactional
	public ResponseMessage setUpDriverWorkIn(JxcClockInOutRecord jxcClockInOutRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcClockInOutRecord.setRecordId(IDGenerator.getInstance().next());
		jxcClockInOutRecord.setClockTime(new Date());
		jxcClockInOutRecord.setRecordType(JxcClockInOutRecord.recordType.CLOCKIN);
		//查询当前司机正在进行中的工作ID
		Integer shortJobId = jxcShortJobRefDriverMapper.getShortJobIdByDriverId(jxcClockInOutRecord.getDriverId());
		jxcClockInOutRecord.setShortJobId(shortJobId);
		//检查当前机械是否有人正在上班
		JxcClockInOutPair jxcClockInOutPair1 = jxcClockInOutRecordMapper.getJxcClockInOutRecordByMachineId(jxcClockInOutRecord.getMachineId());
		if(jxcClockInOutPair1 != null){
			if(!jxcClockInOutPair1.getDriverId().equals(jxcClockInOutRecord.getDriverId())){
				result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
				result.setMessage("该机械有其他司机正在上班中，您不能上班！");
				return result;
			}else {
				result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
				result.setMessage("您正在上班中，请勿重复进行上班操作！");
				return result;
			}
		}
		if(StringUtils.isBlank(jxcClockInOutRecord.getClockAddress())){
			jxcClockInOutRecord.setClockAddress("机主设置司机上班");
		}
		int res = this.jxcClockInOutRecordMapper.insertSelective(jxcClockInOutRecord);
		if (res > 0) {
			//司机台班打卡配对表
			JxcClockInOutPair jxcClockInOutPair = new JxcClockInOutPair();
			jxcClockInOutPair.setClockInId(jxcClockInOutRecord.getRecordId());
			jxcClockInOutPair.setShortJobId(jxcClockInOutRecord.getShortJobId());
			jxcClockInOutPair.setMachineId(jxcClockInOutRecord.getMachineId());
			jxcClockInOutPair.setDriverId(jxcClockInOutRecord.getDriverId());
			jxcClockInOutPair.setChildId(jxcClockInOutRecord.getChildId());
			//上班打卡时间
			jxcClockInOutPair.setClockInTime(new Date());
			jxcClockInOutPairMapper.insertSelective(jxcClockInOutPair);
			//返回上班打卡时间
			result.setData(jxcClockInOutPair.getClockInTime());
		}

		//查询机械未设置司机上班是否有待办事项
		JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
		jxcWaitHandleItems.setMachineId(jxcClockInOutRecord.getMachineId());
		jxcWaitHandleItems.setUserId(jxcClockInOutRecord.getOwnerUserId());
		//未添加司机
		jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE11);
		jxcWaitHandleItems.setIsDeleted(0);
		List<JxcWaitHandleItems> list = jxcWaitHandleItemsMapper.selectListByConditions(jxcWaitHandleItems);
		if(!org.springframework.util.CollectionUtils.isEmpty(list)){
			jxcWaitHandleItems = list.get(0);
			JxcWaitHandleItems waitHandleItems = new JxcWaitHandleItems();
			waitHandleItems.setId(jxcWaitHandleItems.getId());
			//更新为已处理状态
			waitHandleItems.setIsDeleted(1);
			jxcWaitHandleItemsMapper.updateByPrimaryKeySelective(waitHandleItems);
		}
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}


}