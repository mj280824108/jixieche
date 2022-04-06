package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMarketResourceType;
import com.weiwei.jixieche.vo.MarketResourceTypeListVo;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketResourceTypeMapper extends BaseMapper<JxcMarketResourceType> {

    /**
     * 查询市场资源类型列表
     * @return
     */
    List<MarketResourceTypeListVo> queryMarketResourceTypeList(MarketResourceTypeListVo marketResourceTypeListVo);

    /**
     * 查询子类资源类型列表
     * @return
     */
    List<JxcMarketResourceType> queryChildSourceTypeList();
}