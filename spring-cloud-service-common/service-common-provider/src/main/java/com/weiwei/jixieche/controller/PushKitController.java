package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.PushKitService;
import com.weiwei.jixieche.vo.PushKitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName JxcMsgController
 * @Description 华为推送服务
 * @Author houji
 * @Date 2019/5/15 11:03
 * @Version 1.0.1
 **/
@Api(tags="公共模块--华为推送服务")
@RestController
@RequestMapping("/pushkit")
public class PushKitController {

    @Resource
    private PushKitService pushKitService;

    @ApiOperation(httpMethod="POST", value="获取华为机主推送访问的ACCESSTOKEN")
    @PostMapping("/getAccessToken")
    public ResponseMessage getAccessToken() {
        return this.pushKitService.getAccessToken();
    }

    @ApiOperation(httpMethod="POST", value="华为推送")
    @PostMapping("/push")
    public ResponseMessage push(@RequestBody PushKitVo pushKitVo) {
        return this.pushKitService.push(pushKitVo);
    }

}