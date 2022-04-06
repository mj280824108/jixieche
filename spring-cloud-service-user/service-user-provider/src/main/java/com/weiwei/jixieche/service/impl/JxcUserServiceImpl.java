package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcDriverRefOwnerFeign;
import com.weiwei.jixieche.JxcMachineRouteFeign;
import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.bean.JxcManagerRefTenantry;
import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.bean.JxcUserRole;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.generate.MD5Util;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcCreditScoreScoredMapper;
import com.weiwei.jixieche.mapper.JxcManagerRefTenantryMapper;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.mapper.JxcUserRoleMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcUserService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.*;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Service("jxcUserService")
public class JxcUserServiceImpl implements JxcUserService {
    @Resource
    private JxcUserMapper jxcUserMapper;

    @Resource
    private JxcUserRoleMapper jxcUserRoleMapper;

    @Autowired
    private RegisterVerifyUtils registerVerifyUtils;

    @Autowired
    private LoginUtils loginUtils;

    @Resource
    private JxcCreditScoreScoredMapper jxcCreditScoreScoredMapper;

    @Resource
    private JxcManagerRefTenantryMapper jxcManagerRefTenantryMapper;

    @Autowired
    private ResetPwdUtils resetPwdUtils;

    @Autowired
    private JxcDriverRefOwnerFeign jxcDriverRefOwnerFeign;

    @Autowired
    private JxcMachineRouteFeign jxcMachineRouteFeign;

    @Autowired
    private UserSetPayPwdUtils userSetPayPwdUtils;

    @Autowired
    private JpushMsgFeign jpushMsgFeign;

    @Autowired
    private CreditScoreUtils creditScoreUtils;

    /**
     * 前端分页查询用户表
     *
     * @param pageNo   分页索引
     * @param pageSize 每页显示数量
     * @param jxcUser  查询条件
     * @return
     */
    @Override
    public ResponseMessage<JxcUser> findByPageForFront(Integer pageNo, Integer pageSize, JxcUser jxcUser) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(pageNo, pageSize);
        List<JxcUser> list = this.jxcUserMapper.selectListByConditions(jxcUser);
        PageInfo<JxcUser> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 添加用户表
     *
     * @param jxcUser
     * @return
     */
    @Override
    public ResponseMessage<JxcUser> addObj(JxcUser jxcUser) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcUserMapper.insertSelective(jxcUser);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
        return result;
    }

    /**
     * 修改用户表
     *
     * @param jxcUser
     * @return
     */
    @Override
    public ResponseMessage<JxcUser> modifyObj(JxcUser jxcUser) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }

    /**
     * 根据ID查询用户表
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<JxcUser> queryObjById(int id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcUser model = this.jxcUserMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 用户注册
     *
     * @param userRegisterVo
     * @return
     */
    @Transactional
    @Override
    public ResponseMessage register(UserRegisterVo userRegisterVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //注册验证参数
        String resStr = this.registerVerifyUtils.registerVerify(userRegisterVo);
        if (resStr != null) {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        }
        //判断用户是否已经注册
        if (!this.registerVerifyUtils.confirmData(userRegisterVo.getPhone(), userRegisterVo.getClientType())) {
            result = new ResponseMessage(ErrorCodeConstants.USER_PHONE_EXIST.getId(), ErrorCodeConstants.USER_PHONE_EXIST.getDescript());
            return result;
        }
        //用户注册
        JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
        JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
        if (userRegisterVo.getClientType() == ClientTypeConstants.CLIENT_TENANTRY.getId()) {
            result = this.registerVerifyUtils.insertUser(userRegisterVo, UserRoleContants.TEN.getRoleId(), CreditScoreTemplateConstants.SCORE_TEN_PHONE_REGISTER.getId());
            jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
        } else if (userRegisterVo.getClientType() == ClientTypeConstants.CLIENT_OWNER.getId()) {
            result = this.registerVerifyUtils.insertUser(userRegisterVo, UserRoleContants.NO_ROLE.getRoleId(), CreditScoreTemplateConstants.SCORE_OWNER_PHONE_REGISTER.getId());
            jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
        }
        //用户注册站内信以及推送
        if (result.getCode() == 200) {
            //拿到第三方ID
            UserRegisterReturnVo userRegisterReturnVo = (UserRegisterReturnVo)result.getData();
            if(userRegisterReturnVo != null){
                //站内信
                jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_REGISTER_SUCCESS.getId());
                jpushCustomMsgVo.setUserId(userRegisterReturnVo.getUserId());
                jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                //推送
                jpushTemplateVo.setAliases(userRegisterReturnVo.getThirdId());
                jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_REGISTER_SUCCESS.getId());
                jpushMsgFeign.jpushNotice(jpushTemplateVo);
            }
        }

        return result;
    }

    /**
     * 用户机主版app选择绝色
     *
     * @param userChooseRoleVo
     * @return
     */
    @Override
    public ResponseMessage chooseRoleByUserId(UserChooseRoleVo userChooseRoleVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //参数验证
        if (userChooseRoleVo.getRoleId() == null || userChooseRoleVo.getUserId() == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_MUST_EMPTY.getId(), ErrorCodeConstants.PARAM_MUST_EMPTY.getDescript());
        }
        if (userChooseRoleVo.getRoleId() != UserRoleContants.OWNER.getRoleId()
                && userChooseRoleVo.getRoleId() != UserRoleContants.DRIVER.getRoleId()) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_UNKNOW.getId(), ErrorCodeConstants.PARAM_UNKNOW.getDescript());
        }
        JxcUserRole jxcUserRole = new JxcUserRole();
        jxcUserRole.setUserId(userChooseRoleVo.getUserId());
        List<JxcUserRole> list = this.jxcUserRoleMapper.selectListByConditions(jxcUserRole);
        if (!CollectionUtils.isEmpty(list)) {
            jxcUserRole = list.get(0);
            JxcUserRole roleRef = new JxcUserRole();
            roleRef.setId(jxcUserRole.getId());
            roleRef.setRoleId(userChooseRoleVo.getRoleId());
            roleRef.setUpdateTime(new Date());
            //更新用户角色
            this.jxcUserRoleMapper.updateByPrimaryKeySelective(roleRef);
            //当用户为机主角色
            if (userChooseRoleVo.getRoleId() == UserRoleContants.OWNER.getRoleId()) {
                JxcDriverRefOwner jxcDriverRefOwner = new JxcDriverRefOwner();
                jxcDriverRefOwner.setOwnerId(userChooseRoleVo.getUserId());
                jxcDriverRefOwner.setDriverId(userChooseRoleVo.getUserId());
                jxcDriverRefOwner.setDriverType(JxcDriverRefOwner.DriverType.OWN);
                jxcDriverRefOwnerFeign.addDriverRefOwner(jxcDriverRefOwner);
            }
            //返回用户信息
            result.setData(loginUtils.reData(this.jxcUserMapper.selectByPrimaryKey(userChooseRoleVo.getUserId()), userChooseRoleVo.getRoleId()));
        } else {
            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "用户角色不存在，修改失败");
        }
        return result;
    }

    /**
     * 用户登录
     *
     * @param userLoginVo
     * @return
     */
    @Override
    public ResponseMessage login(UserLoginVo userLoginVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //参数非空验证
        String resStr = this.loginUtils.loginVerify(userLoginVo);
        if (resStr != null) {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        } else {
            result = this.loginUtils.login(userLoginVo);
        }
        return result;
    }

    /**
     * 查询用户是否设置支付密码
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage payPwdExist(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            JxcUser jxcUser = this.jxcUserMapper.selectByPrimaryKey(user.getUserId());
            //1--已经设置支付密码  0--未设置支付密码
            if (jxcUser != null && jxcUser.getPayPwd() != null && !"".equals(jxcUser.getPayPwd())) {
                result.setData(1);
            } else {
                result.setData(0);
            }
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token不存在或解析错误");
            return result;
        }
        return result;
    }

    /**
     * 用户设置支付密码身份验证
     *
     * @param user
     * @param userSetPayPwdVerifyVo
     * @return
     */
    @Override
    public ResponseMessage userSetPayPwdVerify(AuthorizationUser user, UserSetPayPwdVerifyVo userSetPayPwdVerifyVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            UserInfoVo userInfoVo = this.jxcUserMapper.queryUserInfo(user.getUserId());
            String resStr = this.userSetPayPwdUtils.verifyParam(userSetPayPwdVerifyVo, userInfoVo);
            if (resStr != null) {
                result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                result.setMessage(resStr);
                return result;
            }
            //如果修改支付密码
            if (userSetPayPwdVerifyVo.getPayPwdType() == 3) {
                JxcUser jxcUser = new JxcUser();
                jxcUser.setId(user.getUserId());
                jxcUser.setPayPwd(userSetPayPwdVerifyVo.getNewPayPwd());
                int res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
                AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
                return result;
            }
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token不存在或解析错误");
            return result;
        }
        return result;
    }

    /**
     * 设置支付密码
     *
     * @param user
     * @param userSetPayPwdVo
     * @return
     */
    @Override
    public ResponseMessage userSetPayPwd(AuthorizationUser user, UserSetPayPwdVo userSetPayPwdVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            if (userSetPayPwdVo.getPayPwdType() == null && userSetPayPwdVo.getPayPwdType() == null) {
                result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                result.setMessage("设置支付密码类型为空");
                return result;
            }
            if (userSetPayPwdVo.getPayPwdType() != 1 && userSetPayPwdVo.getPayPwdType() != 2) {
                result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                result.setMessage("设置支付密码类型不识别");
                return result;
            }
            JxcUser jxcUser = new JxcUser();
            jxcUser.setId(user.getUserId());
            jxcUser.setPayPwd(userSetPayPwdVo.getNewPayPwd());
            int res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
            AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
            return result;
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token不存在或解析错误");
            return result;
        }
    }

    /**
     * 消纳场管理员登录
     *
     * @param siteManagerLoginVo
     * @return
     */
    @Override
    public ResponseMessage loginSiteManager(SiteManagerLoginVo siteManagerLoginVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        String resStr = this.loginUtils.verifyManagerParam(siteManagerLoginVo);
        if (resStr != null) {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        } else {
            return this.loginUtils.loginSiteManager(siteManagerLoginVo);
        }
    }

    /**
     * 用户退出登录
     *
     * @param userLogoutVo
     * @return
     */
    @Override
    public ResponseMessage logout(UserLogoutVo userLogoutVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        return result;
    }

    /**
     * 机主注册临时司机
     *
     * @param ownerRegisterDriverVo
     * @return
     */
    @Override
    @Transactional
    public ResponseMessage ownerRegisterDriver(OwnerRegisterDriverVo ownerRegisterDriverVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //注册用户
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(ownerRegisterDriverVo.getPhone());
        jxcUser.setPassword(ownerRegisterDriverVo.getPassword());
        jxcUser.setThirdId(MD5Util.get32MD5(ownerRegisterDriverVo.getPhone()));
        jxcUser.setRealName(ownerRegisterDriverVo.getRealName());
        Integer userRes = this.jxcUserMapper.insertSelective(jxcUser);
        if (userRes == null || userRes <= 0) {
            result = new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "添加用户失败");
            return result;
        } else {
            //添加用户角色关联表
            JxcUserRole jxcUserRole = new JxcUserRole();
            jxcUserRole.setUserId(jxcUser.getId());
            jxcUserRole.setRoleId(UserRoleContants.CHILD.getRoleId());
            Integer refRes = this.jxcUserRoleMapper.insertSelective(jxcUserRole);
            if (refRes == null || refRes <= 0) {
                result = new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "添加用户角色失败");
                return result;
            }
            result.setData(jxcUser.getId());

            //添加信用分
            UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
            userCreditScoreVo.setUserId(jxcUser.getId());
            userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_DRIVER_PHONE_REGISTER.getId());
            this.creditScoreUtils.verifyCreditScore(userCreditScoreVo);
        }
        return result;
    }

    /**
     * 机主个人信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage queryOwnerInfo(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            OwnerInfoVo ownerInfoVo = this.jxcUserMapper.queryOwnerInfo(user.getUserId());
            if (ownerInfoVo.getConfirmId() == null) {
                ownerInfoVo.setOwnerConfirmStatus(OwnerInfoVo.ConfirmStatus.UNCONFIRM);
            }
            //车牌号
            ResponseMessage machineRes = this.jxcMachineRouteFeign.getMachineCarNoByUserId(user.getUserId());
            if (machineRes.getCode() == 200) {
                ownerInfoVo.setMachineCardNo(machineRes.getData().toString());
            }
            //查询用户信用分
            ownerInfoVo.setCreditScore(this.jxcCreditScoreScoredMapper.queryTotalScoreByUserId(user.getUserId()));
            //查询用户的店铺
            ownerInfoVo.setShopId(this.jxcUserMapper.queryShopsByUserId(user.getUserId()));
            result.setData(ownerInfoVo);
            return result;
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token解析失败");
            return result;
        }
    }

    /**
     * 查询司机个人信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage<DriverUserInfoVo> queryDriverInfo(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            DriverUserInfoVo driverUserInfoVo = this.jxcUserMapper.queryDriverInfo(user.getUserId());
            if (driverUserInfoVo.getConfirmId() == null) {
                driverUserInfoVo.setDriverConfirmStatus(DriverUserInfoVo.ConfirmStatus.UNCONFIRM);
                driverUserInfoVo.setMudDriverConfirmStatus(DriverUserInfoVo.ConfirmStatus.UNCONFIRM);
            }
            //判断驾驶证是否过期
            if (driverUserInfoVo.getLicenceValidityTime() != null) {
                if (DateUtils.diff(driverUserInfoVo.getLicenceValidityTime(), new Date())) {
                    driverUserInfoVo.setLicenceValidityStatus(DriverUserInfoVo.ValidityStatus.OVERTIME);
                } else {
                    driverUserInfoVo.setLicenceValidityStatus(DriverUserInfoVo.ValidityStatus.CONFIRM);
                }
            }
            //判断从业资格证是否过期
            if (driverUserInfoVo.getCertificateValidityTime() != null) {
                if (DateUtils.diff(driverUserInfoVo.getCertificateValidityTime(), new Date())) {
                    driverUserInfoVo.setCertificateValidityStatus(DriverUserInfoVo.ValidityStatus.OVERTIME);
                } else {
                    driverUserInfoVo.setCertificateValidityStatus(DriverUserInfoVo.ValidityStatus.CONFIRM);
                }
            }
            //查询用户信用分
            driverUserInfoVo.setCreditScore(this.jxcCreditScoreScoredMapper.queryTotalScoreByUserId(user.getUserId()));
            //车牌号
            ResponseMessage machineRes = this.jxcMachineRouteFeign.getMachineCarNoByUserId(user.getUserId());
            if (machineRes.getCode() == 200) {
                driverUserInfoVo.setMachineCardNo(machineRes.getData().toString());
            }
            driverUserInfoVo.setShopId(this.jxcUserMapper.queryShopsByUserId(user.getUserId()));
            result.setData(driverUserInfoVo);
            return result;
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token解析失败");
            return result;
        }
    }

    /**
     * 查询承租方个人信息
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage<TenUserInfoVo> queryTenInfo(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user != null && user.getUserId() != null) {
            TenUserInfoVo tenUserInfoVo = this.jxcUserMapper.queryTenInfo(user.getUserId());
            if (tenUserInfoVo.getConfirmId() == null) {
                tenUserInfoVo.setPersonConfirmStatus(TenUserInfoVo.ConfirmStatus.UNCONFIRM);
                tenUserInfoVo.setCompanyConfirmStatus(TenUserInfoVo.ConfirmStatus.UNCONFIRM);
            }
            tenUserInfoVo.setCreditScore(this.jxcCreditScoreScoredMapper.queryTotalScoreByUserId(user.getUserId()));
            tenUserInfoVo.setShopId(this.jxcUserMapper.queryShopsByUserId(user.getUserId()));
            result.setData(tenUserInfoVo);
            return result;
        } else {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token解析失败");
            return result;
        }
    }

    /**
     * 管理员注册
     *
     * @param managerRegisterVo
     * @return
     */
    @Override
    public ResponseMessage managerRegister(ManagerRegisterVo managerRegisterVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //注册用户
        JxcUser jxcUser = new JxcUser();
        jxcUser.setPhone(managerRegisterVo.getPhone());
        jxcUser.setPassword(managerRegisterVo.getPassword());
        jxcUser.setThirdId(MD5Util.get32MD5(managerRegisterVo.getPhone()));
        jxcUser.setRealName(managerRegisterVo.getRealName());
        Integer userRes = this.jxcUserMapper.insertSelective(jxcUser);
        if (userRes == null || userRes <= 0) {
            result = new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "添加用户失败");
            return result;
        } else {
            //添加用户角色关联表
            JxcUserRole jxcUserRole = new JxcUserRole();
            jxcUserRole.setUserId(jxcUser.getId());
            jxcUserRole.setRoleId(UserRoleContants.TEN_MAN.getRoleId());
            this.jxcUserRoleMapper.insertSelective(jxcUserRole);

            //添加承租方管理与与承租方的关系
            JxcManagerRefTenantry jxcManagerRefTenantry = new JxcManagerRefTenantry();
            jxcManagerRefTenantry.setTenId(managerRegisterVo.getTenId());
            jxcManagerRefTenantry.setTenManagerId(jxcUser.getId());
            jxcManagerRefTenantry.setProjectId(managerRegisterVo.getProjectId());
            jxcManagerRefTenantry.setProjectName(managerRegisterVo.getProjectName());
            jxcManagerRefTenantry.setTenManagerName(jxcUser.getRealName());

            Integer refRes = this.jxcManagerRefTenantryMapper.insertSelective(jxcManagerRefTenantry);
            if (refRes == null || refRes <= 0) {
                result = new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "添加管理员角色失败");
                return result;
            }
            result.setData(jxcUser.getId());
        }
        return result;
    }

    /**
     * 承租方修改管理员密码
     *
     * @param authorizationUser
     * @param managerRegisterVo
     * @return
     */
    @Override
    public ResponseMessage updateManagerPwd(AuthorizationUser authorizationUser, ManagerUpdatePwdVo managerRegisterVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //验证token是否存在,并获取承租方用户的userId
        if (authorizationUser == null || authorizationUser.getUserId() == null) {
            result = new ResponseMessage(ErrorCodeConstants.TOKEN_EMPTY.getId(), "用户角色获取失败，修改失败");
            return result;
        }
        //查询承租方id和角色
        JxcUser jxcUser = this.jxcUserMapper.selectByPrimaryKey(authorizationUser.getUserId());
        if (jxcUser == null) {
            result = new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(), "承租方用户为空，修改失败");
            return result;
        }
        //根据承租方管理员的手机号码查询用户是否存在
              /*UserRoleInfoVo userRoleInfoVo = new UserRoleInfoVo();
              userRoleInfoVo.setPhone(managerRegisterVo.getPhone());
              userRoleInfoVo.setRoleId(UserRoleContants.TEN_MAN.getRoleId());
              userRoleInfoVo = this.jxcUserMapper.queryInfoByPhoneRoleId(userRoleInfoVo);
              if(userRoleInfoVo.getUserId() == null){
                     result = new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"未查询到承租方管理员信息");
                     return result;
              }*/
        //查询该管理员是否属于该承租方用户
        JxcManagerRefTenantry jxcManagerRefTenantry = new JxcManagerRefTenantry();
        jxcManagerRefTenantry.setTenId(authorizationUser.getUserId());
        jxcManagerRefTenantry.setTenManagerId(managerRegisterVo.getUserId());
        List<JxcManagerRefTenantry> list = this.jxcManagerRefTenantryMapper.selectListByConditions(jxcManagerRefTenantry);
        if (CollectionUtils.isEmpty(list)) {
            result = new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(), "该管理员不属于此承租方，修改失败");
            return result;
        }
        //修改承租方管理员密码
        jxcUser = new JxcUser();
        jxcUser.setId(managerRegisterVo.getUserId());
        jxcUser.setPassword(managerRegisterVo.getPassword());
        Integer res = this.jxcUserMapper.updateByPrimaryKeySelective(jxcUser);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }

    /**
     * 重置和修改密码
     *
     * @param resetPwdVo
     * @return
     */
    @Override
    public ResponseMessage resetPwd(ResetPwdVo resetPwdVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //参数非空验证
        String resStr = resetPwdUtils.paramVerify(resetPwdVo);
        if (resStr != null) {
            result.setMessage(ErrorCodeConstants.PARAM_EMPTY_ERROR.getDescript());
            result.setMessage(resStr);
            return result;
        } else {
            result = resetPwdUtils.resetPwd(resetPwdVo);
        }
        return result;
    }

    /**
     * 用户基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public ResponseMessage<UserInfoVo> queryUserInfo(Integer userId) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        UserInfoVo userInfoVo = this.jxcUserMapper.queryUserInfo(userId);
        //车牌号
        ResponseMessage machineRes = this.jxcMachineRouteFeign.getMachineCarNoByUserId(userId);
        if (machineRes.getCode() == 200) {
            userInfoVo.setMachineCardNo(machineRes.getData().toString());
        }
        //查询信用分
        userInfoVo.setCreditScore(this.jxcCreditScoreScoredMapper.queryTotalScoreByUserId(userId));
        result.setData(userInfoVo);
        return result;
    }

    /**
     * 用户基本信息
     *
     * @param thirdId
     * @return
     */
    @Override
    public ResponseMessage queryUserInfoByThirdId(String thirdId) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        UserInfoVo userInfoVo = this.jxcUserMapper.queryUserInfoByThirdId(thirdId);
        result.setData(userInfoVo);
        return result;
    }


    /**
     * 用户设置密码
     *
     * @param setPwdVo
     * @return
     */
    @Override
    public ResponseMessage setPwd(SetPwdVo setPwdVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcUser jxcUser = this.jxcUserMapper.selectByPrimaryKey(setPwdVo.getUserId());
        if (jxcUser != null) {
            JxcUser updateUser = new JxcUser();
            updateUser.setId(setPwdVo.getUserId());
            updateUser.setPassword(setPwdVo.getPwd());
            this.jxcUserMapper.updateByPrimaryKeySelective(updateUser);
            jxcUser.setPassword(setPwdVo.getPwd());
            result.setData(loginUtils.reData(jxcUser, this.jxcUserRoleMapper.queryRoleByUserId(setPwdVo.getUserId())));
        } else {
            result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
            result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
            return result;
        }
        return result;
    }

    /**
     * 司机申请为机主
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public ResponseMessage driverApplyOwner(AuthorizationUser user) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (user == null || user.getUserId() == null) {
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("token不存在或解析异常");
            return result;
        } else {
            return this.loginUtils.driverApplyOwner(user);
        }
    }

}