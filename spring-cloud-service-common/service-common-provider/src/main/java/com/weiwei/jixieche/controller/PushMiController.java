package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.PushMiService;
import com.weiwei.jixieche.vo.PushXmVo;
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
 * @Version 2.0
 **/
@Api(tags="公共模块--小米推送服务")
@RestController
@RequestMapping("/pushmi")
public class PushMiController {

    @Resource
    private PushMiService pushMiService;

    @ApiOperation(httpMethod="POST", value="小米推送")
    @PostMapping("/push")
    public ResponseMessage push(@RequestBody PushXmVo pushXmVo) {
        return this.pushMiService.push(pushXmVo);
    }

}