package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcClockInOutPair;
import com.weiwei.jixieche.response.ResponseMessage;

import java.util.ArrayList;

public interface JxcClockInOutPairService extends BaseService<JxcClockInOutPair> {
       /**
     * 前端分页查询司机台班打卡配对表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcClockInOutPair 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcClockInOutPair jxcClockInOutPair);

}