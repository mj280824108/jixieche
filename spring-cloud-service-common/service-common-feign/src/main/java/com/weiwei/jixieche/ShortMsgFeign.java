package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.ShortMsgFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName ShortMsgFeign
 * @Description TODO
 * @Author houji
 * @Date 2019/8/23 16:43
 * @Version 1.0.1
 **/
@FeignClient(value = "COMMON-SERVICE-PROVIDER", fallback = ShortMsgFeignHystrix.class)
public interface ShortMsgFeign {

    @ApiOperation(httpMethod="POST", value="阿里发送短信")
    @PostMapping("/jxcShortMsgTemplate/aliSendShortMsg")
    ResponseMessage aliSendShortMsg(@RequestBody AliShortMsgVo aliShortMsgVo);

}
