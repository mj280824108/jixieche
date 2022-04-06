package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcPlatDescription;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcPlatDescriptionService extends BaseService<JxcPlatDescription> {
       /**
     * 前端分页查询平台说明
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcPlatDescription 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcPlatDescription jxcPlatDescription);
}