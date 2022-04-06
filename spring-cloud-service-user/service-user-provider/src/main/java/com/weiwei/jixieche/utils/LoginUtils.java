package com.weiwei.jixieche.utils;

import com.google.gson.Gson;
import com.weiwei.jixieche.JxcDriverRefOwnerFeign;
import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.bean.JxcUserConfirm;
import com.weiwei.jixieche.bean.JxcUserRole;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.jwt.JwtConstant;
import com.weiwei.jixieche.jwt.JwtUtil;
import com.weiwei.jixieche.jwt.User;
import com.weiwei.jixieche.mapper.JxcUserConfirmMapper;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.mapper.JxcUserRoleMapper;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName registerVerify
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 11:11
 * @Version 1.0.1
 **/
@Component
public class LoginUtils {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private JxcUserMapper jxcUserMapper;

    @Autowired
    private RegisterVerifyUtils registerVerifyUtils;

    @Resource
    private JxcUserConfirmMapper jxcUserConfirmMapper;

    @Resource
    private JxcUserRoleMapper jxcUserRoleMapper;

    @Autowired
    private JxcDriverRefOwnerFeign jxcDriverRefOwnerFeign;

    /**
     * 用户登录参数校验
     * @param userLoginVo
     * @return
     */
    public String loginVerify(UserLoginVo userLoginVo){
        String resStr = null;
        if(userLoginVo.getLoginType() == null){
            resStr = "登录类型不能为空";
            return resStr;
        }
        if(userLoginVo.getClientType() == null){
            resStr = "户端类型不能为空";
            return resStr;
        }
        if(userLoginVo.getClientType() != ClientTypeConstants.CLIENT_OWNER.getId()
                && userLoginVo.getClientType() != ClientTypeConstants.CLIENT_TENANTRY.getId()){
            resStr = "户端类型不识别";
            return resStr;
        }
        if(userLoginVo.getLoginType() != UserLoginVo.loginType.pwd
                && userLoginVo.getLoginType() != UserLoginVo.loginType.code){
            resStr = "登录类型不识别";
            return resStr;
        }
        //用户名密码登录
        if(userLoginVo.getLoginType() == UserLoginVo.loginType.pwd){
            if(userLoginVo.getPhone() == null || userLoginVo.getPassword() == null){
                resStr = "手机号或密码不能为空";
                return resStr;
            }
        }else if(userLoginVo.getLoginType() == UserLoginVo.loginType.code){
            //短信验证码登录
            if(userLoginVo.getPhone() == null || userLoginVo.getVerifyCode() == null){
                resStr = "手机号或验证码不能为空";
                return resStr;
            }
            if(this.redisUtil.hasKey(RedisKeyConstants.SHORT_CODE_LOGIN.getPrefix()+userLoginVo.getPhone())==false){
                resStr = "验证码不存在或者过期";
                return resStr;
            }
            if(!String.valueOf(this.redisUtil.get(RedisKeyConstants.SHORT_CODE_LOGIN.getPrefix()+userLoginVo.getPhone())).equals(userLoginVo.getVerifyCode())){
                resStr = "手机验证码不正确";
                return resStr;
            }
        }
        return resStr;
    }


    /**
     * 用户进行数据验证
     * @param userLoginVo
     * @return
     */
    public Boolean userConfirm(UserLoginVo userLoginVo){
        Boolean bru = false;
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(userLoginVo.getPhone());
        List<JxcUser> list = this.jxcUserMapper.selectListByConditions(jxcUser);
        if(CollectionUtils.isEmpty(list)){
            return bru;
        }else{
            bru = true;
        }
        return bru;
    }

    /**
     * 消纳场管理员登录参数验证
     * @return
     */
    public String verifyManagerParam(SiteManagerLoginVo siteManagerLoginVo){
        String resStr = null;
        if(siteManagerLoginVo.getPhone() == null || siteManagerLoginVo.getPassword() == null){
            resStr = "手机号或密码不能为空";
            return resStr;
        }
        return resStr;
    }


    /**
     * 登录
     * @param userLoginVo
     * @return
     */
    public ResponseMessage login(UserLoginVo userLoginVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //根据手机号和密码查询用户
        JxcUser jxcUser = new JxcUser();
        List<JxcUser> userList = null;
        //用户名密码登录
        if(userLoginVo.getLoginType() == UserLoginVo.loginType.pwd){
            jxcUser.setPhone(userLoginVo.getPhone());
            jxcUser.setPassword(userLoginVo.getPassword());
            userList = this.jxcUserMapper.selectListByConditions(jxcUser);
            if(CollectionUtils.isEmpty(userList)){
                result = new ResponseMessage(ErrorCodeConstants.PHONE_NOT_EXIST.getId(),"用户名密码错误，登录失败");
                return result;
            }
        }else if(userLoginVo.getLoginType() == UserLoginVo.loginType.code){
            jxcUser.setPhone(userLoginVo.getPhone());
            userList = this.jxcUserMapper.selectListByConditions(jxcUser);
            //根据手机号查询用户数据为空，立刻注册该手机号
            if(CollectionUtils.isEmpty(userList)){
                UserRegisterVo userRegisterVo = new UserRegisterVo();
                userRegisterVo.setClientType(userLoginVo.getClientType());
                userRegisterVo.setPhone(userLoginVo.getPhone());
                userRegisterVo.setDeviceId(userLoginVo.getDeviceId());
                //注册承租方用户
                if(userLoginVo.getClientType() == ClientTypeConstants.CLIENT_TENANTRY.getId()){
                    result = this.registerVerifyUtils.insertUser(userRegisterVo, UserRoleContants.TEN.getRoleId(), CreditScoreTemplateConstants.SCORE_TEN_PHONE_REGISTER.getId());
                    return result;
                }else if(userLoginVo.getClientType() == ClientTypeConstants.CLIENT_OWNER.getId()){
                    result = this.registerVerifyUtils.insertUser(userRegisterVo,UserRoleContants.NO_ROLE.getRoleId(),CreditScoreTemplateConstants.SCORE_OWNER_PHONE_REGISTER.getId());
                    return result;
                }
            }
        }
        //查询结果不为空


        //根据用户userId查询用户角色roleId
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setUserId(userList.get(0).getId());
        userRoleVo = this.jxcUserMapper.queryUserRoleByUserId(userRoleVo);
        //用户使用承租方app登录，但查询的角色不是承租方用户，立刻返回错误码
        if(userLoginVo.getClientType() == ClientTypeConstants.CLIENT_TENANTRY.getId()){
            //登录用户不是承租方角色
            if(userRoleVo.getRoleId() != UserRoleContants.TEN.getRoleId()
                    && userRoleVo.getRoleId() != UserRoleContants.SITE_MAN.getRoleId()
                    && userRoleVo.getRoleId() != UserRoleContants.TEN_MAN.getRoleId()){
                result = new ResponseMessage(ErrorCodeConstants.LOGIN_USER_CLIENT_SAME.getId(),ErrorCodeConstants.LOGIN_USER_CLIENT_SAME.getDescript());
                return result;
            }else{
                //登录成功
                result.setData(reData(userList.get(0),userRoleVo.getRoleId()));
            }
        }else{
            //用户使用机主app登录，但查询的角色是承租方用户，立刻返回错误码
            if(userRoleVo.getRoleId().equals(UserRoleContants.NO_ROLE.getRoleId())){
                result.setData(reData(userList.get(0),userRoleVo.getRoleId()));
            }else if(userRoleVo.getRoleId() != UserRoleContants.OWNER.getRoleId()
                    && userRoleVo.getRoleId() != UserRoleContants.DRIVER.getRoleId()
                    && userRoleVo.getRoleId() != UserRoleContants.CHILD.getRoleId()){
                result = new ResponseMessage(ErrorCodeConstants.LOGIN_USER_CLIENT_SAME.getId(),ErrorCodeConstants.LOGIN_USER_CLIENT_SAME.getDescript());
                return result;
            }else{
                //登录成功
                result.setData(reData(userList.get(0),userRoleVo.getRoleId()));
            }
        }
        return result;
    }

    /**
     * 消纳场管理员登录
     * @param siteManagerLoginVo
     * @return
     */
    public ResponseMessage loginSiteManager(SiteManagerLoginVo siteManagerLoginVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();

        UserRoleInfoVo userRoleInfoVo= new UserRoleInfoVo();
        userRoleInfoVo.setPhone(siteManagerLoginVo.getPhone());
        userRoleInfoVo.setRoleId(UserRoleContants.SITE_MAN.getRoleId());
        userRoleInfoVo =  this.jxcUserMapper.queryInfoByPhoneRoleId(userRoleInfoVo);
        if(userRoleInfoVo.getUserId() != null){
            JxcUser jxcUser= new JxcUser();
            jxcUser.setPhone(siteManagerLoginVo.getPhone());
            jxcUser.setPassword(siteManagerLoginVo.getPassword());
            List<JxcUser> list =  this.jxcUserMapper.selectListByConditions(jxcUser);
            if(!CollectionUtils.isEmpty(list)){
                jxcUser = list.get(0);
                //登录成功
                result.setData(reData(jxcUser,UserRoleContants.SITE_MAN.getRoleId()));
            }else{
                result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                result.setMessage("用户名或密码错误");
                return result;
            }
        }else{
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("用户不存在!");
            return result;
        }
        return result;
    }

    /**
     * 生成token
     * @param jxcUser
     * @param roleId
     * @return
     */
    public String genToken(JxcUser jxcUser,Integer roleId){
        String token = null;
        try{
            User user = new User();
            user.setUserId(jxcUser.getId());
            user.setRealName(jxcUser.getRealName());
            user.setThirdId(jxcUser.getThirdId());
            user.setRoleId(roleId);
            user.setPhone(jxcUser.getPhone());
            String subject = new Gson().toJson(user);
            //用户注册机主app端，则用户未选择角色，设置token--60秒过期
            if(roleId == UserRoleContants.NO_ROLE.getRoleId()){
                token = jwtUtil.createJWT(JwtConstant.JWT_ID, JwtConstant.ISSUER, subject, JwtConstant.REGISTER_TOKEN_TIME);
            }else{
                token = jwtUtil.createJWT(JwtConstant.JWT_ID, JwtConstant.ISSUER, subject, JwtConstant.JWT_TTL);
            }
            return token;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return token;
    }

    /**
     * 登录返回值
     * @param jxcUser
     * @param roleId
     * @return
     */
    public UserLoginReturnVo reData(JxcUser jxcUser,Integer roleId){
        //登录成功
        UserLoginReturnVo returnVo = new UserLoginReturnVo();
        returnVo.setRoleId(roleId);
        returnVo.setThirdId(jxcUser.getThirdId());
        returnVo.setUserId(jxcUser.getId());
        returnVo.setPhone(jxcUser.getPhone());
        if(jxcUser.getPassword() != null){
            returnVo.setIsEmptyPwd(UserLoginReturnVo.isEmptyPwd.UNEMPTY);
        }else{
            returnVo.setIsEmptyPwd(UserLoginReturnVo.isEmptyPwd.EMPTY);
        }
        returnVo.setToken(genToken(jxcUser,roleId));

        /*记录最后最新登录时间*/
        JxcUser lastLogin = new JxcUser();
        lastLogin.setId(jxcUser.getId());
        lastLogin.setLastLoginTime(new Date());
        this.jxcUserMapper.updateByPrimaryKeySelective(lastLogin);
        return returnVo;
    }

    /**
     * 司机升级为机主
     * @param user
     * @return
     */
    public ResponseMessage driverApplyOwner(AuthorizationUser user){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //判断司机是否认证
        JxcUserConfirm jxcUserConfirm = new JxcUserConfirm();
        jxcUserConfirm.setUserId(user.getUserId());
        List<JxcUserConfirm> list = this.jxcUserConfirmMapper.selectListByConditions(jxcUserConfirm);
        if(CollectionUtils.isEmpty(list)){
            result.setCode(ErrorCodeConstants.PERSON_CONFIRM_STATUS.getId());
            result.setMessage("用户未实名认证，不能升级为机主");
            return result;
        }
        //判断司机是否认证通过
        jxcUserConfirm = list.get(0);
        if(jxcUserConfirm.getDriverConfirmStatus() != JxcUserConfirm.confirmStatus.CONFIRM){
            result.setCode(ErrorCodeConstants.PERSON_CONFIRM_STATUS.getId());
            result.setMessage("实名认证未通过，不能升级为机主");
            return result;
        }
        //如果司机存在未完成的机主订单，则不允许升级为机主
        Integer unDoneCount = this.jxcUserMapper.queryDriverUnDoneOrder(user.getUserId());
        if(unDoneCount != null && unDoneCount >0){
            result.setCode(ErrorCodeConstants.PERSON_CONFIRM_STATUS.getId());
            result.setMessage("司机存在未完成的机主订单，不能升级为机主");
            return result;
        }
        //升级为机主通过，并返回token
        //1.修改用户的角色
        JxcUserRole jxcUserRole = new JxcUserRole();
        jxcUserRole.setUserId(user.getUserId());
        jxcUserRole.setRoleId(UserRoleContants.DRIVER.getRoleId());
        List<JxcUserRole> userRoles = this.jxcUserRoleMapper.selectListByConditions(jxcUserRole);
        if(!CollectionUtils.isEmpty(userRoles)){
            jxcUserRole = userRoles.get(0);
            JxcUserRole updateRole = new JxcUserRole();
            updateRole.setId(jxcUserRole.getId());
            updateRole.setRoleId(UserRoleContants.OWNER.getRoleId());
            this.jxcUserRoleMapper.updateByPrimaryKeySelective(updateRole);

            JxcUser jxcUser = this.jxcUserMapper.selectByPrimaryKey(user.getUserId());
            //申请为机主后添加我自己
            JxcDriverRefOwner jxcDriverRefOwner = new JxcDriverRefOwner();
            jxcDriverRefOwner.setRemarkName(jxcUser.getRealName());
            jxcDriverRefOwner.setPhone(jxcUser.getPhone());
            jxcDriverRefOwner.setDriverType(JxcDriverRefOwner.DriverType.OWN);
            jxcDriverRefOwner.setOwnerId(jxcUser.getId());
            jxcDriverRefOwner.setDriverId(jxcUser.getId());
            jxcDriverRefOwnerFeign.addDriverRefOwner(jxcDriverRefOwner);
            result.setData(reData(jxcUser,UserRoleContants.OWNER.getRoleId()));
        }else{
            result.setCode(ErrorCodeConstants.PERSON_CONFIRM_STATUS.getId());
            result.setMessage("用户角色不存在");
            return result;
        }
        return result;
    }

}
