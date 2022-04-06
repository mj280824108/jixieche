package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.AppVersionService;
import com.weiwei.jixieche.vo.AppVersionFormVo;
import com.weiwei.jixieche.vo.AppVersionResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 钟焕 
 * @Description
 * @Date 14:14 2019-10-14
 **/
@Api(tags = "系统管理-App版本管理")
@RestController
@RequestMapping("/appVersion")
public class JxcAppVersionController {

	@Autowired
	private AppVersionService appVersionService;


	@ApiOperation("iOS版本更新检查")
	@PostMapping("/checkIosNeedUpdate")
	public ResponseMessage<AppVersionResponseVo> checkIosNeedUpdate(@RequestBody AppVersionFormVo form) {
		return appVersionService.checkIosVersion(form);
	}

	@ApiOperation("android版本更新检查")
	@PostMapping("/checkAndroidNeedUpdate")
	public ResponseMessage<AppVersionResponseVo> checkAndroidNeedUpdate(@RequestBody AppVersionFormVo form) {
		return appVersionService.checkAndroidVersion(form);
	}

}
