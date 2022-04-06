package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcOwnerOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 泥头车首页
 * @author liuy
 * @date 2019-08-22 17:08
 */
@Api(tags="泥头车首页")
@RestController
@RequestMapping("ownerIndex")
public class OwnerIndexController {

	@Autowired
	private JxcOwnerOrderService jxcOwnerOrderService;

	/**
	 * 泥头车首页
	 * @author  liuy
	 * @return
	 * @date    2019/8/22 16:39
	 */
	@ApiOperation("泥头车首页")
	@PostMapping("/index")
	public ResponseMessage getOwnerIndex(AuthorizationUser authorizationUser){
		return this.jxcOwnerOrderService.getOwnerIndex(authorizationUser.getUserId());
	}

	/**
	 * 泥头车接单引导认证
	 * @author  liuy
	 * @param authorizationUser
	 * @return
	 * @date    2019/8/29 17:42
	 */
	@ApiOperation("泥头车接单引导认证")
	@PostMapping("/checkOwnerCertification")
	public ResponseMessage checkOwnerCertification(AuthorizationUser authorizationUser){
		return this.jxcOwnerOrderService.checkOwnerCertification(authorizationUser.getUserId());
	}

	/**
	 * 上班打卡前-检查进行中订单是否跑趟中或是正在上班中
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/4 15:34
	 */
	@ApiOperation("上班打卡前-检查进行中订单是否跑趟中或是正在上班中")
	@PostMapping("/checkMachineWorkIn")
	public ResponseMessage checkMachineWorkIn(AuthorizationUser authorizationUser, @RequestParam("machineId")Integer machineId){
		return this.jxcOwnerOrderService.checkMachineWorkIn(authorizationUser, machineId);
	}


	/**
	 * 获取电子消纳券
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/10 16:26
	 */
	@ApiOperation("获取电子消纳券")
	@PostMapping("/getSiteCouponByMachineId")
	public ResponseMessage getSiteCouponByMachineId(@RequestParam("machineId") Integer machineId) {
		return jxcOwnerOrderService.getSiteCouponByMachineId(machineId);
	}

}
