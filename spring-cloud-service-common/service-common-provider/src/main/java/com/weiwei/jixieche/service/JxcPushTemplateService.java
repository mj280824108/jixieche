package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcPushTemplate;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcPushTemplateService extends BaseService<JxcPushTemplate> {
       /**
     * 前端分页查询消息推送模板
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcPushTemplate 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcPushTemplate jxcPushTemplate);

       /**
        * 初始化加载推送模板数据
        */
       void initPushTemplateData();

}