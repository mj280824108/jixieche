package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weiwei.jixieche.config.PushKitConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.RedisExpireTimeConstants;
import com.weiwei.jixieche.constant.RedisKeyConstants;
import com.weiwei.jixieche.httprequest.HttpClientUtil;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.PushKitService;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.PushKitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JxcMsgServiceImpl
 * @Description TODO
 * @Author houji
 * @Date 2019/5/15 11:07
 * @Version 1.0.1
 **/
@Service("pushKitService")
public class PushKitServiceImpl implements PushKitService {

    @Autowired
    private PushKitConfig pushKitConfig;

    @Autowired
    private RedisUtil redisUtil;

    //获取华为jpush的推送token
    @Override
    public ResponseMessage getAccessToken() {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if(redisUtil.hasKey(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix())){
            result.setData(redisUtil.get(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix()));
            return result;
        }else{
            JSONObject jsonObject = this.getPushKitToken();
            if(this.getPushKitToken() != null){
                redisUtil.set(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix(),jsonObject.getString("access_token"), RedisExpireTimeConstants.REDIS_PREFIX_LOGIN_TOKEN.getExpireTime());
                result.setData(redisUtil.get(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix()));
            }else{
                result.setCode(ErrorCodeConstants.PARAM_EMPTY.getId());
                result.setMessage("获取PushKit的token错误");
                return result;
            }
        }
        return result;
    }

    //获取华为的推送token
    public JSONObject getPushKitToken(){
        Map<String,Object> map = new HashMap<>(3);
        map.put("grant_type","client_credentials");
        map.put("client_secret",pushKitConfig.getCLIENT_SECRET());
        map.put("client_id",pushKitConfig.getCLIENT_ID());
        String reponse = HttpClientUtil.sendPost2(pushKitConfig.getTOKEN_URL(),map);
        JSONObject jsonObject = JSONObject.parseObject(reponse);
        if(jsonObject.containsKey("access_token")){
            redisUtil.set(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix(),jsonObject.getString("access_token"),RedisExpireTimeConstants.REDIS_PREFIX_LOGIN_TOKEN.getExpireTime());
            return jsonObject;
        }else{
            return null;
        }
    }

    //华为推送
    @Override
    public ResponseMessage push(PushKitVo pushKitVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JSONObject jsonObject = new JSONObject();
        JSONObject token = this.getPushKitToken();
        if(token == null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY.getId());
            result.setMessage("获取PushKit的token错误");
            return result;
        }
        if(CollectionUtils.isEmpty(pushKitVo.getDeviceTokensList())){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY.getId());
            result.setMessage("deviceToken不能为空");
            return result;
        }

        //开始构建请求参数
        try{
            jsonObject.put("access_token", redisUtil.get(RedisKeyConstants.REDIS_PUSH_KIT_TOKEN_KEY.getPrefix()));
            jsonObject.put("nsp_svc","openpush.message.api.send");
            jsonObject.put("nsp_ts",String.valueOf(System.currentTimeMillis()/1000));
            JSONArray jsonArray = new JSONArray();
            pushKitVo.getDeviceTokensList().forEach(device -> {
                jsonArray.add(device);
            });
            //0869346033118982300004090500CN01
            jsonObject.put("device_token_list", jsonArray);
            JSONObject body = new JSONObject();
            body.put("title",pushKitVo.getTitle());
            body.put("content",pushKitVo.getContent());
            JSONObject msg = new JSONObject();
            msg.put("type",3);
            msg.put("body",body);
            JSONObject action = new JSONObject();
            action.put("type",3);
            JSONObject param = new JSONObject();
            param.put("appPkgName",pushKitConfig.getOwnerPage());
            action.put("param",param);
            msg.put("action",action);
            JSONObject hps  = new JSONObject();
            hps.put("msg",msg);
            JSONObject payload  = new JSONObject();
            payload.put("hps",hps);
            jsonObject.put("payload", payload);
            String reponse = HttpClientUtil.sendPost2(pushKitConfig.getPUSH_URL(),jsonObject);
            JSONObject resultJson = JSONObject.parseObject(reponse);
            if(resultJson.containsKey("code") && resultJson.getString("code").equals("80000000")){
                return result;
            }else{
                result.setCode(ErrorCodeConstants.EDIT_ERORR.getId());
                result.setMessage("PushKit推送失败");
                return result;
            }
        }catch (Exception ex){
            result.setCode(ErrorCodeConstants.EDIT_ERORR.getId());
            result.setMessage("华为推送异常");
            ex.printStackTrace();
        }
        return result;
    }




}
