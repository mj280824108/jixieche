package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcUserMapper extends BaseMapper<JxcUser> {

    /**
     * 根据userId查询用户角色id
     * @param userRoleVo
     * @return
     */
    UserRoleVo queryUserRoleByUserId(UserRoleVo userRoleVo);

    /**
     * 根据userId查询机主个人信息
     */
    OwnerInfoVo queryOwnerInfo(@Param("userId")Integer userId);

    /**
     * 根据userId查询承租方个人信息
     */
    TenUserInfoVo queryTenInfo(@Param("userId")Integer userId);

    /**
     * 根据userId查询司机个人信息
     */
    DriverUserInfoVo queryDriverInfo(@Param("userId")Integer userId);

    /**
     * 根据用户手机号和角色id查询用户信息
     */
    UserRoleInfoVo queryInfoByPhoneRoleId(UserRoleInfoVo userRoleInfoVo);

    /**
     * 查询用户基本信息
     * @param userId
     * @return
     */
    UserInfoVo queryUserInfo(@Param("userId") Integer userId);

    /**
     * 查询用户基本信息
     * @param thirdId
     * @return
     */
    UserInfoVo queryUserInfoByThirdId(@Param("thirdId") String thirdId);

    /**
     * 查询司机为完成的订单数量
     * @param userId
     * @return
     */
    Integer queryDriverUnDoneOrder(@Param("userId") Integer userId);

    /**
     * 查询司机为完成的订单数量
     * @param userId
     * @return
     */
    String queryUserPayPwdByUserId(@Param("userId") Integer userId);

    /**
     * 根据userId查询店铺
     * @param userId
     * @return
     */
    Integer queryShopsByUserId(@Param("userId") Integer userId);

}