package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JpushFeignHystrix;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JpushCustomMsgVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = JpushFeignHystrix.class)
public interface JpushMsgFeign {
    /**
     * 极光推送通知
     * @param jpushTemplateVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="推送通知")
    @PostMapping(value = "/jpush/jpushNotice")
    ResponseMessage jpushNotice(@RequestBody JpushTemplateVo jpushTemplateVo);

    /**
     * 极光推送消息
     * @param jpushMessageVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="推送消息")
    @PostMapping(value = "/jpush/jpushMessage")
    ResponseMessage jpushMessage(@RequestBody JpushMessageVo jpushMessageVo);


    /**
     * 极光推送自定义消息
     * @param jpushCustomMsgVo
     * @return
     */
    @ApiOperation(httpMethod="POST", value="极光推送站内信")
    @PostMapping(value = "/jpush/jpushCustomMsg")
    ResponseMessage jpushCustomMsg(@RequestBody JpushCustomMsgVo jpushCustomMsgVo);
}
