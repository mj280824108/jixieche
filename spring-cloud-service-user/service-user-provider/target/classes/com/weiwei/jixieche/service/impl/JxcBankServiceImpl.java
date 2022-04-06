package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcBank;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcBankMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcBankService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Service("jxcBankService")
public class JxcBankServiceImpl implements JxcBankService {
       @Resource
       private JxcBankMapper jxcBankMapper;

       /**
        * 前端分页查询银行表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcBank 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcBank> findByPageForFront(Integer pageNo, Integer pageSize, JxcBank jxcBank) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcBank> list = this.jxcBankMapper.selectListByConditions(jxcBank);
              PageInfo<JxcBank> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加银行表
        * @param  jxcBank
        * @return
        */
       @Override
       public ResponseMessage<JxcBank> addObj(JxcBank jxcBank) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcBankMapper.insertSelective(jxcBank);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改银行表
        * @param jxcBank
        * @return
        */
       @Override
       public ResponseMessage<JxcBank> modifyObj(JxcBank jxcBank) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcBankMapper.updateByPrimaryKeySelective(jxcBank);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询银行表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcBank> queryObjById(int id) {
              return null;
       }

       /**
        * 查询银行信息
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcBank> queryById(Long id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              /*JxcBank model=this.jxcBankMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);*/
              return result;
       }
}