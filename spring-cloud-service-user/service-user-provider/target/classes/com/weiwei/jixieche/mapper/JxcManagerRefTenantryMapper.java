package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcManagerRefTenantry;
import com.weiwei.jixieche.vo.TenManagerInfoVo;
import org.apache.ibatis.annotations.Param;

public interface JxcManagerRefTenantryMapper extends BaseMapper<JxcManagerRefTenantry> {

    /**
     * 查询承租方信息
     * @return
     */
    TenManagerInfoVo queryTenManagerInfo(@Param("userId")Integer userId);
}