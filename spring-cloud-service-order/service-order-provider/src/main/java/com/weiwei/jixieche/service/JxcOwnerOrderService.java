package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcOwnerOrder;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JxcRobOrderVo;
import com.weiwei.jixieche.vo.OwnerStatisticsVo;

/**
* @Description: 机主订单管理
* @Author:      liuy
* @Date:  2019/8/20 15:16
*/
public interface JxcOwnerOrderService extends BaseService<JxcOwnerOrder> {

       /**
        * 机主订单管理列表
        * @author  liuy
        * @param user
        * @param lastPageLastId
        * @return
        * @date    2019/8/20 15:45
        */
       ResponseMessage getOwnerOrderList(AuthorizationUser user,  String lastPageLastId,
                                          Integer orderType,  Integer machineId);

       /**
        * 泥头车首页
        * @author  liuy
       * @param userId
        * @return
        * @date    2019/8/22 10:13
        */
       ResponseMessage getOwnerIndex(Integer userId);

       /**
        * 接单列表
        * @author  liuy
        * @param jxcRobOrderVo
        * @return
        * @date    2019/8/22 16:39
        */
       ResponseMessage getProjectOrderList(JxcRobOrderVo jxcRobOrderVo);

       /**
        * 机主订单详情
        * @author  liuy
        * @param ownerOrderId
        * @return
        * @date    2019/8/22 19:38
        */
       ResponseMessage getOwnerOrderDetail(Long ownerOrderId);

       /**
        * 机主取消订单/机主解雇司机/司机取消职位前的验证
        * @param user
        * @param cancelType 1:机主拒绝/解雇司机，2:机主取消承租方订单，3：司机取消已接受的短期职位
        * @param startDate
        * @return
        */
       ResponseMessage cancelConfirm(AuthorizationUser user,Integer cancelType,String startDate);

       /**
        * 取消原因列表
        * @author  liuy
        * @param
        * @return
        * @date    2019/8/23 10:13
        */
       ResponseMessage getCancelReason();

       /**
        * 取消订单
        * @author  liuy
       * @param ownerOrderId 订单ID
        * @param cancelId 取消原因ID
        * @return
        * @date    2019/8/23 11:00
        */
       ResponseMessage cancelOrder(AuthorizationUser user, Long ownerOrderId, Long cancelId);

       /**
        * 申请退出
        * @author  liuy
        * @param ownerOrderId
        * @return
        * @date    2019/8/23 14:57
        */
       ResponseMessage applyOutOrder(Long ownerOrderId, String leaveReason);

       /**
        * 取消申请
        * @author  liuy
        * @param ownerOrderId
        * @return
        * @date    2019/8/23 14:57
        */
       ResponseMessage cancelApplyOrder(Long ownerOrderId);

       /**
        * 机主查看异常趟数列表
        * @author  liuy
        * @param flag 0-未处理 1-已处理
        * @return
        * @date    2019/8/27 13:44
        */
       ResponseMessage ownerAbnormalList(Integer userId, Long lastPageLastId,Integer flag);

       /**
        * 异常趟数详情
        * @author  liuy
        * @param routeId
        * @return
        * @date    2019/8/27 14:59
        */
       ResponseMessage ownerViewAbnormalProgress(Long routeId);

       /**
        * 泥头车接单引导认证
        * @author  liuy
        * @param userId
        * @return
        * @date    2019/8/29 17:42
        */
       ResponseMessage checkOwnerCertification(Integer userId);

       /**
        * 机主订单-接单
        * @author  liuy
        * @param jxcOwnerOrder
        * @return
        * @date    2019/8/30 11:09
        */
       ResponseMessage addOwnerOrder(AuthorizationUser authorizationUser, JxcOwnerOrder jxcOwnerOrder);

       /**
        * 查询首页机主收入统计列表
        *
        * @return
        */
       OwnerStatisticsVo getOwnerStatistics(OwnerStatisticsVo requestVo);

       /**
        * 检查进行中订单是否跑趟中或是正在上班中
        * @author  liuy
        * @param machineId
        * @return  
        * @date    2019/9/4 14:21
        */
       ResponseMessage checkMachineWorkIn(AuthorizationUser authorizationUser, Integer machineId);

       /**
        * 获取电子消纳券
        * @author  liuy
        * @param machineId
        * @return
        * @date    2019/9/10 16:26
        */
       ResponseMessage getSiteCouponByMachineId(Integer machineId);

       /**
        * 检查司机是否有正在进行中的订单和已接单，是否绑定机主
        * @author  liuy
        * @param driverId
        * @return
        * @date    2019/10/17 20:45
        */
       ResponseMessage checkOwnerOrderByDriverId(Integer driverId);

}