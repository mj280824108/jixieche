package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketPointRecord;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcMarketPointRecordService extends BaseService<JxcMarketPointRecord> {
       /**
     * 前端分页查询用户点赞记录表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketPointRecord 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketPointRecord jxcMarketPointRecord);

       /**
        *
        * @param user
        * @param jxcMarketPointRecord
        * @return
        */
       ResponseMessage<JxcMarketPointRecord> addObj(AuthorizationUser user,JxcMarketPointRecord jxcMarketPointRecord);

}