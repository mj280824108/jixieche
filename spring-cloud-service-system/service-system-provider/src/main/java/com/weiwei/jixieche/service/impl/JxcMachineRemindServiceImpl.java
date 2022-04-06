package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMachineRemind;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMachineRemindMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMachineRemindService;
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
@Service("jxcMachineRemindService")
public class JxcMachineRemindServiceImpl implements JxcMachineRemindService {
       @Resource
       private JxcMachineRemindMapper jxcMachineRemindMapper;

       /**
        * 前端分页查询机械提醒类型
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMachineRemind 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRemind> findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRemind jxcMachineRemind) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMachineRemind> list = this.jxcMachineRemindMapper.selectListByConditions(jxcMachineRemind);
              PageInfo<JxcMachineRemind> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加机械提醒类型
        * @param  jxcMachineRemind
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRemind> addObj(JxcMachineRemind jxcMachineRemind) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMachineRemindMapper.insertSelective(jxcMachineRemind);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改机械提醒类型
        * @param jxcMachineRemind
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRemind> modifyObj(JxcMachineRemind jxcMachineRemind) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMachineRemindMapper.updateByPrimaryKeySelective(jxcMachineRemind);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询机械提醒类型
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRemind> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMachineRemind model=this.jxcMachineRemindMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}