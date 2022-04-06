package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.MzPushFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.PushMzVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = MzPushFeignHystrix.class)
public interface MzPushFeign {
    /**
     * 魅族推送
     * @param pushMzVo
     * @return
     */
    @PostMapping("/pushmz/push")
    ResponseMessage push(@RequestBody PushMzVo pushMzVo);
}
