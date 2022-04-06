package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcPlatformFeedback;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcPlatformFeedbackMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPlatformFeedbackService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcPlatformFeedbackService")
public class JxcPlatformFeedbackServiceImpl implements JxcPlatformFeedbackService {
       @Resource
       private JxcPlatformFeedbackMapper jxcPlatformFeedbackMapper;

       /**
        * 前端分页查询平台反馈信息表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcPlatformFeedback 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformFeedback> findByPageForFront(Integer pageNo, Integer pageSize, JxcPlatformFeedback jxcPlatformFeedback) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPlatformFeedback> list = this.jxcPlatformFeedbackMapper.selectListByConditions(jxcPlatformFeedback);
              PageInfo<JxcPlatformFeedback> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加平台反馈信息表
        * @param  jxcPlatformFeedback
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformFeedback> addObj(JxcPlatformFeedback jxcPlatformFeedback) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatformFeedbackMapper.insertSelective(jxcPlatformFeedback);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改平台反馈信息表
        * @param jxcPlatformFeedback
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformFeedback> modifyObj(JxcPlatformFeedback jxcPlatformFeedback) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatformFeedbackMapper.updateByPrimaryKeySelective(jxcPlatformFeedback);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询平台反馈信息表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatformFeedback> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPlatformFeedback model=this.jxcPlatformFeedbackMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}