package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcAbnormalOrderAppeal;
import com.weiwei.jixieche.bean.JxcOwnerApplyQuit;
import com.weiwei.jixieche.bean.JxcProjectOrder;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.*;

/**
 * @Author 钟焕
 * @Description
 * @Date 19:18 2019-08-14
 **/
public interface JxcProjectOrderService extends BaseService<JxcProjectOrder> {
    /**
     * 前端分页查询承租方订单表
     *
     * @param pageNo          分页索引
     * @param pageSize        每页显示数量
     * @param jxcProjectOrder 查询条件
     * @return
     */
    ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcProjectOrder jxcProjectOrder);

    /**
     * 根据预计公里数计算预计价格
     * @param jxcSiteVo
     * @return
     */
    ResponseMessage estimatePrice(JxcSiteVo jxcSiteVo);

    /**
     * 承租方发布订单
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage addJxcProjectOrder(JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 承租方查询订单列表
     *
     * @param user
     * @param projectOrderListVo
     * @return
     */
    ResponseMessage selectProjectOrderList(AuthorizationUser user, ProjectOrderListVo projectOrderListVo);

    /**
     * 根据ID查询承租方订单详情
     *
     * @param id
     * @return
     */
    ResponseMessage queryJxcProjectOrderById(Long id);

    /**
     * 根据订单ID查询订单中的接单车辆
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage selectMachineListInOrder(JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 解雇机械
     *
     * @param orderId
     * @param machineId
     * @param reason
     * @return
     */
    ResponseMessage fireMachine(Long orderId, Integer machineId, String reason);

    /**
     * 停止继续要车
     *
     * @param orderId
     * @return
     */
    ResponseMessage stopContinueCar(Long orderId);

    /**
     * 申请停工
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage applyStopWork(JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 申请提前完工
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage applyEndWork(JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 取消订单之前的验证
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage tenCancelOrderConfirm(JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 取消订单
     *
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage cancelProjectOrder(Integer userId, JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 验证消纳场是否可以更换
     * @param user
     * @param orderId
     * @param siteId
     * @return
     */
    ResponseMessage confirmSiteCoupon(AuthorizationUser user,Long orderId, Integer siteId);

    /**
     * 更换消纳场
     * @param user
     * @param jxcProjectOrderVo
     * @return
     */
    ResponseMessage changeSite(AuthorizationUser user, JxcProjectOrderVo jxcProjectOrderVo);

    /**
     * 承租方查询装车记录
     * @param projectOrderVo 参数：id,查询开始时间以及结束时间(日结只传开始不传结束)
     * @return
     */
    ResponseMessage selectRouteRecord(JxcProjectOrderVo projectOrderVo);

    /**
     * 查询装车记录详情列表
     * @param machineRouteRecord 分页参数，订单ID，机械ID，查询开始时间，查询结束时间（周结才需要），abnormalType异常类型
     * @return
     */
    ResponseMessage selectRouteRecordList(MachineRouteRecord machineRouteRecord);

    /**
     * 查询异常待支付列表
     * @param orderId
     * @return
     */
    ResponseMessage selectAbnormalRouteRecordList(Long orderId);

    /**
     * 根据异常行程ID查询异常详情
     * @param user
     * @param routeId
     * @return
     */
    ResponseMessage getAbnormalRouteDetail(AuthorizationUser user, Long routeId);

    /**
     * 申诉异常行程
     * @param jxcAbnormalOrderAppeal 行程ID，申诉原因
     * @return
     */
    ResponseMessage applyAbnormalRoute(JxcAbnormalOrderAppeal jxcAbnormalOrderAppeal);

    /**
     * 确认异常行程
     * @param routeId
     * @param user
     * @return
     */
    ResponseMessage confirmAbnormalRoute(AuthorizationUser user,Long routeId);

    /**
     * 获取机主退场申请记录
     * @param user
     * @param jxcOwnerApplyQuitVo orderId
     * @return
     */
    ResponseMessage selectOwnerQuitRecordList(AuthorizationUser user, JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo);

    /**
     * 同意或者驳回机主退场申请
     * @param jxcOwnerApplyQuit
     * @return
     */
    ResponseMessage dealOwnerQuitApply(JxcOwnerApplyQuit jxcOwnerApplyQuit);


}