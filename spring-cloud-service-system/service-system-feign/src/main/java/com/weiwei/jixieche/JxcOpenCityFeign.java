package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcOpenCityFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author zhong huan
 * @Date 2019-10-06 13:34
 * @Description
 */
@FeignClient(value = "SYSTEM-SERVICE-PROVIDER", fallback = JxcOpenCityFeignHystrix.class)
public interface JxcOpenCityFeign {

    /**
     * 根据订单查询应付给机主的实际金额
     *
     * @param orderId
     * @param amount
     * @return
     */
    @PostMapping("/jxcOpenedCity/getToOwnerAmount")
    Integer getToOwnerAmount(@RequestParam("orderId") Long orderId, @RequestParam("amount") Integer amount);
}
