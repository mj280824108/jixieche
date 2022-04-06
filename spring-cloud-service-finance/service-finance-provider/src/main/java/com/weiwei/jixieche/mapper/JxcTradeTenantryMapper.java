package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMachineRoute;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.vo.JxcMachineRouteVo;
import com.weiwei.jixieche.vo.JxcWaitHandleItemsVo;
import com.weiwei.jixieche.vo.TenPayAmountVo;
import com.weiwei.jixieche.vo.TenTradeRecordVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface JxcTradeTenantryMapper extends BaseMapper<JxcTradeTenantry> {

    /**
     * 根据主键id查承租方交易流水
     * @param id
     * @return
     */
    JxcTradeTenantry selectByPrimaryKey(Long id);

    /**
     * 查询承租方交易明细列表
     */
    List<TenTradeRecordVo> queryTenTradeRecord(TenTradeRecordVo ownerTradePayVo);

    /**
     * 查询承租方总支出金额
     * @param tenPayAmountVo
     * @return
     */
    TenPayAmountVo queryTotalPayAmount(TenPayAmountVo tenPayAmountVo);

    /**
     * 查询机械行程
     * @param routeId
     * @return
     */
    JxcMachineRoute queryJxcMachineRoute(@Param("id") Long routeId);

    /**
     * 查询待办事项
     * @param jxcWaitHandleItemsVo
     * @return
     */
    JxcWaitHandleItemsVo queryJxcWaitHandle(@Param("vo") JxcWaitHandleItemsVo jxcWaitHandleItemsVo);

    /**
     * 修改待支付待办事项的金额
     * @param id
     * @param amount
     */
    void updatePayAmount(@Param("id") Integer id,@Param("amount") Integer amount);

    /**
     * 删除待办事项
     * @param id
     */
    void delJxcWaitHandle(@Param("id") Integer id);
}