package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.bean.JxcPushTemplate;
import com.weiwei.jixieche.mapper.JxcPushTemplateMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPushTemplateService;
import com.weiwei.jixieche.utils.PushTempateUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.PushTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
@Service("jxcPushTemplateService")
public class JxcPushTemplateServiceImpl implements JxcPushTemplateService {
       @Resource
       private JxcPushTemplateMapper jxcPushTemplateMapper;

       @Autowired
       private PushTempateUtils pushTempateUtils;



       //前端分页查询消息推送模板
       @Override
       public ResponseMessage<JxcPushTemplate> findByPageForFront(Integer pageNo, Integer pageSize, JxcPushTemplate jxcPushTemplate) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPushTemplate> list = this.jxcPushTemplateMapper.selectListByConditions(jxcPushTemplate);
              PageInfo<JxcPushTemplate> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       //添加消息推送模板
       @Override
       public ResponseMessage<JxcPushTemplate> addObj(JxcPushTemplate t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushTemplateMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改消息推送模板
       @Override
       public ResponseMessage<JxcPushTemplate> modifyObj(JxcPushTemplate t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushTemplateMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       //根据ID查询消息推送模板
       @Override
       public ResponseMessage<JxcPushTemplate> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPushTemplate model=this.jxcPushTemplateMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       //初始化加载推送模板数据
       @Override
       public void initPushTemplateData() {
              //从数据库读取所有站内信模版
              List<PushTemplateVo> pushTemplates = this.jxcPushTemplateMapper.selectPushTemplateList();
              Pattern pattern = Pattern.compile("\\$\\((.*?)\\)");
              Matcher matcher = null;
              String field = null;
              for(PushTemplateVo template:pushTemplates){
                     //按正则表达式$(xxxxx)拆出所有的动态字段
                     matcher = pattern.matcher(template.getTemplate());
                     while(matcher.find()){
                            field = matcher.group(1);
                            template.getFields().add(field);
                     }
                     pushTempateUtils.pushTemplateVoMap.put(template.getTypeId(), template);
              }
              System.out.println("------------当前加载模版------------");
       }
}