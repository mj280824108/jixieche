package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketBanner;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcMarketBannerService extends BaseService<JxcMarketBanner> {
       /**
     * 前端分页查询市场banner表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketBanner 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketBanner jxcMarketBanner);
}