package com.weiwei.jixieche.httprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpClientUtil
 * @Description TODO
 * @Author houji
 * @Date 2019/5/27 11:15
 * @Version 1.0.1
 **/
@Slf4j
public class HttpClientUtil {
    // 默认字符集
    private static final String ENCODING = "UTF-8";

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求)
     * @param url 请求地址
     * @param headers 请求头
     * @param data 请求实体
     * @param encoding 字符集
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:36:17
     * @throws
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data, String encoding) {
/*        log.info("进入post请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：headers=" + JSON.toJSONString(headers));
        log.info("请求入参：data=" + JSON.toJSONString(data));*/
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }

            StringEntity entity = new StringEntity(JSON.toJSONString(data),"UTF-8");

            entity.setContentType("application/json");

            // 设置实体
            httpPost.setEntity(entity);
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            } else {
                log.error("响应失败，状态码：" + status);
            }

        } catch (Exception e) {
            log.error("发送post请求失败", e);
        } finally {
            httpPost.releaseConnection();
        }
        return resultJson;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 json 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost1(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数

            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String buildMap(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        if (map.size() > 0) {
            for (String key : map.keySet()) {
                sb.append(key + "=");
                if (StringUtils.isEmpty(String.valueOf(map.get(key)))) {
                    sb.append("&");
                } else {
                    String value = String.valueOf(map.get(key));
                    try {
                        value = URLEncoder.encode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    sb.append(value + "&");
                }
            }
        }
        return sb.toString();
    }



    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码)
     * @param url 请求地址
     * @param data 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:37:28
     * @throws
     */
    public static String sendPost(String url, JSONObject data) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");

        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码)
     * @param url 请求地址
     * @param params 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午6:11:05
     * @throws
     */
    public static String sendPost(String url,Map<String,Object> params){
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        //return sendPost(url,headers,data,ENCODING);

        return sendPost1(url,buildMap(params));

    }

    public static String sendPostCardInfo(String url,Map<String,Object> params){
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url,headers,data,ENCODING);

    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码)
     * 此post请求是拼接调用
     * @param url 请求地址
     * @param params 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午6:11:05
     * @throws
     */
    public static String sendPost2(String url,Map<String,Object> params){
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        //return sendPost(url,headers,data,ENCODING);

        return sendPost1(url,buildMap(params));

    }

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求，请求数据默认使用UTF-8编码)
     * @param url 请求地址
     * @param headers 请求头
     * @param data 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午4:39:03
     * @throws
     */
    public static String sendPost(String url, Map<String, String> headers, JSONObject data) {
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description:(发送post请求，请求数据默认使用UTF-8编码)
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求实体
     * @author wangxy
     * @return String
     * @date 2018年5月10日 下午5:58:40
     * @throws
     */
    public static String sendPost(String url,Map<String,String> headers,Map<String,String> params){
        // 将map转成json
        JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
        return sendPost(url,headers,data,ENCODING);
    }

    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @param params 请求参数
     * @param encoding 编码
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:39:01
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params,String encoding){
/*        log.info("进入get请求方法...");
        log.info("请求入参：URL= " + url);
        log.info("请求入参：params=" + JSON.toJSONString(params));*/
        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if(params != null){
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
            //log.info("请求地址："+ uri);
            // 设置请求地址
            httpGet.setURI(uri);
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if(status == HttpStatus.SC_OK){
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            }else{
                log.error("响应失败，状态码：" + status);
            }
        } catch (Exception e) {
            log.error("发送get请求失败",e);
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }
    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @param params 请求参数
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:32:39
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params){
        return sendGet(url,params,ENCODING);
    }
    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:33:45
     * @throws
     */
    public static String sendGet(String url){
        return sendGet(url,null,ENCODING);
    }


    /**
     * 签名算法
     * @param body
     * 报文体
     * @param ts
     * 时间戳
     * @param signature
     *  密钥
     * @return
     */
    public static String sign(String body, String ts, String signature) {
        String sign = encodeBySHA256(signature + body + ts);

        return sign;
    }

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * encode By SHA-256
     *
     * @param str
     * @return
     */
    public static String encodeBySHA256(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes
     *            the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;

        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }

        return buf.toString();
    }
}
