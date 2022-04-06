package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcUser;
import com.weiwei.jixieche.bean.JxcUserConfirm;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcUserConfirmMapper;
import com.weiwei.jixieche.mapper.JxcUserMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcUserConfirmService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import org.springframework.stereotype.Service;

@Service("jxcUserConfirmService")
public class JxcUserConfirmServiceImpl implements JxcUserConfirmService {
       @Resource
       private JxcUserConfirmMapper jxcUserConfirmMapper;

       @Resource
       private JxcUserMapper jxcUserMapper;

       /**
        * 前端分页查询用户认证信息表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcUserConfirm 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcUserConfirm> findByPageForFront(Integer pageNo, Integer pageSize, JxcUserConfirm jxcUserConfirm) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcUserConfirm> list = this.jxcUserConfirmMapper.selectListByConditions(jxcUserConfirm);
              PageInfo<JxcUserConfirm> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }


       /**
        * 添加用户认证信息表
        * @param  jxcUserConfirm
        * @return
        */
       @Override
       public ResponseMessage<JxcUserConfirm> addObj(JxcUserConfirm jxcUserConfirm) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(jxcUserConfirm.getUserId() != null){
                     JxcUser jxcUser =  this.jxcUserMapper.selectByPrimaryKey(jxcUserConfirm.getUserId());
                     jxcUserConfirm.setPhone(jxcUser.getPhone());
              }
              int res=this.jxcUserConfirmMapper.insertSelective(jxcUserConfirm);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改用户认证信息表
        * @param jxcUserConfirm
        * @return
        */
       @Override
       public ResponseMessage<JxcUserConfirm> modifyObj(JxcUserConfirm jxcUserConfirm) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcUserConfirmMapper.updateByPrimaryKeySelective(jxcUserConfirm);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户认证信息表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcUserConfirm> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcUserConfirm model=this.jxcUserConfirmMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

}