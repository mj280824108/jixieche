package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcClockInOutPairFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@FeignClient(value = "order-service-provider", fallback = JxcClockInOutPairFeignHystrix.class)
public interface JxcClockInOutPairFeign {

}
