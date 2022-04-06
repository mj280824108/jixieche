package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.vo.JxcTransCostsVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author 钟焕
 * @Description
 * @Date 17:44 2019-08-22
 **/
public interface JxcTransCostsMapper {

    /**
     * 根据承租方订单ID查询对应的项目城市ID以及该订单的土方类型
     * @param orderId
     * @return
     */
    Map<String,Object> getCityCodeByOrderId(@Param("orderId") Long orderId);


    /**
     * 根据城市编号以及土方类型获取对应的计费规则
     * @param params
     * @return
     */
    JxcTransCostsVo getTransCosts(@Param("params") Map<String, Object> params);

    /**
     * 根据项目ID获取项目所在城市的cityCode
     * @param projectId
     * @return
     */
    Integer getCityCodeByProjectId(@Param("projectId") Integer projectId);


}
