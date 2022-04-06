package com.weiwei.jixieche.utils;

/**
 * @ClassName XingePushUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/7/18 11:37
 * @Version 1.0.1
 **/

import com.weiwei.jixieche.config.PushXgConfig;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

@Component
public class XingePushUtils {

    @Autowired
    private PushXgConfig pushXgConfig;

    private static String MQTTPUSH_HEADER_BASIC;
    private static final String XINGEPUSH_APPID = "aa80270c0f6c1";// appId，从信鸽后台查看，可以存数据库。
    private static final String XINGEPUSH_SECRETKEY = "3b4adaf4eecee5da01a313b86ccbfa23";// secretKey，从信鸽后台查看，可以存数据库。
    private static final String address = "https://openapi.xg.qq.com/v3/push/app";// v3版本链接

    static {
        if (StringUtils.isBlank(MQTTPUSH_HEADER_BASIC)) {
            try {
                MQTTPUSH_HEADER_BASIC = getHeader(XINGEPUSH_APPID + ":" + XINGEPUSH_SECRETKEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getHeader(String auth) throws Exception {
        return new String(Base64.encodeBase64(auth.getBytes("UTF-8")));
    }

    public String postJsonDataToServer(String address, String jsonData) {
        return postJsonDataToServer(address, jsonData, "UTF-8");
    }

    public static String postJsonDataToServer(String address, String jsonData, String encode) {
        URL url = null;
        try {
            // 创建连接
            url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");// POST
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept-Charset", encode);
            conn.setRequestProperty("Authorization", "Basic " + MQTTPUSH_HEADER_BASIC);// HTTP
            // Basic认证，信鸽账号密码。
            conn.connect();

            // Json参数
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.write(jsonData.getBytes(encode));
            out.flush();
            out.close();

            // 获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), encode));
            String lines;
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(encode), encode);
                lines = URLDecoder.decode(URLDecoder.decode(lines, encode), encode);
                sb.append(lines);
            }
            reader.close();

            // 关闭连接
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {

        }
    }

}
