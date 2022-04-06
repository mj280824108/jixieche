package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcClockPairFeignHystrix;
import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service-provider", fallback = JxcClockPairFeignHystrix.class)
public interface JxcClockPairFeign {

    @ApiOperation(httpMethod="POST", value="批量更新台班支付状态")
    @PostMapping("/jxcClockPair/updateBatchClockPayStatus")
    void updateBatchClockPayStatus(@RequestBody UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo);

    @ApiOperation(httpMethod="POST", value="更新台班支付状态")
    @PostMapping("/jxcClockPair/updateClockPayStatus")
    void updateClockPayStatus(@RequestParam("clockId") Long clockId);
}
