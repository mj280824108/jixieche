package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.bean.JxcPushDeviceToken;
import com.weiwei.jixieche.mapper.JxcPushDeviceTokenMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcPushDeviceTokenService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcPushDeviceTokenService")
public class JxcPushDeviceTokenServiceImpl implements JxcPushDeviceTokenService {
       @Resource
       private JxcPushDeviceTokenMapper jxcPushDeviceTokenMapper;

       //前端分页查询推送设备token表
       @Override
       public ResponseMessage<JxcPushDeviceToken> findByPageForFront(Integer pageNo, Integer pageSize, JxcPushDeviceToken jxcPushDeviceToken) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcPushDeviceToken> list = this.jxcPushDeviceTokenMapper.selectListByConditions(jxcPushDeviceToken);
              PageInfo<JxcPushDeviceToken> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       //添加推送设备token表
       @Override
       public ResponseMessage<JxcPushDeviceToken> addObj(JxcPushDeviceToken t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushDeviceTokenMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改推送设备token表
       @Override
       public ResponseMessage<JxcPushDeviceToken> modifyObj(JxcPushDeviceToken t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcPushDeviceTokenMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       //根据ID查询推送设备token表
       @Override
       public ResponseMessage<JxcPushDeviceToken> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcPushDeviceToken model=this.jxcPushDeviceTokenMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}