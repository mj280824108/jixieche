package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.EagleEyesFeign;
import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.bean.JxcUserRole;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.generate.MD5Util;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.mapper.JxcUserRoleMapper;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.EagleEyesVo;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import com.weiwei.jixieche.vo.UserRegisterReturnVo;
import com.weiwei.jixieche.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName registerVerify
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 11:11
 * @Version 1.0.1
 **/
@Component
public class RegisterVerifyUtils {

    private static Logger logger = LoggerFactory.getLogger(RegisterVerifyUtils.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CreditScoreUtils creditScoreUtils;

    @Resource
    private JxcUserMapper jxcUserMapper;

    @Resource
    private JxcUserRoleMapper jxcUserRoleMapper;

    @Autowired
    private LoginUtils loginUtils;

    @Autowired
    private EagleEyesFeign eagleEyesFeign;

    /**
     * 用户注册验证
     * @param userRegisterVo
     * @return
     */
    public String registerVerify(UserRegisterVo userRegisterVo){
        String resStr = null;
        //客户端类型，1,机主版本 2,承租方版本
        if(userRegisterVo.getClientType() == null){
            resStr = "客户端类型不能为空";
            return resStr;
        }
        //注册类型(1--手机号密码注册 2--手机短信验证码注册)
        if(userRegisterVo.getRegisterType() == null){
            resStr = "注册类型不能为空";
            return resStr;
        }
        if(userRegisterVo.getRegisterType() != UserRegisterVo.registerType.pwd
                && userRegisterVo.getRegisterType() != UserRegisterVo.registerType.code){
            resStr = "注册类型不识别";
            return resStr;
        }
        if(userRegisterVo.getClientType() != ClientTypeConstants.CLIENT_OWNER.getId()
                && userRegisterVo.getClientType() != ClientTypeConstants.CLIENT_TENANTRY.getId()){
            resStr = "客户端类型不识别";
            return resStr;
        }
        //用户名密码注册
        if(userRegisterVo.getRegisterType() == UserRegisterVo.registerType.pwd){
            //用户名密码注册(用户名 + 密码 + 验证码 )
            if(userRegisterVo.getPhone() == null || userRegisterVo.getPassword() == null || userRegisterVo.getVerifyCode() == null){
                resStr = "用户名密码注册参数缺失";
                return resStr;
            }
            if(this.redisUtil.hasKey(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+userRegisterVo.getPhone())==false){
                resStr = "短信验证码不存在或过期";
                return resStr;
            }
            String redisStr = String.valueOf(this.redisUtil.get(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+userRegisterVo.getPhone()));
            logger.info("注册手机号:"+userRegisterVo.getPhone() + "验证码:"+redisStr);
            if(!redisStr.equals(userRegisterVo.getVerifyCode())){
                resStr = "短信验证码错误";
                return resStr;
            }
        }else if(userRegisterVo.getRegisterType() == UserRegisterVo.registerType.code){
            //短信验证码注册
             if(userRegisterVo.getPhone() == null || userRegisterVo.getVerifyCode() == null){
                 resStr = "短信验证码注册手机号或验证码不能为空";
                 return resStr;
            }
            if(this.redisUtil.hasKey(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+userRegisterVo.getPhone())==false){
                resStr = "短信验证码不存在或过期";
                return resStr;
            }
            String redisStr = String.valueOf(this.redisUtil.get(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+userRegisterVo.getPhone()));
            logger.info("注册手机号:"+userRegisterVo.getPhone() + "验证码:"+redisStr);
            if(!redisStr.equals(userRegisterVo.getVerifyCode())){
                resStr = "短信验证码错误";
                return resStr;
            }
        }
        return resStr;
    }

    /**
     * 判断用户是否已经注册
     * @return
     */
    public Boolean confirmData(String phone,Integer clientType){
        Boolean bru = false;
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(phone);
        List<JxcUser> list = this.jxcUserMapper.selectListByConditions(jxcUser);
        if(!CollectionUtils.isEmpty(list)){
            return bru;
        }else{
            bru = true;
        }
        return bru;
    }

    /**
     * 添加用户
     * @param userRegisterVo
     * @return
     */
    public ResponseMessage insertUser(UserRegisterVo userRegisterVo,Integer roleId,Integer templateId){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(userRegisterVo.getPhone());
        jxcUser.setThirdId(MD5Util.get32MD5(userRegisterVo.getPhone()));
        jxcUser.setPassword(userRegisterVo.getPassword());
        //添加用户
        this.jxcUserMapper.insertSelective(jxcUser);
        JxcUserRole jxcUserRole = new JxcUserRole();
        jxcUserRole.setUserId(jxcUser.getId());
        jxcUserRole.setRoleId(roleId);
        //添加用户角色
        this.jxcUserRoleMapper.insertSelective(jxcUserRole);
        //添加信用分
        UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
        userCreditScoreVo.setUserId(jxcUser.getId());
        userCreditScoreVo.setTemplateId(templateId);
        this.creditScoreUtils.verifyCreditScore(userCreditScoreVo);

        //注册百度鹰眼
        EagleEyesVo eagleEyesVo = new EagleEyesVo();
        eagleEyesVo.setActionCode(EagleEyesVo.actionCode.ADDENTITY);
        if(userRegisterVo.getClientType() == ClientTypeConstants.CLIENT_TENANTRY.getId()){
            eagleEyesVo.setClient(String.valueOf(ClientTypeConstants.CLIENT_TENANTRY.getId()));
        }else if(userRegisterVo.getClientType() == ClientTypeConstants.CLIENT_OWNER.getId()){
            eagleEyesVo.setClient(String.valueOf(ClientTypeConstants.CLIENT_OWNER.getId()));
        }
        eagleEyesVo.setEntityName(jxcUser.getThirdId());
        this.eagleEyesFeign.baiduEntity(eagleEyesVo);

        try {
            UserRegisterReturnVo returnVo = new UserRegisterReturnVo();
            returnVo.setUserId(jxcUser.getId());
            returnVo.setRoleId(roleId);
            returnVo.setThirdId(MD5Util.get32MD5(userRegisterVo.getPhone()));
            returnVo.setPhone(jxcUser.getPhone());
            returnVo.setToken(this.loginUtils.genToken(jxcUser,roleId));
            if(this.jxcUserMapper.selectByPrimaryKey(jxcUser.getId()).getPassword() != null){
                returnVo.setIsEmptyPwd(UserRegisterReturnVo.isEmptyPwd.UNEMPTY);
            }else{
                returnVo.setIsEmptyPwd(UserRegisterReturnVo.isEmptyPwd.EMPTY);
            }
            result.setData(returnVo);
        }catch (Exception ex){
            result =  new ResponseMessage(ErrorCodeConstants.TOKEN_GEN_FAIL.getId(),ErrorCodeConstants.TOKEN_GEN_FAIL.getDescript());
        }
        return result;
    }


}
