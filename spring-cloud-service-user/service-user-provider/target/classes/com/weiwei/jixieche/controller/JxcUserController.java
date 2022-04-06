package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcUserService;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--用户表")
@RestController
@RequestMapping("jxcUser")
public class JxcUserController {

       @Resource
       private JxcUserService jxcUserService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcUser> findByPageForFront(@RequestBody JxcUser jxcUser) {
              return this.jxcUserService.findByPageForFront(jxcUser.getPageNo(),jxcUser.getPageSize(),jxcUser);
       }

       @ApiOperation(httpMethod="POST", value="添加用户表")
       @PostMapping("/insert")
       public ResponseMessage<JxcUser> insert(@RequestBody JxcUser jxcUser) {
              return this.jxcUserService.addObj(jxcUser);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户表")
       @PostMapping("/edit")
       public ResponseMessage<JxcUser> edit(@RequestBody JxcUser jxcUser) {
              return this.jxcUserService.modifyObj(jxcUser);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcUser> queryById(Integer id) {
              return this.jxcUserService.queryObjById(id);
       }


       @ApiOperation(httpMethod="POST", value="用户注册")
       @PostMapping("/register")
       public ResponseMessage register(@RequestBody UserRegisterVo userRegisterVo) {
              return this.jxcUserService.register(userRegisterVo);
       }

       @ApiOperation(httpMethod="POST", value="用户机主版app选择绝色")
       @PostMapping("/chooseRoleByUserId")
       public ResponseMessage chooseRoleByUserId(@RequestBody UserChooseRoleVo userChooseRoleVo) {
              return this.jxcUserService.chooseRoleByUserId(userChooseRoleVo);
       }

       @ApiOperation(httpMethod="POST", value="用户登录")
       @PostMapping("/login")
       public ResponseMessage login(@RequestBody UserLoginVo userLoginVo) {
              return this.jxcUserService.login(userLoginVo);
       }

       @ApiOperation(httpMethod="POST", value="查询用户是否设置支付密码")
       @PostMapping("/payPwdExist")
       public ResponseMessage payPwdExist(AuthorizationUser user) {
              return this.jxcUserService.payPwdExist(user);
       }

       @ApiOperation(httpMethod="POST", value="用户设置支付密码身份验证")
       @PostMapping("/userSetPayPwdVerify")
       public ResponseMessage userSetPayPwdVerify(AuthorizationUser user,@RequestBody UserSetPayPwdVerifyVo userSetPayPwdVerifyVo) {
              return this.jxcUserService.userSetPayPwdVerify(user,userSetPayPwdVerifyVo);
       }

       @ApiOperation(httpMethod="POST", value="用户设置支付密码")
       @PostMapping("/userSetPayPwd")
       public ResponseMessage userSetPayPwd(AuthorizationUser user,@RequestBody UserSetPayPwdVo userSetPayPwdVo) {
              return this.jxcUserService.userSetPayPwd(user,userSetPayPwdVo);
       }


       @ApiOperation(httpMethod="POST", value="消纳场管理员登录")
       @PostMapping("/loginSiteManager")
       public ResponseMessage loginSiteManager(@RequestBody SiteManagerLoginVo siteManagerLoginVo) {
              return this.jxcUserService.loginSiteManager(siteManagerLoginVo);
       }

       @ApiOperation(httpMethod="POST", value="用户退出登录")
       @PostMapping("/logout")
       public ResponseMessage logout(@RequestBody UserLogoutVo userLogoutVo) {
              return this.jxcUserService.logout(userLogoutVo);
       }

       @ApiOperation(httpMethod="POST", value="机主注册临时司机")
       @PostMapping("/ownerRegisterDriver")
       public ResponseMessage ownerRegisterDriver(@RequestBody OwnerRegisterDriverVo userLoginVo) {
              return this.jxcUserService.ownerRegisterDriver(userLoginVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方管理员注册")
       @PostMapping("/managerRegister")
       public ResponseMessage managerRegister(@RequestBody ManagerRegisterVo managerRegisterVo) {
              return this.jxcUserService.managerRegister(managerRegisterVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方修改管理员密码")
       @PostMapping("/updateManagerPwd")
       public ResponseMessage updateManagerPwd(AuthorizationUser authorizationUser, @RequestBody ManagerUpdatePwdVo managerRegisterVo) {
              return this.jxcUserService.updateManagerPwd(authorizationUser,managerRegisterVo);
       }

       @ApiOperation(httpMethod="POST", value="重置(忘记密码)密码")
       @PostMapping("/resetPwd")
       public ResponseMessage resetPwd(@RequestBody ResetPwdVo resetPwdVo) {
              return this.jxcUserService.resetPwd(resetPwdVo);
       }

       @ApiOperation(httpMethod="POST", value="用户设置密码")
       @PostMapping("/setPwd")
       public ResponseMessage setPwd(@RequestBody SetPwdVo setPwdVo) {
              return this.jxcUserService.setPwd(setPwdVo);
       }

       @ApiOperation(httpMethod="POST", value="查询承租方个人基本信息")
       @PostMapping("/queryTenInfo")
       public ResponseMessage<TenUserInfoVo> queryTenInfo(AuthorizationUser user) {
              return this.jxcUserService.queryTenInfo(user);
       }

       @ApiOperation(httpMethod="POST", value="查询司机个人基本信息")
       @PostMapping("/queryDriverInfo")
       public ResponseMessage<DriverUserInfoVo> queryDriverInfo(AuthorizationUser user) {
              return this.jxcUserService.queryDriverInfo(user);
       }

       @ApiOperation(httpMethod="POST", value="查询机主个人基本信息")
       @PostMapping("/queryOwnerInfo")
       public ResponseMessage<OwnerInfoVo> queryOwnerInfo(AuthorizationUser user) {
              return this.jxcUserService.queryOwnerInfo(user);
       }

       @ApiOperation(httpMethod="POST", value="查询个人信息")
       @GetMapping("/queryUserInfo")
       public ResponseMessage queryUserInfo(@RequestParam("userId")Integer userId) {
              return this.jxcUserService.queryUserInfo(userId);
       }

       @ApiOperation(httpMethod="POST", value="司机申请为机主")
       @PostMapping("/driverApplyOwner")
       public ResponseMessage driverApplyOwner(AuthorizationUser user) {
              return this.jxcUserService.driverApplyOwner(user);
       }

       @ApiOperation("邀请注册（网页接口）")
       @PostMapping("/invitationRegister")
       public ResponseMessage invitationRegister(@RequestParam(required = false,name = "thirdId") String thirdId) {
              if (VerifyStr.isEmpty(thirdId)) {
                     return new ResponseMessage<>(ErrorCodeConstants.PARAM_EMPTY.getId(), "third不能为空");
              }
              return jxcUserService.queryUserInfoByThirdId(thirdId);
       }

}