package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.bean.JxcPushRecord;
import com.weiwei.jixieche.mapper.JxcPushRecordMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPushRecordService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.JpushRecordUpdateVo;
import com.weiwei.jixieche.vo.PushRecordTypeUnReadVo;
import org.springframework.stereotype.Service;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
@Service("jxcPushRecordService")
public class JxcPushRecordServiceImpl implements JxcPushRecordService {
       @Resource
       private JxcPushRecordMapper jxcPushRecordMapper;

       /**
        * 前端分页查询消息推送记录表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcPushRecord 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcPushRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcPushRecord jxcPushRecord) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPushRecord> list = this.jxcPushRecordMapper.selectListByConditions(jxcPushRecord);
              PageInfo<JxcPushRecord> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加消息推送记录表
        * @param t
        * @return
        */
       @Override
       public ResponseMessage<JxcPushRecord> addObj(JxcPushRecord t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushRecordMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改消息推送记录表
        * @param t
        * @return
        */
       @Override
       public ResponseMessage<JxcPushRecord> modifyObj(JxcPushRecord t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushRecordMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询消息推送记录表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcPushRecord> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPushRecord model=this.jxcPushRecordMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询用户未读消息类型列表
        * @param pushRecordTypeUnReadVo
        * @return
        */
       @Override
       public ResponseMessage<PushRecordTypeUnReadVo> queryUnReadPushRecordType(PushRecordTypeUnReadVo pushRecordTypeUnReadVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<PushRecordTypeUnReadVo> list = this.jxcPushRecordMapper.queryUnReadPushRecordType(pushRecordTypeUnReadVo);
              if(!CollectionUtils.isEmpty(list)){
                     result.setData(list);
                     return result;
              }else{
                     result.setData(null);
                     return result;
              }
       }

       /**
        * 批量修改用户消息记录读取状态
        * @param jpushRecordUpdateVo
        * @return
        */
       @Override
       public ResponseMessage updateBatchPushRecordStatus(JpushRecordUpdateVo jpushRecordUpdateVo) {
           ResponseMessage result = ResponseMessageFactory.newInstance();
           this.jxcPushRecordMapper.updateBatchPushRecordStatus(jpushRecordUpdateVo.getRecordIdList());
           return result;
       }

}