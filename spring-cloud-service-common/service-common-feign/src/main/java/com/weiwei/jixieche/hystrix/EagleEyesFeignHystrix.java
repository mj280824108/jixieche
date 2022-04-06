package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.EagleEyesFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.EagleEyesVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName EagleEyesFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:17
 * @Version 1.0.1
 **/
@Component
public class EagleEyesFeignHystrix implements EagleEyesFeign {

    @Override
    public ResponseMessage baiduEntity(EagleEyesVo eagleEyesVo) {
        return null;
    }
}
