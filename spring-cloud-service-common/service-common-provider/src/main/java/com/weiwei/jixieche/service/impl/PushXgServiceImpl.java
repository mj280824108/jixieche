package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weiwei.jixieche.config.PushXgConfig;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.PushXgService;
import com.weiwei.jixieche.utils.XingePushUtils;
import com.weiwei.jixieche.vo.PushXgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName JxcMsgServiceImpl
 * @Description TODO
 * @Author houji
 * @Date 2019/5/15 11:07
 * @Version 1.0.1
 **/
@Service("pushXgService")
public class PushXgServiceImpl implements PushXgService {

    @Autowired
    private XingePushUtils xingePushUtils;

    @Autowired
    private PushXgConfig pushXgConfig;

    //华为推送通知
    @Override
    public ResponseMessage pushNotifyByAccount(PushXgVo pushXgVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platform","android");
        jsonObject.put("audience_type","account");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(pushXgVo.getThirdId());
        jsonObject.put("account_list",jsonArray);
        jsonObject.put("message_type","notify");
        JSONObject message = new JSONObject();
        message.put("title",pushXgVo.getTitle());
        message.put("content",pushXgVo.getContent());
        message.put("custom_content",pushXgVo.getCustomContent());
        jsonObject.put("message",message);
        this.xingePushUtils.postJsonDataToServer(pushXgConfig.getPushUrl(),jsonObject.toString());
        return result;
    }

    //腾讯信鸽推送Android透传消息
    @Override
    public ResponseMessage pushMessageByAccount(PushXgVo pushXgVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platform","android");
        jsonObject.put("audience_type","account");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(pushXgVo.getThirdId());
        jsonObject.put("account_list",jsonArray);
        jsonObject.put("message_type","message");
        JSONObject message = new JSONObject();
        message.put("title",pushXgVo.getTitle());
        message.put("content",pushXgVo.getContent());
        JSONObject customContent = new JSONObject();
        customContent.put("custom_content",pushXgVo.getCustomContent());
        message.put("android",customContent);
        jsonObject.put("message",message);
        this.xingePushUtils.postJsonDataToServer(pushXgConfig.getPushUrl(),jsonObject.toString());
        return result;
    }


}
