package com.weiwei.jixieche.service;


import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushMzVo;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface PushMzService {

    /**
     * 小米推送消息
     * @return
     */
    ResponseMessage push(PushMzVo pushMzVo);
}
