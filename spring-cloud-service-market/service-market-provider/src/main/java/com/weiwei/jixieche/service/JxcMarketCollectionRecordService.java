package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketCollectionRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UserCollectionInfoVo;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketCollectionRecordService extends BaseService<JxcMarketCollectionRecord> {
       /**
     * 前端分页查询市场收藏记录
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketCollectionRecord 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketCollectionRecord jxcMarketCollectionRecord);

       /**
        * 分页查询用户收藏资讯/机械/店铺信息
        * @param userCollectionInfoVo
        * @return
        */
       ResponseMessage<JxcMarketCollectionRecord> queryCollectionInfo(UserCollectionInfoVo userCollectionInfoVo);

       /**
        * 用户收藏/取消收藏资讯/机械/店铺
        * @param jxcMarketCollectionRecord
        * @return
        */
       ResponseMessage<JxcMarketCollectionRecord> editUserCollectionRecord(JxcMarketCollectionRecord jxcMarketCollectionRecord);
}