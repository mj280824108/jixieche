package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcClockInOutPair;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcClockInOutPairMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockInOutPairService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcClockInOutPairService")
public class JxcClockInOutPairServiceImpl implements JxcClockInOutPairService {
       @Resource
       private JxcClockInOutPairMapper jxcClockInOutPairMapper;

       /**
        * 前端分页查询司机台班打卡配对表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcClockInOutPair 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcClockInOutPair> findByPageForFront(Integer pageNo, Integer pageSize, JxcClockInOutPair jxcClockInOutPair) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcClockInOutPair> list = this.jxcClockInOutPairMapper.selectListByConditions(jxcClockInOutPair);
              PageInfo<JxcClockInOutPair> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加司机台班打卡配对表
        * @param  jxcClockInOutPair
        * @return
        */
       @Override
       public ResponseMessage<JxcClockInOutPair> addObj(JxcClockInOutPair jxcClockInOutPair) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcClockInOutPairMapper.insertSelective(jxcClockInOutPair);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改司机台班打卡配对表
        * @param jxcClockInOutPair
        * @return
        */
       @Override
       public ResponseMessage<JxcClockInOutPair> modifyObj(JxcClockInOutPair jxcClockInOutPair) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcClockInOutPairMapper.updateByPrimaryKeySelective(jxcClockInOutPair);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询司机台班打卡配对表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcClockInOutPair> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcClockInOutPair model=this.jxcClockInOutPairMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }


}