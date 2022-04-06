package com.weiwei.jixieche.service;


import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushXgVo;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface PushXgService {

    /**
     * 腾讯信鸽通知
     * @return
     */
    ResponseMessage pushNotifyByAccount(PushXgVo pushXgVo);

    /**
     * 腾讯信鸽推送Android透传消息
     * @param pushXgVo
     * @return
     */
    ResponseMessage pushMessageByAccount(PushXgVo pushXgVo);
}
