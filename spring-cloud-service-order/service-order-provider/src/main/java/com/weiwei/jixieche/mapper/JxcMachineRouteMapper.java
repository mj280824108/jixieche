package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JxcMachineRouteMapper extends BaseMapper<JxcMachineRoute> {

	/**
	 * 根据机械ID查询总趟数
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/8/14 19:10
	 */
	int getTotalCountByMachineId(@Param("machineId")Integer machineId);

	/**
	 * 根据机械行程趟数集合查询趟数总金额
	 * @param list
	 * @return
	 */
	Integer queryTotalAmount(@Param("list") List<Long> list);

	/**
	 * 根据机械行程趟数集合批量修改机械行程支付状态
	 * @param updateBatchRoutePayStatusVo
	 */
	void updateBatchPayStatus(UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo);

	/**
	 * 趟数记录
	 * @author  liuy
	 * @param jxcMachineRouteVo
	 * @return
	 * @date    2019/8/20 11:48
	 */
	List<JxcMachineRouteVo> getMachineRouteList(JxcMachineRouteVo jxcMachineRouteVo);

	/**
	 * 查询趟数记录是否还有下一页
	 * @param jxcMachineRouteVo
	 * @return
	 */
	Long hasNextPage(JxcMachineRouteVo jxcMachineRouteVo);

	/**
	 * 总趟数
	 * @author  liuy
	 * @param jxcMachineRouteVo
	 * @return
	 * @date    2019/8/20 14:05
	 */
	Integer getTotalCountMachineRoute(JxcMachineRouteVo jxcMachineRouteVo);

	/**
	 * 检查司机是否正在跑趟中
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 14:58
	 */
	Long checkDriverRun(@Param("driverId") Integer driverId);

	/**
	 * 检查机械是否正在跑趟中
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/8/21 14:58
	 */
	Long checkMachineRun(@Param("machineId") Integer machineId);

	/**
	 * 根据订单ID统计已完成和异常趟数
	 * @author  liuy
	 * @param ownerOrderId
	 * @param flag 1:已完成; 2:异常
	 * @return
	 * @date    2019/8/23 9:38
	 */
	Map<String,Object> getTotalCountByOrderId(@Param("ownerOrderId")Long ownerOrderId, @Param("flag")Integer flag);

	/**
	 * 查询申诉异常所需要的行程信息
	 * @param routeId
	 * @return
	 */
	JxcMachineRoute getRouteInfoById(@Param("routeId") Long routeId);

	/**
	 * 根据routeId查询异常申请记录
	 * @param routeId
	 * @return
	 */
	JxcAbnormalOrderAppeal getJxcAbnormalOrderAppealByRouteId(@Param("routeId") Long routeId);

	/**
	 * 根据消纳场管理员ID查询消纳场ID
	 * @param userId
	 * @return
	 */
	Integer getSiteIdByUserId(@Param("userId") Integer userId);

	/**
	 * 查询消纳场
	 * @param siteId
	 * @return
	 */
	JxcSite getJxcSiteById(@Param("siteId") Integer siteId);

	/**
	 * 检查消纳场是否入驻
	 * @param siteId
	 * @return
	 */
	Integer checkSiteIntoFlag(@Param("siteId") Integer siteId);

	/**
	 * 获取消纳券相关信息
	 * @param machineCardNo
	 * @param siteId
	 * @return
	 */
	JxcSiteCouponVo getSiteCouponByMachineId(@Param("machineCardNo") String machineCardNo,@Param("siteId") Integer siteId);

	/**
	 * 查询施工场地的打卡记录
	 * @param vo
	 * @return
	 */
	JxcMachineRoute queryJxcMachineRoute(@Param("routeVo") JxcMachineRouteVo vo);

	/**
	 * 查询机械订单的相关信息
	 * @param vo
	 * @return
	 */
	JxcMachineRoute queryMachineOrderInfo(@Param("routeVo") JxcMachineRouteVo vo);

	/**
	 * 查询当前机械的司机ID
	 * @param vo
	 * @return
	 */
	Integer getDriverId(@Param("routeVo") JxcMachineRouteVo vo);

	/**
	 * 根据司机的ID查询车牌号
	 * @param driverId
	 * @return
	 */
	String getMachineCardNoByDriverId(@Param("driverId") Integer driverId);

	/**
	 * 查询计费模式，预计公里数等等信息
	 * @param jxcOrderRefSite
	 * @return
	 */
	JxcOrderRefSite queryOrderRefSite(@Param("os") JxcOrderRefSite jxcOrderRefSite);

	/**
	 * 查询司机的第三方ID
	 * @param driverId
	 * @return
	 */
	String getDriverThirdId(@Param("driverId") Integer driverId);

	/**
	 * 获取交接班的信息
	 * @param routeId
	 * @return
	 */
	JxcDriverHandoverVo getDriverHandover(@Param("routeId") Long routeId);

	/**
	 * 插入交易流水记录
	 * @param jxcTradeOwner
	 */
	void insertJxcTradeOwner(@Param("jxcTradeOwner")JxcTradeOwner jxcTradeOwner);

	/**
	 * 修改消纳券状态以及异常类型
	 * @param vo
	 */
	void updateSiteCoupon1(@Param("vo") SiteClockVo vo);

	/**
	 * 修改消纳券状态以及异常类型
	 * @param vo
	 */
	void updateSiteCoupon(@Param("vo") SiteClockVo vo);

	/**
	 * 获取机械ID
	 * @param couponId
	 * @return
	 */
	Integer getMachineIdByCouponId(@Param("couponId") Long couponId);

	/**
	 * 将车辆携带的其他消纳券退回
	 * @param machineId
	 */
	void updateCouponFlag(@Param("machineId") Integer machineId);

	/**
	 * 获取日历标记
	 * @param orderId
	 * @param driverId
	 * @param yearMonth
	 * @return
	 */
	List<String> dateListWhichHasRouteFinishedOrError(@Param("orderId") Long orderId,@Param("driverId") Integer driverId, @Param("yearMonth") String yearMonth);

	/**
	 * 获取日历标记（正常异常都有）
	 * @param orderId
	 * @param driverId
	 * @param yearMonth
	 * @return
	 */
	List<String> dateListWhichHasRouteFinishedOrError1(@Param("orderId") Long orderId,@Param("driverId") Integer driverId, @Param("yearMonth") String yearMonth);

	/**
	 * 统计异常趟数
	 * @param date
	 * @param driverId
	 * @param ownerOrderId
	 * @return
	 */
	int dateRouteHasError(@Param("date") String date, @Param("ownerOrderId") Long ownerOrderId,@Param("driverId") Integer driverId);

	/**
	 * 查询异常详情
	 * @param routeId
	 * @return
	 */
	AbnormalRouteVo getAbnormalRouteDetail(@Param("routeId") Long routeId);

	/**
	 * 查询行程信息
	 * @author  liuy
	* @param routeId
	 * @return
	 * @date    2019/9/18 16:56
	 */
	JxcMachineRoute getById(@Param("routeId") Long routeId);
}