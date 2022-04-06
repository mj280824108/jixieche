package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.XgPushFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushXgVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = XgPushFeignHystrix.class)
public interface XgPushFeign {
    /**
     * 信鸽推送
     * @param pushXgVo
     * @return
     */
    @PostMapping("/pushxg/pushMessageByAccount")
    ResponseMessage pushNotifyByAccount(@RequestBody PushXgVo pushXgVo);
}
