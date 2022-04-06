package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcTradeTenantryRef;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface JxcTradeTenantryRefMapper extends BaseMapper<JxcTradeTenantryRef> {

    /**
     * 批量添加承租方支付趟数记录
     * @param map
     */
    void insertBatch(@Param("map")Map<String,Object> map);

    /**
     * 根据商户订单号查询所有的趟数集合
     */
    List<Long> queryRouteIdList(@Param("id") Long id);

    /**
     * 批量修改行程记录的支付状态
     * @param list
     */
    void updateBatchMachineRoute(@Param("list")List<Long> list);

}