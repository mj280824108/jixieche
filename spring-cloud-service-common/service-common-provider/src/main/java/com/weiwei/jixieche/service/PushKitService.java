package com.weiwei.jixieche.service;


import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushKitVo;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface PushKitService{

    /**
     * 获取华为jpush的推送token
     * @return
     */
    ResponseMessage getAccessToken();

    /**
     * 华为推送消息
     * @return
     */
    ResponseMessage push(PushKitVo pushKitVo);
}
