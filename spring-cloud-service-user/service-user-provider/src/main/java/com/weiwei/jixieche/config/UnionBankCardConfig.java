package com.weiwei.jixieche.config;

import com.alibaba.fastjson.JSONObject;
import com.weiwei.jixieche.httprequest.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UnionBankCardConfig
 * @Description 查询银联银行卡信息配置
 * @Author houji
 * @Date 2019/9/2 10:11
 * @Version 1.0.1
 **/
@Component
public class UnionBankCardConfig {

    //API认证账号
    @Value("${unionpay.appid}")
    private String appId;
    //#API认证密钥
    @Value("${unionpay.appsecret}")
    private String appSecret;
    //获取银行卡地址
    @Value("${unionpay.tokenurl}")
    private String tokenUrl;
    //获取银行卡信息
    @Value("${unionpay.cardinfourl}")
    private String cardinfoUrl;
    //签名秘钥
    @Value("${unionpay.signature}")
    private String signature;

    private static final String BANK_ALI_URL = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";

    /**
     * 获取银联的请求的token
     * @return
     */
    public String getToken(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("app_id",appId);
        map.put("app_secret",appSecret);
        String payRes = HttpClientUtil.sendGet(tokenUrl,map);
        return payRes;
    }

    /**
     * 获取银联银行卡信息
     * @param cardNo
     * @return
     */
    public String getBankCardInfo(String cardNo){
        String resCardInfo = null;
        //根据app_id,app_secret发起第三方银联请求获取token
        JSONObject tokenJson = JSONObject.parseObject(getToken());
        if(tokenJson.get("respCd").equals("0000")){
            //时间戳
            String strTs = String.valueOf(System.currentTimeMillis());
            //JSON请求体
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cardNo",cardNo);
            String signRes = HttpClientUtil.sign(jsonObject.toString(),strTs,signature);
            StringBuffer url = new StringBuffer();
            url.append(cardinfoUrl).append("?");
            url.append("token=").append(tokenJson.getString("token")).append("&");
            url.append("sign=").append(signRes).append("&");
            url.append("ts=").append(strTs);

            //POST请求体
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("cardNo",cardNo);
            JSONObject bankJson = JSONObject.parseObject(HttpClientUtil.sendPostCardInfo(url.toString(),map));
            if(bankJson.get("respCd").equals("0000")){
                resCardInfo = bankJson.get("data").toString();
            }
        }
        return resCardInfo;
    }

}
