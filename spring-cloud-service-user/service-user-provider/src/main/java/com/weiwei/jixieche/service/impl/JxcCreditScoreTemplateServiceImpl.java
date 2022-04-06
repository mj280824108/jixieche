package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcCreditScoreTemplate;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.mapper.JxcCreditScoreTemplateMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcCreditScoreTemplateService;
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
@Service("jxcCreditScoreTemplateService")
public class JxcCreditScoreTemplateServiceImpl implements JxcCreditScoreTemplateService {
       @Resource
       private JxcCreditScoreTemplateMapper jxcCreditScoreTemplateMapper;

       /**
        * 前端分页查询信用分模板表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcCreditScoreTemplate 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreTemplate> findByPageForFront(Integer pageNo, Integer pageSize, JxcCreditScoreTemplate jxcCreditScoreTemplate) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcCreditScoreTemplate> list = this.jxcCreditScoreTemplateMapper.selectListByConditions(jxcCreditScoreTemplate);
              PageInfo<JxcCreditScoreTemplate> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加信用分模板表
        * @param  jxcCreditScoreTemplate
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreTemplate> addObj(JxcCreditScoreTemplate jxcCreditScoreTemplate) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCreditScoreTemplateMapper.insertSelective(jxcCreditScoreTemplate);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改信用分模板表
        * @param jxcCreditScoreTemplate
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreTemplate> modifyObj(JxcCreditScoreTemplate jxcCreditScoreTemplate) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCreditScoreTemplateMapper.updateByPrimaryKeySelective(jxcCreditScoreTemplate);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询信用分模板表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreTemplate> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcCreditScoreTemplate model=this.jxcCreditScoreTemplateMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 获取拒绝规则
        *
        * @param roleType
        * @param cancelType
        * @return
        */
       @Override
       public int getRejectCondition(Integer roleType,Integer cancelType) {
              JxcCreditScoreTemplate rule = null;
              if (roleType.equals(UserRoleContants.OWNER.getRoleId()) && cancelType.equals(1)) {
                     rule = jxcCreditScoreTemplateMapper.getEffectiveById(1);
              } else if (roleType.equals(UserRoleContants.TEN.getRoleId()) && cancelType.equals(4)) {
                     rule = jxcCreditScoreTemplateMapper.getEffectiveById(4);
              } else if (roleType.equals(UserRoleContants.DRIVER.getRoleId()) && cancelType.equals(3)) {
                     rule = jxcCreditScoreTemplateMapper.getEffectiveById(3);
              } else if (roleType.equals(UserRoleContants.OWNER.getRoleId()) && cancelType.equals(2)) {
                     rule = jxcCreditScoreTemplateMapper.getEffectiveById(2);
              }
              return rule == null ? -1 : rule.getCondition();
       }
}