package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.PushXgService;
import com.weiwei.jixieche.vo.PushXgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName JxcMsgController
 * @Description 腾讯信鸽服务
 * @Author houji
 * @Date 2019/5/15 11:03
 * @Version 1.0.1
 **/
@Api(tags="公共模块--腾讯信鸽推送服务")
@RestController
@RequestMapping("/pushxg")
public class PushXgController {

    @Resource
    private PushXgService pushXgService;

    @ApiOperation(httpMethod="POST", value="腾讯信鸽推送通知")
    @PostMapping("/pushNotifyByAccount")
    public ResponseMessage pushNotifyByAccount(@RequestBody PushXgVo pushXgVo) {
        return this.pushXgService.pushNotifyByAccount(pushXgVo);
    }

    @ApiOperation(httpMethod="POST", value="腾讯信鸽推送Android透传消息")
    @PostMapping("/pushMessageByAccount")
    public ResponseMessage pushMessageByAccount(@RequestBody PushXgVo pushXgVo) {
        return this.pushXgService.pushMessageByAccount(pushXgVo);
    }

}