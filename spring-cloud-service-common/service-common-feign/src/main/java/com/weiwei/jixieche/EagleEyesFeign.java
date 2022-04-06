package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.EagleEyesFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.EagleEyesVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = EagleEyesFeignHystrix.class)
public interface EagleEyesFeign {

    /**
     * 百度鹰眼
     * @param eagleEyesVo
     * @return
     */
    @PostMapping("/eagleEyes/baiduEntity")
    ResponseMessage baiduEntity(@RequestBody EagleEyesVo eagleEyesVo);

}
