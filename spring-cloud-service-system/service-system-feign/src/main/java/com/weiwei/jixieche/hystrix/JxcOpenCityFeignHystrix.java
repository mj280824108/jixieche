package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcOpenCityFeign;
import org.springframework.stereotype.Component;

/**
 * @Author zhong huan
 * @Date 2019-10-06 13:36
 * @Description
 */
@Component
public class JxcOpenCityFeignHystrix implements JxcOpenCityFeign {

    @Override
    public Integer getToOwnerAmount(Long orderId, Integer amount) {
        return null;
    }
}
