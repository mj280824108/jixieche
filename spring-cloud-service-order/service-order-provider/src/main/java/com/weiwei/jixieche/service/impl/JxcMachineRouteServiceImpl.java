package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMachineRoute;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcMachineRouteMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMachineRouteService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.*;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;
import org.springframework.stereotype.Service;

@Service("jxcMachineRouteService")
public class JxcMachineRouteServiceImpl implements JxcMachineRouteService {
       @Resource
       private JxcMachineRouteMapper jxcMachineRouteMapper;

       /**
        * 前端分页查询机械行程表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMachineRoute 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRoute> findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRoute jxcMachineRoute) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMachineRoute> list = this.jxcMachineRouteMapper.selectListByConditions(jxcMachineRoute);
              PageInfo<JxcMachineRoute> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加机械行程表
        * @param  jxcMachineRoute
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRoute> addObj(JxcMachineRoute jxcMachineRoute) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMachineRouteMapper.insertSelective(jxcMachineRoute);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改机械行程表
        * @param jxcMachineRoute
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRoute> modifyObj(JxcMachineRoute jxcMachineRoute) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMachineRouteMapper.updateByPrimaryKeySelective(jxcMachineRoute);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询机械行程表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMachineRoute> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMachineRoute model=this.jxcMachineRouteMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 根据机械行程趟数集合查询趟数总金额
        * @param routeIdList
        * @return
        */
       @Override
       public ResponseMessage queryTotalAmount(List<Long> routeIdList) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              Integer totalAmount = this.jxcMachineRouteMapper.queryTotalAmount(routeIdList);
              if(totalAmount != null  && totalAmount >0){
                     result.setData(totalAmount);
              }else{
                     result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
                     result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
              }
              return result;
       }

       /**
        * 根据机械行程趟数集合批量修改机械行程支付状态
        * @param updateBatchRoutePayStatusVo
        * @return
        */
       @Override
       public void updateBatchPayStatus(UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo) {
              this.jxcMachineRouteMapper.updateBatchPayStatus(updateBatchRoutePayStatusVo);
       }

       /**
        * 机主查看已接单的行程的日期和异常统计
        * @param user
        * @param orderId
        * @param yearMonth
        * @return
        */
       @Override
       public ResponseMessage viewRouteSign(AuthorizationUser user, Long orderId,Integer driverId, String yearMonth) {
              if (orderId == null) {
                     return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "机主订单id不能为空");
              }

              if (VerifyStr.isEmpty(yearMonth)) {
                     return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "年月不能为空");
              }

              if (!VerifyStr.isYYYYMM(yearMonth)) {
                     return new ResponseMessage(ErrorCodeConstants.PARAM_UNKNOW.getId(), "年月格式不正确");
              }

              List<String> dateListWhichHasRouteFinishedOrError = jxcMachineRouteMapper.dateListWhichHasRouteFinishedOrError(orderId,driverId, yearMonth);
              List<String> dateListWhichHasRouteFinishedOrError1 = jxcMachineRouteMapper.dateListWhichHasRouteFinishedOrError1(orderId,driverId, yearMonth);
              dateListWhichHasRouteFinishedOrError.removeAll(dateListWhichHasRouteFinishedOrError1);
              dateListWhichHasRouteFinishedOrError.addAll(dateListWhichHasRouteFinishedOrError1);
              dateListWhichHasRouteFinishedOrError = new ArrayList<String>(new HashSet<>(dateListWhichHasRouteFinishedOrError));

              List<Map<String,Object>> dateAndHasErrorList = new ArrayList<>();
              dateListWhichHasRouteFinishedOrError.stream().forEach(date -> {
                     if(date != null) {
                            dateAndHasErrorList.add(new HashMap<String, Object>(2) {{
                                   put("date", date);
                                   put("hasError", jxcMachineRouteMapper.dateRouteHasError(date, orderId, driverId) > 0 ? 1 : 0);
                            }});
                     }
              });
              return new ResponseMessage(new HashMap<String, Object>(1) {{
                     put("dateAndHasErrorList", dateAndHasErrorList);
              }});
       }
}