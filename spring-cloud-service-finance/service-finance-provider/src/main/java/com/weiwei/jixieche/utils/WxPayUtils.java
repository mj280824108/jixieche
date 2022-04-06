package com.weiwei.jixieche.utils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.config.WxPayOwnerConfig;
import com.weiwei.jixieche.config.WxPayTenantryConfig;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.AliTradePayVo;
import com.weiwei.jixieche.vo.TradePayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @ClassName WxPayUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 11:30
 * @Version 1.0.1
 **/
@Component
public class WxPayUtils {

    private static Logger logger = LoggerFactory.getLogger(WxPayUtils.class);

    @Autowired
    private WxPayOwnerConfig wxPayOwnerConfig;

    @Autowired
    private WxPayTenantryConfig wxPayTenantryConfig;

    @Autowired
    private AliPayUtils aliPayUtils;

    @Resource
    private JxcTradeTenantryMapper jxcTradeTenantryMapper;

    @Resource
    private JxcTradeOwnerMapper jxcTradeOwnerMapper;

    /**
     * 参数非空验证
     * @return
     */
    public String confirmParam(TradePayVo tradePayVo){
        String resStr = null;
        if (!VerifyStr.isDouble(tradePayVo.getTradeAmount())){
            resStr = "支付金额不合法";
            return  resStr;
        }
        if(VerifyStr.strToInteger(tradePayVo.getTradeAmount()) <= 0){
            resStr = "微信支付支付金额不能为0";
            return  resStr;
        }
        return resStr;
    }

    /**
     * 微信支付
     * @return
     */
    public ResponseMessage wxPay(TradePayVo tradePayVo,HttpServletRequest req){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //选择加载承租方支付或机主支付
        WXPay wxpay = null;
        if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_OWNER.getId())) {
            wxpay = new WXPay(wxPayOwnerConfig);
            logger.info("调用微信机主端支付......");
        }else if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
            wxpay = new WXPay(wxPayTenantryConfig);
            logger.info("调用微信承租方端支付......");
        }

        Map<String, String> data = new HashMap<String, String>();
        //1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现
        if(tradePayVo.getTradeType().equals(AliTradePayVo.tradeType.TEN_PAY)){
            data.put("body", "承租方支付订单");
        }else if(tradePayVo.getTradeType().equals(AliTradePayVo.tradeType.OWNER_IN)){
            data.put("body", "机主提现");
        }else if(tradePayVo.getTradeType().equals(AliTradePayVo.tradeType.OWNER_OUT)){
            data.put("body", "机主支付工资");
        }else if(tradePayVo.getTradeType().equals(AliTradePayVo.tradeType.DRIVER_IN)){
            data.put("body", "司机提现");
        }
        data.put("body", String.valueOf(tradePayVo.getTradeType()));
        tradePayVo.setOutTradeNo(IDGenerator.getInstance().next()+"");
        logger.info("微信支付生成的商户订单号: " + tradePayVo.getOutTradeNo());
        data.put("out_trade_no", tradePayVo.getOutTradeNo());
        data.put("spbill_create_ip",req.getRemoteAddr());
        //异步通知地址（请注意必须是外网）
        if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_OWNER.getId())){
            data.put("notify_url", wxPayOwnerConfig.getNotifyUrl());
        }else if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
            data.put("notify_url", wxPayTenantryConfig.getNotifyUrl());
        }
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("trade_type", "APP");
        data.put("sign", getSign(data,tradePayVo.getClientType()));
        logger.info("微信支付金额：" + tradePayVo.getTradeAmount());
        data.put("total_fee", String.valueOf(VerifyStr.strToInteger(tradePayVo.getTradeAmount())));

        logger.info("微信发起预支付参数： "+data.toString());
        try{
            Map<String, String> resp = wxpay.unifiedOrder(data);
            logger.info("微信发起预支付返回值: {}"+ resp.toString());
            String returnCode = resp.get("return_code");    //获取返回码
            String returnMsg = resp.get("return_msg");
            //若返回码为SUCCESS，则会返回一个result_code,再对该result_code进行判断
            if("SUCCESS".equals(returnCode)){
                String resultCode = resp.get("result_code");
                String errCodeDes = resp.get("err_code_des");
                if("SUCCESS".equals(resultCode)){
                    logger.info("微信加载预支付成功...");
                    //微信交易预申请成功，进行业务数据添加
                    Integer res = this.aliPayUtils.insertData(tradePayVo);
                    if(res == null || res < 0){
                        logger.error("添加支付记录数据失败...");
                        result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
                        result.setMessage(ErrorCodeConstants.ADD_ERORR.getDescript());
                        return result;
                    }else {
                        //返回数据给前端
                        String nonce_str = resp.get("nonce_str");
                        String prepay_id = resp.get("prepay_id");
                        Long time =System.currentTimeMillis()/1000;
                        String timestamp=time.toString();
                        //签名生成算法
                        Map<String,String> signMap = new HashMap<>();
                        String appId = "";
                        String mchId = "";
                        if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_OWNER.getId())){
                            appId = wxPayOwnerConfig.getAppID();
                            mchId = wxPayOwnerConfig.getMchID();
                        }else if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
                            appId = wxPayTenantryConfig.getAppID();
                            mchId = wxPayTenantryConfig.getMchID();
                        }
                        signMap.put("appid",appId);
                        signMap.put("partnerid",mchId);
                        signMap.put("package","Sign=WXPay");
                        signMap.put("noncestr",nonce_str);
                        signMap.put("timestamp",timestamp);
                        signMap.put("prepayid",prepay_id);
                        String sign = getSign(signMap,tradePayVo.getClientType());
                        String resultString="{\"appid\":\""+appId+"\",\"partnerid\":\""+mchId+"\",\"package\":\"Sign=WXPay\"," +
                                "\"noncestr\":\""+nonce_str+"\",\"timestamp\":"+timestamp+"," +
                                "\"prepayid\":\""+prepay_id+"\",\"sign\":\""+sign+"\"}";
                        logger.info("微信预支付返回app的数据：   "+resultString);
                        Map<String,String> resultMap = new HashMap<>();
                        resultMap.put("orderString",resultString);
                        result.setData(resultMap);
                        return result;
                    }
                }else {
                    logger.error("微信预支付异常,异常信息：",errCodeDes);
                    result.setCode(ErrorCodeConstants.WX_TRADE_PRE_PAY.getId());
                    result.setMessage(ErrorCodeConstants.WX_TRADE_PRE_PAY.getDescript());
                    return result;
                }
            }else {
                logger.error("微信预支付参数异常,异常信息：" + returnMsg);
                result.setCode(ErrorCodeConstants.WX_TRADE_PRE_PAY.getId());
                result.setMessage(ErrorCodeConstants.WX_TRADE_PRE_PAY.getDescript());
                return result;
            }
        }catch (Exception ex){
            logger.error("微信预支付异常......");
            result.setCode(ErrorCodeConstants.WX_TRADE_PRE_PAY.getId());
            result.setMessage(ErrorCodeConstants.WX_TRADE_PRE_PAY.getDescript());
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 微信支付签名
     * @param data
     * @param client
     * @return
     */
    public String getSign(Map<String, String> data, Integer client) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
            {
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        if(client.equals(ClientTypeConstants.CLIENT_OWNER.getId())){
            sb.append("key=").append(wxPayOwnerConfig.getKey());
        }else if(client.equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
            sb.append("key=").append(wxPayTenantryConfig.getKey());
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] array = new byte[0];
        try {
            array = md.digest(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb2 = new StringBuilder();
        for (byte item : array) {
            sb2.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb2.toString().toUpperCase();
    }

    /**
     * 微信支付回调
     * @param request
     * @return
     */
    public String notify(HttpServletRequest request){
        String resXml="";
        logger.info("进入微信支付回调:"+ DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        try{
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml=sb.toString();
            logger.info("微信支付异步通知请求包: {}", resXml);
            String result = payBack(resXml);
            return result;
        }catch (Exception e){
            logger.error("手机支付回调通知失败",e);
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }

    /**
     * 微信支付回调验证
     * @param notifyData
     * @return
     */
    public String payBack(String notifyData) {
        WXPay wxpay = null;
        String xmlBack="";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(notifyData);// 转换成map
            if(notifyMap.get("appid").equals(wxPayOwnerConfig.getAppID())){
                wxpay = new WXPay(wxPayOwnerConfig);
            }else if(notifyMap.get("appid").equals(wxPayTenantryConfig.getAppID())){
                wxpay = new WXPay(wxPayTenantryConfig);
            }
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                String  returnCode = notifyMap.get("return_code");//状态
                String outTradeNo = notifyMap.get("out_trade_no");//订单号
                String transactionId = notifyMap.get("transaction_id");
                String totalFee = notifyMap.get("total_fee");
                if("SUCCESS".equals(returnCode)){
                    if(outTradeNo!=null){
                            logger.info("微信手机支付回调成功订单号:{}", outTradeNo);
                            JxcTradeTenantry jxcTradeTenantry = this.jxcTradeTenantryMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                            if(jxcTradeTenantry != null){
                                if(jxcTradeTenantry.getTradeStatus().equals(JxcTradeTenantry.tradeState.UN_SUCCESS)){
                                    this.aliPayUtils.notityAction(TradePayVo.tradeType.TEN_PAY,outTradeNo,transactionId);
                                }
                            }else{
                                JxcTradeOwner jxcTradeOwner = this.jxcTradeOwnerMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                                if(jxcTradeOwner.getTradeStatus().equals(JxcTradeOwner.TradeStatus.UN_SUCCESS)){
                                    this.aliPayUtils.notityAction(jxcTradeOwner.getTradeType(),outTradeNo,transactionId);
                                }
                            }
                            //this.aliPayUtils.notityAction();
                            //回调成功，修改平台流水信息
                            /*JxcTradePlatform jxcTradePlatform = this.jxcTradePlatformMapper.queryByTradeNo(outTradeNo);
                            //验证平台订单流水记录判断
                            if(jxcTradePlatform == null){
                               logger.error("根据商户订单号未查询流水数据: "+ outTradeNo);
                            }else{
                                //防止微信重复回调
                                if(jxcTradePlatform.getTradeStatus() == JxcTradePlatform.tradeStatus.PAY){
                                    jxcTradePlatform.setThirdTradeNo(transactionId);
                                    jxcTradePlatform.setTradeStatus(JxcTradePlatform.tradeStatus.SUCCESS);
                                    Integer res = this.jxcTradePlatformMapper.updateByPrimaryKeySelective(jxcTradePlatform);
                                    if(res == null || res < 0){
                                        logger.error("修改平台流水记录失败,outTradeNo : " + outTradeNo);
                                    }else{
                                        aliPayUtils.updateBatchPayStatus(jxcTradePlatform.getMachineRouteIdList(),jxcTradePlatform.getClockInIdList());
                                    }
                                }
                            }*/
                            xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        }
                    }else {
                        logger.info("微信手机支付回调失败订单号:{}",outTradeNo);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                return xmlBack;
            }else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                logger.error("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            logger.error("手机支付回调通知失败",e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

}
