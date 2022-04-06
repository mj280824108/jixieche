package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.PushMzService;
import com.weiwei.jixieche.vo.PushMzVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName JxcMsgController
 * @Description 小米推送服务
 * @Author houji
 * @Date 2019/5/15 11:03
 * @Version 1.0.1
 **/
@Api(tags="公共模块--魅族推送服务")
@RestController
@RequestMapping("/pushmz")
public class PushMzController {

    @Resource
    private PushMzService pushMzService;

    @ApiOperation(httpMethod="POST", value="魅族推送")
    @PostMapping("/push")
    public ResponseMessage push(@RequestBody PushMzVo pushMzVo) {
        return this.pushMzService.push(pushMzVo);
    }

}