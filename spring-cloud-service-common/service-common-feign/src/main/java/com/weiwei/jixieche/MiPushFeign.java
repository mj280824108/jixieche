package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.MiPushFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushXmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = MiPushFeignHystrix.class)
public interface MiPushFeign {
    /**
     * 魅族推送
     * @param pushXmVo
     * @return
     */
    @PostMapping("/pushmi/push")
    ResponseMessage push(@RequestBody PushXmVo pushXmVo);
}
