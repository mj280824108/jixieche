package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcDriverHandover;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcDriverHandoverMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcDriverHandoverService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("jxcDriverHandoverService")
public class JxcDriverHandoverServiceImpl implements JxcDriverHandoverService {
       @Resource
       private JxcDriverHandoverMapper jxcDriverHandoverMapper;

       /**
        * 添加司机行程中途交接存储表
        * @param  jxcDriverHandover
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverHandover> addObj(JxcDriverHandover jxcDriverHandover) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcDriverHandoverMapper.insertSelective(jxcDriverHandover);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改司机行程中途交接存储表
        * @param jxcDriverHandover
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverHandover> modifyObj(JxcDriverHandover jxcDriverHandover) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcDriverHandoverMapper.updateByPrimaryKeySelective(jxcDriverHandover);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询司机行程中途交接存储表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverHandover> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcDriverHandover model=this.jxcDriverHandoverMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}