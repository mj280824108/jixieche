package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.UnionPayService;
import com.weiwei.jixieche.vo.CashApplyVo;
import com.weiwei.jixieche.vo.TradePayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UnionPayController
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 14:56
 * @Version 1.0.1
 **/
@Api(tags="财务模块--银联支付")
@RestController
@RequestMapping("unionPay")
public class UnionPayController {

    @Resource
    private UnionPayService unionPayService;

    @ApiOperation(httpMethod="POST", value="银联支付")
    @PostMapping("/pay")
    public ResponseMessage pay(@RequestBody TradePayVo tradePayVo) {
        return this.unionPayService.pay(tradePayVo);
    }

    @ApiOperation(httpMethod="POST", value="银联支付回调")
    @RequestMapping(value = "/notify", produces = "application/json;charset=UTF-8", method = {
            RequestMethod.GET, RequestMethod.POST })
    public String notify(HttpServletRequest req) throws Exception{
        return this.unionPayService.notify(req);
    }

    @ApiOperation(httpMethod="POST", value="银联提现申请")
    @PostMapping("/cashApply")
    public ResponseMessage cashApply(AuthorizationUser user, @RequestBody CashApplyVo cashApplyVo) {
        return this.unionPayService.cashApply(user,cashApplyVo);
    }

}
