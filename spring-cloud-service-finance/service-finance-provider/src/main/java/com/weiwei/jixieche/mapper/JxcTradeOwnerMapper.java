package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface JxcTradeOwnerMapper extends BaseMapper<JxcTradeOwner> {

    /**
     * 查询机主冻结金额
     * @param userId
     * @return
     */
    BigDecimal queryOwnerLockAmount(@Param("userId")Integer userId);

    /**
     * 查询机主解冻(可用)金额
     * @param userId
     * @return
     */
    BigDecimal queryOwnerUnLockAmount(@Param("userId")Integer userId);

    /**
     * 用户收入金额
     * @param userTradeAmountVo
     * @return
     */
    UserTradeAmountVo queryUserTradeAmount(UserTradeAmountVo userTradeAmountVo);

    /**
     * 查询用户提现未处理金额
     */
    Integer queryUnDoneAmountByUserId(@Param("userId")Integer userId);

    /**
     * 根据id查询机主交易记录
     * @param id
     * @return
     */
    JxcTradeOwner selectByPrimaryKey(Long id);

    /**
     * 根据台班打卡id修改台班支付状态
     */
    Integer updatePayStatusByClockId(@Param("clockId")Long clockId);

    /**
     * 机主交易明细记录
     */
    List<OwnerTradeRecordVo> queryOwnerTradeRecord(OwnerTradeRecordVo ownerTradePayVo);

    /**
     * 查询机主月份收入支出总金额
     * @param ownerTradePayVo
     * @return
     */
    OwnerTradePayVo queryOwnerTotalPay(OwnerTradePayVo ownerTradePayVo);

    /**
     * 根据承租方订单查询承租方项目信息
     */
    TenProjectInfoVo queryTenProjectInfo(TenProjectInfoVo tenProjectInfoVo);

    /**
     * 查询机主付款收款人信息
     * @param userPayeeInfoVo
     * @return
     */
    UserPayeeInfoVo queryPayeeInfo(UserPayeeInfoVo userPayeeInfoVo);

    /**
     * 根据银行卡id查询银行卡信息
     */
    UserBankCardInfoVo queryBankCard(UserBankCardInfoVo userBankCardInfoVo);

    /**
     * 根据userId查询提现人的基本信息
     * @param userId
     * @return
     */
    CashApplyUserInfoVo queryCashApplyUserInfo(@Param("userId") Integer userId);

    /**
     * 查询机主支付台班信息
     * @param ownerPayClockVo
     */
    OwnerPayClockVo queryOwnerPayTeamAmount(OwnerPayClockVo ownerPayClockVo);

}