package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.DriverService;
import com.weiwei.jixieche.vo.AttendanceRecordVo;
import com.weiwei.jixieche.vo.DriverVo;
import com.weiwei.jixieche.vo.JxcMachineRouteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags="司机管理")
@RestController
@RequestMapping("driver")
public class DriverController {

	@Resource
	private DriverService driverService;

	/**
	 * 司机管理列表
	 * @author  liuy
	 * @param authorizationUser 机主用户ID
	 * @return
	 * @date    2019/8/14 16:14
	 */
	@ApiOperation("司机管理列表")
	@PostMapping("/getAllDriverList")
	public ResponseMessage<DriverVo> getAllDriverList(AuthorizationUser authorizationUser, @RequestParam(defaultValue = "1") Integer pageNo,
	                                                  @RequestParam(defaultValue = "10") Integer pageSize,
	                                                  @RequestParam(value = "flag",defaultValue = "1") Integer flag){
		return driverService.getAllDriverById(authorizationUser.getUserId(), pageNo, pageSize, flag);
	}

	/**
	 * 添加司机
	 * @author  liuy
	 * @param authorizationUser 机主用户信息
	 * @param  driverVo 司机信息
	 * @return
	 * @date    2019/8/14 19:30
	 */
	@ApiOperation("添加司机")
	@PostMapping("/addDriver")
	public ResponseMessage addDriver(AuthorizationUser authorizationUser, @RequestBody DriverVo driverVo){
		return driverService.addDriver(authorizationUser, driverVo);
	}

	/**
	 * 考勤记录
	 * @author  liuy
	 * @param attendanceRecordVo
	 * @return
	 * @date    2019/8/19 17:11
	 */
	@ApiOperation("考勤记录")
	@PostMapping("/getDriverClockPair")
	public ResponseMessage getDriverClockPair(AuthorizationUser authorizationUser, @RequestBody(required = false) AttendanceRecordVo attendanceRecordVo){
		if(null == attendanceRecordVo){
			attendanceRecordVo = new AttendanceRecordVo();
		}
		return driverService.getDriverClockPair(attendanceRecordVo);
	}


	/**
	 * 趟数记录
	 * @author  liuy
	 * @param jxcMachineRouteVo
	 * @return
	 * @date    2019/8/20 14:15
	 */
	@ApiOperation("趟数记录")
	@PostMapping("/getMachineRouteList")
	public ResponseMessage getMachineRouteList(AuthorizationUser authorizationUser, @RequestBody(required = false)JxcMachineRouteVo jxcMachineRouteVo){
		if(null == jxcMachineRouteVo){
			jxcMachineRouteVo = new JxcMachineRouteVo();
		}
		return driverService.getMachineRouteList(jxcMachineRouteVo);
	}

	/**
	 * 司机详情
	 * @author  liuy
	 * @param id 主键ID
	 * @return
	 * @date    2019/8/21 14:12
	 */
	@ApiOperation("司机详情")
	@PostMapping("/getDriverById")
	public ResponseMessage getDriverById(AuthorizationUser authorizationUser, @RequestParam("id")Integer id){
		return driverService.getDriverById(id);
	}

	/**
	 * 检查司机是否跑趟中或是正在上班中
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 15:31
	 */
	@ApiOperation("检查司机是否跑趟中或是正在上班中")
	@PostMapping("/checkDriverRunOrWorkIn")
	public ResponseMessage checkDriverRunOrWorkIn(@RequestParam("driverId")Integer driverId){
		return driverService.checkDriverRunOrWorkIn(driverId);
	}

	/**
	 * 司机删除
	 * @author  liuy
	 * @param driverId
	 * @param typeId 1:删除; 2:解雇
	 * @return
	 * @date    2019/8/21 15:36
	 */
	@ApiOperation("司机-删除")
	@PostMapping("/delDriver")
	public ResponseMessage delDriver(AuthorizationUser authorizationUser, @RequestParam("driverId")Integer driverId,
	                                 @RequestParam("typeId")Integer typeId,
	                                 @RequestParam(value = "reason", required = false) String reason,
	                                 @RequestParam(value = "shortJobId",required = false) Integer shortJobId){
		return driverService.delDriver(authorizationUser.getUserId(), driverId, typeId, reason, shortJobId);
	}

	/**
	 * 历史司机列表
	 * @author  liuy
	 * @param authorizationUser
	 * @return
	 * @date    2019/8/21 16:42
	 */
	@ApiOperation("历史司机")
	@PostMapping("/getHistoryDriverList")
	public ResponseMessage getHistoryDriverList(AuthorizationUser authorizationUser, @RequestParam(defaultValue = "1") Integer pageNo,
	                                            @RequestParam(defaultValue = "10") Integer pageSize){
		return driverService.getHistoryDriverList(authorizationUser.getUserId(), pageNo, pageSize);
	}

	/**
	 * 添加子账号与机主关联关系
	 * @author  liuy
	 * @param jxcDriverRefOwner
	 * @return
	 * @date    2019/8/29 11:11
	 */
	@ApiOperation("添加子账号与机主关联关系")
	@PostMapping("/addDriverRefOwner")
	public ResponseMessage addDriverRefOwner(@RequestBody JxcDriverRefOwner jxcDriverRefOwner){
		return driverService.addDriverRefOwner(jxcDriverRefOwner);
	}

	/**
	 * 考勤工资
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	@ApiOperation("考勤工资")
	@PostMapping("/driverAttendancePay")
	public ResponseMessage driverAttendancePay(AuthorizationUser authorizationUser, @RequestParam("driverId") Integer driverId,
	                                           @RequestParam(value = "lastPageLastId", required = false) String lastPageLastId,
	                                           @RequestParam(value = "shortJobId", required = false) Integer shortJobId){
		return driverService.driverAttendancePay(driverId, lastPageLastId, shortJobId);
	}

	/**
	 * 考勤详情
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/4 16:18
	 */
	@ApiOperation("考勤详情")
	@PostMapping("/driverAttendancePayDetail")
	public ResponseMessage driverAttendancePayDetail(AuthorizationUser authorizationUser, @RequestParam("driverId") Integer driverId,
	                                                 @RequestParam("clockId") String clockId, @RequestParam(value = "lastPageLastId", required = false) String lastPageLastId){
		return driverService.driverAttendancePayDetail(driverId, clockId, lastPageLastId);
	}

}