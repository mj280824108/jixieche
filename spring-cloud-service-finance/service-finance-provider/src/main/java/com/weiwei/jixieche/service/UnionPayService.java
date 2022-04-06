package com.weiwei.jixieche.service;

import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.CashApplyVo;
import com.weiwei.jixieche.vo.TradePayVo;

import javax.servlet.http.HttpServletRequest;

public interface UnionPayService {

    /**
     * 银联支付
     * @param tradePayVo
     * @return
     */
    ResponseMessage pay(TradePayVo tradePayVo);

    /**
     * 银联支付回调
     * @param req
     * @return
     * @throws Exception
     */
    String notify(HttpServletRequest req) throws Exception;

    /**
     * 提现申请
     */
    ResponseMessage cashApply(AuthorizationUser user, CashApplyVo cashApplyVo);

}