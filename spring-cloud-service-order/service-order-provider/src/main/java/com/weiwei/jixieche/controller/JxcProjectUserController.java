package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.EagleEyesFeign;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcProjectUserService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.vo.EagleEyesVo;
import com.weiwei.jixieche.vo.JxcSendSiteCouponVo;
import com.weiwei.jixieche.vo.SiteClockVo;
import com.weiwei.jixieche.vo.TabPageListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 施工场地及消纳场地打卡
 * @author liuy
 * @date 2019-08-26 11:00
 */
@Api(tags = "承租方管理员扫一扫")
@RestController
@RequestMapping("jxcProjectUser")
public class JxcProjectUserController {

	@Resource
	private JxcProjectUserService jxcProjectUserService;

	@ApiOperation("承方管理员扫一扫")
	@PostMapping("/projectUserScan")
	public ResponseMessage projectUserScan(AuthorizationUser user, @RequestParam("machineCarNo") String machineCarNo, @RequestParam("flag")Integer flag){
		return jxcProjectUserService.projectUserScan(user,machineCarNo, flag);
	}

	@ApiOperation("承租方管理员确认补上一趟的卡")
	@PostMapping("/confirmClock")
	public ResponseMessage confirmClock(@RequestParam("machineCarNo") String machineCarNo){
		return jxcProjectUserService.confirmClock(machineCarNo);
	}

	@ApiOperation("手动发卡机械列表以及发卡记录列表")
	@PostMapping("/waitSendCouponMachineList")
	public ResponseMessage waitSendCouponMachineList(AuthorizationUser user,@RequestBody JxcSendSiteCouponVo vo){
		return jxcProjectUserService.waitSendCouponMachineList(user,vo);
	}

	@ApiOperation("承方管理员实体卡")
	@PostMapping("/entityCardClock")
	public ResponseMessage entityCardClock(@RequestParam("machineCarNo") String machineCarNo){
		return jxcProjectUserService.entityCardClock(machineCarNo);
	}

	@ApiOperation("消纳场管理员扫司机码或者司机扫消纳场二维码")
	@PostMapping("/siteAdminScan")
	public ResponseMessage siteAdminScan(AuthorizationUser user,@RequestBody SiteClockVo siteClockVo){
		return jxcProjectUserService.siteAdminScan(user,siteClockVo);
	}

	@ApiOperation("消纳券核销")
	@PostMapping("/siteClockIn")
	public ResponseMessage siteClockIn(AuthorizationUser user,@RequestBody SiteClockVo siteClockVo){
		return jxcProjectUserService.siteClockIn(user,siteClockVo);
	}

	@ApiOperation("消纳场管理员查看消纳券记录")
	@PostMapping("/selectSiteCouponUsedList")
	public ResponseMessage selectSiteCouponList(AuthorizationUser user,@RequestBody TabPageListVo tabPageListVo){
		return jxcProjectUserService.selectSiteCouponList(user,tabPageListVo);
	}

}
