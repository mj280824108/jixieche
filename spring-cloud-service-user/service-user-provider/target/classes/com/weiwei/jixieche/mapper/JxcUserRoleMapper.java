package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcUserRoleMapper extends BaseMapper<JxcUserRole> {

    /**
     * 根据userId查询用户角色
     * @param userId
     * @return
     */
    Integer queryRoleByUserId(@Param("userId")Integer userId);

}