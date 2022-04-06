package com.weiwei.jixieche.aop;

import com.weiwei.jixieche.bean.UserLoginRecord;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.mapper.UserLoginRecordMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UserInfoVo;
import com.weiwei.jixieche.vo.UserLoginReturnVo;
import com.weiwei.jixieche.vo.UserLoginVo;
import com.weiwei.jixieche.vo.UserLogoutVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户行为记录
 * */
@Aspect
@Component
public class UserBehaviorRecord {
    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;
    @Resource
    private JxcUserMapper jxcUserMapper;
    /**
     * 用户登录
     * */
    @Around(value="execution( * com.weiwei.jixieche.service.impl.JxcUserServiceImpl.logout(..))")
    public Object Logout(ProceedingJoinPoint point){
        ResponseMessage result=null;
        Object[] args = point.getArgs();
        UserLogoutVo userLogoutVo =(UserLogoutVo)args[0];
        UserInfoVo userInfo = jxcUserMapper.queryUserInfo(userLogoutVo.getUserId());
        try {
            result=(ResponseMessage)point.proceed();
            if(result.getCode()==200){
                UserLoginRecord userLoginRecord = new UserLoginRecord();
                userLoginRecord.setPhone(userInfo.getPhone());
                userLoginRecord.setState(3);
                userLoginRecord.setCreateTime(new Date());
                userLoginRecord.setDescribe(result.getMessage());
                userLoginRecordMapper.insertSelective(userLoginRecord);
            }else{
                UserLoginRecord userLoginRecord = new UserLoginRecord();
                userLoginRecord.setPhone(userInfo.getPhone());
                userLoginRecord.setState(4);
                userLoginRecord.setCreateTime(new Date());
                userLoginRecord.setDescribe(result.getMessage());
                userLoginRecordMapper.insertSelective(userLoginRecord);
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
    @Around(value="execution( * com.weiwei.jixieche.service.impl.JxcUserServiceImpl.login(..))")
    public Object login(ProceedingJoinPoint point){
        ResponseMessage result=null;
        try {
            result=(ResponseMessage)point.proceed();
            if(result.getCode()==200){
            UserLoginReturnVo userInfo = (UserLoginReturnVo) result.getData();
            UserLoginRecord userLoginRecord = new UserLoginRecord();
            userLoginRecord.setPhone(userInfo.getPhone());
            userLoginRecord.setState(1);
            userLoginRecord.setCreateTime(new Date());
            userLoginRecord.setDescribe(result.getMessage());
            userLoginRecordMapper.insertSelective(userLoginRecord);
        }else{
            Object[] args = point.getArgs();
            UserLoginVo userLoginVo =(UserLoginVo)args[0];
                UserLoginRecord userLoginRecord = new UserLoginRecord();
                userLoginRecord.setPhone(userLoginVo.getPhone());
                userLoginRecord.setState(2);
                userLoginRecord.setCreateTime(new Date());
                userLoginRecord.setDescribe(result.getMessage());
                userLoginRecordMapper.insertSelective(userLoginRecord);
        }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
