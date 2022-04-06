package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.config.WxPayOwnerConfig;
import com.weiwei.jixieche.config.WxPayTenantryConfig;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.WxPayService;
import com.weiwei.jixieche.utils.AliPayUtils;
import com.weiwei.jixieche.utils.WxPayUtils;
import com.weiwei.jixieche.vo.TradePayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


@Service("wxPayService")
public class WxPayServiceImpl implements WxPayService {

    private static Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);

    @Autowired
    private WxPayUtils wxPayUtils;

    @Autowired
    private AliPayUtils aliPayUtils;

    /**
     * 微信支付
     * @param tradePayVo
     * @param req
     * @return
     */
    @Override
    public synchronized ResponseMessage pay(TradePayVo tradePayVo, HttpServletRequest req) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        String resStr  = aliPayUtils.verifyParam(tradePayVo);
        if( resStr != null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        }else{
            //发起支付
            result = wxPayUtils.wxPay(tradePayVo,req);
        }
        return result;
    }

    @Override
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        return this.wxPayUtils.notify(request);
    }
}