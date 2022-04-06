package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcAndroidAppVersion;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcAndroidAppVersionMapper extends BaseMapper<JxcAndroidAppVersion> {

    /**
     * 查找版本
     *
     * @param version
     * @param appClient
     * @param isTest
     * @return
     */
    JxcAndroidAppVersion selectOne(@Param("version") int version, @Param("appClient") int appClient,
                                   @Param("isTest") int isTest);

    /**
     * 获取最新的版本
     *
     * @param version
     * @param appClient
     * @param isTest
     * @return
     */
    JxcAndroidAppVersion selectLatest(@Param("version") int version, @Param("appClient") int appClient,
                                      @Param("isTest") int isTest);

    /**
     * 若服务器当前版本不属于强制更新版本，则检测用户传入版本与当前版本之间是否存在强制更新版本
     *
     * @return
     */
    int countForceVersionBetween(@Param("version") int version, @Param("latestVersion") int latestVersion,
                                 @Param("appClient") int appClient, @Param("isTest") int isTest);
}