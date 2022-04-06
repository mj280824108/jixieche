package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcUserConfirm;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcUserConfirmService extends BaseService<JxcUserConfirm> {
       /**
     * 前端分页查询用户认证信息表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcUserConfirm 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcUserConfirm jxcUserConfirm);

}