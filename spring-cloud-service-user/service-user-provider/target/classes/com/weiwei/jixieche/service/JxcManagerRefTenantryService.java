package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcManagerRefTenantry;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.TenManagerInfoVo;

/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcManagerRefTenantryService extends BaseService<JxcManagerRefTenantry> {
       /**
     * 前端分页查询承租方管理员与承租方关联表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcManagerRefTenantry 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcManagerRefTenantry jxcManagerRefTenantry);

       /**
        * 查询承租方管理员信息
        * @param tenManagerInfoVo
        * @return
        */
       ResponseMessage<JxcManagerRefTenantry> queryTenManagerInfo(TenManagerInfoVo tenManagerInfoVo);
}