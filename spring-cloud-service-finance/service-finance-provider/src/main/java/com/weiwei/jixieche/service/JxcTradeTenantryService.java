package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.TenTradeRecordListVo;

public interface JxcTradeTenantryService extends BaseService<JxcTradeTenantry> {
       /**
     * 前端分页查询承租方交易流水表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcTradeTenantry 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeTenantry jxcTradeTenantry);

       /**
        * 查询承租方交易明细
        * @param tenTradeRecordListVo
        * @return
        */
       ResponseMessage queryTenTradeRecord(TenTradeRecordListVo tenTradeRecordListVo);
}