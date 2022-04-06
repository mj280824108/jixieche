package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author 钟焕 
 * @Description
 * @Date 14:00 2019-08-22
 **/
public interface JxcProjectOrderMapper extends BaseMapper<JxcProjectOrder> {

    /**
     * 承租方新增订单
     * @param vo
     */
    void insertJxcProjectOrder(@Param("params")JxcProjectOrderVo vo);

    /**
     * 插入消纳场与订单关联表
     * @param siteList
     */
    void insertOrderRefSite(@Param("siteList") List<JxcOrderRefSite> siteList);

    /**
     * 根据ID查询订单详情
     * @param id
     * @return
     */
    JxcProjectOrderVo selectJxcProjectOrderById(@Param("id") Long id);

    /**
     * 查询承租方订单中已接单的车辆数
     * @param orderId
     * @return
     */
    Integer queryAcceptedCarCount(@Param("orderId") Long orderId);

    /**
     * 统计该订单内所有车辆是否已经被评分过
     * @param orderId
     * @param userId
     * @return
     */
    Integer countJxcScore(@Param("orderId") String orderId,@Param("userId") Integer userId);

    /**
     * 查询订单中的消纳场
     * @param orderId
     * @return
     */
    List<JxcOrderRefSite> selectSiteListByOrderId(@Param("orderId") Long orderId);

    /**
     * 查询承租方管理员管理的项目ID
     * @param userId
     * @return
     */
    Integer getProjectIdByTenantryManId(@Param("userId") Integer userId);

    /**
     * 查询订单列表
     * @param userId
     * @param tabFlag
     * @param orderId
     * @param projectId
     * @return
     */
    List<JxcProjectOrder> selectProjectOrderList(@Param("userId") Integer userId,@Param("tabFlag") Integer tabFlag,@Param("orderId") Long orderId,@Param("projectId") Integer projectId);

    /**
     * 统计已经完成的趟数
     * @param orderId
     * @return
     */
    Integer countCompleteTransportTimes(@Param("orderId") Long orderId);

    /**
     * 统计今天已经完成的趟数
     * @param orderId
     * @return
     */
    Integer countTodayCompleteTransportTimes(@Param("orderId") Long orderId);

    /**
     * 查询已接单车辆数
     * @param orderId
     * @return
     */
    Integer countReceivedMachineCount(@Param("orderId") Long orderId);

    /**
     * 统计今日参与车辆数
     * @param orderId
     * @return
     */
    Integer countTodayMachineCount(@Param("orderId") Long orderId);

    /**
     * 统计总参与车辆数
     * @param orderId
     * @return
     */
    Integer countTotalMachineCount(@Param("orderId") Long orderId);

    /**
     * 统计待支付的金额(不包含异常行程)
     * @param orderId
     * @param endTime
     * @return
     */
    int sumPayAmount(@Param("orderId") Long orderId,@Param("endTime") String endTime);

    /**
     * 查询订单中的接单机械(待接单，进行中状态)
     * @param orderId
     * @param orderState
     * @return
     */
    List<MachineListVo> selectMachineListInOrder1(@Param("orderId") Long orderId, @Param("orderState") Integer orderState);

    /**
     * 查询订单中的接单机械（已完工状态）
     * @param orderId
     * @return
     */
    List<MachineListVo> selectMachineListInOrder2(@Param("orderId") Long orderId);

    /**
     * 查询订单中的接单机械（取消状态）
     * @param orderId
     * @return
     */
    List<MachineListVo> selectMachineListInOrder3(@Param("orderId") Long orderId);

    /**
     * 查询机械绑定的司机
     * @param id
     * @return
     */
    List<MachineListVo> selectDriverVoList(@Param("machineId") Integer id);

    /**
     * 查询机主订单此时的状态
     * @param orderId
     * @param machineId
     * @return
     */
    JxcOwnerOrder queryOwnerOrderState(@Param("orderId") Long orderId, @Param("machineId") Integer machineId);

    /**
     * 查询机主订单此时的状态
     * @param orderId
     * @param machineId
     * @return
     */
    JxcOwnerOrder queryOwnerOrderStateByProjectId(@Param("orderId") Long orderId, @Param("machineId") Integer machineId);


    /**
     * 承租方解雇机械
     * @param orderId
     * @param machineId
     * @param orderState
     * @param reason
     */
    void updateOwnerOrderState(@Param("orderId") Long orderId,@Param("machineId") Integer machineId,@Param("orderState") Integer orderState,@Param("reason") String reason);

    /**
     * 更改机械状态
     * @param machineId
     */
    void updateMachineState(@Param("machineId") Integer machineId);

    /**
     * 解雇推送查询机主信息
     * @param jxcOwnerOrder
     * @return
     */
    JxcOwnerOrderDetailVo getOwnerPushInfoByMachineId(@Param("jxcOwnerOrder") JxcOwnerOrder jxcOwnerOrder);

    /**
     * 解雇推送查询司机信息
     * @param machineId
     * @param userId
     * @return
     */
    List<Map<String,Object>> getDriverPushInfoByMachineId(@Param("machineId") Integer machineId, @Param("userId") Integer userId);
    /**
     * 停止继续要车
     * @param orderId
     */
    void updateContinueCarFlag(@Param("orderId") Long orderId);

    /**
     * 申请停工
     * @param projectOrderVo
     */
    void applyStopWork(@Param("projectOrderVo") JxcProjectOrderVo projectOrderVo);

    /**
     * 获取推送必要的信息
     * @param orderId
     * @param orderState
     * @return
     */
    List<JxcOwnerOrderDetailVo> getOwnerPushInfoList(@Param("orderId") Long orderId, @Param("orderState") Integer orderState);

    /**
     * 获取推送给司机的信息
     * @param machineId
     * @param userId
     * @return
     */
    List<Map<String,Object>> getDriverPushInfoList(@Param("machineId") Integer machineId, @Param("userId") Integer userId);

    /**
     * 申请提前完工
     * @param projectOrderVo
     */
    void applyEndWork(@Param("projectOrderVo") JxcProjectOrderVo projectOrderVo);

    /**
     * 取消承租方订单
     * @param projectOrderVo
     */
    void cancelProjectOrder(@Param("projectOrderVo") JxcProjectOrderVo projectOrderVo);

    /**
     * 取消机主订单
     * @param projectOrderVo
     */
    void cancelOwnerOrder(@Param("projectOrderVo") JxcProjectOrderVo projectOrderVo);

    /**
     * 查询消纳券剩余量
     * @param userId
     * @param siteId
     * @param couponType
     * @param capacity
     * @return
     */
    Integer countSiteCouponCount(@Param("tenantryId") Integer userId,@Param("siteId") Integer siteId, @Param("couponType") Integer couponType,@Param("capacity") Integer capacity);

    /**
     * 查询目前在途的车辆数
     * @param orderId
     * @return
     */
    List<Integer> countMachineOngoing(@Param("orderId") Long orderId);

    /**
     * 查询现有订单消纳场的计价方式
     * @param orderId
     * @return
     */
    JxcOrderRefSite getJxcOrderRefSite(@Param("orderId") Long orderId);

    /**
     * 将原消纳场改为停用状态
     * @param id
     */
    void updateJxcOrderRefSite(@Param("id") Integer id);

    /**
     * 获取订单总趟数
     * @param orderId
     * @return
     */
    int getTotalCount(@Param("orderId") Long orderId);

    /**
     * 获取已支付金额
     * @param orderId
     * @return
     */
    int getPayAmount(@Param("orderId") Long orderId);

    /**
     * 查询趟数记录
     * @param vo
     * @param flag 1:待支付趟数 2:正常趟数 3:异常趟数
     * @return
     */
    int countRouteCount(@Param("orderVo") JxcProjectOrderVo vo,@Param("flag") Integer flag);

    /**
     * 查询待支付统计列表
     * @param vo
     * @return
     */
    List<MachineRouteRecord> selectNoPayList(@Param("orderVo") JxcProjectOrderVo vo);

    /**
     * 查询所有待支付的行程ID
     * @param vo
     * @return
     */
    List<Long> getRouteIdListA(@Param("orderVo") JxcProjectOrderVo vo);

    /**
     * 根据机械查询待支付的行程ID
     * @param vo
     * @return
     */
    List<Long> getRouteIdListB(@Param("vo") MachineRouteRecord vo);

    /**
     * 查询正常趟数列表
     * @param vo
     * @return
     */
    List<MachineRouteRecord> selectNormalList(@Param("orderVo") JxcProjectOrderVo vo);

    /**
     * 查询正常趟数列表
     * @param vo
     * @return
     */
    List<MachineRouteRecord> selectAbnormalList(@Param("orderVo") JxcProjectOrderVo vo);

    /**
     * 根据机械查询异常处理情况
     * @param vo
     * @param machineId
     * @return
     */
    List<AbnormalRouteVo> selectAbnormalRouteVoList(@Param("orderVo") JxcProjectOrderVo vo,@Param("machineId") Integer machineId);

    /**
     * 查询正常趟数记录详情
     * @param vo
     * @param accountMethod
     * @param flag 1:正常行程 2：异常行程
     * @return
     */
    List<RouteRecordDetailVo> selectRouteRecordDetailList(@Param("vo") MachineRouteRecord vo,@Param("accountMethod") Integer accountMethod,@Param("flag") Integer flag);

    /**
     * 查询异常待支付列表
     * @param orderId
     * @return
     */
    List<AbnormalRouteVo> selectAbnormalRouteRecordList(@Param("orderId") Long orderId);

    /**
     * 统计异常待支付趟数总金额
     * @param orderId
     * @return
     */
    Integer sumAbnormalPayAmount(@Param("orderId") Long orderId);

    /**
     * 插入异常申请记录
     * @param jxcMachineRoute
     * @param abnormalDescription
     */
    void insertAbnormalOrderAppeal(@Param("route")JxcMachineRoute jxcMachineRoute,@Param("abnormalDescription") String abnormalDescription);

    /**
     * 承租方自主确认时插入异常申请记录
     * @param jxcMachineRoute
     */
    void insertAbnormalOrderAppealBySelf(@Param("route")JxcMachineRoute jxcMachineRoute);

    /**
     * 查询某个机械的的待支付行程ID集合
     * @param vo
     * @param flag
     * @return
     */
    List<Long> selectRouteIdListByMachineId(@Param("vo") MachineRouteRecord vo,@Param("flag") Integer flag);

    /**
     * 查询某个机械的的待支付行程总金额
     * @param vo
     * @param flag
     * @return
     */
    Integer sumPayAmountByMachineId(@Param("vo") MachineRouteRecord vo,@Param("flag") Integer flag);

    /**
     * 查询有效的积分模板
     *
     * @param id
     * @return
     */
    JxcCreditScoreTemplate getEffectiveById(@Param("id") Integer id);

    /**
     * 获取正在上班中的司机ID
     * @param orderId
     * @return
     */
    List<String> getDriverThirdId(@Param("orderId") Long orderId);

    /**
     * 查询消纳场名称
     * @param siteId
     * @return
     */
    JxcSite getSiteNameBySiteId(@Param("siteId") Integer siteId);

}