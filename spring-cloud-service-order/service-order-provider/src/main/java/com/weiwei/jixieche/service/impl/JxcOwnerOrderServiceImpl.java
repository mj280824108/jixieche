package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcOpenCityFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcOwnerOrderService;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.StatisticsUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 机主订单管理
 * @Author: liuy
 * @Date: 2019/8/20 15:16
 */
@Service("jxcOwnerOrderService")
public class JxcOwnerOrderServiceImpl implements JxcOwnerOrderService {
	@Resource
	private JxcOwnerOrderMapper jxcOwnerOrderMapper;

	@Resource
	private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

	@Resource
	private JxcProjectOrderMapper jxcProjectOrderMapper;

	@Autowired
	private JxcUserFeign jxcUserFeign;

	@Resource
	private JxcMachineRouteMapper jxcMachineRouteMapper;

	@Resource
	private JxcMachineMapper jxcMachineMapper;

	@Resource
	private JxcDriverRefOwnerMapper jxcDriverRefOwnerMapper;

	@Resource
	private JxcScoreMapper jxcScoreMapper;

	@Resource
	private StatisticsUtils statisticsUtils;

	@Resource
	private JxcOwnerApplyQuitMapper jxcOwnerApplyQuitMapper;

	@Resource
	private JxcDriverHandoverMapper jxcDriverHandoverMapper;

	@Resource
	private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

	@Resource
	private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

	@Resource
	private JxcSiteMapper jxcSiteMapper;

	@Resource
	private RedisUtil redisUtil;

	@Resource
	private JxcShortJobRefDriverMapper jxcShortJobRefDriverMapper;

	@Autowired
	private JxcOpenCityFeign jxcOpenCityFeign;

	@Autowired
	private JpushMsgFeign jpushMsgFeign;

	/**
	 * 添加机主订单表
	 *
	 * @param jxcOwnerOrder
	 * @return
	 */
	@Override
	public ResponseMessage<JxcOwnerOrder> addObj(JxcOwnerOrder jxcOwnerOrder) {
		return new ResponseMessage<>();
	}

	/**
	 * 机主订单-请假
	 *
	 * @param jxcOwnerOrder
	 * @return
	 */
	@Override
	public ResponseMessage<JxcOwnerOrder> modifyObj(JxcOwnerOrder jxcOwnerOrder) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcOwnerOrder.setLeaveState(1);
		int res = this.jxcOwnerOrderMapper.updateByPrimaryKeySelective(jxcOwnerOrder);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
		//站内信以及推送
		JxcOwnerOrderDetailVo ownerOrderDetail = jxcOwnerOrderMapper.getOwnerOrderDetail(jxcOwnerOrder.getId());
		JxcProjectOrderVo jxcProjectOrderVo = jxcProjectOrderMapper.selectJxcProjectOrderById(ownerOrderDetail.getProjectOrderId());
		//站内信以及推送通知给承租方
		JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
		jpushCustomMsgVo.setUserId(jxcProjectOrderVo.getUserId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_APPLY_LEAVE.getId());
		JSONObject js = new JSONObject();
		js.put("machineCardNo",ownerOrderDetail.getMachineCarNo());
		js.put("reason",jxcOwnerOrder.getLeaveReason());
		js.put("startDate",DateUtils.format(jxcOwnerOrder.getLeaveStart()));
		js.put("endDate",DateUtils.format(jxcOwnerOrder.getLeaveEnd()));
		js.put("orderId",jxcProjectOrderVo.getId());
		jpushCustomMsgVo.setParam(js);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		//推送
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcProjectOrderVo.getUserId());
		if (userInfoVoResponseMessage.getData() != null) {
			UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setParams(js);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_APPLY_LEAVE.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}
		return result;
	}

	/**
	 * 根据ID查询机主订单表
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ResponseMessage<JxcOwnerOrder> queryObjById(int id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcOwnerOrder model = this.jxcOwnerOrderMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
		result.setData(model);
		return result;
	}

	/**
	 * 机主订单管理列表
	 *
	 * @param user
	 * @param lastPageLastId
	 * @return
	 * @author liuy
	 * @date 2019/8/20 15:45
	 */
	@Override
	public ResponseMessage getOwnerOrderList(AuthorizationUser user, String lastPageLastId, Integer orderType, Integer machineId) {
		if (orderType == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "订单类型不能为空");
		}
		Map<String, Object> resultMap = new HashMap<>();
		List<JxcOwnerOrderVo> ownerOrderVoList = new ArrayList<>();
		//机主用户ID
		Integer ownerUserId = null;
		//司机用户或子账号时获取司机所绑定的机械ID
		if(user.getRoleId() == 2){
			ownerUserId = user.getUserId();

		}else{
			//子账号或司机账号时没有绑定机械
			JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(user.getUserId());
			if(jxcMachine != null) {
				machineId = jxcMachine.getId();
			}else{
				resultMap.put("list", ownerOrderVoList);
				return new ResponseMessage(resultMap);
			}
		}
		PageHelper.clearPage();
		ownerOrderVoList = this.jxcOwnerOrderMapper.getOwnerOrderList(ownerUserId, lastPageLastId, orderType, machineId);
		if (!CollectionUtils.isEmpty(ownerOrderVoList)) {
			ownerOrderVoList.stream().forEach(jxcOwnerOrderVo -> {
				//查询是否有未垫付的异常行程，有的话加“待收款”标记
				/*Integer waitPayFlag = this.jxcOwnerOrderMapper.countWaitPayRoute(jxcOwnerOrderVo.getOwnerOrderId());
				if (waitPayFlag != null && waitPayFlag > 0) {
					jxcOwnerOrderVo.setWaitPayFlag(JxcOwnerOrderVo.waitPayFlag.IS_WAITPAYFLAG);
				}*/
				//查询是否评价过
				JxcScore jxcScore = new JxcScore();
				jxcScore.setOrderId(jxcOwnerOrderVo.getOwnerOrderId().toString());
				jxcScore.setSourceId(user.getUserId());
				List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
				//评价状态(1:已评价; 0:未评价)
				if(CollectionUtils.isEmpty(jxcScores)){
					jxcOwnerOrderVo.setEvaluationStatus(0);
				}else{
					jxcOwnerOrderVo.setEvaluationStatus(1);
				}
				int ownerOrderState = jxcOwnerOrderVo.getOwnerOrderState();
				if(orderType.equals(1)){
					if (ownerOrderState == 0) {
						jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_RECEIVED);
					}
				}
				if(orderType.equals(2)){
					if (ownerOrderState == 2) {
						jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_PROCESSING);
						//查询是否是申请退出中状态
						List<JxcOwnerApplyQuit> jxcOwnerApplyQuits = jxcOwnerOrderMapper.selectJxcOwnerApplyQuitList(jxcOwnerOrderVo.getOwnerOrderId());
						if(jxcOwnerApplyQuits != null && jxcOwnerApplyQuits.size() > 0){
							for (JxcOwnerApplyQuit jxcOwnerApplyQuit : jxcOwnerApplyQuits){
								if(jxcOwnerApplyQuit.getApplyState().equals(0)){
									jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.APPLY_ING);
									break;
								}
							}
						}
					}else if(ownerOrderState == 3 || ownerOrderState == 4){
						jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.WAIT_PAY);
					}
				}
				if(orderType.equals(3)){
					//已取消
					if (ownerOrderState == 1 ) {
						jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_CANCELLED);
					}
					//已完结
					if (ownerOrderState == 3 || ownerOrderState == 4) {
						jxcOwnerOrderVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_FINISHED);
					}
				}
			});
			int hasNextPage = 0;
			if (ownerOrderVoList.size() == 10) {
				//mark 将最后一条数据作为下次查询的条件
				Long nextPageFirstId = this.jxcOwnerOrderMapper.hasNextPage(user.getUserId(), ownerOrderVoList.get(9).getOwnerOrderId().toString(), orderType, machineId);
				if (null != nextPageFirstId) {
					hasNextPage = 1;
					resultMap.put("lastPageLastId", ownerOrderVoList.get(9).getOwnerOrderId());
				}
			}
			resultMap.put("hasNextPage", hasNextPage);
		}
		resultMap.put("list", ownerOrderVoList);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 泥头车首页
	 *
	 * @param userId
	 * @return
	 * @author liuy
	 * @date 2019/8/22 10:14
	 */
	@Override
	public ResponseMessage getOwnerIndex(Integer userId) {
		JxcIndexVo jxcIndexVo = new JxcIndexVo();

		//职位类型1:全职; 2:兼职
		Integer jobType = 1;

		//查询是否有正在进行中的兼职职位
		PageHelper.clearPage();
		Integer shortJobId = jxcShortJobRefDriverMapper.getShortJobIdByDriverId(userId);
		if(null == shortJobId){
			JxcShortJobRefDriver jxcShortJobRefDriver = new JxcShortJobRefDriver();
			jxcShortJobRefDriver.setDriverId(userId);
			//已接单
			jxcShortJobRefDriver.setState(0);
			List<JxcShortJobRefDriver> shortJobRefDriverList = jxcShortJobRefDriverMapper.selectListByConditions(jxcShortJobRefDriver);
			if(!CollectionUtils.isEmpty(shortJobRefDriverList)){
				//标识不显示工作台
				jxcIndexVo.setStatus(JxcIndexVo.status.NO_CONFIRM);
				//标识兼职司机
				jxcIndexVo.setJobType(JxcIndexVo.isJobType.SHORTJOB);
				return new ResponseMessage(jxcIndexVo);
			}
		}else{
			//标识兼职司机
			jobType = JxcIndexVo.isJobType.SHORTJOB;
		}

		//司机是否绑定机主
		JxcDriverRefOwner jxcDriverRefOwner = new JxcDriverRefOwner();
		jxcDriverRefOwner.setDriverId(userId);
		jxcDriverRefOwner.setDelState(1);
		List<JxcDriverRefOwner> list = jxcDriverRefOwnerMapper.selectListByConditions(jxcDriverRefOwner);
		if(CollectionUtils.isEmpty(list)){
			//标识不显示工作台
			jxcIndexVo.setStatus(JxcIndexVo.status.NO_CONFIRM);
			jxcIndexVo.setJobType(jobType);
			return new ResponseMessage(jxcIndexVo);
		}else{
			jxcDriverRefOwner = list.get(0);
			ResponseMessage responseMessage = jxcUserFeign.queryUserInfo(jxcDriverRefOwner.getOwnerId());
			if (responseMessage.getData() != null) {
				UserInfoVo userInfoVo = (UserInfoVo) responseMessage.getData();
				//机主电话
				jxcIndexVo.setOwnerPhone(userInfoVo.getPhone());
			}
		}


		//1.检查司机是否绑定车辆信息
		JxcMachine jxcMachine = this.jxcMachineRefDriverMapper.getMachineCarNoById(userId);
		if (jxcMachine != null && StringUtils.isNotBlank(jxcMachine.getMachineCardNo())) {
			//机械ID
			Integer machineId = jxcMachine.getId();
			//2.查询是否有正在进行中的订单
			jxcIndexVo = jxcOwnerOrderMapper.getOwnerOrderByMachineId(machineId);
			if (jxcIndexVo != null) {
				//进行中
				jxcIndexVo.setStatus(JxcIndexVo.status.HAVE_IN_ORDER);
				//3.正在进行中的订单是否有人在上班中,有的话返回上班时间
				JxcIndexVo jxcIndexVo1 = jxcOwnerOrderMapper.getWorkInByMachineId(machineId);
				if (jxcIndexVo1 != null) {
					//判断当前上班的司机是否跟当前的用户ID一致
					if (userId.equals(jxcIndexVo1.getDriverId())) {
						//跟当前用户一致的话显示上班时间
						jxcIndexVo.setIsWork(JxcIndexVo.isWork.ISWORK_YES);
						jxcIndexVo.setWorkInTime(jxcIndexVo1.getWorkInTime());

						//正在跑趟中的行程ID
						Long routeId = jxcMachineRouteMapper.checkMachineRun(machineId);
						if(routeId != null){
							jxcIndexVo.setRouteId(routeId);
							JxcMachineRoute jxcMachineRoute = jxcMachineRouteMapper.getById(routeId);
							if(jxcMachineRoute != null){
								//发卡类型: 1:实体卡; 2:消纳券
								jxcIndexVo.setCardType(jxcMachineRoute.getCardType());
							}
						}
					} else {
						//标识有其他司机正在上班中
						jxcIndexVo.setIsWorkDriver(JxcIndexVo.isWorkDriver.ISWORKDRIVER_YES);
						ResponseMessage responseMessage = jxcUserFeign.queryUserInfo(jxcIndexVo1.getDriverId());
						if (responseMessage.getData() != null) {
							UserInfoVo userInfoVo = (UserInfoVo) responseMessage.getData();
							//正在上班中的司机电话
							jxcIndexVo.setDriverWorkPhone(userInfoVo.getPhone());
						}
					}
				} else {
					//没有人正在上班中，直接打卡成功
				}
			}
			//没有正在进行中的订单时
			else {
				if(null == jxcIndexVo){
					jxcIndexVo = new JxcIndexVo();
				}
				//查询是否有已接订单
				PageHelper.clearPage();
				List<JxcOwnerOrderVo> ownerOrderVoList = this.jxcOwnerOrderMapper.getOwnerOrderList(null, null, 1, jxcMachine.getId());
				if (CollectionUtils.isEmpty(ownerOrderVoList)) {
					//查询可接单列表
					JxcRobOrderVo jxcRobOrderVo = new JxcRobOrderVo();
					jxcRobOrderVo.setAreaId(jxcMachine.getAreaCode());
					jxcRobOrderVo.setMachineId(jxcMachine.getId());
					ResponseMessage projectOrderList = getProjectOrderList(jxcRobOrderVo);
					if(projectOrderList.getData() != null){
						//可接单
						jxcIndexVo.setStatus(JxcIndexVo.status.ACCEPTABLE_ORDER);
					} else {
						//暂无可接订单
						jxcIndexVo.setStatus(JxcIndexVo.status.NO_ORDER);
					}
				} else {
					//已接单列表
					jxcIndexVo.setStatus(JxcIndexVo.status.ACCEPTED_ORDER);
				}
			}
			jxcIndexVo.setMachineCarNo(jxcMachine.getMachineCardNo());
			jxcIndexVo.setAreaId(jxcMachine.getAreaCode());
			jxcIndexVo.setMachineId(machineId);
			jxcIndexVo.setMode(jxcMachine.getMode());
		} else {
			//未绑定车辆
			jxcIndexVo.setStatus(JxcIndexVo.status.NO_MACHINE);
		}
		jxcIndexVo.setJobType(jobType);
		return new ResponseMessage(jxcIndexVo);
	}

	/**
	 * 接单列表
	 *
	 * @param jxcRobOrderVo
	 * @return
	 * @author liuy
	 * @date 2019/8/22 16:39
	 */
	@Override
	public ResponseMessage getProjectOrderList(JxcRobOrderVo jxcRobOrderVo) {
		if (null == jxcRobOrderVo.getAreaId()) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数区域ID不能为空");
		}
		//PageHelper.startPage(jxcRobOrderVo.getPageNo(), jxcRobOrderVo.getPageSize());
		List<JxcRobOrderVo> jxcRobOrderVoList = this.jxcOwnerOrderMapper.getProjectOrderList(jxcRobOrderVo.getAreaId());
        List<Long> projectOrderIdList = jxcOwnerOrderMapper.queryProjectOrderIdList(jxcRobOrderVo.getMachineId());
        //去除被解雇的以及退出的
        List<Integer> indexList = new ArrayList<>();
        if(projectOrderIdList != null && projectOrderIdList.size() >0){
            for(int i = 0 ; i <= jxcRobOrderVoList.size() - 1; i++){
                for (Long projectOrderId : projectOrderIdList){
                    if(projectOrderId.compareTo(jxcRobOrderVoList.get(i).getProjectOrderId()) == 0){
                        indexList.add(i);
                    }
                }
            }
        }
        if(indexList != null && indexList.size() > 0){
            for(Integer index : indexList){
                jxcRobOrderVoList.remove(jxcRobOrderVoList.get(index));
            }
        }
		List<JxcRobOrderVo> resultList = new ArrayList<>();
        if(jxcRobOrderVoList != null && jxcRobOrderVoList.size() >0) {
            for (JxcRobOrderVo jxcRobOrderVo1 : jxcRobOrderVoList) {
                if (jxcRobOrderVo1.getFixedPrice() != null) {
                    Integer toOwnerAmount = jxcOpenCityFeign.getToOwnerAmount(jxcRobOrderVo1.getProjectOrderId(), jxcRobOrderVo1.getFixedPrice().intValue());
                    jxcRobOrderVo1.setFixedPrice(DataTypeEmptyUtils.emptyBigDecimalMoney(new BigDecimal(toOwnerAmount)));
                }
                if (jxcRobOrderVo1.getEstimatePrice() != null) {
                    Integer toOwnerAmount = jxcOpenCityFeign.getToOwnerAmount(jxcRobOrderVo1.getProjectOrderId(), jxcRobOrderVo1.getEstimatePrice().intValue());
                    jxcRobOrderVo1.setEstimatePrice(DataTypeEmptyUtils.emptyBigDecimalMoney(new BigDecimal(toOwnerAmount)));
                }
                if (jxcRobOrderVo1.getStartDate() != null && jxcRobOrderVo1.getEndDate() != null) {
                    //承租方订单详情
                    JxcProjectOrderVo jxcProjectOrderVo = this.jxcProjectOrderMapper.selectJxcProjectOrderById(jxcRobOrderVo1.getProjectOrderId());
                    //查询已接单车辆数
                    Integer acceptedCarCount = jxcProjectOrderMapper.queryAcceptedCarCount(jxcRobOrderVo1.getProjectOrderId());
                    int estimateMachineCount = jxcProjectOrderVo.getEstimateMachineCount();
                    if (acceptedCarCount >= estimateMachineCount) {
                        continue;
                    }

                    //可接单开始日期和结束日期
                    Date projectStartDate = DateUtils.parseYMD(jxcRobOrderVo1.getStartDate());
                    Date projectEndDate = DateUtils.parseYMD(jxcRobOrderVo1.getEndDate());
                    //查询已接单列表
                    List<Map<String, Object>> list = jxcOwnerOrderMapper.getOwnerListByMachineId(jxcRobOrderVo.getMachineId());
                    if (!CollectionUtils.isEmpty(list)) {
                        boolean flag = true;
                        for (Map map : list) {
                            if (map.get("startDate") != null && map.get("endDate") != null) {
                                //已接单开始日期和结束日期
                                Date startDate = DateUtils.parseYMD(map.get("startDate").toString());
                                Date endDate = DateUtils.parseYMD(map.get("endDate").toString());
                                if (!DateUtils.belongCalendarBru(projectStartDate, projectEndDate, startDate, endDate)) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if (flag) {
                            resultList.add(jxcRobOrderVo1);
                        }
                    } else {
                        resultList.add(jxcRobOrderVo1);
                    }
                }
            }
        }
		PageInfo<JxcRobOrderVo> page = new PageInfo<>(resultList);
		return new ResponseMessage(new PageUtils<>(page).getPageViewDatatable());
	}

	/**
	 * 机主订单详情
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/22 19:39
	 */
	@Override
	public ResponseMessage getOwnerOrderDetail(Long ownerOrderId) {
		if (null == ownerOrderId) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数订单ID不能为空");
		}
		JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo = jxcOwnerOrderMapper.getOwnerOrderDetail(ownerOrderId);
		if (jxcOwnerOrderDetailVo != null) {
			if(jxcOwnerOrderDetailVo.getOrderPrice() != null && jxcOwnerOrderDetailVo.getOrderPrice().compareTo(BigDecimal.ZERO) > 0){
				Integer toOwnerAmount = jxcOpenCityFeign.getToOwnerAmount(jxcOwnerOrderDetailVo.getProjectOrderId(), jxcOwnerOrderDetailVo.getOrderPrice().intValue());
				jxcOwnerOrderDetailVo.setOrderPrice(DataTypeEmptyUtils.emptyBigDecimalMoney(new BigDecimal(toOwnerAmount)));
			}else {
				jxcOwnerOrderDetailVo.setOrderPrice(BigDecimal.ZERO);
			}
			//根据订单ID统计已完成趟数
			Map<String, Object> map = jxcMachineRouteMapper.getTotalCountByOrderId(ownerOrderId, 1);
			if (map != null && map.get("totalCount") != null) {
				jxcOwnerOrderDetailVo.setCompleteCount(Integer.parseInt(map.get("totalCount").toString()));
				if (map.get("ownerAmount") != null) {
					BigDecimal bigDecimal = new BigDecimal(map.get("ownerAmount").toString());
					jxcOwnerOrderDetailVo.setPayAmount(DataTypeEmptyUtils.emptyBigDecimalMoney(bigDecimal));
				}
			}
			//根据订单ID统计异常趟数
			map = jxcMachineRouteMapper.getTotalCountByOrderId(ownerOrderId, 2);
			if (map != null && map.get("totalCount") != null) {
				jxcOwnerOrderDetailVo.setAbnormalCount(Integer.parseInt(map.get("totalCount").toString()));
			}
			if(jxcOwnerOrderDetailVo.getDriverId() != null){
				ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcOwnerOrderDetailVo.getDriverId());
				if(userInfoVoResponseMessage.getData() != null){
					UserInfoVo data = userInfoVoResponseMessage.getData();
					jxcOwnerOrderDetailVo.setDriverName(data.getRealName());
					jxcOwnerOrderDetailVo.setDriverPhone(data.getPhone());
				}
			}

			//查询是否评价过
			JxcScore jxcScore = new JxcScore();
			jxcScore.setOrderId(jxcOwnerOrderDetailVo.getOwnerOrderId().toString());
			jxcScore.setSourceId(jxcOwnerOrderDetailVo.getUserId());
			List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
			//评价状态(1:已评价; 0:未评价)
			if(CollectionUtils.isEmpty(jxcScores)){
				jxcOwnerOrderDetailVo.setEvaluationStatus(0);
			}else{
				jxcOwnerOrderDetailVo.setEvaluationStatus(1);
			}

			//订单状态
			int ownerOrderState = jxcOwnerOrderDetailVo.getOwnerOrderState();
			if (ownerOrderState == 0) {
				jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_RECEIVED);
			}
			//已取消
			else if (ownerOrderState == 1 ) {
				jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_CANCELLED);
			}
			//进行中
			else if (ownerOrderState == 2) {
				jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_PROCESSING);
				//查询是否是申请退出中状态
				List<JxcOwnerApplyQuit> jxcOwnerApplyQuits = jxcOwnerOrderMapper.selectJxcOwnerApplyQuitList(jxcOwnerOrderDetailVo.getOwnerOrderId());
				if(jxcOwnerApplyQuits != null && jxcOwnerApplyQuits.size() > 0){
					for (JxcOwnerApplyQuit jxcOwnerApplyQuit : jxcOwnerApplyQuits){
						if(jxcOwnerApplyQuit.getApplyState().equals(0)){
							jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.APPLY_ING);
							break;
						}
					}
				}
			}
			//已完结
			else if (ownerOrderState == 3 || ownerOrderState == 4) {
				jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.ORDER_FINISHED);
			}
			//查询是否有未垫付的异常行程，有的话加“待收款”标记
			Integer waitPayFlag = this.jxcOwnerOrderMapper.countWaitPayRoute(jxcOwnerOrderDetailVo.getOwnerOrderId());
			if (waitPayFlag != null && waitPayFlag > 0) {
				jxcOwnerOrderDetailVo.setOrderStatus(JxcOwnerOrderVo.orderStatus.WAIT_PAY);
			}

		} else {
			return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "该订单不存在");
		}
		return new ResponseMessage(jxcOwnerOrderDetailVo);
	}

	/**
	 * 机主取消订单/机主解雇司机/司机取消职位前的验证
	 * @param cancelType 1:机主拒绝/解雇司机，2:机主取消承租方订单，3：司机取消已接受的短期职位
	 * @param startDate
	 * @return
	 */
	@Override
	public ResponseMessage cancelConfirm(AuthorizationUser user,Integer cancelType,String startDate) {
		if (user == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "登录信息不能为空！");
		}
		Integer roleType = user.getRoleId();
		JxcCreditScoreTemplate effectiveById = new JxcCreditScoreTemplate();
		String str1 = "";
		String str2 = "";
		String str3 = "";
		String str4 = "";
		if (roleType.equals(UserRoleContants.OWNER.getRoleId()) && cancelType.equals(1)) {
			effectiveById = jxcProjectOrderMapper.getEffectiveById(1);
			str1 = "确定解雇该司机吗？";
			str2 = "工作";
			str3 = "解雇该司机";
			str4 = "解雇";
		} else if (roleType.equals(UserRoleContants.DRIVER.getRoleId()) && cancelType.equals(3)) {
			effectiveById = jxcProjectOrderMapper.getEffectiveById(3);
			str1 = "确定取消该订单吗？";
			str2 = "订单";
			str3 = "取消该订单";
			str4 = "取消";
		} else if (roleType.equals(UserRoleContants.OWNER.getRoleId()) && cancelType.equals(2)) {
			effectiveById = jxcProjectOrderMapper.getEffectiveById(2);
			str1 = "确定取消该订单吗？";
			str2 = "订单";
			str3 = "取消该订单";
			str4 = "取消";
		}
		if (effectiveById != null) {
			//拿到规则时间
			Integer condition = effectiveById.getCondition();
			Integer score = Math.abs(effectiveById.getScore());
			if ((DateUtils.parseYMD(startDate).getTime() - System.currentTimeMillis()) > condition * 60 * 60 * 1000) {
				//大于规则时间提示
				if(cancelType.equals(2)) {
					return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(), "订单取消后无法恢复，请慎重考虑！");
				}else {
					return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(), str1);
				}
			} else {
				//司机在职位开始以后取消职位
				if(cancelType.equals(3) && (DateUtils.parseYMD(startDate).getTime() - System.currentTimeMillis()) <= 0){
					return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(), "取消订单会扣除您的信用分哦！");
				}
				if(cancelType.equals(2)) {
					return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_2.getId(), str2 + "将于" + condition + "小时后开始，现在" + str3 + "会扣除" + score + "分信用分，请慎重考虑！");
				}else {
					return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_2.getId(), str2 + "将于" + condition + "小时后开始，现在" + str3 + "会扣除" + score + "分信用分，确定" + str4 + "吗？");
				}
			}
		}else {
			if(cancelType.equals(2)) {
				return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(), "订单取消后无法恢复，请慎重考虑！");
			}else {
				return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(), str1);
			}

		}

	}

	/**
	 * 取消原因列表
	 *
	 * @param
	 * @return
	 * @author liuy
	 * @date 2019/8/23 10:13
	 */
	@Override
	public ResponseMessage getCancelReason() {
		List<JxcCancelReason> list = jxcOwnerOrderMapper.getCancelReason();
		return new ResponseMessage(list);
	}

	/**
	 * 取消订单
	 *
	 * @param ownerOrderId 订单ID
	 * @param cancelId     取消原因ID
	 * @return
	 * @author liuy
	 * @date 2019/8/23 11:00
	 */
	@Override
	@Transactional
	public ResponseMessage cancelOrder(AuthorizationUser user, Long ownerOrderId, Long cancelId) {
		if(null == ownerOrderId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "机主订单ID不能为空");
		}
		if(null == cancelId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "取消原因ID不能为空");
		}
		JxcOwnerOrder jxcOwnerOrder = new JxcOwnerOrder();
		jxcOwnerOrder.setId(ownerOrderId);
		jxcOwnerOrder.setCancelId(cancelId.intValue());
		jxcOwnerOrder.setOrderState(1);
		jxcOwnerOrderMapper.updateByPrimaryKeySelective(jxcOwnerOrder);

		//查询该机械还有没有别的已接单或进行中的单 没有的话 状态才能改为空闲
		JxcOwnerOrder jxcOwnerOrder1 = jxcOwnerOrderMapper.getById(ownerOrderId);
		if(jxcOwnerOrder1 != null && jxcOwnerOrder1.getMachineId() != null){
			JxcOwnerOrder jxcOwnerOrder2 = new JxcOwnerOrder();
			jxcOwnerOrder2.setMachineId(jxcOwnerOrder1.getMachineId());
			jxcOwnerOrder2.setOrderState(0);
			List<JxcOwnerOrder> list = jxcOwnerOrderMapper.selectListByConditions(jxcOwnerOrder);
			if(CollectionUtils.isEmpty(list)){
				//更新机械状态为空闲状态
				JxcMachine jxcMachine = new JxcMachine();
				jxcMachine.setId(jxcOwnerOrder1.getMachineId());
				jxcMachine.setStatus(JxcMachine.carStatus.IN_IDLE);
				jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine);
			}
		}
		//拿到信用分扣取规则
		JxcOwnerOrderDetailVo ownerOrderDetail = jxcOwnerOrderMapper.getOwnerOrderDetail(ownerOrderId);
		JxcCreditScoreTemplate effectiveById = jxcProjectOrderMapper.getEffectiveById(2);
		if (effectiveById != null) {
			//拿到规则时间
			Integer condition = effectiveById.getCondition();
			if ((DateUtils.parseYMD(ownerOrderDetail.getStartDate()).getTime() - System.currentTimeMillis()) < condition * 60 * 60 * 1000) {
				//添加信用分记录
				UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
				userCreditScoreVo.setUserId(user.getUserId());
				userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_CHILD_CANCEL_TEN_ORDER.getId());
				jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
			}
		}
		JxcProjectOrderVo jxcProjectOrderVo = jxcProjectOrderMapper.selectJxcProjectOrderById(ownerOrderDetail.getProjectOrderId());
		//站内信以及推送通知给承租方
		JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
		jpushCustomMsgVo.setUserId(jxcProjectOrderVo.getUserId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_CANCEL.getId());
		JSONObject js = new JSONObject();
		js.put("orderId",jxcProjectOrderVo.getId());
		jpushCustomMsgVo.setParam(js);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		//推送
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcProjectOrderVo.getUserId());
		if (userInfoVoResponseMessage.getData() != null) {
			UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setParams(js);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_CANCEL.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}
		return new ResponseMessage();
	}

	/**
	 * 申请退出
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/23 14:57
	 */
	@Override
	@Transactional
	public ResponseMessage applyOutOrder(Long ownerOrderId, String leaveReason) {
		//防止重复提交
		JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo = jxcOwnerApplyQuitMapper.queryByOwnerOrderId(ownerOrderId);
		if(jxcOwnerApplyQuitVo != null){
			return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(),"该订单已经申请了退出，不能重复申请！");
		}
		JxcOwnerOrder jxcOwnerOrder1 = jxcOwnerOrderMapper.getById(ownerOrderId);
		//检查机械是否正在跑趟中
		if(jxcOwnerOrder1 != null) {
			Long routeId = jxcMachineRouteMapper.checkMachineRun(jxcOwnerOrder1.getMachineId());
			if(routeId != null){
				return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"正在跑趟中,不可以申请退出");
			}
		}

		JxcOwnerOrder jxcOwnerOrder = new JxcOwnerOrder();
		jxcOwnerOrder.setId(ownerOrderId);
		jxcOwnerOrder.setLeaveReason(leaveReason);
		jxcOwnerOrder.setLeaveState(2);
		jxcOwnerOrderMapper.updateByPrimaryKeySelective(jxcOwnerOrder);

		//机主申请退出
		JxcOwnerApplyQuit jxcOwnerApplyQuit = new JxcOwnerApplyQuit();
		jxcOwnerApplyQuit.setApplyReason(leaveReason);
		jxcOwnerApplyQuit.setOwnerOrderId(ownerOrderId);
		jxcOwnerApplyQuit.setCreateTime(new Date());
		jxcOwnerApplyQuitMapper.insertSelective(jxcOwnerApplyQuit);

		//添加待办事项
		JxcWaitHandleItems jxcWaitHandleItems = jxcOwnerOrderMapper.getJxcWaitHandleItems(ownerOrderId);
		jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE1);
		jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);

		//站内信以及推送
		JxcOwnerOrderDetailVo ownerOrderDetail = jxcOwnerOrderMapper.getOwnerOrderDetail(ownerOrderId);
		JxcProjectOrderVo jxcProjectOrderVo = jxcProjectOrderMapper.selectJxcProjectOrderById(ownerOrderDetail.getProjectOrderId());
		//站内信以及推送通知给承租方
		JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
		jpushCustomMsgVo.setUserId(jxcProjectOrderVo.getUserId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_LEAVE_BEFORE.getId());
		JSONObject js = new JSONObject();
		js.put("machineCardNo",ownerOrderDetail.getMachineCarNo());
		js.put("reason",leaveReason);
		js.put("orderId",jxcProjectOrderVo.getId());
		jpushCustomMsgVo.setParam(js);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		//推送
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcProjectOrderVo.getUserId());
		if (userInfoVoResponseMessage.getData() != null) {
			UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setParams(js);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_LEAVE_BEFORE.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}
		return new ResponseMessage();
	}

	/**
	 * 取消申请
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/23 14:57
	 */
	@Override
	public ResponseMessage cancelApplyOrder(Long ownerOrderId) {
		JxcOwnerOrder jxcOwnerOrder = new JxcOwnerOrder();
		jxcOwnerOrder.setId(ownerOrderId);
		jxcOwnerOrder.setLeaveState(0);
		jxcOwnerOrderMapper.updateByPrimaryKeySelective(jxcOwnerOrder);

		//删除机主申请记录
		jxcOwnerApplyQuitMapper.deleteByOrderId(ownerOrderId);

		//删除待办事项
		JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
		jxcWaitHandleItems.setItemType(1);
		jxcWaitHandleItems.setOwnerOrderId(ownerOrderId);
		List<JxcWaitHandleItems> jxcWaitHandleItems1 = jxcWaitHandleItemsMapper.selectListByConditions(jxcWaitHandleItems);
		if (jxcWaitHandleItems1 != null && jxcWaitHandleItems1.size() >0){
			JxcWaitHandleItems jxcWaitHandleItems2 = jxcWaitHandleItems1.get(0);
			jxcWaitHandleItems2.setIsDeleted(1);
			jxcWaitHandleItemsMapper.updateByPrimaryKeySelective(jxcWaitHandleItems2);
		}
		return new ResponseMessage();
	}

	/**
	 * 机主查看异常趟数列表
	 * @author  liuy
	 * @param flag 0-未处理 1-已处理
	 * @return
	 * @date    2019/8/27 13:44
	 */
	@Override
	public ResponseMessage ownerAbnormalList(Integer userId, Long lastPageLastId, Integer flag) {
		if (userId == null) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "当前用户ID不能为空");
		}
		List<JxcOwnerAbnormalVo> ownerAbnormalVoList = jxcOwnerOrderMapper.ownerAbnormalList(userId, lastPageLastId, flag);
		if(!CollectionUtils.isEmpty(ownerAbnormalVoList)){
			ownerAbnormalVoList.stream().forEach(jxcOwnerAbnormalVo -> {
				//支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）
				Integer payState = jxcOwnerAbnormalVo.getPayState();
				//异常状态(0--异常申请待处理 1--处理完毕)
				Integer abnormalStatus = jxcOwnerAbnormalVo.getAbnormalStatus();
				if(payState == 0 && abnormalStatus == -1){
					//待处理
					jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.NO_PROCESSED);
				}
				if(payState == 0 && abnormalStatus == 0){
					//待核实
					jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.VERIFIED);
				}
				if((payState == 1 || abnormalStatus == 2) && abnormalStatus == 1){
					//已处理
					jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PROCESSED);
				}
				if(payState != 0 && abnormalStatus == 1){
					//已支付
					jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PAID);
				}

				//协商处理人userid
				Integer consultUserId = jxcOwnerAbnormalVo.getConsultUserId();
				//异常申请用户userid
				Integer appealUserId = jxcOwnerAbnormalVo.getAppealUserId();
				Integer selfHandleFlag = jxcOwnerAbnormalVo.getSelfHandleFlag();
				//判断一下协商价为0的特殊情况
				if(abnormalStatus == 1){
					//排除承租方自己确认的正常并且金额刚好也是0的情况
					if( consultUserId != null  && appealUserId != null) {
						if(selfHandleFlag.equals(0)) {
							if (jxcOwnerAbnormalVo.getConsultPrice().compareTo(BigDecimal.ZERO) == 0) {
								//已处理
								jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PROCESSED);
							}
						}else if(selfHandleFlag.equals(1)){
							//如果是承租方自己确认的正常
							jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PAID);
						}
					}
				}
			});
		}
		Map<String, Object> map = new HashMap<>();
		int hasNextPage = 0;
		if (ownerAbnormalVoList.size() == 10) {
			//mark: 最后一条数据作为查询条件
			Long nextPageFirstId = jxcOwnerOrderMapper.hasNextPageAbnormalList(userId, ownerAbnormalVoList.get(9).getRouteId(), flag);
			if (null != nextPageFirstId) {
				hasNextPage = 1;
				map.put("lastPageLastId", ownerAbnormalVoList.get(9).getRouteId());
			}
		}
		map.put("abnormalList", ownerAbnormalVoList);
		map.put("hasNextPage", hasNextPage);
		//获取总数据量
		Integer totalCount = jxcOwnerOrderMapper.ownerAbnormalAllList(userId, flag);
		map.put("totalCount", totalCount);
		return new ResponseMessage(map);
	}

	/**
	 * 异常趟数详情
	 * @author  liuy
	 * @param routeId
	 * @return
	 * @date    2019/8/27 14:59
	 */
	@Override
	public ResponseMessage ownerViewAbnormalProgress(Long routeId) {
		if(null == routeId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(),"行程ID不能为空");
		}
		//返回结果
		Map<String,Object> resultMap = new HashMap<>();

		List<Map<String,Object>> progressList = new ArrayList<>();
		JxcOwnerAbnormalVo jxcOwnerAbnormalVo = jxcOwnerOrderMapper.ownerViewAbnormalProgress(routeId);
		if(jxcOwnerAbnormalVo != null){
			if(jxcOwnerAbnormalVo.getDriverId() != null){
				ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcOwnerAbnormalVo.getDriverId());
				if(userInfoVoResponseMessage.getData() != null){
					UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
					jxcOwnerAbnormalVo.setDriverPhone(userInfoVo.getPhone());
				}
			}

			//支付状态（0：异常趟或正在跑趟中 1：待支付（平台已垫付） 2：已支付）
			Integer payState = jxcOwnerAbnormalVo.getPayState();
			//异常状态(0--异常申请待处理 1--处理完毕)
			Integer abnormalStatus = jxcOwnerAbnormalVo.getAbnormalStatus();

			String startTime = jxcOwnerAbnormalVo.getStartTime();
			String endTime = jxcOwnerAbnormalVo.getEndTime();
			String time1 = jxcOwnerAbnormalVo.getTime1();
			String time2 = jxcOwnerAbnormalVo.getTime2();

			if(payState == 0 && abnormalStatus == -1){
				//待处理
				jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.NO_PROCESSED);
				progressList.clear();
				Map<String,Object> map = new HashMap<>();
				map.put("content","确认异常，等待承租方处理");
				if(jxcOwnerAbnormalVo.getEndTime() != null) {
					map.put("time", String.valueOf(endTime));
				}else {
					map.put("time", String.valueOf(startTime));
				}
				progressList.add(map);
			}
			if(payState == 0 && abnormalStatus == 0){
				//待核实
				jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.VERIFIED);
				progressList.clear();
				Map<String,Object> map1 = new HashMap<>();
				map1.put("content","确认异常，等待承租方处理");
				if(endTime != null) {
					map1.put("time", String.valueOf(endTime));
				}else {
					map1.put("time", String.valueOf(startTime));
				}
				progressList.add(map1);
				Map<String,Object> map2 = new HashMap<>();
				map2.put("content","承租方已提交申诉，等待客服核实");
				map2.put("time", time1);
				progressList.add(map2);
			}
			if((payState == 1 || abnormalStatus == 2) && abnormalStatus == 1){
				//已处理
				jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PROCESSED);
				progressList.clear();
				Map<String,Object> map1 = new HashMap<>();
				map1.put("content","确认异常，等待承租方处理");
				if(endTime != null) {
					map1.put("time", String.valueOf(endTime));
				}else {
					map1.put("time", String.valueOf(startTime));
				}
				progressList.add(map1);
				Map<String,Object> map2 = new HashMap<>();
				map2.put("content","承租方已提交申诉，等待客服核实");
				map2.put("time", time1);
				progressList.add(map2);
				Map<String,Object> map3 = new HashMap<>();
				map3.put("content","客服已处理，等待支付");
				map3.put("time", time2);
				progressList.add(map3);
			}
			if(payState != 0 && abnormalStatus == 1){
				//已支付
				jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PAID);
				progressList.clear();
				Map<String,Object> map1 = new HashMap<>();
				map1.put("content","确认异常，等待承租方处理");
				if(endTime != null) {
					map1.put("time", String.valueOf(endTime));
				}else {
					map1.put("time", String.valueOf(startTime));
				}
				progressList.add(map1);
				Map<String,Object> map2 = new HashMap<>();
				map2.put("content","承租方已提交申诉，等待客服核实");
				map2.put("time", time1);
				progressList.add(map2);
				Map<String,Object> map3 = new HashMap<>();
				map3.put("content","客服已处理，等待支付");
				map3.put("time", time2);
				progressList.add(map3);
				Map<String,Object> map4 = new HashMap<>();
				map4.put("content","已支付");
				map4.put("time", time2);
				progressList.add(map4);
			}

			//判断一下协商价为0的特殊情况
			//协商处理人userid
			Integer consultUserId = jxcOwnerAbnormalVo.getConsultUserId();
			//异常申请用户userid
			Integer appealUserId = jxcOwnerAbnormalVo.getAppealUserId();
			Integer selfHandleFlag = jxcOwnerAbnormalVo.getSelfHandleFlag();

			//判断一下协商价为0的特殊情况
			if(abnormalStatus == 1){
				//排除承租方自己确认的正常并且金额刚好也是0的情况
				if( consultUserId != null  && appealUserId != null) {
					if(selfHandleFlag.equals(0)) {
						if (jxcOwnerAbnormalVo.getConsultPrice().compareTo(BigDecimal.ZERO) == 0) {
							//已处理
							jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PROCESSED);
							//已处理
							progressList.clear();
							Map<String,Object> map1 = new HashMap<>();
							map1.put("content","确认异常，等待承租方处理");
							if(endTime != null) {
								map1.put("time", endTime);
							}else {
								map1.put("time", startTime);
							}
							progressList.add(map1);

							Map<String,Object> map2 = new HashMap<>();
							map2.put("content","承租方已提交申诉，等待客服核实");
							map2.put("time", time1);
							progressList.add(map2);

							Map<String,Object> map3 = new HashMap<>();
							map3.put("content","客服已处理");
							map3.put("time", time2);
							progressList.add(map3);
						}
					}else if(selfHandleFlag.equals(1)){
						//如果是承租方自己确认的正常
						jxcOwnerAbnormalVo.setState(JxcOwnerAbnormalVo.state.PAID);
						progressList.clear();
						Map<String,Object> map1 = new HashMap<>();
						map1.put("content","确认异常，等待承租方处理");
						if(endTime != null) {
							map1.put("time", endTime);
						}else {
							map1.put("time", startTime);
						}
						progressList.add(map1);

						Map<String,Object> map2 = new HashMap<>();
						map2.put("content","承租方已确认正常，等待支付");
						map2.put("time", time1);
						progressList.add(map2);

						Map<String,Object> map3 = new HashMap<>();
						map3.put("content","已支付");
						map3.put("time", time1);
						progressList.add(map3);
					}
				}
			}
		}
		resultMap.put("jxcOwnerAbnormalVo", jxcOwnerAbnormalVo);
		resultMap.put("progressList", progressList);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 泥头车接单引导认证
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/8/29 17:42
	 */
	@Override
	public ResponseMessage checkOwnerCertification(Integer userId) {
		//机主认证(0:未认证)
		Integer confirmStatus = 0;
		//是否添加车辆
		Integer machineStatus = 0;
		//是否添加司机
		Integer driverStatus = 0;

		Integer confirmId = null;

		//认证标识1:已经认证过一次; 0:未认证过一次
		Integer confirmFlag = 0;
		if(redisUtil.hasKey("ownerIndexConfirmStatus_" + userId)) {
			confirmFlag = Integer.parseInt(redisUtil.get("ownerIndexConfirmStatus_" + userId).toString());
		}
		Map<String,Object> resultMap = new HashMap<>();

		//机主认证
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(userId);
		if(userInfoVoResponseMessage.getData() != null){
			UserInfoVo data = userInfoVoResponseMessage.getData();
			if(data.getConfirmId() != null){
				//机主认证
				if(data.getConfirmType() == 3){
					//机主认证状态; (0--未认证 1--已认证，2--未通过，3--审核中)
					confirmStatus = data.getOwnerConfirmStatus();
					confirmId = data.getConfirmId();
				}
			}
		}
		resultMap.put("confirmId", confirmId);
		resultMap.put("confirmStatus", confirmStatus);

		//是否添加车辆(1:是; 0:否)
		JxcMachine jxcMachine = new JxcMachine();
		jxcMachine.setUserId(userId);
		jxcMachine.setDeleteStatus(0);
		List<JxcMachine> machineList = jxcMachineMapper.selectListByConditions(jxcMachine);
		if(!CollectionUtils.isEmpty(machineList)){
			machineStatus = 1;
			resultMap.put("machineStatus", 1);
		}else{
			machineStatus = 0;
			resultMap.put("machineStatus", 0);
		}

		//是否绑定司机(1:是; 0:否)
		Integer count = jxcMachineMapper.checkBindDriver(userId);
		if(count > 0){
			driverStatus = 1;
			resultMap.put("driverStatus", 1);
		}else{
			driverStatus = 0;
			resultMap.put("driverStatus", 0);
		}

		//全部认证通过一次之后
		if(confirmStatus == 1 && machineStatus == 1 && driverStatus == 1){
			if(!redisUtil.hasKey("ownerIndexConfirmStatus_" + userId)) {
				redisUtil.set("ownerIndexConfirmStatus_" + userId, 1);
			}
		}
		resultMap.put("confirmFlag", confirmFlag);
		return new ResponseMessage(resultMap);
	}

	/**
	 * 机主订单-接单
	 * @author  liuy
	 * @param jxcOwnerOrder
	 * @return
	 * @date    2019/8/30 11:09
	 */
	@Override
	public ResponseMessage addOwnerOrder(AuthorizationUser authorizationUser, JxcOwnerOrder jxcOwnerOrder) {
		if (null == jxcOwnerOrder.getTenantryOrderId()) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "承租方订单ID不能为空");
		}
		if (null == jxcOwnerOrder.getMachineId() || jxcOwnerOrder.getMachineId().equals(0)) {
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "车辆ID不能为空或者0");
		}
		Long projectOrderId = jxcOwnerOrder.getTenantryOrderId();
		//承租方订单详情
		JxcProjectOrderVo jxcProjectOrderVo = this.jxcProjectOrderMapper.selectJxcProjectOrderById(projectOrderId);
		//查询已接单车辆数
		Integer acceptedCarCount = jxcProjectOrderMapper.queryAcceptedCarCount(projectOrderId);
		int estimateMachineCount = jxcProjectOrderVo.getEstimateMachineCount();
		if (acceptedCarCount >= estimateMachineCount) {
			return new ResponseMessage(ErrorCodeConstants.RESOURCE_OVER_COST.getId(), "该订单车辆已满");
		}

		if (null == jxcProjectOrderVo) {
			return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "无此订单");
		}
		//查询当前机械已接单列表
		List<Map<String, Object>> list = jxcOwnerOrderMapper.getOwnerListByMachineId(jxcOwnerOrder.getMachineId());
		if (!CollectionUtils.isEmpty(list)) {
			for (Map map : list) {
				if (map.get("startDate") != null && map.get("endDate") != null) {
					//已接单开始日期和结束日期
					Date startDate = DateUtils.parseYMD(map.get("startDate").toString());
					Date endDate = DateUtils.parseYMD(map.get("endDate").toString());
					Date projectStartDate = DateUtils.parseYMD(jxcProjectOrderVo.getStartDate());
					Date projectEndDate = DateUtils.parseYMD(jxcProjectOrderVo.getEndDate());

					if (!DateUtils.belongCalendarBru(projectStartDate, projectEndDate, startDate, endDate)) {
						return new ResponseMessage(ErrorCodeConstants.NO_RES_FIND.getId(), "当前时段已有订单！");
					}
				}
			}
		}
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcOwnerOrder.setId(IDGenerator.getInstance().next());
		jxcOwnerOrder.setCreateTime(new Date());
		//机主角色
		if(authorizationUser.getRoleId() == 2){
			jxcOwnerOrder.setUserId(authorizationUser.getUserId());
		}else{
			//司机用户时查询司机所绑定的机主id
			JxcDriverRefOwner jxcDriverRefOwner = new JxcDriverRefOwner();
			jxcDriverRefOwner.setDriverId(authorizationUser.getUserId());
			jxcDriverRefOwner.setDelState(1);
			List<JxcDriverRefOwner> driverRefOwnerList = jxcDriverRefOwnerMapper.selectListByConditions(jxcDriverRefOwner);
			if(!CollectionUtils.isEmpty(driverRefOwnerList)){
				JxcDriverRefOwner jxcDriverRefOwner1 = driverRefOwnerList.get(0);
				jxcOwnerOrder.setUserId(jxcDriverRefOwner1.getOwnerId());
			}
		}
		//承租方订单状态为进行中时设置机主订单状态为进行中
		if(jxcProjectOrderVo.getOrderState() == 2){
			jxcOwnerOrder.setOrderState(2);
		}
		jxcOwnerOrder.setDriverId(authorizationUser.getUserId());
		//您已接过该订单
		JxcOwnerOrder jxcOwnerOrder1 = new JxcOwnerOrder();
		jxcOwnerOrder1.setTenantryOrderId(jxcOwnerOrder.getTenantryOrderId());
		jxcOwnerOrder1.setMachineId(jxcOwnerOrder.getMachineId());
		jxcOwnerOrder1.setOrderState(0);
		List<JxcOwnerOrder> jxcOwnerOrderList = jxcOwnerOrderMapper.selectListByConditions(jxcOwnerOrder1);
		if (!CollectionUtils.isEmpty(jxcOwnerOrderList)) {
			return new ResponseMessage(ErrorCodeConstants.NO_RES_FIND.getId(), "您已接过该订单");
		}
		int res = this.jxcOwnerOrderMapper.insertSelective(jxcOwnerOrder);
		if(res > 0){
			//进行中
			if(jxcProjectOrderVo.getOrderState() == 2){
				JxcMachine jxcMachine = new JxcMachine();
				jxcMachine.setStatus(JxcMachine.carStatus.PROCESSING);
				jxcMachine.setId(jxcOwnerOrder.getMachineId());
				//更新机械状态为进行中
				jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine);
			}
		}
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		//站内信以及推送通知给承租方
		JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
		jpushCustomMsgVo.setUserId(jxcProjectOrderVo.getUserId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_RECEIPT.getId());
		JSONObject js = new JSONObject();
		js.put("orderId",jxcProjectOrderVo.getId());
		jpushCustomMsgVo.setParam(js);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		//推送
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcProjectOrderVo.getUserId());
		if (userInfoVoResponseMessage.getData() != null) {
			UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
			//当前司机名称
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setParams(js);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_RECEIPT.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}

		//站内信以及推送通知给机主
		JxcOwnerOrderDetailVo ownerOrderDetail = jxcOwnerOrderMapper.getOwnerOrderDetail(jxcOwnerOrder.getId());
		jpushCustomMsgVo.setUserId(jxcOwnerOrder.getUserId());
		jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_OWNER_RECEIPT_SUCCESS.getId());
		JSONObject js2 = new JSONObject();
		js2.put("machineCardNo",ownerOrderDetail.getMachineCarNo());
		js2.put("startDate",jxcProjectOrderVo.getStartDate());
		js2.put("endDate",jxcProjectOrderVo.getEndDate());
		js2.put("orderId",jxcOwnerOrder.getId());
		jpushCustomMsgVo.setParam(js2);
		jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
		//推送
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage1 = jxcUserFeign.queryUserInfo(jxcOwnerOrder.getUserId());
		if (userInfoVoResponseMessage1.getData() != null) {
			UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage1.getData();
			//当前司机名称
			JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
			jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
			jpushTemplateVo.setAliases(userInfoVo.getThirdId());
			jpushTemplateVo.setParams(js2);
			jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_OWNER_RECEIPT_SUCCESS.getId());
			jpushMsgFeign.jpushNotice(jpushTemplateVo);
		}

		return result;
	}

	/**
	 * 查询首页机主收入统计列表
	 *
	 * @return
	 */
	@Override
	public OwnerStatisticsVo getOwnerStatistics(OwnerStatisticsVo ownerStatisticsVo) {
		if (ownerStatisticsVo.getStatisticsType() == OwnerStatisticsVo.statisticsType.DATE_TYPE) {
			//按时间查询的数据
			ownerStatisticsVo = statisticsUtils.getDateDataList(ownerStatisticsVo.getUserId());
		} else if (ownerStatisticsVo.getStatisticsType() == OwnerStatisticsVo.statisticsType.MACHINE_TYPE) {
			//按机械查询的数据
			MachineDataVo machineDataVo = statisticsUtils.getMachineDataList(ownerStatisticsVo.getUserId(), ownerStatisticsVo.getStartDate(),ownerStatisticsVo.getEndDate(),ownerStatisticsVo.getMonthDate());
			ownerStatisticsVo.setMachineData(machineDataVo);
		}
		return ownerStatisticsVo;
	}

	/**
	 * 检查进行中订单是否跑趟中或是正在上班中
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/4 14:21
	 */
	@Override
	public ResponseMessage checkMachineWorkIn(AuthorizationUser authorizationUser, Integer machineId) {
		if(null == machineId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(),"机械ID不能为空");
		}
		//跑趟中状态(status: 0:未跑趟中 1:第一次交接班; 2:第二次交接班)
		//上班中状态(0:未上班 1:正在上班中)

		//是否跑趟中
		JxcMachineWorkInVo jxcMachineWorkInVo = jxcOwnerOrderMapper.checkMachineRun(machineId);
		if(jxcMachineWorkInVo != null){
			jxcMachineWorkInVo.setDriverId(authorizationUser.getUserId());
			ResponseMessage userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(authorizationUser.getUserId());
			if (userInfoVoResponseMessage.getData() != null) {
				UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
				//当前司机名称
				jxcMachineWorkInVo.setDriverName(userInfoVo.getRealName());
			}
			//是否正在上班中
			JxcIndexVo jxcIndexVo = jxcOwnerOrderMapper.getWorkInByMachineId(machineId);
			if(jxcIndexVo != null && !jxcIndexVo.getDriverId().equals(authorizationUser.getUserId())){
				//上班中
				jxcMachineWorkInVo.setIsWork(1);
				ResponseMessage responseMessage = jxcUserFeign.queryUserInfo(jxcIndexVo.getDriverId());
				if (responseMessage.getData() != null) {
					UserInfoVo userInfoVo = (UserInfoVo) responseMessage.getData();
					//正在上班中的司机电话
					jxcMachineWorkInVo.setUpDriverId(jxcIndexVo.getDriverId());
					jxcMachineWorkInVo.setUpDriverPhone(userInfoVo.getPhone());
					jxcMachineWorkInVo.setUpDriverName(userInfoVo.getRealName());
				}
			}
			//查询是否交接班一次
			JxcDriverHandover jxcDriverHandover = jxcDriverHandoverMapper.selectById(jxcMachineWorkInVo.getRouteId());
			if(jxcDriverHandover != null){
				//交接班过一次,第二次交接班
				jxcMachineWorkInVo.setStatus(2);
			}else{
				//第一次交接班
				jxcMachineWorkInVo.setStatus(1);
			}
		}else{
			jxcMachineWorkInVo = new JxcMachineWorkInVo();
			jxcMachineWorkInVo.setDriverId(authorizationUser.getUserId());
			ResponseMessage userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(authorizationUser.getUserId());
			if (userInfoVoResponseMessage.getData() != null) {
				UserInfoVo userInfoVo = (UserInfoVo) userInfoVoResponseMessage.getData();
				//当前司机名称
				jxcMachineWorkInVo.setDriverName(userInfoVo.getRealName());
			}
			//是否正在上班中
			JxcIndexVo jxcIndexVo = jxcOwnerOrderMapper.getWorkInByMachineId(machineId);
			if(jxcIndexVo != null && !jxcIndexVo.getDriverId().equals(authorizationUser.getUserId())){
				//上班中
				jxcMachineWorkInVo.setIsWork(1);
				ResponseMessage responseMessage = jxcUserFeign.queryUserInfo(jxcIndexVo.getDriverId());
				if (responseMessage.getData() != null) {
					UserInfoVo userInfoVo = (UserInfoVo) responseMessage.getData();
					//正在上班中的司机电话
					jxcMachineWorkInVo.setUpDriverId(jxcIndexVo.getDriverId());
					jxcMachineWorkInVo.setUpDriverPhone(userInfoVo.getPhone());
					jxcMachineWorkInVo.setUpDriverName(userInfoVo.getRealName());
				}
			}
		}
		return new ResponseMessage(jxcMachineWorkInVo);
	}

	/**
	 * 获取电子消纳券
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/10 16:26
	 */
	@Override
	public ResponseMessage getSiteCouponByMachineId(Integer machineId) {
		List<JxcSiteCouponVo> jxcSiteCouponVoList = jxcOwnerOrderMapper.getSiteCouponByMachineId(machineId);
		return new ResponseMessage(jxcSiteCouponVoList);
	}

	/**
	 * 检查司机是否有正在进行中的订单和已接单，是否绑定机主
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/10/17 20:45
	 */
	@Override
	public ResponseMessage checkOwnerOrderByDriverId(Integer driverId) {
		//0:可以申请机主; 1:未实名认证; 2:有未完成订单; 3:未解绑机主;
		ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(driverId);
		if(userInfoVoResponseMessage != null){
			UserInfoVo data = userInfoVoResponseMessage.getData();
			if(data != null){
				if(data.getDriverConfirmStatus() != 1){
					return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "未实名认证不可申请为机主");
				}
			}
		}
		//是否存在未完成兼职职位
		Integer resCount = jxcShortJobRefDriverMapper.queryShortJobByDriverId(driverId);
		if(resCount > 0){
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "有未完成兼职职位不可申请为机主");
		}else {
			//是否有未完成订单
			Integer count = jxcOwnerOrderMapper.checkOwnerListByDriverId(driverId);
			if (count > 0) {
				return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "有未完成订单不可申请为机主");
			}
		}
		//是否解除与机主关系
		JxcMachineRefDriver jxcMachineRefDriver = jxcMachineRefDriverMapper.getMachineRefDriverById(driverId);
		if(jxcMachineRefDriver != null && jxcMachineRefDriver.getOwnerUserId() != null){
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "已绑定机主不可申请为机主");
		}
		return new ResponseMessage();
	}

}