package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.PushTemplateConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.service.JxcMachineRefDriverService;
import com.weiwei.jixieche.service.JxcWaitHandleItemsService;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.DriverVo;
import com.weiwei.jixieche.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 机械与司机或子账号绑定关系
 * @Author: liuy
 * @Date: 2019/8/14 9:38
 */
@Service("jxcMachineRefDriverService")
public class JxcMachineRefDriverServiceImpl implements JxcMachineRefDriverService {
	@Resource
	private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

	@Resource
	private JxcMachineRouteMapper jxcMachineRouteMapper;

	@Resource
	private DriverMapper driverMapper;

	@Resource
	private JxcMachineMapper jxcMachineMapper;

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

	@Autowired
	private JxcClockInOutRecordService jxcClockInOutRecordService;

	@Resource
	private JxcDriverRefOwnerMapper jxcDriverRefOwnerMapper;

	@Resource
	private JxcClockInOutPairMapper jxcClockInOutPairMapper;

	@Resource
	private JxcWaitHandleItemsService jxcWaitHandleItemsService;

	@Autowired
	private JpushMsgFeign jpushMsgFeign;

	@Autowired
	private JxcUserFeign jxcUserFeign;

	/**
	 * 添加机械与司机或子账号绑定关系表
	 *
	 * @param t
	 * @return
	 * @author liuy
	 * @date 2019/8/14 9:42
	 */
	@Override
	@Transactional
	public ResponseMessage<JxcMachineRefDriver> addObj(JxcMachineRefDriver t) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = 0;

		//车辆绑定司机判断超过5个给出提示
		List<DriverVo> driverList = driverMapper.getDriverListByMachineId(t.getMachineId());
		if(!CollectionUtils.isEmpty(driverList)){
			if(driverList.size() >= 5){
				return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "一个机械车最多绑定5个司机");
			}
		}

		//mark 当前司机正在上班所绑定的机主不是当前机主时 提示联系司机打下班卡
		Integer ownerUserId = jxcClockInOutPairMapper.getOwnerUserIdByDriverId(t.getUserId());
		if(ownerUserId != null && !t.getOwnerUserId().equals(ownerUserId)){
			return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "司机还在上班中");
		}

		//检查是否已经绑定过其他机主
		JxcMachineRefDriver machineRefDriver = new JxcMachineRefDriver();
		machineRefDriver.setBindState(JxcMachineRefDriver.bindState.IS_BIND);
		machineRefDriver.setUserId(t.getUserId());
		List<JxcMachineRefDriver> jxcMachineRefDriverList = jxcMachineRefDriverMapper.selectListByConditions(machineRefDriver);
		if(!CollectionUtils.isEmpty(jxcMachineRefDriverList)){
			//这个司机的手机号码绑定在其他机主的车辆上，请先解绑后再进行绑定
			return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"该司机账号已绑定其他机械");
		}

		//查询机械与司机是否存在绑定关系
		JxcMachineRefDriver jxcMachineRefDriver = this.jxcMachineRefDriverMapper.queryByMachineIdAndDriverId(t);
		if (jxcMachineRefDriver != null) {
			return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"已存在绑定关系");
		} else {
			//绑定时间
			t.setBindTime(new Date());
			res = this.jxcMachineRefDriverMapper.insertSelective(t);
		}

		JxcMachine jxcMachine = jxcMachineMapper.selectByPrimaryKey(t.getMachineId());
		if (jxcMachine != null) {
			//绑定司机时如果车辆状态为无账号时，则更新为空闲中
			if (jxcMachine.getStatus().equals(JxcMachine.carStatus.NO_ACCOUNT)) {
				JxcMachine jxcMachine1 = new JxcMachine();
				jxcMachine1.setId(jxcMachine.getId());
				jxcMachine1.setStatus(JxcMachine.carStatus.IN_IDLE);
				jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine1);
			}
		}

		//查询机械未绑定司机是否有待办事项
		JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
		jxcWaitHandleItems.setMachineId(t.getMachineId());
		jxcWaitHandleItems.setUserId(t.getOwnerUserId());
		//未添加司机
		jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE12);
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
		//发送通知，刷新司机工作台
		JpushMessageVo jpushTemplateVo = new JpushMessageVo();
		jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(t.getUserId());
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

		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 解绑机械与司机关系
	 *
	 * @param t
	 * @return
	 * @author liuy
	 * @date 2019/8/14 9:45
	 */
	@Override
	public ResponseMessage<JxcMachineRefDriver> modifyObj(JxcMachineRefDriver t) {
		//检查司机是否正在跑趟中
		Long routeId = jxcMachineRouteMapper.checkDriverRun(t.getUserId());
		if(routeId != null){
			return new ResponseMessage("-1");
		}
		Integer driverId = t.getUserId();
		//检查司机是否正在上班中
		Integer count = driverMapper.getDriverWorkStateById(driverId);
		if(count > 0){
			JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
			jxcClockInOutRecord.setDriverId(driverId);
			jxcClockInOutRecord.setClockAddress("强制补下班卡");
			jxcClockInOutRecord.setMachineId(t.getMachineId());
			//下班打卡
			jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
		}

		ResponseMessage result = ResponseMessageFactory.newInstance();
		t.setBindState(JxcMachineRefDriver.bindState.NOT_BIND);
		t.setUnbindTime(new Date());
		int res = this.jxcMachineRefDriverMapper.updateByMachineAndDriver(t);
		if(res > 0){
			//mark 检查该机械是否存在进行中的订单未绑定司机，有则写入待办事项
			jxcWaitHandleItemsService.getOrderRunList(t.getMachineId());
		}
		//发送通知，刷新司机工作台
		JpushMessageVo jpushTemplateVo = new JpushMessageVo();
		jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(t.getUserId());
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
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
		return result;
	}

	/**
	 * 根据ID查询机械与司机或子账号绑定关系表
	 *
	 * @param id
	 * @return
	 * @author liuy
	 * @date 2019/8/14 9:46
	 */
	@Override
	public ResponseMessage<JxcMachineRefDriver> queryObjById(int id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcMachineRefDriver model = this.jxcMachineRefDriverMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
		result.setData(model);
		return result;
	}

	/**
	 * 绑车记录
	 *
	 * @param jxcMachineRefDriver
	 * @return
	 * @author liuy
	 * @date 2019/8/22 17:25
	 */
	@Override
	public ResponseMessage<JxcMachineRefDriver> getMachineRefDriverList(JxcMachineRefDriver jxcMachineRefDriver) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(jxcMachineRefDriver.getPageNo(), jxcMachineRefDriver.getPageSize());
		List<JxcMachineRefDriver> list = this.jxcMachineRefDriverMapper.getMachineRefDriverList(jxcMachineRefDriver.getUserId());
		PageInfo<JxcMachineRefDriver> page = new PageInfo<>(list);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 * 根据机械ID查询所绑定的司机列表
	 *
	 * @param machineId
	 * @return
	 * @author liuy
	 * @date 2019/8/24 14:35
	 */
	@Override
	public ResponseMessage getDriverListByMachineId(Integer machineId) {
		//车辆绑定司机信息
		List<DriverVo> driverList = driverMapper.getDriverListByMachineId(machineId);
		if (!CollectionUtils.isEmpty(driverList)) {
			for (DriverVo driverVo : driverList) {
				//查询司机工作状态
				int count = driverMapper.getDriverWorkStateById(driverVo.getDriverId());
				if (count > 0) {
					driverVo.setWorkState(1);
				} else {
					driverVo.setWorkState(0);
				}
			}
		}
		return new ResponseMessage(driverList);
	}

	/**
	 * 查询司机用户所绑定的车牌号码
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/9/21 11:24
	 */
	@Override
	public ResponseMessage getMachineCarNoByUserId(Integer userId) {
		String machineCarNo = "";
		JxcMachine jxcMachine = jxcMachineRefDriverMapper.getMachineCarNoById(userId);
		if(jxcMachine != null){
			machineCarNo = jxcMachine.getMachineCardNo();
		}
		return new ResponseMessage(machineCarNo);
	}

	/**
	 * 绑车记录-解绑
	 * @author  liuy
	 * @param authorizationUser
	 * @return
	 * @date    2019/10/7 17:19
	 */
	@Override
	@Transactional
	public ResponseMessage updateDriverAndOwner(AuthorizationUser authorizationUser, Integer ownerUserId, Integer machineId) {
		if(null == ownerUserId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "机主用户ID不能为空");
		}
		if(null == authorizationUser.getUserId()){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机用户ID不能为空");
		}
		//检查司机是否正在跑趟中
		Long routeId = jxcMachineRouteMapper.checkDriverRun(authorizationUser.getUserId());
		if(routeId != null){
			return new ResponseMessage("-1");
		}
		Integer driverId = authorizationUser.getUserId();
		//检查司机是否正在上班中
		Integer count = driverMapper.getDriverWorkStateById(driverId);
		if(count > 0){
			JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
			jxcClockInOutRecord.setDriverId(driverId);
			jxcClockInOutRecord.setClockAddress("强制补下班卡");
			//查询司机是否绑定车辆
			JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(driverId);
			if(jxcMachine != null){
				jxcClockInOutRecord.setMachineId(jxcMachine.getId());
			}
			//下班打卡
			jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
		}
		//先解绑机械与司机关系
		jxcMachineRefDriverMapper.updateByDriverId(driverId);

		//删除司机
		jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(ownerUserId, driverId, JxcDriverRefOwner.DriverDelState.IS_DELETE, null);

		return new ResponseMessage();
	}

	/**
	 * 绑车记录-查询司机已绑定车信息和绑定的机主信息
	 * @author  liuy
	 * @param authorizationUser
	 * @return
	 * @date    2019/10/7 18:07
	 */
	@Override
	public ResponseMessage getDriverBindMachineById(AuthorizationUser authorizationUser) {
		JxcMachineRefDriver jxcMachineRefDriver = jxcMachineRefDriverMapper.getMachineRefDriverById(authorizationUser.getUserId());
		if(null == jxcMachineRefDriver){
			jxcMachineRefDriver = new JxcMachineRefDriver();
			jxcMachineRefDriver.setOwnerBindState(0);
		}
		return new ResponseMessage(jxcMachineRefDriver);
	}
}