package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.MachineTypeVo;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketMachineTypeService extends BaseService<JxcMarketMachineType> {
       /**
     * 前端分页查询市场机械类型字典表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketMachineType 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketMachineType jxcMarketMachineType);

       /**
        * 查询市场资源类型表(不分页)
        * @param jxcMarketMachineType
        * @return
        */
       ResponseMessage<JxcMarketMachineType> query(JxcMarketMachineType jxcMarketMachineType);

       /**
        * 查询机械父类品牌
        */
       ResponseMessage queryMachineType(MachineTypeVo machineTypeVo);

       /**
        * 查询所有的父类机械
        * @return
        */
       ResponseMessage queryParentMachineType();
}