package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcOpenedCity;
import com.weiwei.jixieche.response.ResponseMessage;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcOpenedCityService extends BaseService<JxcOpenedCity> {
    /**
     * 前端分页查询开放城市车载容量表
     *
     * @param pageNo        分页索引
     * @param pageSize      每页显示数量
     * @param jxcOpenedCity 查询条件
     * @return
     */
    ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcOpenedCity jxcOpenedCity);

    /**
     * 查询开放城市的扣除佣金以后给到机主的实际金额
     * @param orderId
     * @param amount
     * @return
     */
    Integer getToOwnerAmount(Long orderId, Integer amount);
}