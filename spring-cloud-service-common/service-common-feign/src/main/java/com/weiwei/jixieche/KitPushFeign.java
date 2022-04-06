package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.KitPushFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushKitVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = KitPushFeignHystrix.class)
public interface KitPushFeign {
    /**
     * 魅族推送
     * @param pushKitVo
     * @return
     */
    @PostMapping("/pushkit/push")
    ResponseMessage push(@RequestBody PushKitVo pushKitVo);
}
