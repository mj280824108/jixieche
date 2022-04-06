package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcPlatDescription;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcPlatDescriptionMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPlatDescriptionService;
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
@Service("jxcPlatDescriptionService")
public class JxcPlatDescriptionServiceImpl implements JxcPlatDescriptionService {
       @Resource
       private JxcPlatDescriptionMapper jxcPlatDescriptionMapper;

       /**
        * 前端分页查询平台说明
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcPlatDescription 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatDescription> findByPageForFront(Integer pageNo, Integer pageSize, JxcPlatDescription jxcPlatDescription) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPlatDescription> list = this.jxcPlatDescriptionMapper.selectListByConditions(jxcPlatDescription);
              PageInfo<JxcPlatDescription> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加平台说明
        * @param  jxcPlatDescription
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatDescription> addObj(JxcPlatDescription jxcPlatDescription) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatDescriptionMapper.insertSelective(jxcPlatDescription);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改平台说明
        * @param jxcPlatDescription
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatDescription> modifyObj(JxcPlatDescription jxcPlatDescription) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPlatDescriptionMapper.updateByPrimaryKeySelective(jxcPlatDescription);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询平台说明
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcPlatDescription> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPlatDescription model=this.jxcPlatDescriptionMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}