package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.vo.ClockRecord;
import com.weiwei.jixieche.vo.JxcClockPairVo;
import com.weiwei.jixieche.vo.JxcDriverTeamCostVo;
import com.weiwei.jixieche.vo.JxcShortJobListVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.INTERNAL;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author zhong huan
 * @Date 2019-09-02 14:33
 * @Description
 */
public interface JxcQuartzMapper {
    /**
     * 查询所有待接单状态的的订单
     * @param orderState
     * @return
     */
    List<Map<String,Object>> queryTenantryOrderIdList(@Param("orderState") Integer orderState);

    /**
     * 根据承租方订单ID查询机主订单ID
     * @param tenantryOrderId
     * @return
     */
    List<JxcOwnerOrder> queryOwnerOrderIdList(@Param("tenantryOrderId") Long tenantryOrderId);

    /**
     * 更新承租方订单状态(取消，进行中，已完工)
     * @param id
     * @param orderState
     */
    void updateTenantryOrderState(@Param("id") Long id,@Param("orderState") Integer orderState);

    /**
     * 更新停工标记
     */
    void updateStopWorkState();

    /**
     * 更新机主订单状态(取消，进行中，已完工)
     * @param tenantryOrderId
     * @param orderState
     * @param machineStatus
     */
    void updateOwnerOrderState(@Param("tenantryOrderId") Long tenantryOrderId,@Param("orderState") Integer orderState,@Param("machineStatus") Integer machineStatus);

    /**
     * 更新营运证状态为即将过期 0可用 1过期 2即将过期
     * @author  liuy
     * @return
     * @date    2019/9/2 15:46
     */
    void updateMachineOpState(@Param("currentTime") String currentTime);

    /**
     * 更新行驶证状态为即将过期 0--可用 1--过期 2--即将过期
     * @author  liuy
     * @return
     * @date    2019/9/2 15:46
     */
    void updateMachineDlState(@Param("currentTime") String currentTime);

    /**
     * 更新营运证状态为过期 0可用 1过期 2即将过期
     * @author  liuy
     * @return
     * @date    2019/9/2 15:46
     */
    void updateMachineOpState1(@Param("currentTime") String currentTime);

    /**
     * 更新行驶证状态为过期 0--可用 1--过期 2--即将过期
     * @author  liuy
     * @return
     * @date    2019/9/2 15:46
     */
    void updateMachineDlState1(@Param("currentTime") String currentTime);

    /**
     * 正在跑趟中的机械车
     * @author  liuy
     * @return
     * @date    2019/8/24 13:51
     */
    List<JxcWaitHandleItems> getMachineRunList();

    /**
     * 查询所有正在进行中的订单
     * @author  liuy
     * @return
     * @date    2019/8/24 14:15
     */
    List<JxcWaitHandleItems> getOrderRunList();

    /**
     * 司机是否上班状态
     * @author  liuy
     * @param driverId 司机用户ID
     * @return
     * @date    2019/8/14 16:59
     */
    int getDriverWorkStateById(@Param("driverId") Integer driverId);

    /**
     * 查询司机正在上班的信息
     * @param driverId
     * @return
     */
    List<JxcClockPair> getJxcClockPairByDriverId(@Param("driverId") Integer driverId);

    /**
     * 短期职位
     * @author  liuy
     * @return
     * @date    2019/9/3 10:42
     */
    List<Map<String,Object>> queryShortJObList();

    /**
     * 长期职位
     * @author  liuy
     * @return
     * @date    2019/9/3 10:42
     */
    List<Map<String,Object>> queryLongJobList();

    /**
     * 更新兼职职位状态为失效
     * @author  liuy
     * @return
     * @date    2019/9/3 10:49
     */
    void updateShortJobStatus(@Param("state")Integer state, @Param("jobId") Integer jobId);

    /**
     * 更新长期职位状态为失效
     * @author  liuy
     * @return
     * @date    2019/9/3 10:52
     */
    void updateLongJobStatus(@Param("jobId") Integer jobId);

    /**
     * 16小时内未打下班卡列表
     * @author  liuy
     * @return
     * @date    2019/9/3 13:50
     */
    List<ClockInOutPair> getNotClockOutPairClockInIdBefore16Hour();

    /**
     * 查询职位ID
     * @author  liuy
     * @return
     * @date    2019/9/3 13:55
     */
    Integer getJobId(@Param("recordId") Long recordId);

    /**
     * 向上下班记录表中插入一条下班卡的记录
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/3 14:00
     */
    void addClockRecord(@Param("params") Map<String, Object> params);

    /**
     * 更新配对表
     * @author  liuy
     * @param clockInId
     * @return
     * @date    2019/9/3 14:00
     */
    void updateClockPair(@Param("clockInId") Long clockInId, @Param("clockOutId") Long clockOutId);

    /**
     * 8小时以后未打消纳场卡的 变为异常趟数
     * @author  liuy
     * @return
     * @date    2019/9/3 14:18
     */
    List<Map<String,Object>> getMachineRouteList();

    /**
     * 查询行程信息
     * @param routeId
     * @return
     */
    JxcMachineRoute getJxcMachineRouteById(@Param("routeId") Long routeId);

    /**
     * 根据orderId 查询预计金额
     * @author  liuy
     * @param orderId
     * @return
     * @date    2019/9/3 14:22
     */
    Integer getEstimatePrice(@Param("orderId") Long orderId);

    /**
     * 根据承租方订单ID查询对应的项目城市ID以及该订单的土方类型
     * @param orderId
     * @return
     */
    Map<String,Object> getCityCodeByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据城市编号以及土方类型获取对应的计费规则
     * @param params
     * @return
     */
    TransCosts getTransCosts(@Param("params") Map<String, Object> params);

    /**
     * 修改行程趟数异常记录
     * @author  liuy
    *  @param estimatePrice
     * @return
     * @date    2019/9/3 14:37
     */
    void updateMachineRoute(@Param("estimatePrice") Integer estimatePrice, @Param("routeId") Long routeId, @Param("toOwnerAmount") Integer toOwnerAmount);

    /**
     * 超过12小时机主申请退出未同意
     * @author  liuy
     * @return
     * @date    2019/9/4 11:14
     */
    List<Map<String,Object>> getOwnerApplyQuitList();

    /**
     * 更新机主申请退出状态为同意
     * @author  liuy
     * @return
     * @date    2019/9/4 11:35
     */
    int updateOwnerApplyQuit(List<Integer> list);

    /**
     * 清除承租方机主退场申请待办事项
     * @param list
     */
    void updateJxcWaitHandle(List<Long> list);

    /**
     * 查询承租方日结订单
     * @return
     */
    List<Map<String,Object>> getTenantryIdAndOrderIdDay();

    /**
     * 方便合并短信以及推送
     * @return
     */
    List<Map<String,Object>> getTenantryIdAndOrderIdDay1();

    /**
     * 周结 对上一周 未支付或者未支付完成的行程的订单进行扫描拿到订单号以及对应承租方的ID
     *
     * @return
     */
    List<Map<String,Object>> getTenantryIdAndOrderIdWeek();

    /**
     * 查询未支付的行程ID
     * @param orderId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Long> getList(@Param("orderId")Long orderId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询需要待支付的待办事项(日结)
     * @return
     */
    List<JxcWaitHandleItems> selectJxcWaitHandleItemsListDay();

    /**
     * 查出所有周结订单
     * @return
     */
    List<Map<String,Object>> selectWeekOrder();

    /**
     * 查询需要待支付的待办事项(周结)
     * @param orderId
     * @param startTime
     * @param endTime
     * @return
     */
    List<JxcWaitHandleItems> selectJxcWaitHandleItemsListWeek(@Param("orderId")Long orderId,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 机械账号评价统计
     * @author  liuy
     * @return
     * @date    2019/9/5 15:38
     */
    List<Map<String,Object>> getMachineScoreList();

    /**
     * 承租方、机主、司机评分统计
     * @author  liuy
     * @return
     * @date    2019/9/5 15:39
     */
    List<Map<String,Object>> getUserScoreList();

    /**
     * 更新机械评分
     * @author  liuy
     * @param machineId
     * @return
     * @date    2019/9/5 15:46
     */
    void updateMachineById(@Param("machineId")Integer machineId, @Param("score") double score);

    /**
     * 更新用户评价
     * @author  liuy
     * @param userId
     * @return
     * @date    2019/9/5 15:46
     */
    void updateUserById(@Param("userId")Integer userId, @Param("score") double score);
    
    /**
     * 查询所有的司机兼职职位
     * @author  liuy
     * @return
     * @date    2019/9/17 20:07
     */
    List<JxcShortJobListVo> getAllShortJobList();

    /**
     * 查询所有的结束的兼职职位
     * @author  liuy
     * @return
     * @date    2019/9/17 20:07
     */
    List<JxcShortJobListVo> getShortJobEndList();

    /**
     * 更新兼职职位状态为进行中
     * @author  liuy
    * @param id
     * @return
     * @date    2019/9/17 20:16
     */
    void updateShortJobStateById(@Param("id")Integer id);

    /**
     * 将职位更改为进行中状态
     */
    void updateDriverJob();

    /**
     * 绑定司机为兼职司机
     * @author  liuy
     * @param jxcDriverRefOwner
     * @return
     * @date    2019/9/18 18:13
     */
    void insertDriverRefOwner(JxcDriverRefOwner jxcDriverRefOwner);

    /**
     * 查询司机是否跟机主存在绑定关系
     * @author  liuy
     * @param ownerId
     * @return
     * @date    2019/9/18 18:56
     */
    Integer getDriverRefOwnerById(@Param("ownerId")Integer ownerId, @Param("driverId") Integer driverId);

    /**
     * 查询是否有未支付
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/8/21 16:18
     */
    Integer getCountPayByDriverId(@Param("driverId") Integer driverId, @Param("shortJobId") Integer shortJobId);

    /**
     * 更新司机职位状态为已完结
     * @author  liuy
     * @param id
     * @param state
     * @return
     * @date    2019/9/17 10:33
     */
    void updateShortJobDriverById(@Param("state")Integer state, @Param("id")Integer id);

    /**
     * 更新司机与机主绑定关系为解雇
     * @author  liuy
     * @param id
     * @param state
     * @return
     * @date    2019/9/10 15:29
     */
    int updateByOwnerIdAndDriverId(@Param("id") Integer id,
                                   @Param("state")Integer state);

    /**
     * 查询职位已招聘人数
     * @author  liuy
     * @param shortJobId
     * @return
     * @date    2019/8/15 14:25
     */
    int getShortJobCount(@Param("shortJobId") Integer shortJobId);
    
    /**
     * 查询是否已经存在正在跑趟的司机没有打上班卡的记录
     * @author  liuy
    * @param jxcWaitHandleItems
     * @return  
     * @date    2019/9/21 17:06
     */
    int checkWaitHandleItemById(JxcWaitHandleItems jxcWaitHandleItems);

    /**
     * 查询前一天的打卡的司机信息
     * @author  liuy
     * @return
     * @date    2019/9/23 10:27
     */
    List<JxcClockInOutPair> getClockInoutPair();

    /**
     * 打卡次数统计
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/24 9:27
     */
    Integer getClockCount(@Param("params") Map<String,Object> params);

    /**
     * 工作管理-查出司机打卡记录
     * @author  liuy
     * @param params
     * @return
     * @date    2019/9/24 9:28
     */
    List<ClockRecord> getClockRecordList(@Param("params")Map<String,Object> params);

    /**
     * 查询司机台班打卡记录,统计趟数
     * @author  liuy
    * @param params
     * @return
     * @date    2019/9/24 9:29
     */
    Integer getTotalCountRoute(@Param("params")Map<String,Object> params);

    /**
     * 查询司机台班费用
     * @author  liuy
     * @param cityId
     * @return
     * @date    2019/8/15 14:10
     */
    JxcDriverTeamCostVo queryDriverTeamCost(@Param("cityId") Integer cityId);

    /**
     * 生成司机账单
     * @author  liuy
     * @param jxcClockPairVo
     * @return
     * @date    2019/9/24 9:33
     */
    void insertDriverBill(JxcClockPairVo jxcClockPairVo);

    /**
     * 更新配对表
     * @author  liuy
     * @param clockId
     * @return
     * @date    2019/9/24 9:37
     */
    void updateClockInOutPairById(@Param("clockInId")Long clockInId, @Param("clockId")Long clockId);

    /**
     * 机械是否绑定司机
     * @author  liuy
    * @param machineId
     * @return
     * @date    2019/9/25 13:59
     */
    Integer getMachineBindDriver(@Param("machineId")Integer machineId);

    /**
     * 每周二解冻上周冻结金额
     */
    void unfreeze();

    /**
     * 查询待办事项
     * @param vo
     * @return
     */
    JxcWaitHandleItems queryByOrderId(@Param("vo") JxcWaitHandleItems vo);

    /**
     * 更新订单状态为已完结
     * @param list
     */
    void updateOwnerOrderById(List<Long> list);

    /**
     * 解绑司机绑定的机械关系
     * @author  liuy
     * @param driverId
     * @return
     * @date    2019/9/10 15:38
     */
    int updateByDriverId(Integer driverId);

    /**
     * 获取用户手机号码
     * @author  liuy
     * @param userId
     * @return
     * @date    2019/10/10 16:38
     */
    String getPhoneByUserId(@Param("userId") Integer userId);

    /**
     * 当天12点查询当天晚上24点即将开始的订单的承租方ID
     * @return
     */
    List<Map<String,Object>> getTenantryId();

    /**
     * 查询机主订单ID
     * @param orderId
     * @return
     */
    List<Map<String,Object>> getOwnerId(@Param("orderId") Long orderId);

    /**
     * 获取推送给司机的信息不包含机主
     * @param machineId
     * @param userId
     * @return
     */
    List<Map<String,Object>> getDriverPushInfoList(@Param("machineId") Integer machineId, @Param("userId") Integer userId);

    /**
     * 获取推送给司机的信息不包含机主
     * @param machineId
     * @return
     */
    List<Map<String,Object>> getDriverPushInfoList2(@Param("machineId") Integer machineId);

    /**
     * 根据userId获取第三方ID
     * @param userId
     * @return
     */
    String getThirdIdByUserId(@Param("userId") Integer userId);

    /**
     * 24小时以后未付款的订单
     * @return
     */
    List<Map<String,Object>> select24HourNoPayOrder();

    /**
     * 限制发单
     * @param userId
     */
    void updateSendOrderState(@Param("userId") Integer userId);

    /**
     * 获取正在上班中的司机ID
     * @param orderId
     * @return
     */
    List<Map<String,Object>> getDriverThirdId(@Param("orderId") Long orderId);

    /**
     * 提前完工
     * @param orderId
     */
    void updateTenantryOrderState1(@Param("id") Long orderId);

    /**
     * 2小时以后未处理的订单更新消纳券订单状态为已取消
     * @author  liuy
     * @return
     * @date    2019/10/22 9:18
     */
    Integer updateSiteOrderFlag();
}
