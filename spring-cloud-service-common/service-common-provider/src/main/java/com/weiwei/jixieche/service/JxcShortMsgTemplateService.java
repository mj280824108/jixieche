package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcShortMsgTemplate;
import com.weiwei.jixieche.rabbit.JxcShortMsgVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import com.weiwei.jixieche.vo.ShortMsgTemplate;
import com.weiwei.jixieche.vo.ShortMsgTemplateVo;
import com.weiwei.jixieche.vo.ShortMsgTestVo;

import java.util.Map;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcShortMsgTemplateService extends BaseService<JxcShortMsgTemplate> {
       /**
     * 前端分页查询短信模板
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcShortMsgTemplate 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcShortMsgTemplate jxcShortMsgTemplate);

       /**
        * 初始化短信模板
        */
       void initShortMsgTemplateDatas();

       /**
        * 获取消息模板集合
        *
        * @return
        */
       Map<Integer, ShortMsgTemplate> getTemplateList();

       /**
        * 根据id获取消息模板
        *
        * @param msgType
        * @return
        */
       ShortMsgTemplate getTemplate(int msgType);

       /**
        * 根据短信模板发送短信
        */
       ResponseMessage sendShortMsg(ShortMsgTemplateVo shortMsgTemplateVo) throws  Exception;

       /**
        * 发送短信验证码(测试所用)
        * @return
        */
       ResponseMessage sendShortMsgCodeTest(ShortMsgTestVo shortMsgTestVo);

       /**
        * 阿里云发送短信
        * @param aliShortMsgVo
        * @return
        */
       ResponseMessage aliSendShortMsg(AliShortMsgVo aliShortMsgVo);
}