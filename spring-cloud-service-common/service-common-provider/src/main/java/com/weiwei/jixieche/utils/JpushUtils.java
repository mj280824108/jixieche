package com.weiwei.jixieche.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.notification.Notification;
import com.weiwei.jixieche.config.JpushConfig;
import com.weiwei.jixieche.verify.VerifyStr;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JpushUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 18:18
 * @Version 1.0.1
 **/
@Component
@Data
@Configuration
public class JpushUtils {

    @Resource
    private JpushConfig jpushConfig;

    public static Map<String,String> optionMap = new HashMap<>();

/*    private JPushClient JPushClient_owner;

    private JPushClient JPushClient_tenantry;*/

    private JPushClient JPushClient_owner = new JPushClient("e8a9fa6c3a841c61e8fb5cc7", "09f5c44bcc0cdaded442ec4a");
    private JPushClient JPushClient_tenantry = new JPushClient("b8afc93cd4eddf39d2901ccc", "c39e592d40c83ddbeeed5d3f");

    public void checkAppKeyAndMasterSecret(int appClient) throws Exception {

        switch (appClient){
            case 0:{
                if (VerifyStr.isEmpty(jpushConfig.getAPP_KEY_OWNER()) || VerifyStr.isEmpty(jpushConfig.getMASTER_SECRET_OWNER()) || VerifyStr.isEmpty(jpushConfig.getAPP_KEY_TENANTRY()) || VerifyStr.isEmpty(jpushConfig.getMASTER_SECRET_TENANTRY())) {
                    throw new Exception();
                }
                break;
            }
            case 1:{
                if (VerifyStr.isEmpty(jpushConfig.getAPP_KEY_OWNER()) || VerifyStr.isEmpty(jpushConfig.getMASTER_SECRET_OWNER())) {
                    throw new Exception();
                }
                break;
            }
            case 2:{
                if (VerifyStr.isEmpty(jpushConfig.getAPP_KEY_TENANTRY()) || VerifyStr.isEmpty(jpushConfig.getMASTER_SECRET_TENANTRY())) {
                    throw new Exception();
                }
                break;
            }
        }
    }

    public PushPayload.Builder initPushPayloadBuilder(Notification notification, Message message, Options options) {
        PushPayload.Builder pushPayloadBuilder = PushPayload.newBuilder().setPlatform(Platform.android_ios()).setOptions(options);
        if (notification != null) {
            pushPayloadBuilder.setNotification(notification);
        }
        if (message != null) {
            pushPayloadBuilder.setMessage(message);
        }
        if (options != null) {
            pushPayloadBuilder.setOptions(options);
        }
        return pushPayloadBuilder;
    }


    public void sendWithClient(int appClient,PushPayload.Builder pushPayloadBuilder) throws Exception{
        switch (appClient){
            case 1: {doSend(pushPayloadBuilder,JPushClient_owner);break;}
            case 2: {doSend(pushPayloadBuilder,JPushClient_tenantry);break;}
            case 0: {
                doSend(pushPayloadBuilder,JPushClient_owner);
                doSend(pushPayloadBuilder,JPushClient_tenantry);
                break;
            }
        }
    }

    private static void doSend(PushPayload.Builder pushPayloadBuilder,JPushClient jPushClient) throws Exception{
        PushPayload pushPayload = pushPayloadBuilder.build();
        String errorMsg = "";
        try {
            PushResult result = jPushClient.sendPush(pushPayload);
            System.out.println("Got result - " + result);
        } catch (APIConnectionException e) {
            errorMsg = "Connection error. Should retry later. ";
            System.out.println(errorMsg + e);
            throw new Exception(errorMsg);
        } catch (APIRequestException e) {
            System.out.println("Error response from JPush server. Should review and fix it. " + e);
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Msg ID: " + e.getMsgId());
            errorMsg = "Error Code:" + e.getErrorCode() + "; Error Message:" + e.getErrorMessage() + "; Msg ID:" + e.getMsgId() ;
            throw new Exception(errorMsg);
        }
    }
}
