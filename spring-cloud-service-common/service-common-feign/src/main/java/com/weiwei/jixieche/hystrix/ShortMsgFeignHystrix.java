package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.ShortMsgFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import com.weiwei.jixieche.vo.ShortMsgVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushMsgFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:15
 * @Version 1.0.1
 **/
@Component
public class ShortMsgFeignHystrix implements ShortMsgFeign {

    @Override
    public ResponseMessage aliSendShortMsg(AliShortMsgVo aliShortMsgVo) {
        return null;
    }
}
