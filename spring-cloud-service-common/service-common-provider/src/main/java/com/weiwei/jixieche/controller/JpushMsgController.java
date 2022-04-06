package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.rabbit.RabbitSender;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JpushMsgService;
import com.weiwei.jixieche.vo.JpushCustomMsgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName 机关推送接口
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 16:27
 * @Version 1.0.1
 **/
@Api(tags="公共模块--极光推送接口模块")
@RestController
@RequestMapping("/jpush")
public class JpushMsgController {

    @Resource
    private JpushMsgService jpushMsgService;

    @Autowired
    private RabbitSender rabbitSender;

    /**
     * 极光推送通知
     * @param jpushTemplateVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="推送通知")
    @PostMapping(value = "/jpushNotice")
    public ResponseMessage jpushNotice(@RequestBody JpushTemplateVo jpushTemplateVo){
        return jpushMsgService.jpushNotice(jpushTemplateVo);
    }

    /**
     * 极光推送消息
     * @param jpushMessageVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="推送消息")
    @PostMapping(value = "/jpushMessage")
    public ResponseMessage jpushMessage(@RequestBody JpushMessageVo jpushMessageVo){
        return jpushMsgService.jpushMessage(jpushMessageVo);
    }


    /**
     * 推送使用rabbitMq发送通知
     * @param jpushTemplateVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="推送使用rabbitMq发送通知")
    @PostMapping(value = "/jpushRabbitMqMsg")
    public void jpushRabbitMqMsg(@RequestBody JpushTemplateVo jpushTemplateVo){
        rabbitSender.sendJpushMessage(jpushTemplateVo);
    }

    /**
     * 极光推送自定义消息
     * @param jpushCustomMsgVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="极光推送站内信")
    @PostMapping(value = "/jpushCustomMsg")
    public ResponseMessage jpushCustomMsg(@RequestBody JpushCustomMsgVo jpushCustomMsgVo){
        return jpushMsgService.jpushCustomMsg(jpushCustomMsgVo);
    }

}
