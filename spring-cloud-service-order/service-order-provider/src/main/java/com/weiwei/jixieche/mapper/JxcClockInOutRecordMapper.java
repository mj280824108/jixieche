package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcClockInOutPair;
import com.weiwei.jixieche.bean.JxcClockInOutRecord;
import com.weiwei.jixieche.vo.ClockRecord;
import com.weiwei.jixieche.vo.ClockRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @Description: 司机打卡记录
* @Author:      liuy
* @Date:  2019/8/17 10:18
*/
public interface JxcClockInOutRecordMapper extends BaseMapper<JxcClockInOutRecord> {

	/**
	 * 取最后一条上下班记录ID
	 * @author  liuy
	* @param jxcClockInOutPair
	 * @return
	 * @date    2019/8/17 11:23
	 */
	Long getClockInId(JxcClockInOutPair jxcClockInOutPair);

	/**
	 * 司机打卡记录
	 * @author  liuy
	 * @param params
	 * @return
	 * @date    2019/8/17 13:58
	 */
	List<ClockRecord> getClockRecordList(@Param("params") Map<String, Object> params);

	/**
	 * 查询司机台班打卡记录,统计趟数
	 * @author  liuy
	 * @param params
	 * @return
	 * @date    2019/8/17 14:23
	 */
	Integer getTotalCountRoute(@Param("params") Map<String, Object> params);

	/**
	 * 根据台班ID查询
	 * @param clockInId
	 * @return
	 */
	ClockRecord getClockRecordById(@Param("clockInId")Long clockInId);

	/**
	 * 查询异常情况
	 * @param clockInId
	 * @return
	 */
	Map<String,Object> getAbnormalApplyState(@Param("clockInId") Long clockInId);

	/**
	 * 查询司机打卡日期
	 * @param params
	 * @return
	 */
	List<ClockRecordVo> getClockDateById(@Param("params") Map<String, Object> params);
	Long hasNextPage(@Param("params") Map<String, Object> params);

	/**
	 * 查询司机考勤工资
	 * @param params
	 * @return
	 */
	List<ClockRecordVo> getAttendancePayById(@Param("params") Map<String, Object> params);
	Long hasNextPageAttendancePay(@Param("params") Map<String, Object> params);

	/**
	 * 考勤工资-详情
	 * @author  liuy
	* @param params
	 * @return
	 * @date    2019/9/4 17:08
	 */
	ClockRecordVo getAttendancePayDetail(@Param("params") Map<String, Object> params);

	/**
	 * 打卡次数统计
	 * @author  liuy
	* @param params
	 * @return
	 * @date    2019/8/19 10:55
	 */
	Integer getClockCount(@Param("params") Map<String, Object> params);

	/**
	 * 获取某天第一次打上班的时间
	 * @author  liuy
	 * @param params
	 * @return
	 * @date    2019/8/19 11:09
	 */
	String getClockInTime(@Param("params") Map<String, Object> params);

	/**
	 * 获取某天最后一次打下班卡的时间
	 * @author  liuy
	 * @param params
	 * @return
	 * @date    2019/8/19 11:09
	 */
	String getClockOutTime(@Param("params") Map<String, Object> params);

	/**
	 * 排除重复提交表单
	 * @param machineId
	 * @return
	 */
	JxcClockInOutRecord getJxcClockInOutRecord(@Param("machineId") Integer machineId);

	/**
	 * 查询目前该机械是否有人正在上班
	 * @param machineId
	 * @return
	 */
	JxcClockInOutPair getJxcClockInOutRecordByMachineId(@Param("machineId") Integer machineId);

}