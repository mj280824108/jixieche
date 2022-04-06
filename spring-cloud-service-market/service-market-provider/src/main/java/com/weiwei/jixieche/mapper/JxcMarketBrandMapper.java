package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMarketBrand;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketBrandMapper extends BaseMapper<JxcMarketBrand> {

    /**
     * 查询市场品牌子类
     * @return
     */
    List<JxcMarketBrand> queryChildBrandList();
}