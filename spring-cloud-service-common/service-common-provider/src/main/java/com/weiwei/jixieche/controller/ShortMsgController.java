package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.ShortMsgService;
import com.weiwei.jixieche.vo.ShortMsgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName ShortMsgController
 * @Description TODO
 * @Author houji
 * @Date 2019/8/23 16:35
 * @Version 1.0.1
 **/
@Api(tags="公共模块--阿里云短信接口模块")
@RestController
@RequestMapping("/shortMsg")
public class ShortMsgController {

    @Resource
    private ShortMsgService shortMsgService;

    @ApiOperation(httpMethod="POST", value="阿里发送短信")
    @PostMapping("/sendShortMsg")
    public ResponseMessage sendShortMsg(@RequestBody ShortMsgVo shortMsgVo) {
        return this.shortMsgService.sendShortMsg(shortMsgVo);
    }
}
