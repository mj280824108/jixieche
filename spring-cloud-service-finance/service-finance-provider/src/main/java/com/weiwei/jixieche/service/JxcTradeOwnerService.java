package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.*;

public interface JxcTradeOwnerService extends BaseService<JxcTradeOwner> {
       /**
     * 前端分页查询机主交易流水表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcTradeOwner 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeOwner jxcTradeOwner);

       /**
        * 通过ID查询机主交易流水表
        * @param id
        * @return
        */
       ResponseMessage<JxcTradeOwner> queryObjById(Long id);

       /**
        * 机主查询我的钱包
        * @param ownerWalletVo
        * @return
        */
       ResponseMessage<OwnerWalletVo> queryOwnerWallet(AuthorizationUser user,OwnerWalletVo ownerWalletVo);

       /**
        * 机主交易明细记录
        * @param ownerTradeRecordVo
        * @return
        */
       ResponseMessage queryOwnerTradeRecord(AuthorizationUser user,OwnerTradeRecordListVo ownerTradeRecordVo);

       /**
        * 查询机主月份收入支出总金额
        * @param ownerTradePayVo
        * @return
        */
       ResponseMessage queryOwnerTotalPay(OwnerTradePayVo ownerTradePayVo);

}