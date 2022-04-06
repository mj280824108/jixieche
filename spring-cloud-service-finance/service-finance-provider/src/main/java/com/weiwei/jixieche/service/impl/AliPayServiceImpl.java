package com.weiwei.jixieche.service.impl;

import com.alipay.api.internal.util.AlipaySignature;
import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.bean.JxcTradeTenantryRef;
import com.weiwei.jixieche.config.AliPayConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryRefMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.AliPayService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.AliPayUtils;
import com.weiwei.jixieche.vo.TradePayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service("aliPayService")
public class AliPayServiceImpl implements AliPayService {

    private static Logger logger = LoggerFactory.getLogger(AliPayService.class);

    @Autowired
    private AliPayUtils aliPayUtils;

    @Autowired
    private AliPayConfig aliPayConfig;

    @Resource
    private JxcTradeTenantryRefMapper jxcTradeTenantryRefMapper;

    /**
     * 支付宝支付
     * @param tradePayVo
     * @return
     */
    @Override
    @Transactional
    public ResponseMessage pay(TradePayVo tradePayVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        String resStr = aliPayUtils.verifyParam(tradePayVo);
        if(resStr != null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        }else{
            result = this.aliPayUtils.pay(tradePayVo);
        }
        return result;
    }

    /**
     * 支付宝回调
     * @param request
     */
    @Override
    @Transactional
    public void notify(HttpServletRequest request) {
        logger.info("支付宝回调时间:"+ DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        try {
            boolean flag = false;
            if (params.get("app_id").equals(aliPayConfig.getOwnerAppId())){
                flag = AlipaySignature.rsaCheckV1(params, aliPayConfig.getOwnerAlipayPublicKey(), aliPayConfig.getCharset(),aliPayConfig.getSignType());
            }else if(params.get("app_id").equals(aliPayConfig.getTenantryAppId())){
                flag = AlipaySignature.rsaCheckV1(params, aliPayConfig.getTenantryAlipayPublicKey(), aliPayConfig.getCharset(),aliPayConfig.getSignType());
            }
            if (flag) {
                String tradeStatus = params.get("trade_status");
                String outTradeNo = params.get("out_trade_no");
                String tradeNo = params.get("trade_no");
                //交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
                Integer tradeType = Integer.parseInt(params.get("body"));

                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    logger.info("支付宝回调成功返回商户唯一订单号:" + params.get("out_trade_no"));
                    logger.info("支付宝回调成功返回支付宝交易流水号:" + params.get("trade_no"));
                    logger.info("交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)值:" + tradeType);
                    aliPayUtils.notityAction(tradeType,outTradeNo,tradeNo);
                } else if ("TRADE_CLOSED".equals(tradeStatus)) {
                    // 未付款交易超时关闭,或支付完成后全额退款,执行相关业务逻辑

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("支付宝回调失败,商户订单号： " + params.get("outTradeNo"));
        }
    }
}