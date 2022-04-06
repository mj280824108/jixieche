package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcRoleResource;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcRoleResourceMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcRoleResourceService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Service("jxcRoleResourceService")
public class JxcRoleResourceServiceImpl implements JxcRoleResourceService {
       @Resource
       private JxcRoleResourceMapper jxcRoleResourceMapper;

       /**
        * 前端分页查询用户表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcRoleResource 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcRoleResource> findByPageForFront(Integer pageNo, Integer pageSize, JxcRoleResource jxcRoleResource) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcRoleResource> list = this.jxcRoleResourceMapper.selectListByConditions(jxcRoleResource);
              PageInfo<JxcRoleResource> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加用户表
        * @param  jxcRoleResource
        * @return
        */
       @Override
       public ResponseMessage<JxcRoleResource> addObj(JxcRoleResource jxcRoleResource) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcRoleResourceMapper.insertSelective(jxcRoleResource);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改用户表
        * @param jxcRoleResource
        * @return
        */
       @Override
       public ResponseMessage<JxcRoleResource> modifyObj(JxcRoleResource jxcRoleResource) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcRoleResourceMapper.updateByPrimaryKeySelective(jxcRoleResource);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcRoleResource> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcRoleResource model=this.jxcRoleResourceMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}