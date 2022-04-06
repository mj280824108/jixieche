package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcPushRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JpushRecordUpdateVo;
import com.weiwei.jixieche.vo.PushRecordTypeUnReadVo;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
public interface JxcPushRecordService extends BaseService<JxcPushRecord> {
       /**
     * 前端分页查询消息推送记录表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcPushRecord 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcPushRecord jxcPushRecord);

       /**
        * 查询用户未读消息类型列表
        * @param pushRecordTypeUnReadVo
        * @return
        */
       ResponseMessage<PushRecordTypeUnReadVo> queryUnReadPushRecordType(PushRecordTypeUnReadVo pushRecordTypeUnReadVo);

       /**
        * 批量修改消息读取状态
        * @param jpushRecordUpdateVo
        * @return
        */
       ResponseMessage updateBatchPushRecordStatus(JpushRecordUpdateVo jpushRecordUpdateVo);
}