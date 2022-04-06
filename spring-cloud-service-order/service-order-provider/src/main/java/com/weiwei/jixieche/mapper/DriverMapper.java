package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.vo.AttendanceRecordVo;
import com.weiwei.jixieche.vo.DriverVo;
import com.weiwei.jixieche.vo.JxcAreaVo;
import com.weiwei.jixieche.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Description: 司机管理
* @Author:      liuy
* @Date:  2019/8/14 11:33
*/
public interface DriverMapper{

	/**
	 * 司机管理列表
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/14 10:40
	 */
	List<DriverVo> getAllDriverById(@Param("ownerUserId") Integer ownerUserId, @Param("flag") Integer flag);

	/**
	 * 检查司机是否已经注册过
	 * @param driverVo
	 * @return
	 */
	DriverVo checkDriver(DriverVo driverVo);
	
	/**
	 * 兼职司机展示工作结束时间
	 * @author  liuy
	 * @param driverId 司机用户ID
	 * @param ownerUserId 机主用户ID
	 * @return  
	 * @date    2019/8/14 16:59
	 */
	DriverVo getDriverEndDateById(@Param("driverId") Integer driverId, @Param("ownerUserId") Integer ownerUserId);

	/**
	 * 获取车辆所绑定司机列表信息
	 * @author  liuy
	* @param machineId 机械ID
	 * @return
	 * @date    2019/8/14 17:38
	 */
	List<DriverVo> getDriverListByMachineId(Integer machineId);

	/**
	 * 司机是否上班状态
	 * @author  liuy
	 * @param driverId 司机用户ID
	 * @return
	 * @date    2019/8/14 16:59
	 */
	int getDriverWorkStateById(@Param("driverId") Integer driverId);

	/**
	 * 司机-考勤记录
	 * @author  liuy
	 * @param attendanceRecordVo
	 * @return
	 * @date    2019/8/19 16:34
	 */
	List<AttendanceRecordVo> getDriverClockPair(AttendanceRecordVo attendanceRecordVo);

	/**
	 * 考勤统计总工时和出勤天数
	 * @author  liuy
	 * @param attendanceRecordVo
	 * @return
	 * @date    2019/8/19 16:53
	 */
	Map<String,Object> statisDriverWorkHours(AttendanceRecordVo attendanceRecordVo);

	/**
	 * 司机详情
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/21 14:02
	 */
	DriverVo getDriverById(@Param("id") Integer id);

	/**
	 * 历史司机列表
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/21 16:42
	 */
	List<DriverVo> getHistoryDriverList(Integer ownerUserId);

	/**
	 * 根据id查询城市和上级信息
	 * @param id
	 * @return
	 */
	JxcAreaVo queryCityById(@Param("id") Integer id);

	/**
	 * 查询用户认证信息
	 * @param phone
	 * @return
	 */
	UserInfoVo getUserInfoByPhone(@Param("phone") String phone);
}