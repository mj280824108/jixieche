package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.WxPayService;
import com.weiwei.jixieche.vo.AliTradePayVo;
import com.weiwei.jixieche.vo.TradePayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName WxPayController
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 14:56
 * @Version 1.0.1
 **/
@Api(tags="财务模块--微信支付")
@RestController
@RequestMapping("wxPay")
public class WxPayController {

    @Resource
    private WxPayService wxPayService;

    @ApiOperation(httpMethod="POST", value="微信支付")
    @PostMapping("/pay")
    public ResponseMessage pay(@RequestBody TradePayVo tradePayVo, HttpServletRequest req) {
        return this.wxPayService.pay(tradePayVo,req);
    }

    @ApiOperation(httpMethod="GET", value="微信回调")
    @PostMapping(value = "/notify")
    public String notify(HttpServletRequest request, HttpServletResponse response){
        return this.wxPayService.notify(request,response);
    }


}
