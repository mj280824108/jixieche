package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMachineRefDriver;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMachineRefDriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "机械与司机或子账号绑定关系表")
@RestController
@RequestMapping("jxcMachineRefDriver")
public class JxcMachineRefDriverController {
	@Resource
	private JxcMachineRefDriverService jxcMachineRefDriverService;

	/**
	 * 绑定司机
	 *
	 * @param authorizationUser
	 * @return
	 * @author liuy
	 * @date 2019/8/15 17:09
	 */
	@ApiOperation(httpMethod = "POST", value = "绑定司机")
	@PostMapping("/insert")
	public ResponseMessage<JxcMachineRefDriver> insert(AuthorizationUser authorizationUser, @RequestBody JxcMachineRefDriver jxcMachineRefDriver) {
		jxcMachineRefDriver.setOwnerUserId(authorizationUser.getUserId());
		return this.jxcMachineRefDriverService.addObj(jxcMachineRefDriver);
	}

	/**
	 * 解绑司机
	 *
	 * @param authorizationUser
	 * @return
	 * @author liuy
	 * @date 2019/8/15 17:09
	 */
	@ApiOperation(httpMethod = "POST", value = "解绑司机")
	@PostMapping("/edit")
	public ResponseMessage<JxcMachineRefDriver> edit(AuthorizationUser authorizationUser, @RequestBody JxcMachineRefDriver jxcMachineRefDriver) {
		return this.jxcMachineRefDriverService.modifyObj(jxcMachineRefDriver);
	}

	/**
	 * 绑车记录(带分页)
	 *
	 * @param jxcMachineRefDriver
	 * @return
	 * @author liuy
	 * @date 2019/8/22 17:25
	 */
	@ApiOperation("绑车记录")
	@PostMapping("/getMachineRefDriverList")
	public ResponseMessage getMachineRefDriverList(AuthorizationUser authorizationUser, @RequestBody JxcMachineRefDriver jxcMachineRefDriver) {
		jxcMachineRefDriver.setUserId(authorizationUser.getUserId());
		return this.jxcMachineRefDriverService.getMachineRefDriverList(jxcMachineRefDriver);
	}

	/**
	 * 根据机械ID查询所绑定的司机列表
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/8/24 14:35
	 */
	@ApiOperation("根据机械ID查询所绑定的司机列表")
	@PostMapping("/getDriverListByMachineId")
	public ResponseMessage getDriverListByMachineId(@RequestParam("machineId") Integer machineId) {
			return this.jxcMachineRefDriverService.getDriverListByMachineId(machineId);
	}

	@ApiOperation("查询司机用户所绑定的车牌号码")
	@PostMapping("/getMachineCarNoByUserId")
	public ResponseMessage getMachineCarNoByUserId(@RequestParam("userId") Integer userId) {
		return this.jxcMachineRefDriverService.getMachineCarNoByUserId(userId);
	}

	@ApiOperation("绑车记录-解绑")
	@PostMapping("/updateDriverAndOwner")
	public ResponseMessage updateDriverAndOwner(AuthorizationUser authorizationUser, Integer ownerUserId, Integer machineId) {
		return this.jxcMachineRefDriverService.updateDriverAndOwner(authorizationUser, ownerUserId, machineId);
	}

	@ApiOperation("绑车记录-查询司机已绑定车信息和绑定的机主信息")
	@PostMapping("/getMachineRefDriverById")
	public ResponseMessage getMachineRefDriverById(AuthorizationUser authorizationUser){
		return this.jxcMachineRefDriverService.getDriverBindMachineById(authorizationUser);
	}

}