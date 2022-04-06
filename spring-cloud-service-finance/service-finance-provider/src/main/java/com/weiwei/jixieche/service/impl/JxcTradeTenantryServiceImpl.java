package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcTradeTenantryService;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.TenPayAmountVo;
import com.weiwei.jixieche.vo.TenProjectInfoVo;
import com.weiwei.jixieche.vo.TenTradeRecordListVo;
import com.weiwei.jixieche.vo.TenTradeRecordVo;
import org.springframework.stereotype.Service;

@Service("jxcTradeTenantryService")
public class JxcTradeTenantryServiceImpl implements JxcTradeTenantryService {
       @Resource
       private JxcTradeTenantryMapper jxcTradeTenantryMapper;

       @Resource
       private JxcTradeOwnerMapper jxcTradeOwnerMapper;

       /**
        * 前端分页查询承租方交易流水表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcTradeTenantry 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantry> findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeTenantry jxcTradeTenantry) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcTradeTenantry> list = this.jxcTradeTenantryMapper.selectListByConditions(jxcTradeTenantry);
              PageInfo<JxcTradeTenantry> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加承租方交易流水表
        * @param  jxcTradeTenantry
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantry> addObj(JxcTradeTenantry jxcTradeTenantry) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeTenantryMapper.insertSelective(jxcTradeTenantry);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改承租方交易流水表
        * @param jxcTradeTenantry
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantry> modifyObj(JxcTradeTenantry jxcTradeTenantry) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeTenantryMapper.updateByPrimaryKeySelective(jxcTradeTenantry);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询承租方交易流水表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeTenantry> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcTradeTenantry model=this.jxcTradeTenantryMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查询承租方交易详情
        * @param tenTradeRecordListVo
        * @return
        */
       @Override
       public ResponseMessage queryTenTradeRecord(TenTradeRecordListVo tenTradeRecordListVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              //查询承租方交易详情
              TenTradeRecordVo tenTradeRecordVo = new TenTradeRecordVo();
              tenTradeRecordVo.setDateTime(tenTradeRecordListVo.getDateTime());
              tenTradeRecordVo.setUserId(tenTradeRecordListVo.getUserId());
              PageHelper.startPage(tenTradeRecordListVo.getPageNo(),tenTradeRecordListVo.getPageSize());
              PageHelper.orderBy("create_time DESC");
              List<TenTradeRecordVo> recordList =  this.jxcTradeTenantryMapper.queryTenTradeRecord(tenTradeRecordVo);
              recordList.forEach(recordVo -> {
                     if(recordVo.getTenantryOrderId() != null){
                            TenProjectInfoVo tenProjectInfoVo = new TenProjectInfoVo();
                            tenProjectInfoVo.setTenOrderId(recordVo.getTenantryOrderId());
                            tenProjectInfoVo = this.jxcTradeOwnerMapper.queryTenProjectInfo(tenProjectInfoVo);
                            recordVo.setProjectName(tenProjectInfoVo.getProjectName());
                     }
                     recordVo.setTradeAmount("-"+recordVo.getTradeAmount());
              });
              tenTradeRecordListVo.setTradeRecordList(recordList);
              //查询交易列表页码数
              PageInfo<TenTradeRecordVo> page = new PageInfo<>(recordList);
              tenTradeRecordListVo.setPageCount(new PageUtils<>(page).getPageViewDatatable().getPageCount());
              //查询承租方总支出
              TenPayAmountVo tenPayAmountVo = new TenPayAmountVo();
              tenPayAmountVo.setUserId(tenTradeRecordListVo.getUserId());
              tenPayAmountVo = this.jxcTradeTenantryMapper.queryTotalPayAmount(tenPayAmountVo);
              if(tenPayAmountVo != null){
                     tenTradeRecordListVo.setPayAmount(tenPayAmountVo.getTotalPayAmount());
                     //查询承租方月份支出
                     tenPayAmountVo.setDateTime(tenTradeRecordListVo.getDateTime());
                     tenPayAmountVo.setUserId(tenTradeRecordListVo.getUserId());
                     tenPayAmountVo = this.jxcTradeTenantryMapper.queryTotalPayAmount(tenPayAmountVo);
                     if(tenPayAmountVo != null){
                            tenTradeRecordListVo.setMonthPayAmount(tenPayAmountVo.getTotalPayAmount());
                     }
              }
              result.setData(tenTradeRecordListVo);
              return result;
       }
}