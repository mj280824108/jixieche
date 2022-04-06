package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketBrand;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketBrandService extends BaseService<JxcMarketBrand> {
       /**
     * 前端分页查询市场品牌字典表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketBrand 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketBrand jxcMarketBrand);

       /**
        * 查询市场品牌字典表(不分页)
        * @return
        */
       ResponseMessage<JxcMarketBrand> query();
}