package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcTradeTenantryRef;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcTradeTenantryRefMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcTradeTenantryRefService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcTradeTenantryRefService")
public class JxcTradeTenantryRefServiceImpl implements JxcTradeTenantryRefService {
       @Resource
       private JxcTradeTenantryRefMapper jxcTradeTenantryRefMapper;

       /**
        * 前端分页查询承租方支付趟数关联表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcTradeTenantryRef 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantryRef> findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeTenantryRef jxcTradeTenantryRef) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcTradeTenantryRef> list = this.jxcTradeTenantryRefMapper.selectListByConditions(jxcTradeTenantryRef);
              PageInfo<JxcTradeTenantryRef> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加承租方支付趟数关联表
        * @param  jxcTradeTenantryRef
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantryRef> addObj(JxcTradeTenantryRef jxcTradeTenantryRef) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeTenantryRefMapper.insertSelective(jxcTradeTenantryRef);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改承租方支付趟数关联表
        * @param jxcTradeTenantryRef
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantryRef> modifyObj(JxcTradeTenantryRef jxcTradeTenantryRef) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeTenantryRefMapper.updateByPrimaryKeySelective(jxcTradeTenantryRef);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询承租方支付趟数关联表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantryRef> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcTradeTenantryRef model=this.jxcTradeTenantryRefMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}