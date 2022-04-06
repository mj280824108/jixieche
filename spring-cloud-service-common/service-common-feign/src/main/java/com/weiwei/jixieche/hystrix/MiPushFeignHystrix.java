package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.MiPushFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushXmVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushMsgFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:15
 * @Version 1.0.1
 **/
@Component
public class MiPushFeignHystrix implements MiPushFeign {

    @Override
    public ResponseMessage push(PushXmVo pushXmVo) {
        return null;
    }
}
