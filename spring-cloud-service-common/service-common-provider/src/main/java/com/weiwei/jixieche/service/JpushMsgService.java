package com.weiwei.jixieche.service;


import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JpushCustomMsgVo;

/**
 * @ClassName JpushMsgService
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 17:52
 * @Version 1.0.1
 **/
public interface JpushMsgService {

    /**
     * 推送通知
     * @param jpushTemplateVo
     * @return
     */
    ResponseMessage jpushNotice(JpushTemplateVo jpushTemplateVo);

    /**
     * 推送自定义消息
     */
    ResponseMessage jpushMessage(JpushMessageVo jpushMessageVo);

    /**
     * 极光推送站内信(站内信)
     * @param socketPushMsgVo
     * @return
     */
    ResponseMessage jpushCustomMsg(JpushCustomMsgVo socketPushMsgVo);
}
