package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcTradeTenantryRef;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcTradeTenantryRefService extends BaseService<JxcTradeTenantryRef> {
       /**
     * 前端分页查询承租方支付趟数关联表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcTradeTenantryRef 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeTenantryRef jxcTradeTenantryRef);
}