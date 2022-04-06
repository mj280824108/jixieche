package com.weiwei.jixieche.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.weiwei.jixieche.constant.RedisPrefixConstants;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseException;
import com.weiwei.jixieche.vo.ShortMsgTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ShortMsgUtils
 * @Description 短信发送
 * @Author houji
 * @Date 2019/5/14 18:02
 * @Version 1.0.1
 **/
@Component
public class ShortMsgUtils {

    @Autowired
    private RedisUtil redisUtil;

    static Logger log = LoggerFactory.getLogger(ShortMsgUtils.class);

    public static Map<Integer, String> clientMap = new HashMap<>();

    public static Map<Integer, String> codePrefixMap = new HashMap<>();
    public static Map<Integer, String> countPrefixMap = new HashMap<>();

    public Map<Integer, ShortMsgTemplate> templateMap = new HashMap<>();

    /**
     * 发送短信参数
     */
    public static String username = "api";
    public static String password = "key-c6d78daa8c1dba43c023138125d0cd8a";
    public static String url = "http://sms-api.luosimao.com/v1/send.json";

    //判断是否发送短信
    @Value("${short.msg}")
    private boolean flag;

    static {
        codePrefixMap.put(1, RedisPrefixConstants.REGISTER_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap.put(2, RedisPrefixConstants.PASSWORD_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap.put(3, RedisPrefixConstants.CONFIRM_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap.put(4, RedisPrefixConstants.OWNER_CONFIRM_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap.put(5, RedisPrefixConstants.DRIVER_CONFIRM_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap.put(6, RedisPrefixConstants.BANKCARD_CONFIRM_MOBILE_CODE_PREFIX.getPrefix());
        codePrefixMap = Collections.unmodifiableMap(codePrefixMap);

        countPrefixMap.put(1, RedisPrefixConstants.REGISTER_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap.put(2, RedisPrefixConstants.PASSWORD_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap.put(3, RedisPrefixConstants.CONFIRM_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap.put(4, RedisPrefixConstants.OWNER_CONFIRM_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap.put(5, RedisPrefixConstants.DRIVER_CONFIRM_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap.put(6, RedisPrefixConstants.BANKCARD_CONFIRM_MOBILE_COUNT_PREFIX.getPrefix());
        countPrefixMap = Collections.unmodifiableMap(countPrefixMap);
        clientMap.put(1, "机主版");
        clientMap.put(2, "承租方版");
    }

    /**
     * 螺丝帽发送短信
     * @param formData
     * @return
     * @throws Exception
     */
    public static String send(MultivaluedMapImpl formData) throws Exception{
        // just replace key here
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username,password));
        WebResource webResource = client.resource(url);

        ClientResponse response =  webResource.type( MediaType.APPLICATION_FORM_URLENCODED ).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        if(status != 200){
            String error_msg = "螺丝帽官方服务器异常";
            log.error(error_msg);
            throw new ResponseException(error_msg);
        }
        return textEntity;
    }

    public String sendAndParseResult(MultivaluedMapImpl formData) throws Exception {
        String error = "0";
        //如果是开发测试环境，不想把短信发出去时候，yml文件修改值
        /*****************************************/
        if(flag){
            String jerseyResultStr = send(formData);
            JSONObject jsonObject = JSONObject.parseObject(jerseyResultStr);
            error = jsonObject.getString("error");
        }
        /*****************************************/
        return error;
    }


}
