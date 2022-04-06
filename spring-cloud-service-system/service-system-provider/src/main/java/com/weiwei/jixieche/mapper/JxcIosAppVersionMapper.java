package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcIosAppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcIosAppVersionMapper extends BaseMapper<JxcIosAppVersion> {

    /**
     * 查找版本
     *
     * @param version
     * @param appClient
     * @param isTest
     * @return
     */
    JxcIosAppVersion selectOne(@Param("version") String version, @Param("appClient") int appClient,
                               @Param("isTest") int isTest);

    /**
     * 获取最新的版本
     *
     * @param version
     * @param appClient
     * @param isTest
     * @return
     */
    JxcIosAppVersion selectLatest(@Param("version") String version, @Param("appClient") int appClient,
                                  @Param("isTest") int isTest);

    /**
     * 若服务器当前版本不属于强制更新版本，则检测用户传入版本与当前版本之间是否存在强制更新版本
     *
     * @param params
     * @return
     */
    int countForceVersionBetween(@Param("params") Map<String, Object> params);
}