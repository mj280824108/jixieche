package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.bean.JxcShortMsgRecord;
import com.weiwei.jixieche.mapper.JxcShortMsgRecordMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcShortMsgRecordService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 2.0
 **/
@Service("jxcShortMsgRecordService")
public class JxcShortMsgRecordServiceImpl implements JxcShortMsgRecordService {
       @Resource
       private JxcShortMsgRecordMapper jxcShortMsgRecordMapper;

       //前端分页查询短信记录表
       @Override
       public ResponseMessage<JxcShortMsgRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcShortMsgRecord jxcShortMsgRecord) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcShortMsgRecord> list = this.jxcShortMsgRecordMapper.selectListByConditions(jxcShortMsgRecord);
              PageInfo<JxcShortMsgRecord> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       //添加短信记录表
       @Override
       public ResponseMessage<JxcShortMsgRecord> addObj(JxcShortMsgRecord t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcShortMsgRecordMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改短信记录表
       @Override
       public ResponseMessage<JxcShortMsgRecord> modifyObj(JxcShortMsgRecord t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcShortMsgRecordMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       //根据ID查询短信记录表
       @Override
       public ResponseMessage<JxcShortMsgRecord> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcShortMsgRecord model=this.jxcShortMsgRecordMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}