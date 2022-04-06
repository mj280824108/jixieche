package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AttendanceRecordVo;
import com.weiwei.jixieche.vo.DriverVo;
import com.weiwei.jixieche.vo.JxcMachineRouteVo;

/**
* @Description: 司机管理
* @Author:      liuy
* @Date:  2019/8/14 10:30
*/
public interface DriverService {

	/**
	 * 司机管理列表
	 * @author  liuy
	 * @param ownerUserId 机主用户ID
	 * @return
	 * @date    2019/8/14 13:44
	 */
	ResponseMessage getAllDriverById(Integer ownerUserId, Integer pageNo, Integer pageSize, Integer flag);

	/**
	 * 添加司机
	 * @author  liuy
	 * @param authorizationUser 机主用户信息
	 * @param driverVo
	 * @return
	 * @date    2019/8/14 16:00
	 */
	ResponseMessage addDriver(AuthorizationUser authorizationUser, DriverVo driverVo);

	/**
	 * 司机-考勤记录
	 * @author  liuy
	 * @param attendanceRecordVo
	 * @return
	 * @date    2019/8/19 16:34
	 */
	ResponseMessage getDriverClockPair(AttendanceRecordVo attendanceRecordVo);

	/**
	 * 趟数记录
	 * @author  liuy
	 * @param jxcMachineRouteVo
	 * @return
	 * @date    2019/8/20 13:52
	 */
	ResponseMessage getMachineRouteList(JxcMachineRouteVo jxcMachineRouteVo);

	/**
	 * 司机详情
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/21 13:50
	 */
	ResponseMessage getDriverById(Integer id);

	/**
	 * 检查司机是否跑趟中或是正在上班中
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 15:28
	 */
	ResponseMessage checkDriverRunOrWorkIn(Integer driverId);

	/**
	 * 删除司机
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 15:36
	 */
	ResponseMessage delDriver(Integer ownerUserId, Integer driverId, Integer typeId, String reason, Integer shortJobId);

	/**
	 * 历史司机列表
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/21 16:42
	 */
	ResponseMessage getHistoryDriverList(Integer ownerUserId, Integer pageNo, Integer pageSize);

	/**
	 * 添加子账号与机主关联关系
	 * @author  liuy
	 * @param jxcDriverRefOwner
	 * @return  
	 * @date    2019/8/29 11:05
	 */
	ResponseMessage addDriverRefOwner(JxcDriverRefOwner jxcDriverRefOwner);

	/**
	 * 考勤工资
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	ResponseMessage driverAttendancePay(Integer driverId, String lastPageLastId, Integer shortJobId);

	/**
	 * 考勤工资-详情
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	ResponseMessage driverAttendancePayDetail(Integer driverId, String clockId, String lastPageLastId);
}