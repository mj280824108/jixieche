package com.weiwei.jixieche.service;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AliTradePayVo;
import com.weiwei.jixieche.vo.TradePayVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxPayService {

    /**
     * 微信支付
     * @param tradePayVo
     * @param req
     * @return
     */
    ResponseMessage pay(TradePayVo tradePayVo, HttpServletRequest req);

    /**
     * 微信回调
     * @param request
     * @param response
     * @return
     */
    String notify(HttpServletRequest request, HttpServletResponse response);

}