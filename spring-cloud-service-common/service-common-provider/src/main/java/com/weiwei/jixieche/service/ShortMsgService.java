package com.weiwei.jixieche.service;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ShortMsgVo;

/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface ShortMsgService {

    /**
     * 阿里发送短信
     * @param shortMsgVo
     * @return
     */
    ResponseMessage sendShortMsg(ShortMsgVo shortMsgVo);

}
