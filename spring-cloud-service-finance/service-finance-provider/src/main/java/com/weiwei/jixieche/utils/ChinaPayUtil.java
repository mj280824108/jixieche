package com.weiwei.jixieche.utils;

import chinapay.SecureLink;
import chinapay.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ChinaPayUtil {

    private static Logger logger = LoggerFactory.getLogger(ChinaPayUtil.class);

    @Autowired
    private ChinaPayConstant chinaPayConstant;

    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    //向chinapay请求提现
    public ChinaPayResult treatPay(Map<String,String> params){

        HashMap<String,Object> urlParams = new HashMap<>();
        LinkedHashMap<String, Object> signParams = new LinkedHashMap<>();

        urlParams.put("merId", chinaPayConstant.merId);
        signParams.put("merId", chinaPayConstant.merId);

        String merDate = df.format(new Date());
        urlParams.put("merDate", merDate);
        signParams.put("merDate",merDate);

        String merSeqId = params.get("merSeqId");
        urlParams.put("merSeqId", merSeqId);
        signParams.put("merSeqId",merSeqId);

        String cardNo = params.get("cardNo");
        urlParams.put("cardNo", cardNo);
        signParams.put("cardNo",cardNo);

        String usrName = params.get("usrName");
        urlParams.put("usrName", usrName);
        signParams.put("usrName",usrName);

        String openBank = params.get("openBank");
        urlParams.put("openBank", openBank);
        signParams.put("openBank",openBank);

        String prov = "湖北";
        urlParams.put("prov", prov);
        signParams.put("prov",prov);

        String city = "武汉";
        urlParams.put("city", city);
        signParams.put("city",city);

        String transAmt = params.get("transAmt");
        urlParams.put("transAmt", transAmt);
        signParams.put("transAmt",transAmt);

        String purpose = "可用余额提现";
        urlParams.put("purpose", purpose);
        signParams.put("purpose",purpose);

        String subBank = "";
        urlParams.put("subBank", subBank);
        signParams.put("subBank",subBank);

        String flag = "00";
        urlParams.put("flag", "00");
        signParams.put("flag","00");

        String version = chinaPayConstant.version;
        urlParams.put("version", version);
        signParams.put("version", version);

        int signFlag = chinaPayConstant.signFlag;
        urlParams.put("signFlag", signFlag);
//        signParams.put("signFlag", signFlag);

        String termType = "07";
        urlParams.put("termType", termType);
        signParams.put("termType", termType);

        String payMode = "1";
        urlParams.put("payMode", payMode);
        signParams.put("payMode", payMode);


        //userId + userRegisterTime + userMail + userMobile + diskSn + mac + imei + ip + coordinates +
        //        baseStationSn + codeInputType + mobileForBank+desc

        String userId = "";
        urlParams.put("userId", userId);
        signParams.put("userId", userId);

        String userRegisterTime = "";
        urlParams.put("userRegisterTime", userRegisterTime);
        signParams.put("userRegisterTime", userRegisterTime);

        String userMail = "";
        urlParams.put("userMail", userMail);
        signParams.put("userMail", userMail);

        String userMobile = "";
        urlParams.put("userMobile", userMobile);
        signParams.put("userMobile", userMobile);

        String diskSn = "";
        urlParams.put("diskSn", diskSn);
        signParams.put("diskSn", diskSn);

        String mac = "";
        urlParams.put("mac", mac);
        signParams.put("mac", mac);

        String imei = "";
        urlParams.put("imei", imei);
        signParams.put("imei", imei);

        String ip = "";
        urlParams.put("ip", ip);
        signParams.put("ip", ip);

        String coordinates = "";
        urlParams.put("coordinates", coordinates);
        signParams.put("coordinates", coordinates);

        String baseStationSn = "";
        urlParams.put("baseStationSn", baseStationSn);
        signParams.put("baseStationSn", baseStationSn);

        String codeInputType = "";
        urlParams.put("codeInputType", codeInputType);
        signParams.put("codeInputType", codeInputType);

        String mobileForBank = "";
        urlParams.put("mobileForBank", mobileForBank);
        signParams.put("mobileForBank", mobileForBank);

        String desc = "lalala";
        urlParams.put("desc", desc);
        signParams.put("desc", desc);

        //对被签名的数据进行编码
        StringBuffer sb = new StringBuffer();
        signParams.entrySet().forEach(entry -> {
            sb.append(entry.getValue());
        });
        String msg = sb.toString();

        String msg1 = new String(Base64.encode(msg.getBytes()));

        chinapay.PrivateKey key = new chinapay.PrivateKey();

        boolean signResult = key.buildKey(chinaPayConstant.merId, chinaPayConstant.keyUsage, chinaPayConstant.keyFilePath);
        if (!signResult) {
            System.out.println("build key error!");
            return new ChinaPayResult(){{
                setRs(-1);
                setMsg("签名key异常");
            }};
        }

        //签名
        SecureLink s = new SecureLink(key);

        String chkValue = s.Sign(msg1);
        urlParams.put("chkValue", chkValue);

        List<String> prmList = new ArrayList<>();
        for(Map.Entry entry:urlParams.entrySet()){
            prmList.add(entry.getKey() + "=" + entry.getValue().toString());
        }
        String url = new StringBuffer().append(chinaPayConstant.transferUrl).append("?").append(StringUtils.join(prmList,"&")).toString();

        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        String respStr = "";
        try {
            response = httpclient.execute(httpGet);
            if(response != null){
                respStr = EntityUtils.toString(response.getEntity(),"UTF-8");

                Map<String,String> respVals = new HashMap<>();
                String[] v = null;
                for(String val:respStr.split("&")){
                    v = val.split("=");
                    respVals.put(v[0],v[1]);
                }

                String respCode = respVals.get("responseCode");
                String stat = respVals.get("stat");

                logger.info("银联内部提现返回code....." + respCode);
                logger.info("银联内部提现返回stat....." + stat);

                //有返回 而且成功时
                if(respCode != null && respCode.equals("0000") && stat != null && stat.equals("s")){
                    //将返回的银行卡号以及金额、本地流水号，银联流水号进行返回
                    return new ChinaPayResult(){{
                        setRs(1);
                        setMsg("ok");
                        setData(respVals);
                    }};
                }else{

                    if(respCode.equals("0105")){
                        return new ChinaPayResult(){{
                            setRs(0);
                            setMsg("重复交易!");
                            setData(respVals);
                        }};
                    }else if(respCode.equals("0101")){
                        return new ChinaPayResult(){{
                            setRs(0);
                            setMsg("交易失败:签名验证错误!");
                            setData(respVals);
                        }};
                    }else if(respCode.equals("0103")){
                        return new ChinaPayResult(){{
                            setRs(0);
                            setMsg("交易失败:银联账号金额不足");
                            setData(respVals);
                        }};
                    }else {
                        return new ChinaPayResult(){{
                            if(stat.equals("6")){
                                setRs(-1);
                                setMsg("交易失败:当前银行卡开户名与账号实名认证姓名不一致！");
                                setData(respVals);
                            }else {
                                setRs(-1);
                                setMsg("交易失败!");
                                setData(respVals);
                            }
                        }};
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
