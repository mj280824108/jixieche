package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcUserRole;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcUserRoleMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcUserRoleService;
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
@Service("jxcUserRoleService")
public class JxcUserRoleServiceImpl implements JxcUserRoleService {
       @Resource
       private JxcUserRoleMapper jxcUserRoleMapper;

       /**
        * 前端分页查询用户角色表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcUserRole 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcUserRole> findByPageForFront(Integer pageNo, Integer pageSize, JxcUserRole jxcUserRole) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcUserRole> list = this.jxcUserRoleMapper.selectListByConditions(jxcUserRole);
              PageInfo<JxcUserRole> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加用户角色表
        * @param  jxcUserRole
        * @return
        */
       @Override
       public ResponseMessage<JxcUserRole> addObj(JxcUserRole jxcUserRole) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcUserRoleMapper.insertSelective(jxcUserRole);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改用户角色表
        * @param jxcUserRole
        * @return
        */
       @Override
       public ResponseMessage<JxcUserRole> modifyObj(JxcUserRole jxcUserRole) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcUserRoleMapper.updateByPrimaryKeySelective(jxcUserRole);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户角色表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcUserRole> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcUserRole model=this.jxcUserRoleMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}