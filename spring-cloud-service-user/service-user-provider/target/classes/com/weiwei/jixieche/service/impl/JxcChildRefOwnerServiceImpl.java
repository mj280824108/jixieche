package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcChildRefOwner;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcChildRefOwnerMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcChildRefOwnerService;
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
@Service("jxcChildRefOwnerService")
public class JxcChildRefOwnerServiceImpl implements JxcChildRefOwnerService {
       @Resource
       private JxcChildRefOwnerMapper jxcChildRefOwnerMapper;

       /**
        * 前端分页查询子账号与机主关联关系表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcChildRefOwner 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcChildRefOwner> findByPageForFront(Integer pageNo, Integer pageSize, JxcChildRefOwner jxcChildRefOwner) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcChildRefOwner> list = this.jxcChildRefOwnerMapper.selectListByConditions(jxcChildRefOwner);
              PageInfo<JxcChildRefOwner> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加子账号与机主关联关系表
        * @param  jxcChildRefOwner
        * @return
        */
       @Override
       public ResponseMessage<JxcChildRefOwner> addObj(JxcChildRefOwner jxcChildRefOwner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcChildRefOwnerMapper.insertSelective(jxcChildRefOwner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改子账号与机主关联关系表
        * @param jxcChildRefOwner
        * @return
        */
       @Override
       public ResponseMessage<JxcChildRefOwner> modifyObj(JxcChildRefOwner jxcChildRefOwner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcChildRefOwnerMapper.updateByPrimaryKeySelective(jxcChildRefOwner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询子账号与机主关联关系表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcChildRefOwner> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcChildRefOwner model=this.jxcChildRefOwnerMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}