package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.bean.JxcScore;
import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.DriverMapper;
import com.weiwei.jixieche.mapper.JxcClockPairMapper;
import com.weiwei.jixieche.mapper.JxcScoreMapper;
import com.weiwei.jixieche.mapper.JxcShortJobMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcShortJobService;
import com.weiwei.jixieche.util.AgeBirthdayUtil;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 短期职位招聘
 * @author liuy
 * @date 2019-08-15 14:00
 */
@Service
public class JxcShortJobServiceImpl implements JxcShortJobService {

	@Resource
	private JxcShortJobMapper jxcShortJobMapper;

	@Resource
	private JxcScoreMapper jxcScoreMapper;

	@Resource
	private JxcClockPairMapper jxcClockPairMapper;

	@Autowired
	private JxcUserFeign jxcUserFeign;

	@Resource
	private DriverMapper driverMapper;

	/**
	 * 短期职位招聘列表
	 * @author  liuy
	 * @param driverJobVo 查询条件
	 * @return
	 * @date    2019/8/15 13:58
	 */
	@Override
	public ResponseMessage getShortJobList(AuthorizationUser user, DriverJobVo driverJobVo) {
		//返回结果
		ResponseMessage result = ResponseMessageFactory.newInstance();
		List<JxcShortJob> list = this.jxcShortJobMapper.getShortJobList(driverJobVo);
		List<JxcShortJob> resultList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(list)) {
			for (JxcShortJob jxcShortJob : list) {
				//工作到达开始时间的
				if (DateUtils.differentDays(new Date(),jxcShortJob.getWorkTimeStart()) <= 0 && DateUtils.differentDays( new Date(), jxcShortJob.getWorkTimeEnd()) >= 0){
					//进行中
					jxcShortJob.setStatus(JxcShortJob.status.PROCESSING);
				}

				//到达结束时间，还有司机的工资未支付时显示待支付状态
				if(DateUtils.differentDays(new Date(), jxcShortJob.getWorkTimeEnd()) < 0){
					Integer count = jxcShortJobMapper.getShortJobDriverNoPayById(jxcShortJob.getId());
					if(count > 0){
						//待支付
						jxcShortJob.setStatus(JxcShortJob.status.WAITPAY);
					}else {
						//否则，查询目前的flag
						Integer flag = jxcShortJob.getFlag();
						if(flag != null && flag.equals(0)){
							jxcShortJob.setStatus(JxcShortJob.status.DISABLED);
						}
					}
				}
				//添加短期职位的台班计费规则
				if(jxcShortJob.getAreaId() != null) {
					JxcDriverTeamCostVo jxcDriverTeamCostVo = this.jxcShortJobMapper.queryDriverTeamCost(jxcShortJob.getAreaId());
					if(jxcDriverTeamCostVo != null){
						jxcShortJob.setJxcDriverTeamCostVo(jxcDriverTeamCostVo);
						jxcShortJob.setDriverTeamPrice(jxcDriverTeamCostVo.getDriverTeamPrice());
					}
				}
				//查询职位已招聘人数
				int shortJobCount = this.jxcShortJobMapper.getShortJobCount(jxcShortJob.getId());
				jxcShortJob.setShortJobNum(shortJobCount);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//司机角色时需要过滤一下时间重复的数据
				if(user.getRoleId() == 4) {
					//兼职职位状态已失效和机主关闭职位时不显示职位信息
					if(jxcShortJob.getStatus() == 0) {
						if (StringUtils.isNotBlank(sdf.format(jxcShortJob.getWorkTimeStart())) && StringUtils.isNotBlank(sdf.format(jxcShortJob.getWorkTimeEnd()))) {
							List<Map<String, Object>> mapList = jxcShortJobMapper.getShortJobListByDriverId(user.getUserId());
							if (!CollectionUtils.isEmpty(mapList)) {
								//已接单开始日期和结束日期
								Date jobStartDate = jxcShortJob.getWorkTimeStart();
								Date jobEndDate = jxcShortJob.getWorkTimeEnd();
								boolean flag = true;
								for (Map map : mapList) {
									if (map.get("startDate") != null && map.get("endDate") != null) {
										//已接单开始日期和结束日期
										Date startDate = DateUtils.parseYMD(map.get("startDate").toString());
										Date endDate = DateUtils.parseYMD(map.get("endDate").toString());
										if (!DateUtils.belongCalendarBru(jobStartDate, jobEndDate, startDate, endDate)){
											flag = false;
											break;
										}
									}
								}
								if (flag) {
									resultList.add(jxcShortJob);
								}
							} else {
								resultList.add(jxcShortJob);
							}
						}
					}
				}else{
					resultList.add(jxcShortJob);
				}
			}
		}
		PageHelper.startPage(driverJobVo.getPageNo(), driverJobVo.getPageSize());
		PageInfo<JxcShortJob> page = new PageInfo<>(resultList);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 * 发布短期职位信息
	 * @author  liuy
	 * @param jxcShortJob
	 * @return
	 * @date    2019/8/15 13:50
	 */
	@Override
	public ResponseMessage insert(JxcShortJob jxcShortJob) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		if(jxcShortJob.getEffectiveDateEnd() != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dataStr = DateUtils.format(jxcShortJob.getEffectiveDateEnd()) + " 23:59:59";
			try {
				jxcShortJob.setEffectiveDateEnd(sdf.parse(dataStr));
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		int res=this.jxcShortJobMapper.insertSelective(jxcShortJob);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 更新短期职位信息
	 * @author  liuy
	 * @param jxcShortJob
	 * @return
	 * @date    2019/8/15 13:50
	 */
	@Override
	public ResponseMessage update(JxcShortJob jxcShortJob) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		jxcShortJob.setUpdateTime(new Date());
		int res = this.jxcShortJobMapper.updateByPrimaryKeySelective(jxcShortJob);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
		return result;
	}

	/**
	 * 短期职位详情
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/15 13:54
	 */
	@Override
	public ResponseMessage getById(Integer id) {
		JxcShortJob model = this.jxcShortJobMapper.selectByPrimaryKey(id);
		//添加短期职位的台班计费规则
		if(model.getAreaId() != null) {
			model.setJxcDriverTeamCostVo(this.jxcShortJobMapper.queryDriverTeamCost(model.getAreaId()));
		}
		if(model.getContactPhone() != null){
			//获取用户认证信息
			UserInfoVo userInfoVo = driverMapper.getUserInfoByPhone(model.getContactPhone());
			if(userInfoVo != null) {
				model.setOwnerScore(userInfoVo.getScore());
				model.setOwnerHeadImg(userInfoVo.getHeadImg());
				model.setOwnerConfirmStatus(userInfoVo.getOwnerConfirmStatus());
			}
		}
		//查询职位已招聘人数
		int shortJobCount = this.jxcShortJobMapper.getShortJobCount(model.getId());
		model.setShortJobNum(shortJobCount);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_ERORR.getDescript(), ErrorCodeConstants.QUERY_ERORR.getId());
		return new ResponseMessage(model);
	}

	/**
	 * 删除
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/15 15:14
	 */
	@Override
	public ResponseMessage deleteById(Integer id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcShortJob jxcLongJob = new JxcShortJob();
		jxcLongJob.setId(id);
		jxcLongJob.setJobIsDelete(JxcShortJob.jobIsDelete.deleted);
		int res = this.jxcShortJobMapper.updateByPrimaryKeySelective(jxcLongJob);
		AssertUtil.numberGtZero(res,ErrorCodeConstants.DELETE_ERORR.getDescript(),ErrorCodeConstants.DELETE_ERORR.getId());
		return result;
	}

	/**
	 * 查询司机台班费用
	 * @author  liuy
	 * @param cityId
	 * @return
	 * @date    2019/8/28 10:03
	 */
	@Override
	public ResponseMessage queryDriverTeamCost(Integer cityId) {
		if(null == cityId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "城市区域ID不能为空");
		}
		JxcDriverTeamCostVo model = this.jxcShortJobMapper.queryDriverTeamCost(cityId);
		return new ResponseMessage(model);
	}

	/**
	 * 关闭兼职职位
	 * @author  liuy
	 * @param shortJobId
	 * @return
	 * @date    2019/8/28 11:42
	 */
	@Override
	@Transactional
	public ResponseMessage closeShortJob(Integer shortJobId) {
		JxcShortJob jxcShortJob = this.jxcShortJobMapper.selectByPrimaryKey(shortJobId);
		//判断是否在有效时间内关闭职位
		if (DateUtils.differentDays(new Date(),jxcShortJob.getEffectiveDateEnd()) >= 0) {
			//查询职位已招聘人数
			int shortJobCount = this.jxcShortJobMapper.getShortJobCount(shortJobId);
			jxcShortJob = new JxcShortJob();
			jxcShortJob.setId(shortJobId);
			if(shortJobCount == 0){
				//--已失效状态
				jxcShortJob.setStatus(1);
				jxcShortJob.setId(shortJobId);
			}else{
				//--机主关闭职位
				jxcShortJob.setStatus(3);
			}
			this.jxcShortJobMapper.updateByPrimaryKeySelective(jxcShortJob);
			return new ResponseMessage();
		} else {
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(), "订单已经开始，不允许关闭!");
		}
	}

	/**
	 * 接单职位司机列表
	 * @author  liuy
	 * @param shortJobId
	 * @return
	 * @date    2019/8/30 14:01
	 */
	@Override
	public ResponseMessage getShortDriverList(AuthorizationUser user, Integer shortJobId, Integer pageNo, Integer pageSize) {
		if(null == shortJobId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "职位ID不能为空");
		}
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(pageNo, pageSize);
		List<JxcShortJobDriverVo> list = jxcShortJobMapper.getShortDriverList(shortJobId);
		if(!CollectionUtils.isEmpty(list)){
			list.stream().forEach(jxcShortJobDriverVo -> {
				//查询是否有未支付
				Integer countPayByDriverId = jxcClockPairMapper.getCountPayByDriverId(jxcShortJobDriverVo.getDriverId(), shortJobId);
				if(countPayByDriverId > 0){
					jxcShortJobDriverVo.setPayState(0);
				}else {
					jxcShortJobDriverVo.setPayState(1);
				}
			});
		}
		PageInfo<JxcShortJobDriverVo> page = new PageInfo<>(list);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 *  接单职位司机-详情
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/30 15:11
	 */
	@Override
	public ResponseMessage getShortDriverDetail(AuthorizationUser user, Integer driverId, Integer shortJobId) {
		if(null == driverId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "司机ID不能为空");
		}
		if(null == shortJobId){
			return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "职位ID不能为空");
		}
		JxcShortJobDriverDetailVo jxcShortJobDriverDetailVo = jxcShortJobMapper.getShortDriverDetail(driverId, shortJobId);
		if(jxcShortJobDriverDetailVo != null){
			if(jxcShortJobDriverDetailVo.getBirthday() != null){
				try {
					jxcShortJobDriverDetailVo.setAge(AgeBirthdayUtil.getAgeByBirth(jxcShortJobDriverDetailVo.getBirthday()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//查询是否评价过
			JxcScore jxcScore = new JxcScore();
			jxcScore.setOrderId(shortJobId.toString());
			jxcScore.setSourceId(user.getUserId());
			List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
			//评价状态(1:已评价; 0:未评价)
			if(CollectionUtils.isEmpty(jxcScores)){
				jxcShortJobDriverDetailVo.setEvaluationStatus(0);
			}else{
				jxcShortJobDriverDetailVo.setEvaluationStatus(1);
			}

			//查询司机评分
			ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(jxcShortJobDriverDetailVo.getDriverId());
			if(userInfoVoResponseMessage != null){
				UserInfoVo data = userInfoVoResponseMessage.getData();
				jxcShortJobDriverDetailVo.setScore(data.getScore());
			}
		}else{
			return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(),"该司机详情不存在");
		}
		return new ResponseMessage(jxcShortJobDriverDetailVo);
	}

}
