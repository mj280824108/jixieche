package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMachineRoute;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;

import java.util.ArrayList;
import java.util.List;

public interface JxcMachineRouteService extends BaseService<JxcMachineRoute> {
       /**
     * 前端分页查询机械行程表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMachineRoute 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRoute jxcMachineRoute);

       /**
        * 根据机械行程趟数集合查询趟数总金额
        * @param routeIdList
        * @return
        */
       ResponseMessage queryTotalAmount(List<Long> routeIdList);

       /**
        * 根据机械行程趟数集合批量修改机械行程支付状态
        * @param updateBatchRoutePayStatusVo
        * @return
        */
       void updateBatchPayStatus(UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo);


       /**
        * 机主查看已接单的行程的日期和异常统计
        * @param user
        * @param orderId
        * @param driverId
        * @param yearMonth
        * @return
        */
      ResponseMessage viewRouteSign(AuthorizationUser user, Long orderId,Integer driverId, String yearMonth);
}