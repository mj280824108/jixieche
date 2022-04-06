package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.vo.MachineTypeChildVo;
import com.weiwei.jixieche.vo.MachineTypeVo;

import java.util.List;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketMachineTypeMapper extends BaseMapper<JxcMarketMachineType> {

    /**
     * 查询机械子品牌
     * @param machineTypeChildVo
     * @return
     */
    List<MachineTypeChildVo> queryChildMachineType(MachineTypeChildVo machineTypeChildVo);

    /**
     * 查询所有的父类品牌
     * @param machineTypeVo
     * @return
     */
    List<MachineTypeVo> queryMachineType(MachineTypeVo machineTypeVo);
}