package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.jwt.JwtUtil;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.ResetPwdVo;
import com.weiwei.jixieche.vo.SetPwdVo;
import com.weiwei.jixieche.vo.UserRoleInfoVo;
import com.weiwei.jixieche.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ResetPwdUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/15 19:10
 * @Version 1.0.1
 **/
@Component
public class ResetPwdUtils {

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private JxcUserMapper jxcUserMapper;

    /**
     * 重置密码和修改密码
     * @param resetPwdVo
     * @return
     */
    public String paramVerify(ResetPwdVo resetPwdVo){
        String resStr = null;
        //客户端类型和修改类型为空判断
        if(resetPwdVo.getClientType() == null || resetPwdVo.getResetType() == null){
            resStr = "app客户端类型或设置密码类型不能为空";
            return resStr;
        }
        if(resetPwdVo.getClientType() != ClientTypeConstants.CLIENT_OWNER.getId()
                && resetPwdVo.getClientType() != ClientTypeConstants.CLIENT_TENANTRY.getId()){
            resStr = "客户端类型不识别";
            return resStr;
        }
        if(resetPwdVo.getResetType() != ResetPwdVo.resetType.FORGET
                && resetPwdVo.getResetType() != ResetPwdVo.resetType.UPDATE){
            resStr = "密码设置类型不识别";
            return resStr;
        }
        //如果是忘记密码，手机号和验证码不能为空
        if(resetPwdVo.getResetType() == ResetPwdVo.resetType.FORGET){
            if(resetPwdVo.getPhone() == null || resetPwdVo.getVerifyCode() == null){
                resStr = "手机号或验证码不能为空";
                return resStr;
            }
            //redis不存在用户忘记密码存入的验证码前缀key
            if(redisUtil.hasKey(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+resetPwdVo.getPhone())){
                resStr = "验证码过期或不存在";
                return resStr;
            }
            //redis中的短信验证码和用户的验证码是否一致
            if(String.valueOf(redisUtil.get(RedisPrefixConstants.FORGET_MOBILE_CODE_PREFIX.getPrefix())).equals(resetPwdVo.getVerifyCode())){
                resStr = "验证码错误";
                return resStr;
            }
        }else{
            //如果是重置密码
            if(resetPwdVo.getPhone() == null || resetPwdVo.getOldPwd() == null || resetPwdVo.getPwd() == null){
                resStr = "重置密码手机号或新旧密码不能为空";
                return resStr;
            }
        }
        return resStr;
    }

    /**
     * 根据用户名和密码查询用户是否存在
     * @param resetPwdVo
     * @return
     */
    public boolean confirmData(ResetPwdVo resetPwdVo){
        boolean bru = false;
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(resetPwdVo.getPhone());
        jxcUser.setPassword(resetPwdVo.getOldPwd());
        List<JxcUser> list = this.jxcUserMapper.selectListByConditions(jxcUser);
        if(CollectionUtils.isEmpty(list)){
            return bru;
        }else{
            bru = true;
        }
        return bru;
    }

    /**
     * 修改密码
     * @return
     */
    public ResponseMessage resetPwd(ResetPwdVo resetPwdVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //承租方修改密码
        if(resetPwdVo.getClientType() == ClientTypeConstants.CLIENT_TENANTRY.getId()){
            UserRoleInfoVo userRoleInfoVo = new UserRoleInfoVo();
            userRoleInfoVo.setPhone(resetPwdVo.getPhone());
            userRoleInfoVo.setRoleId(UserRoleContants.TEN.getRoleId());
            userRoleInfoVo = this.jxcUserMapper.queryInfoByPhoneRoleId(userRoleInfoVo);
            //如果用户是承租方，直接修改密码
            if(userRoleInfoVo != null){
                JxcUser jxcUser = new JxcUser();
                jxcUser.setId(userRoleInfoVo.getUserId());
                jxcUser.setPassword(resetPwdVo.getPwd());
                jxcUser.setUpdateTime(new Date());
                int res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
                AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
            }else{
                //如果是承租方管理员，提示联系承租方修改密码，个人不能修改密码
                UserRoleInfoVo tenManager = new UserRoleInfoVo();
                tenManager.setPhone(resetPwdVo.getPhone());
                tenManager.setRoleId(UserRoleContants.TEN_MAN.getRoleId());
                tenManager = this.jxcUserMapper.queryInfoByPhoneRoleId(tenManager);
                if(tenManager != null){
                    result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                    result.setMessage("管理员请联系雇主修改密码!");
                    return result;
                }else{
                    result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                    result.setMessage("用户不存在!");
                    return result;
                }
            }
        }else{
            //机主版修改密码
            JxcUser jxcUser = new JxcUser();
            jxcUser.setPhone(resetPwdVo.getPhone());
            if(resetPwdVo.getResetType() == ResetPwdVo.resetType.UPDATE){
                jxcUser.setPassword(resetPwdVo.getOldPwd());
            }
            List<JxcUser> list = this.jxcUserMapper.selectListByConditions(jxcUser);
            if(CollectionUtils.isEmpty(list)){
                result = new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"账户密码错误!");
                return result;
            }
            //判断该用户是否是机主或司机
            jxcUser = list.get(0);
            UserRoleVo userRoleVo = new UserRoleVo();
            userRoleVo.setUserId(jxcUser.getId());
            userRoleVo = this.jxcUserMapper.queryUserRoleByUserId(userRoleVo);
            //当用户使用账户是承租方时
            if(userRoleVo.getRoleId() == UserRoleContants.TEN.getRoleId()){
                result = new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"请使用承租方版app修改密码");
                return result;
            }
            jxcUser = new JxcUser();
            jxcUser.setPassword(resetPwdVo.getPwd());
            jxcUser.setId(userRoleVo.getUserId());
            jxcUser.setUpdateTime(new Date());
            int res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
            AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
        }
        return result;
    }

}
