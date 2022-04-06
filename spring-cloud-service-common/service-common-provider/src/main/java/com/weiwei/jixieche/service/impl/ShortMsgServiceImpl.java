package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.ShortMsgService;
import com.weiwei.jixieche.vo.ShortMsgVo;
import org.springframework.stereotype.Service;

/**
 * @ClassName JxcMsgServiceImpl
 * @Description TODO
 * @Author houji
 * @Date 2019/5/15 11:07
 * @Version 1.0.1
 **/
@Service("shortMsgService")
public class ShortMsgServiceImpl implements ShortMsgService {

    /**
     * 阿里发送短信
     * @param shortMsgVo
     * @return
     */
    @Override
    public ResponseMessage sendShortMsg(ShortMsgVo shortMsgVo) {
        return null;
    }
}
