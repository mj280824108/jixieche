package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcCapacity;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcCapacityMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcCapacityService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import org.springframework.stereotype.Service;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcCapacityService")
public class JxcCapacityServiceImpl implements JxcCapacityService {
       @Resource
       private JxcCapacityMapper jxcCapacityMapper;

       /**
        * 前端分页查询方量字典表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcCapacity 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcCapacity> findByPageForFront(Integer pageNo, Integer pageSize, JxcCapacity jxcCapacity) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcCapacity> list = this.jxcCapacityMapper.selectListByConditions(jxcCapacity);
              PageInfo<JxcCapacity> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加方量字典表
        * @param  jxcCapacity
        * @return
        */
       @Override
       public ResponseMessage<JxcCapacity> addObj(JxcCapacity jxcCapacity) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCapacityMapper.insertSelective(jxcCapacity);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改方量字典表
        * @param jxcCapacity
        * @return
        */
       @Override
       public ResponseMessage<JxcCapacity> modifyObj(JxcCapacity jxcCapacity) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCapacityMapper.updateByPrimaryKeySelective(jxcCapacity);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询方量字典表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcCapacity> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcCapacity model=this.jxcCapacityMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 根据城市id查询方量
        * @param jxcCapacity
        * @return
        */
       @Override
       public ResponseMessage<JxcCapacity> queryByCityId(JxcCapacity jxcCapacity) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<JxcCapacity> list = this.jxcCapacityMapper.selectListByConditions(jxcCapacity);
              if(!CollectionUtils.isEmpty(list)){
                     result.setData(list);
              }else{
                     result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
                     result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
                     return result;
              }
              return result;
       }
}