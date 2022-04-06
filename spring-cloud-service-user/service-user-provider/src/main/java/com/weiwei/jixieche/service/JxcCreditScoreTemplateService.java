package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcCreditScoreTemplate;
import com.weiwei.jixieche.response.ResponseMessage;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcCreditScoreTemplateService extends BaseService<JxcCreditScoreTemplate> {
       /**
     * 前端分页查询信用分模板表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcCreditScoreTemplate 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcCreditScoreTemplate jxcCreditScoreTemplate);

       /**
        * 获取拒绝规则
        *
        * @param roleType
        * @param cancelType
        * @return
        */
       int getRejectCondition(Integer roleType, Integer cancelType);
}