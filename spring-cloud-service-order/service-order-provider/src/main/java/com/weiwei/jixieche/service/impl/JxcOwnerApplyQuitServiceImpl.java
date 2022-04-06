package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcOwnerApplyQuit;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcOwnerApplyQuitMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcOwnerApplyQuitService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcOwnerApplyQuitService")
public class JxcOwnerApplyQuitServiceImpl implements JxcOwnerApplyQuitService {
       @Resource
       private JxcOwnerApplyQuitMapper jxcOwnerApplyQuitMapper;

       /**
        * 添加jxc_owner_apply_quit
        * @param  jxcOwnerApplyQuit
        * @return
        */
       @Override
       public ResponseMessage<JxcOwnerApplyQuit> addObj(JxcOwnerApplyQuit jxcOwnerApplyQuit) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcOwnerApplyQuitMapper.insertSelective(jxcOwnerApplyQuit);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改jxc_owner_apply_quit
        * @param jxcOwnerApplyQuit
        * @return
        */
       @Override
       public ResponseMessage<JxcOwnerApplyQuit> modifyObj(JxcOwnerApplyQuit jxcOwnerApplyQuit) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcOwnerApplyQuitMapper.updateByPrimaryKeySelective(jxcOwnerApplyQuit);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询jxc_owner_apply_quit
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcOwnerApplyQuit> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcOwnerApplyQuit model=this.jxcOwnerApplyQuitMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}