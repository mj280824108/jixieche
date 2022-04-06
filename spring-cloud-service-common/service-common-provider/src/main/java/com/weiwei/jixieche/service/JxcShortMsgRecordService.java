package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcShortMsgRecord;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcShortMsgRecordService extends BaseService<JxcShortMsgRecord> {
       /**
     * 前端分页查询短信记录表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcShortMsgRecord 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcShortMsgRecord jxcShortMsgRecord);
}