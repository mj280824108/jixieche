package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.mapper.JxcClockInOutRecordMapper;
import com.weiwei.jixieche.mapper.JxcShortJobMapper;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.vo.ClockRecord;
import com.weiwei.jixieche.vo.ClockRecordVo;
import com.weiwei.jixieche.vo.JxcDriverTeamCostVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 司机打卡公共方法
 * @author liuy
 * @date 2019-08-19 10:48
 */
@Component
public class JxcClockUtils {

	@Resource
	private JxcClockInOutRecordMapper jxcClockInOutRecordMapper;

	@Resource
	private JxcShortJobMapper jxcShortJobMapper;

	/**
	 * 获取某天第一次打上班的时间
	 * @author  liuy
	 * @param driverUserId
	 * @param clockDate 打卡日期
	 * @return
	 * @date    2019/8/19 11:37
	 */
	public String getClockInTime(Integer driverUserId, String clockDate){
		Map<String,Object> params = new HashMap<>();
		params.put("driverUserId", driverUserId);
		params.put("clockDate", clockDate);
		return jxcClockInOutRecordMapper.getClockInTime(params);
	}

	/**
	 * 获取某天最后一次打下班卡的时间
	 * @author  liuy
	 * @param driverUserId
	 * @param clockDate 打卡日期
	 * @return
	 * @date    2019/8/19 11:37
	 */
	public String getClockOutTime(Integer driverUserId, String clockDate){
		Map<String,Object> params = new HashMap<>();
		params.put("driverUserId", driverUserId);
		params.put("clockDate", clockDate);
		return jxcClockInOutRecordMapper.getClockOutTime(params);
	}


	/**
	 * 某一天的打卡次数统计
	 * @author  liuy
	 * @param driverUserId 司机用户ID
	 * @param startTime 打卡开始时间
	 * @param endTime 打卡结束时间
	 * @return
	 * @date    2019/8/19 10:51
	 */
	public Integer getClockCount(Integer driverUserId, String startTime, String endTime){
		Integer clockCount = 0;
		Map<String,Object> params = new HashMap<>();
		params.put("driverUserId", driverUserId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		clockCount = jxcClockInOutRecordMapper.getClockCount(params);
		return clockCount;
	}

	/**
	 * 某一天的总趟数统计
	 * @author  liuy
	 * @param driverUserId 司机用户ID
	 * @param startTime 打卡开始时间
	 * @param endTime 打卡结束时间
	 * @return
	 * @date    2019/8/19 10:51
	 */
	public Integer getTotalCountRoute(Integer driverUserId, String startTime, String endTime){
		Integer totalCount = 0;
		Map<String,Object> params = new HashMap<>();
		params.put("driverUserId", driverUserId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		totalCount = jxcClockInOutRecordMapper.getTotalCountRoute(params);
		return totalCount;
	}

	/**
	 * 待收工资计算
	 * @param clockInId
	 * @param overtimeRoute 加班趟数
	 * @param areaId 区域ID
	 * @param workHours 工时
	 * @return
	 */
	public BigDecimal getTotalAmount(Long clockInId, Integer overtimeRoute, Integer areaId, double workHours){
		Map<String, Object> map = new HashMap<>();

		BigDecimal totalAmount = new BigDecimal(0.00);

		//查出城市台班费和加班趟数费用
		int driverTeamPrice = 0;
		int driverOutPrice = 0;
		JxcDriverTeamCostVo jxcDriverTeamCostVo = this.jxcShortJobMapper.queryDriverTeamCost(areaId);
		if (jxcDriverTeamCostVo != null) {
			driverTeamPrice = jxcDriverTeamCostVo.getDriverTeamPrice();
			driverOutPrice = jxcDriverTeamCostVo.getDriverOutPrice();
			//1.有加班趟数时 计算方式 8小时台班费用+加班趟数*加班趟数费用
			if(overtimeRoute > 0){
				//计算每小时平均的台班费
				totalAmount = new BigDecimal(driverTeamPrice).add(new BigDecimal(overtimeRoute).multiply(new BigDecimal(driverOutPrice))).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				//工作时间刚好是一个台班时
				if(workHours == 8){
					totalAmount = new BigDecimal(driverTeamPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				//工作时间小于8小时时
				else if(workHours < 8){
					BigDecimal bigDecimal = new BigDecimal(workHours - 8);
					//计算每小时平均的台班费
					BigDecimal price = new BigDecimal(driverTeamPrice).divide(new BigDecimal(8), 2, BigDecimal.ROUND_HALF_UP);
					//计算费用
					totalAmount = price.multiply(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
			}
		}
		//查询是否有异常申诉记录 如果有取出协商价格
		Map<String, Object> map1 = jxcClockInOutRecordMapper.getAbnormalApplyState(clockInId);
		if(!CollectionUtils.isEmpty(map1)){
			if (map1.get("abnormalStatus") != null && (Integer) map1.get("abnormalStatus") == 0) {
				map.put("totalAmount", totalAmount);
			} else {
				BigDecimal consultPrice = new BigDecimal(String.valueOf(map1.get("consultPrice")));
				if (consultPrice.compareTo(BigDecimal.ZERO) > 0) {
					totalAmount = consultPrice.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
				}
			}
		}
		return totalAmount;
	}

	/**
	 * 计算加班趟数和总工时
	 * @author  liuy
	 * @param clockRecordList
	 * @return
	 * @date    2019/8/17 18:30
	 */
	public ClockRecordVo getClockRecordList(ClockRecordVo clockRecordVo, List<ClockRecord> clockRecordList,
	                                        Integer driverUserId, String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//总工时
		double totalCountHours = 0;
		//是否有加班
		boolean isOver = false;
		//超过8小时的时间点或者跨天的那个时间点
		Date outTime = null;

		for (ClockRecord clockRecord : clockRecordList) {
			//上班时间
			String clockInTime = clockRecord.getClockInTime();
			//下班时间
			String clockOutTime = clockRecord.getClockOutTime();

			//没有打下班卡时
			if (StringUtils.isBlank(clockOutTime)) {
				continue;
			}
			//工时计算
			Date dateIn = null;
			Date dateOut = null;

			try {
				dateIn = sdf.parse(clockInTime);
				dateOut = sdf.parse(clockOutTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//计算一个台班工时
			double workHours = DateUtils.getDistanceTime2(dateOut, dateIn);
			totalCountHours += workHours;
			if (totalCountHours > 8) {
				double hour = totalCountHours - 8;
				//超过8小时的
				int addMinutes = (int) (hour * 60);
				if(outTime == null) {
					//计算超过8小时的时间点
					outTime = DateUtils.addDateMinutes(dateOut, -addMinutes);
					isOver = true;
				}
			}
		}
		clockRecordVo.setWorkHours(totalCountHours);
		//加班趟数
		if (isOver) {
			Map<String, Object> params1 = new HashMap<>();
			params1.put("driverId", driverUserId);
			params1.put("startTime", outTime);
			params1.put("endTime", endTime);
			int overCount = jxcClockInOutRecordMapper.getTotalCountRoute(params1);
			//加班趟数
			clockRecordVo.setOverTrainNum(overCount);
		}
		return clockRecordVo;
	}
}
