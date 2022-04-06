package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcManagerRefTenantry;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcManagerRefTenantryMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcManagerRefTenantryService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.TenManagerInfoVo;
import org.springframework.stereotype.Service;

@Service("jxcManagerRefTenantryService")
public class JxcManagerRefTenantryServiceImpl implements JxcManagerRefTenantryService {
       @Resource
       private JxcManagerRefTenantryMapper jxcManagerRefTenantryMapper;

       /**
        * 前端分页查询承租方管理员与承租方关联表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcManagerRefTenantry 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcManagerRefTenantry> findByPageForFront(Integer pageNo, Integer pageSize, JxcManagerRefTenantry jxcManagerRefTenantry) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              jxcManagerRefTenantry.setDisabled("1");
              List<JxcManagerRefTenantry> list = this.jxcManagerRefTenantryMapper.selectListByConditions(jxcManagerRefTenantry);
              PageInfo<JxcManagerRefTenantry> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加承租方管理员与承租方关联表
        * @param  jxcManagerRefTenantry
        * @return
        */
       @Override
       public ResponseMessage<JxcManagerRefTenantry> addObj(JxcManagerRefTenantry jxcManagerRefTenantry) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcManagerRefTenantryMapper.insertSelective(jxcManagerRefTenantry);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改承租方管理员与承租方关联表
        * @param jxcManagerRefTenantry
        * @return
        */
       @Override
       public ResponseMessage<JxcManagerRefTenantry> modifyObj(JxcManagerRefTenantry jxcManagerRefTenantry) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcManagerRefTenantryMapper.updateByPrimaryKeySelective(jxcManagerRefTenantry);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询承租方管理员与承租方关联表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcManagerRefTenantry> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcManagerRefTenantry model=this.jxcManagerRefTenantryMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询承租方管理员信息
        * @param tenManagerInfoVo
        * @return
        */
       @Override
       public ResponseMessage<JxcManagerRefTenantry> queryTenManagerInfo(TenManagerInfoVo tenManagerInfoVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(tenManagerInfoVo.getTenManagerId()== null){
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("承租方管理员id不能为空");
                     return result;
              }
              tenManagerInfoVo = this.jxcManagerRefTenantryMapper.queryTenManagerInfo(tenManagerInfoVo.getTenManagerId());
              result.setData(tenManagerInfoVo);
              return result;
       }
}