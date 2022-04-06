package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.vo.MachineDataListVo;
import com.weiwei.jixieche.vo.MachineDataVo;
import com.weiwei.jixieche.vo.OwnerMachineStatistics;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
* @Description: 机主-数据统计
* @Author:      liuy
* @Date:  2019/9/2 10:37
*/
public interface OwnerStatisticsMapper {

	/**
	 * 根据日期查询机主完结趟数(昨天，前天，本周，本月)
	 * @param params
	 * @return
	 */
	Integer queryFinishCountByDate(@Param("params") Map<String, Object> params);

	/**
	 * 根据日期查询机主已完结总金额(昨天，前天，本周，本月)
	 * @param params
	 * @return
	 */
	BigDecimal queryFinishAmountByDate(@Param("params") Map<String, Object> params);

	/**
	 * 根据日期查询机主已完结总金额(昨天，前天，本周，本月)
	 * @param params
	 * @return
	 */
	Integer queryAbnormalCountByDate(@Param("params") Map<String, Object> params);

	/**
	 * 根据月份的起始时间和结束时间查询机械
	 * @param params
	 * @return
	 */
	Integer queryMonthMachineCount(@Param("params") Map<String, Object> params);

	/**
	 * 根据月份起始时间和结束时间查询运行趟数
	 * @param params
	 * @return
	 */
	Integer queryMonthRouteCount(@Param("params") Map<String, Object> params);

	/**
	 * 查询机械统计
	 * (日期日期(yyyy-MM-dd)或
	 * (yyyy-MM-dd)-(yyyy-MM-dd))数据
	 * @param params
	 * @return
	 */
	List<MachineDataListVo> queryMachineDayData(@Param("params") Map<String, Object> params);

	/**
	 * 根据日期查询机主已完结总趟数(yyyy-MM-dd)
	 * @param params
	 * @return
	 */
	MachineDataVo queryDayFinishTotalCount(@Param("params") Map<String, Object> params);

}
