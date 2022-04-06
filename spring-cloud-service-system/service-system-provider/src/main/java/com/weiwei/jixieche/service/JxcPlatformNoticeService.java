package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcPlatformNotice;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcPlatformNoticeService extends BaseService<JxcPlatformNotice> {
       /**
     * 前端分页查询平台公告信息表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcPlatformNotice 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcPlatformNotice jxcPlatformNotice);
}