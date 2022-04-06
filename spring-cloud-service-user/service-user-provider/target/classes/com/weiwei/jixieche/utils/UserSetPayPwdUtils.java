package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.constant.RedisKeyConstants;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.vo.UserInfoVo;
import com.weiwei.jixieche.vo.UserSetPayPwdVerifyVo;
import com.weiwei.jixieche.vo.UserSetPayPwdVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName UserSetPayPwdUtils
 * @Description 用户设置支付密码验证
 * @Author houji
 * @Date 2019/9/20 16:44
 * @Version 1.0.1
 **/
@Component
public class UserSetPayPwdUtils {

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private JxcUserMapper jxcUserMapper;

    /**
     * 验证设置支付密码参数
     * @param userSetPayPwdVo
     * @return
     */
    public String verifyParam(UserSetPayPwdVerifyVo userSetPayPwdVo, UserInfoVo userInfoVo){
        String resStr = null;
        if(userSetPayPwdVo.getPayPwdType() == null){
            resStr="设置支付密码类型不能为空";
            return resStr;
        }
        if(userSetPayPwdVo.getPayPwdType() != 1
                && userSetPayPwdVo.getPayPwdType() != 2
                && userSetPayPwdVo.getPayPwdType() != 3){
            resStr="设置支付密码类型不识别";
            return resStr;
        }

        if(userSetPayPwdVo.getPayPwdType() == 1 || userSetPayPwdVo.getPayPwdType() == 2){
            if(userSetPayPwdVo.getRealName() == null
                    || userSetPayPwdVo.getCardCode() == null
                    || userSetPayPwdVo.getCode() == null
                    || userSetPayPwdVo.getPhone() == null){
                resStr="必填参数不能为空";
                return resStr;
            }
            if(!userSetPayPwdVo.getPhone().equals(userInfoVo.getPhone())){
                resStr="手机号码不一致";
                return resStr;
            }
            if(!userSetPayPwdVo.getRealName().equals(userInfoVo.getRealName())){
                resStr="姓名不一致";
                return resStr;
            }
            if(!userSetPayPwdVo.getCardCode().equals(userInfoVo.getCardCode())){
                resStr="身份证号不一致";
                return resStr;
            }
            //验证码验证
            if(this.redisUtil.hasKey(RedisKeyConstants.SHORT_CODE_USER_SET_PAY_PWD.getPrefix()+userSetPayPwdVo.getPhone())==false){
                resStr = "验证码不存在或者过期";
                return resStr;
            }
            if(!String.valueOf(this.redisUtil.get(RedisKeyConstants.SHORT_CODE_USER_SET_PAY_PWD.getPrefix()+userSetPayPwdVo.getPhone())).equals(userSetPayPwdVo.getCode())){
                resStr = "手机验证码不正确";
                return resStr;
            }
        }else{
            //修改密码
            if(userSetPayPwdVo.getOldPayPwd() == null){
                resStr = "旧支付密码不能为空";
                return resStr;
            }
            if(userSetPayPwdVo.getNewPayPwd() == null){
                resStr = "新支付密码不能为空";
                return resStr;
            }
            String oldPayPwd = this.jxcUserMapper.queryUserPayPwdByUserId(userInfoVo.getUserId());
            if(!oldPayPwd.equals(userSetPayPwdVo.getOldPayPwd())){
                resStr = "旧密码不匹配";
                return resStr;
            }
        }
        return resStr;
    }
}
