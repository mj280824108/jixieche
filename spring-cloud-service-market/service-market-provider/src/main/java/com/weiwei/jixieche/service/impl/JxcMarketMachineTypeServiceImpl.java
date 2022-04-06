package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcMarketMachineTypeMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketMachineTypeService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.MachineTypeChildVo;
import com.weiwei.jixieche.vo.MachineTypeVo;
import org.springframework.stereotype.Service;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketMachineTypeService")
public class JxcMarketMachineTypeServiceImpl implements JxcMarketMachineTypeService {
       @Resource
       private JxcMarketMachineTypeMapper jxcMarketMachineTypeMapper;

       /**
        * 前端分页查询市场机械类型字典表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketMachineType 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketMachineType> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketMachineType jxcMarketMachineType) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketMachineType> list = this.jxcMarketMachineTypeMapper.selectListByConditions(jxcMarketMachineType);
              PageInfo<JxcMarketMachineType> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询市场资源类型表(不分页)
        * @param jxcMarketMachineType
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketMachineType> query(JxcMarketMachineType jxcMarketMachineType) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<JxcMarketMachineType> list = this.jxcMarketMachineTypeMapper.selectListByConditions(jxcMarketMachineType);
              if(CollectionUtils.isEmpty(list)){
                     result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
                     result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
                     return result;
              }else{
                     result.setData(list);
              }
              return result;
       }

       /**
        * 添加市场机械类型字典表
        * @param  jxcMarketMachineType
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketMachineType> addObj(JxcMarketMachineType jxcMarketMachineType) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketMachineTypeMapper.insertSelective(jxcMarketMachineType);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改市场机械类型字典表
        * @param jxcMarketMachineType
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketMachineType> modifyObj(JxcMarketMachineType jxcMarketMachineType) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketMachineTypeMapper.updateByPrimaryKeySelective(jxcMarketMachineType);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询市场机械类型字典表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketMachineType> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketMachineType model=this.jxcMarketMachineTypeMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询父类品牌
        * @param machineTypeVo
        * @return
        */
       @Override
       public ResponseMessage queryMachineType(MachineTypeVo machineTypeVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              MachineTypeChildVo machineTypeChildVo = new MachineTypeChildVo();
              machineTypeChildVo.setPId(machineTypeVo.getPId());
              List<MachineTypeChildVo> list = this.jxcMarketMachineTypeMapper.queryChildMachineType(machineTypeChildVo);
              result.setData(list);
              return result;
       }

       /**
        * 查找所有的父类机械
        * @return
        */
       @Override
       public ResponseMessage queryParentMachineType() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              MachineTypeVo machineTypeVo = new MachineTypeVo();
              List<MachineTypeVo> list = this.jxcMarketMachineTypeMapper.queryMachineType(machineTypeVo);
              result.setData(list);
              return result;
       }
}