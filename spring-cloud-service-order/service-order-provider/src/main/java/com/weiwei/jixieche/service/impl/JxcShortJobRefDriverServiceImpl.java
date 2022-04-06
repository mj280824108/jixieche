package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.CreditScoreTemplateConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.PushTemplateConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.service.JxcShortJobRefDriverService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jxcShortJobRefDriverService")
public class JxcShortJobRefDriverServiceImpl implements JxcShortJobRefDriverService {
	@Resource
	private JxcShortJobRefDriverMapper jxcShortJobRefDriverMapper;

	@Resource
	private JxcDriverRefOwnerMapper jxcDriverRefOwnerMapper;

	@Resource
	private JxcShortJobMapper jxcShortJobMapper;

	@Resource
	private DriverMapper driverMapper;

	@Resource
	private JxcClockInOutPairMapper jxcClockInOutPairMapper;

	@Resource
	private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

	@Resource
	private JxcUserFeign jxcUserFeign;

	@Resource
	private JxcClockPairMapper jxcClockPairMapper;

	@Resource
	private JxcScoreMapper jxcScoreMapper;

	@Autowired
	private JxcProjectOrderMapper jxcProjectOrderMapper;

	@Autowired
	private JpushMsgFeign jpushMsgFeign;

	@Resource
	private JxcMachineRouteMapper jxcMachineRouteMapper;

	@Resource
	private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

	@Autowired
	private JxcClockInOutRecordService jxcClockInOutRecordService;

	/**
	 * 前端分页查询兼职职位与司机的关联表
	 *
	 * @param pageNo               分页索引
	 * @param pageSize             每页显示数量
	 * @param jxcShortJobRefDriver 查询条件
	 * @return
	 */
	@Override
	public ResponseMessage<JxcShortJobRefDriver> findByPageForFront(Integer pageNo, Integer pageSize, JxcShortJobRefDriver jxcShortJobRefDriver) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(pageNo, pageSize);
		List<JxcShortJobRefDriver> list = this.jxcShortJobRefDriverMapper.selectListByConditions(jxcShortJobRefDriver);
		PageInfo<JxcShortJobRefDriver> page = new PageInfo<>(list);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 * 司机-工作管理
	 *
	 * @param shortJobVo 查询条件
	 * @return
	 * @author liuy
	 * @date 2019/8/16 15:06
	 */
	@Override
	public ResponseMessage getShortJobManager(ShortJobVo shortJobVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(shortJobVo.getPageNo(), shortJobVo.getPageSize());
		List<ShortJobVo> shortJobVoList = this.jxcShortJobRefDriverMapper.getShortJobManager(shortJobVo);
		shortJobVoList.stream().forEach(shortJobVo1 -> {
			//查询司机工作的是否有未支付的台班
			int count = this.jxcShortJobRefDriverMapper.checkShortJobPayState(shortJobVo);
			if (count > 0) {
				//显示待收款
				shortJobVo1.setPayFlag(ShortJobVo.payFlag.IS_PAY);
			}
		});
		PageInfo<ShortJobVo> page = new PageInfo<>(shortJobVoList);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 * 司机-工作管理详情
	 *
	 * @param shortJobId 查询条件
	 * @return
	 * @author liuy
	 * @date 2019/8/16 15:06
	 */
	@Override
	public ResponseMessage getShortJobDetail(AuthorizationUser user, Integer shortJobId) {
		if (shortJobId == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数shortJobId不能为空");
		}
		//台班
		BigDecimal machineTeam = null;
		//预计工资
		BigDecimal expectedPay = null;
		ShortJobVo shortJobVo = this.jxcShortJobRefDriverMapper.getShortJobDetail(user.getUserId(), shortJobId);
		if (shortJobVo != null) {
			//查出职位发布的工作天数即预计台班
			if (StringUtils.isNotBlank(shortJobVo.getJobWorkTime())) {
				machineTeam = new BigDecimal(shortJobVo.getJobWorkTime()).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			//查出所在城市的台班费
			JxcDriverTeamCostVo jxcDriverTeamCostVo = this.jxcShortJobMapper.queryDriverTeamCost(shortJobVo.getAreaId());
			if (jxcDriverTeamCostVo != null) {
				BigDecimal teamPrice = new BigDecimal(jxcDriverTeamCostVo.getDriverTeamPrice());
				if (machineTeam != null) {
					expectedPay = machineTeam.multiply(teamPrice);
					shortJobVo.setExpectedPay(expectedPay);
				}
			}
			//工作区域名称
			if (shortJobVo.getAreaId() != null) {
				JxcAreaVo jxcAreaVo = driverMapper.queryCityById(shortJobVo.getAreaId());
				if (jxcAreaVo != null) {
					shortJobVo.setWorkArea(jxcAreaVo.getProvinceName() + jxcAreaVo.getCityName());
				}
			}
			//查询是否有未支付的
			int count = jxcClockPairMapper.getCountPayByDriverId(shortJobVo.getDriverUserId(), shortJobId);
			if (count > 0) {
				shortJobVo.setState(6);
			}
			//查询是否评价过
			JxcScore jxcScore = new JxcScore();
			jxcScore.setOrderId(shortJobId.toString());
			jxcScore.setSourceId(user.getUserId());
			List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
			//评价状态(1:已评价; 0:未评价)
			if (CollectionUtils.isEmpty(jxcScores)) {
				shortJobVo.setEvaluationStatus(0);
			} else {
				shortJobVo.setEvaluationStatus(1);
			}
		} else {
			return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(), "查询司机工作职位不存在");
		}
		return new ResponseMessage(shortJobVo);
	}

	/**
	 * 司机取消订单
	 *
	 * @param driverUserId 司机用户ID
	 * @param shortJobId   职位ID
	 * @return
	 * @author liuy
	 * @date 2019/8/17 9:11
	 */
	@Override
	public ResponseMessage driverCancelOrder(Integer driverUserId, Integer shortJobId) {
		if (driverUserId == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数driverUserId不能为空");
		}
		if (shortJobId == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数shortJobId不能为空");
		}
		//兼职职位信息
		JxcShortJob jxcShortJob = this.jxcShortJobMapper.selectByPrimaryKey(shortJobId);
		if(jxcShortJob != null) {
			//更新兼职职位状态为辞职（司机主动取消）
			this.jxcShortJobRefDriverMapper.deleteByShortIdUserId(driverUserId, shortJobId);

			//进行中状态时
			if(DateUtils.differentDays(new Date(), jxcShortJob.getWorkTimeEnd()) >= 0) {
				//检查司机是否正在跑趟中
				Long routeId = jxcMachineRouteMapper.checkDriverRun(driverUserId);
				if (routeId != null) {
					return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "正在跑趟中，不能取消该职位!");
				}

				//检查司机是否正在上班中
				Integer count = driverMapper.getDriverWorkStateById(driverUserId);
				if (count > 0) {
					JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
					jxcClockInOutRecord.setShortJobId(shortJobId);
					jxcClockInOutRecord.setDriverId(driverUserId);
					jxcClockInOutRecord.setClockAddress("机主强制补下班卡");
					//查询司机是否绑定车辆
					JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(driverUserId);
					if (jxcMachine != null) {
						jxcClockInOutRecord.setMachineId(jxcMachine.getId());
					}
					//下班打卡
					jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
				}

				//先解绑机械与司机关系
				jxcMachineRefDriverMapper.updateByDriverId(driverUserId);

				//查询司机是否有未支付
				Integer countPayByDriverId = jxcClockPairMapper.getCountPayByDriverId(driverUserId, shortJobId);
				if(countPayByDriverId > 0){
					//解雇司机
					jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(jxcShortJob.getOwnerId(), driverUserId, JxcDriverRefOwner.DriverDelState.FIRE_DELETE, shortJobId);
				}else{
					//删除司机
					jxcDriverRefOwnerMapper.updateByOwnerIdAndDriverId(jxcShortJob.getOwnerId(), driverUserId, JxcDriverRefOwner.DriverDelState.IS_DELETE, shortJobId);
				}
			}else{
				//直接删除数据
				jxcShortJobRefDriverMapper.delShortJobRefDriverById(shortJobId, driverUserId);
			}

			JxcShortJob updateJob = new JxcShortJob();
			updateJob.setId(shortJobId);
			//先判断一下是否过了有效时间  如果没有过有效时间 再把状态改为开启
			if (jxcShortJob.getEffectiveDateEnd().getTime() > System.currentTimeMillis()) {
				if (jxcShortJob.getStatus().equals(JxcShortJob.status.FULL)) {
					updateJob.setStatus(JxcShortJob.status.START);
					this.jxcShortJobMapper.updateByPrimaryKeySelective(updateJob);
				}
			}

			JxcCreditScoreTemplate effectiveById = jxcProjectOrderMapper.getEffectiveById(3);
			if (effectiveById != null) {
				//拿到规则时间
				Integer condition = effectiveById.getCondition();
				if ((jxcShortJob.getWorkTimeStart().getTime() - System.currentTimeMillis()) < condition * 60 * 60 * 1000) {
					//添加信用分记录
					UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
					userCreditScoreVo.setUserId(driverUserId);
					userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_DRIVER_CANCEL_SHORT_JOB.getId());
					jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
				}
			}
			//添加信用分记录
			UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
			userCreditScoreVo.setUserId(driverUserId);
			userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_DRIVER_CANCEL_SHORT_JOB.getId());
			jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
		}else{
			return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(),"数据不存在");
		}
		return new ResponseMessage();
	}

	/**
	 * 添加兼职职位与司机的关联表
	 *
	 * @param jxcShortJobRefDriver
	 * @return
	 */
	@Override
	public ResponseMessage<JxcShortJobRefDriver> addObj(JxcShortJobRefDriver jxcShortJobRefDriver) {
		//检查机主是否已经绑定了该司机为我的司机
		JxcDriverRefOwner jxcChildRefOwner = new JxcDriverRefOwner();
		jxcChildRefOwner.setDriverId(jxcShortJobRefDriver.getDriverId());
		jxcChildRefOwner.setDelState(1);
		//司机类型：我的司机
		jxcChildRefOwner.setDriverType(DriverVo.driverType.OWNERDRIVER);
		List<JxcDriverRefOwner> list = jxcDriverRefOwnerMapper.selectListByConditions(jxcChildRefOwner);
		if (!CollectionUtils.isEmpty(list)) {
			return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "您被机主添加为司机，请解绑后再接单，且接兼职订单期间不可被其他机主绑为司机");
		}

		//判断临时职位是否已经过期或者招满
		JxcShortJob jxcShortJob = this.jxcShortJobMapper.selectByPrimaryKey(jxcShortJobRefDriver.getShortJobId());
		if (jxcShortJob.getStatus() == 1 || jxcShortJob.getStatus() == 2 || jxcShortJob.getJobIsDelete() == 1) {
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "该临时岗位招满已失效或删除！");
		}
		//判断该职位是否已经被该司机接过  只要接过 不管是自己取消还是被取消还是被拒绝或被解雇  都不能再进行接单了
		Integer count = jxcShortJobRefDriverMapper.checkDriverTookOver(jxcShortJobRefDriver.getShortJobId(), jxcShortJobRefDriver.getDriverId());
		if (count > 0) {
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "由于您被该职位雇主拒绝，所以不能再次接受该职位！");
		}

		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcShortJobRefDriverMapper.insertSelective(jxcShortJobRefDriver);

		//查询职位已招聘人数
		int shortJobCount = this.jxcShortJobMapper.getShortJobCount(jxcShortJobRefDriver.getShortJobId());
		if (shortJobCount == jxcShortJob.getNeedNum()) {
			jxcShortJob = new JxcShortJob();
			//已招满
			jxcShortJob.setStatus(JxcShortJob.status.FULL);
			jxcShortJob.setId(jxcShortJobRefDriver.getShortJobId());
			jxcShortJobMapper.updateByPrimaryKeySelective(jxcShortJob);
		}

		//通知机主
		JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
		jpushCustomMsgVo.setUserId(jxcShortJobRefDriver.getDriverId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_SHORT_DRIVER_RECEIPT_SUCCESS.getId());
		JSONObject js = new JSONObject();
		js.put("startDate", DateUtils.format(jxcShortJob.getWorkTimeStart()));
		js.put("endDate", DateUtils.format(jxcShortJob.getWorkTimeEnd()));
		js.put("jobId", jxcShortJob.getId());
		jpushCustomMsgVo.setParam(js);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcShortJobRefDriver.getDriverId());
		if (userInfoVoResponseMessage != null && userInfoVoResponseMessage.getData() != null) {
			UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
			jpushTemplateVo.setParams(js);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_SHORT_DRIVER_RECEIPT_SUCCESS.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 修改兼职职位与司机的关联表
	 *
	 * @param jxcShortJobRefDriver
	 * @return
	 */
	@Override
	public ResponseMessage<JxcShortJobRefDriver> modifyObj(JxcShortJobRefDriver jxcShortJobRefDriver) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcShortJobRefDriverMapper.updateByPrimaryKeySelective(jxcShortJobRefDriver);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
		return result;
	}

	/**
	 * 根据ID查询兼职职位与司机的关联表
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ResponseMessage<JxcShortJobRefDriver> queryObjById(int id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcShortJobRefDriver model = this.jxcShortJobRefDriverMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
		result.setData(model);
		return result;
	}

	/**
	 * 工作管理--日历打标记
	 *
	 * @param user
	 * @param jobId
	 * @param yearMonth
	 * @return
	 */
	@Override
	public ResponseMessage singCalendar(AuthorizationUser user, Integer driverUserId, Integer jobId, String yearMonth) {
		if (driverUserId == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机id不能为空，鉴权框架异常");
		}

		if (null == jobId) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "职位ID不能为空");
		}

		if (StringUtils.isBlank(yearMonth)) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "选择的月份不正确");
		}
		Map<String, Object> params = new HashMap<String, Object>() {{
			put("driverId", driverUserId);
			put("yearMonth", yearMonth);
			put("jobId", jobId);
		}};
		List<Map<String, Object>> list = jxcClockInOutPairMapper.dateListWhichHasRouteFinishedOrError(params);
		return new ResponseMessage(list);
	}

}