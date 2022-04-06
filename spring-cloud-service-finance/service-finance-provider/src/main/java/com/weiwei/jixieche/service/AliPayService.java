package com.weiwei.jixieche.service;


import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AliTradePayVo;
import com.weiwei.jixieche.vo.TradePayVo;

import javax.servlet.http.HttpServletRequest;

public interface AliPayService {

    /**
     * 支付宝支付
     * @param tradePayVo
     * @return
     */
    ResponseMessage pay(TradePayVo tradePayVo);
    /**
     * 支付宝回调
     * @param request
     */
    void notify(HttpServletRequest request);


}