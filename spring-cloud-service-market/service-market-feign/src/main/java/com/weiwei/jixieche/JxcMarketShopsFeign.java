package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcMarketShopsFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName JxcMarketShopsFeign
 * @Description TODO
 * @Author houji
 * @Date 2019/9/26 19:09
 * @Version 1.0.1
 **/
@FeignClient(value = "market-service-provider", fallback = JxcMarketShopsFeignHystrix.class)
public interface JxcMarketShopsFeign {

}
