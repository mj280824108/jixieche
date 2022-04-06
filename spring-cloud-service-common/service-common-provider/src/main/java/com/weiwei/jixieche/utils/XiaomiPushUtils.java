package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.config.PushXmConfig;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName XiaomiPushUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/7/19 17:01
 * @Version 1.0.1
 **/
@Component
public class XiaomiPushUtils {

    @Autowired
    private PushXmConfig pushXmConfig;

    public Result sendMessageToAliases(String secret,String page,List<String> thirdIdList) throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(secret);
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        List<String> aliasList = thirdIdList;
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(page)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        Result result = sender.sendToAlias(message, aliasList, thirdIdList.size()); //根据aliasList, 发送消息到指定设备上
        return result;
    }


}
