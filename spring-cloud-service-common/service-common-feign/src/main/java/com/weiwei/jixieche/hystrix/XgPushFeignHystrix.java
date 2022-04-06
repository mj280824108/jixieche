package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.XgPushFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushXgVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushMsgFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:15
 * @Version 1.0.1
 **/
@Component
public class XgPushFeignHystrix implements XgPushFeign {

    @Override
    public ResponseMessage pushNotifyByAccount(PushXgVo pushXgVo) {
        return null;
    }
}
