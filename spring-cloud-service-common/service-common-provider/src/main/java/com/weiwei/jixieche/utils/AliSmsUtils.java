package com.weiwei.jixieche.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.weiwei.jixieche.bean.JxcShortMsgRecord;
import com.weiwei.jixieche.bean.JxcShortMsgTemplate;
import com.weiwei.jixieche.config.AliShortMsgConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcShortMsgRecordMapper;
import com.weiwei.jixieche.mapper.JxcShortMsgTemplateMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import com.weiwei.jixieche.vo.ShortMsgTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName AliSmsUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/29 11:25
 * @Version 1.0.1
 **/
@Component
public class AliSmsUtils {

    @Resource
    private AliShortMsgConfig aliShortMsgConfig;

    @Resource
    private JxcShortMsgTemplateMapper jxcShortMsgTemplateMapper;

    @Resource
    private JxcShortMsgRecordMapper jxcShortMsgRecordMapper;

    /**
     * 阿里短信参数验证
     * @param aliShortMsgVo
     * @return
     */
    public boolean paramVerify(AliShortMsgVo aliShortMsgVo){
        boolean bru = false;
        //客户端类型
        if(aliShortMsgVo.getClientType() == null){
            return bru;
        }
        //手机号码
        if(aliShortMsgVo.getPhone() == null){
            return bru;
        }
        if(aliShortMsgVo.getClientType() == AliShortMsgVo.ClientType.BACK){
            //后台的templateId验证
            if(aliShortMsgVo.getTemplateId() == null){
                return bru;
            }else{
                bru = true;
            }
        }else{
            bru = true;
        }
        return bru;
    }

    /**
     * 阿里发送短信
     * @param phone
     * @param templateCode
     * @param templateParam
     * @return
     */
    public ResponseMessage SendAliSms(String phone, String templateCode, String templateParam){

        ResponseMessage result = ResponseMessageFactory.newInstance();
        //发送短信配置
        DefaultProfile profile = DefaultProfile.getProfile("default", aliShortMsgConfig.getKey(), aliShortMsgConfig.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "喂喂机械");
        //app调用短信只是发送验证码
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject  aliSmsJson = JSONObject.parseObject(response.getData());
            if(aliSmsJson.getString("Code").equals("OK")){
                //短信发送成功，添加短信记录
                JxcShortMsgTemplate jxcShortMsgTemplate = this.jxcShortMsgTemplateMapper.queryByTemplateCode(templateCode);
                String msgBody = jxcShortMsgTemplate.getTemplateBody();
                Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
                Matcher matcher = null;
                //短信模板中的《您的账号于${date}日，申请提现${money}元，请注意核实！！》的$的key
                String field = null;
                //发送短信需要的参数key的值
                String fieldVal = null;
                matcher = pattern.matcher(msgBody);
                while (matcher.find()) {
                    field = matcher.group(1);
                    fieldVal = JSONObject.parseObject(templateParam).getString(field);
                    //替换${}里的内容，存入数据库
                    msgBody = msgBody.replaceAll(field, fieldVal);
                }
                //添加短信记录进入短信记录表
                JxcShortMsgRecord jxcShortMsgRecord = new JxcShortMsgRecord();
                jxcShortMsgRecord.setMsg(msgBody);
                jxcShortMsgRecord.setPhone(phone);
                jxcShortMsgRecord.setTemplateCode(templateCode);
                this.jxcShortMsgRecordMapper.insertSelective(jxcShortMsgRecord);
            }else{
                result.setCode(ErrorCodeConstants.SHORT_MSG_SEND_FAIL.getId());
                result.setMessage(ErrorCodeConstants.SHORT_MSG_SEND_FAIL.getDescript());
                return result;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            result.setCode(ErrorCodeConstants.SHORT_MSG_SEND_FAIL.getId());
            result.setMessage("短信发送异常");
            return result;
        } catch (ClientException e) {
            e.printStackTrace();
            result.setCode(ErrorCodeConstants.SHORT_MSG_SEND_FAIL.getId());
            result.setMessage("短信发送异常");
            return result;
        }
        return result;
    }

}
