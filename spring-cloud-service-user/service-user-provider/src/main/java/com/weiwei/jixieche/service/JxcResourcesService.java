package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcResources;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcResourcesService extends BaseService<JxcResources> {
       /**
     * 前端分页查询资源(菜单)表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcResources 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcResources jxcResources);
}