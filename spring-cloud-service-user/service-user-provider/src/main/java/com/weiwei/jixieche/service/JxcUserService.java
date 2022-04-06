package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.*;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcUserService extends BaseService<JxcUser> {
       /**
     * 前端分页查询用户表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcUser 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcUser jxcUser);

       /**
        * 用户注册
        * @param userRegisterVo
        * @return
        */
       ResponseMessage register(UserRegisterVo userRegisterVo);

       /**
        * 用户机主版app选择绝色
        * @param userChooseRoleVo
        * @return
        */
       ResponseMessage chooseRoleByUserId(UserChooseRoleVo userChooseRoleVo);

       /**
        * 用户登录
        * @param userLoginVo
        * @return
        */
       ResponseMessage login(UserLoginVo userLoginVo);

       /**
        * 查询用户是否设置支付密码
        * @param user
        * @return
        */
       ResponseMessage payPwdExist(AuthorizationUser user);

       /**
        * 用户设置支付密码身份验证
        * @param user
        * @param userSetPayPwdVerifyVo
        * @return
        */
       ResponseMessage userSetPayPwdVerify(AuthorizationUser user,UserSetPayPwdVerifyVo userSetPayPwdVerifyVo);

       /**
        * 设置支付密码
        * @param user
        * @param userSetPayPwdVo
        * @return
        */
       ResponseMessage userSetPayPwd(AuthorizationUser user,UserSetPayPwdVo userSetPayPwdVo);

       /**
        * 承租方管理员登录
        * @param siteManagerLoginVo
        * @return
        */
       ResponseMessage loginSiteManager(SiteManagerLoginVo siteManagerLoginVo);

       /**
        * 用户退出登录
        * @param userLogoutVo
        * @return
        */
       ResponseMessage logout(UserLogoutVo userLogoutVo);

       /**
        * 机主注册临时司机
        * @param userLoginVo
        * @return
        */
       ResponseMessage ownerRegisterDriver(OwnerRegisterDriverVo userLoginVo);


       /**
        * 承租方管理员注册
        * @param managerRegisterVo
        * @return
        */
       ResponseMessage managerRegister(ManagerRegisterVo managerRegisterVo);

       /**
        * 承租方修改管理员密码
        * @param authorizationUser
        * @param managerRegisterVo
        * @return
        */
       ResponseMessage updateManagerPwd(AuthorizationUser authorizationUser, ManagerUpdatePwdVo managerRegisterVo);

       /**
        * 重置和修改密码
        * @param resetPwdVo
        * @return
        */
       ResponseMessage resetPwd(ResetPwdVo resetPwdVo);

       /**
        * 用户基本信息
        * @param userId
        * @return
        */
       ResponseMessage<UserInfoVo> queryUserInfo(Integer userId);

       /**
        * 用户基本信息
        * @param thirdId
        * @return
        */
       ResponseMessage queryUserInfoByThirdId(String thirdId);

       /**
        * 查询承租方个人信息
        * @param user
        * @return
        */
       ResponseMessage<TenUserInfoVo> queryTenInfo(AuthorizationUser user);

       /**
        * 机主个人信息
        * @param user
        * @return
        */
       ResponseMessage queryOwnerInfo(AuthorizationUser user);

       /**
        * 查询机主个人信息
        * @param user
        * @return
        */
       ResponseMessage<DriverUserInfoVo> queryDriverInfo(AuthorizationUser user);

       /**
        * 用户设置密码
        * @param setPwdVo
        * @return
        */
       ResponseMessage setPwd(SetPwdVo setPwdVo);

       /**
        * 司机申请为机主
        * @param user
        * @return
        */
       ResponseMessage driverApplyOwner(AuthorizationUser user);
}