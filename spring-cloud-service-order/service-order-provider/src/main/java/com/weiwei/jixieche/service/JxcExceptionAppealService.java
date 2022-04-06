package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcExceptionAppeal;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcExceptionAppealService extends BaseService<JxcExceptionAppeal> {
       /**
     * 前端分页查询异常订单申诉表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcExceptionAppeal 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcExceptionAppeal jxcExceptionAppeal);
}