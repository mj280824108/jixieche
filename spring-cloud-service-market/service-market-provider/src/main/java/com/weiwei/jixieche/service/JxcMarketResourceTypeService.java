package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketResourceType;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketResourceTypeService extends BaseService<JxcMarketResourceType> {
       /**
     * 前端分页查询市场资源类型表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketResourceType 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketResourceType jxcMarketResourceType);

       /**
        * 查询市场资源类型表(不分页)
        * @param
        * @return
        */
       ResponseMessage<JxcMarketResourceType> query();

       /**
        * 查询市场资源类型列表
        */
       ResponseMessage queryMarketResourceTypeList();
}