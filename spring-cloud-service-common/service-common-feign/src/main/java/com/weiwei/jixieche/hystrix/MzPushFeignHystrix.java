package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.MzPushFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushMzVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushMsgFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:15
 * @Version 1.0.1
 **/
@Component
public class MzPushFeignHystrix implements MzPushFeign {

    @Override
    public ResponseMessage push(PushMzVo pushMzVo) {
        return null;
    }
}
