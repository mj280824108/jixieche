package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcExceptionAppeal;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcExceptionAppealMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcExceptionAppealService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcExceptionAppealService")
public class JxcExceptionAppealServiceImpl implements JxcExceptionAppealService {
       @Resource
       private JxcExceptionAppealMapper jxcExceptionAppealMapper;

       /**
        * 前端分页查询异常订单申诉表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcExceptionAppeal 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcExceptionAppeal> findByPageForFront(Integer pageNo, Integer pageSize, JxcExceptionAppeal jxcExceptionAppeal) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcExceptionAppeal> list = this.jxcExceptionAppealMapper.selectListByConditions(jxcExceptionAppeal);
              PageInfo<JxcExceptionAppeal> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加异常订单申诉表
        * @param  jxcExceptionAppeal
        * @return
        */
       @Override
       public ResponseMessage<JxcExceptionAppeal> addObj(JxcExceptionAppeal jxcExceptionAppeal) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcExceptionAppealMapper.insertSelective(jxcExceptionAppeal);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改异常订单申诉表
        * @param jxcExceptionAppeal
        * @return
        */
       @Override
       public ResponseMessage<JxcExceptionAppeal> modifyObj(JxcExceptionAppeal jxcExceptionAppeal) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcExceptionAppealMapper.updateByPrimaryKeySelective(jxcExceptionAppeal);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询异常订单申诉表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcExceptionAppeal> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcExceptionAppeal model=this.jxcExceptionAppealMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}