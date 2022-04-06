package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.AliPayService;
import com.weiwei.jixieche.vo.TradePayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AliPayController
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 14:56
 * @Version 1.0.1
 **/

@Api(tags="财务模块--支付宝支付")
@RestController
@RequestMapping("aliPay")
public class AliPayController {

    @Resource
    private AliPayService aliPayService;

    @ApiOperation(httpMethod="POST", value="支付宝支付")
    @PostMapping("/pay")
    public ResponseMessage pay(@RequestBody TradePayVo tradePayVo) {
        return this.aliPayService.pay(tradePayVo);
    }

    @ApiOperation(value="支付宝支付回调")
    @PostMapping(value = "/notify")
    public void notify(HttpServletRequest request) {
        this.aliPayService.notify(request);
    }

}
