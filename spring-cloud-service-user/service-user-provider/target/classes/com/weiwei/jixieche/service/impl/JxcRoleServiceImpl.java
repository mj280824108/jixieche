package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcRole;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcRoleMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcRoleService;
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
@Service("jxcRoleService")
public class JxcRoleServiceImpl implements JxcRoleService {
       @Resource
       private JxcRoleMapper jxcRoleMapper;

       /**
        * 前端分页查询角色表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcRole 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcRole> findByPageForFront(Integer pageNo, Integer pageSize, JxcRole jxcRole) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcRole> list = this.jxcRoleMapper.selectListByConditions(jxcRole);
              PageInfo<JxcRole> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加角色表
        * @param  jxcRole
        * @return
        */
       @Override
       public ResponseMessage<JxcRole> addObj(JxcRole jxcRole) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcRoleMapper.insertSelective(jxcRole);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改角色表
        * @param jxcRole
        * @return
        */
       @Override
       public ResponseMessage<JxcRole> modifyObj(JxcRole jxcRole) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcRoleMapper.updateByPrimaryKeySelective(jxcRole);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询角色表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcRole> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcRole model=this.jxcRoleMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}