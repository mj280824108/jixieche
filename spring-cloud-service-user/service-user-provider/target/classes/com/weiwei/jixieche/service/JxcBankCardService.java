package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcBankCard;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcBankCardService extends BaseService<JxcBankCard> {
       /**
     * 前端分页查询用户银行卡
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcBankCard 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcBankCard jxcBankCard);

       /**
        * 查询用户银行卡列表信息(不分页)
        * @param jxcBankCard
        * @return
        */
       ResponseMessage<JxcBankCard> queryBankCardList(JxcBankCard jxcBankCard);
}