package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcChildRefOwner;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcChildRefOwnerService extends BaseService<JxcChildRefOwner> {
       /**
     * 前端分页查询子账号与机主关联关系表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcChildRefOwner 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcChildRefOwner jxcChildRefOwner);
}