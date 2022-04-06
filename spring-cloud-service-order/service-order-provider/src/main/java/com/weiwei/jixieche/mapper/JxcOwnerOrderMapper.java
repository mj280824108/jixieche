package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Description: 机主订单管理
* @Author:      liuy
* @Date:  2019/8/20 15:15
*/
public interface JxcOwnerOrderMapper extends BaseMapper<JxcOwnerOrder> {

	/**
	 * 机主订单管理列表
	 * @author  liuy
	 * @param ownerUserId
	 * @param lastPageLastId
	 * @return
	 * @date    2019/8/20 15:45
	 */
	List<JxcOwnerOrderVo> getOwnerOrderList(@Param("ownerUserId") Integer ownerUserId, @Param("lastPageLastId") String lastPageLastId,
	                                        @Param("orderType") Integer orderType, @Param("machineId") Integer machineId);
	Long hasNextPage(@Param("ownerUserId") Integer ownerUserId, @Param("lastPageLastId") String lastPageLastId,
	                    @Param("orderType") Integer orderType, @Param("machineId") Integer machineId);

	/**
	 * 查询订单申请退出的状态
	 * @param ownerOrderId
	 * @return
	 */
	List<JxcOwnerApplyQuit> selectJxcOwnerApplyQuitList(@Param("ownerOrderId") Long ownerOrderId);
	/**
	 * 查询是否有未垫付的异常行程，有的话加“待收款”标记
	 * @author  liuy
	 * @param ownerOrderId
	 * @return
	 * @date    2019/8/20 16:10
	 */
	Integer countWaitPayRoute(@Param("ownerOrderId") Long ownerOrderId);

	/**
	 * 查询当前订单有没有行程记录
	 * @author  liuy
	* @param ownerOrderId
	 * @return
	 * @date    2019/8/20 16:33
	 */
	Long checkRoute(@Param("ownerOrderId") Long ownerOrderId);

	/**
	 * 机械有没有正在进行中的订单
	 * @author  liuy
	* @param machineId
	 * @return
	 * @date    2019/8/22 14:55
	 */
	JxcIndexVo getOwnerOrderByMachineId(@Param("machineId")Integer machineId);

	/**
	 * 正在进行中的订单是否有人在上班中
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/8/22 15:05
	 */
	JxcIndexVo getWorkInByMachineId(@Param("machineId")Integer machineId);

	/**
	 * 正在进行中的订单是否正在跑趟中
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/4 14:07
	 */
	JxcMachineWorkInVo checkMachineRun(@Param("machineId") Integer machineId);

	/**
	 * 接单列表
	 * @author  liuy
	 * @param areaId
	 * @return
	 * @date    2019/8/22 16:39
	 */
	List<JxcRobOrderVo> getProjectOrderList(@Param("areaId")Integer areaId);

	/**
	 * 机主订单详情
	 * @author  liuy
	 * @param ownerOrderId
	 * @return
	 * @date    2019/8/22 20:36
	 */
	JxcOwnerOrderDetailVo getOwnerOrderDetail(@Param("ownerOrderId") Long ownerOrderId);

	/**
	 * 取消原因列表
	 * @author  liuy
	 * @param
	 * @return
	 * @date    2019/8/23 10:13
	 */
	List<JxcCancelReason> getCancelReason();

	/**
	 * 查询所有自身未完成的job
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/8/23 11:33
	 */
	List<JxcProjectOrder> getProjectOrderIdList(@Param("userId")Integer userId);

	/**
	 * 机主查看异常趟数列表
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/8/27 14:02
	 */
	List<JxcOwnerAbnormalVo> ownerAbnormalList(@Param("userId") Integer userId, @Param("lastPageLastId") Long lastPageLastId, @Param("flag") Integer flag);
	Long hasNextPageAbnormalList(@Param("userId") Integer userId, @Param("lastPageLastId") Long lastPageLastId,@Param("flag") Integer flag);

	/**
	 * 查询申请退出的待办事项
	 * @param ownerOrderId
	 * @return
	 */
	JxcWaitHandleItems getJxcWaitHandleItems(@Param("ownerOrderId") Long ownerOrderId);

	/**
	 * 机主查看异常总趟数
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/8/27 14:56
	 */
	Integer ownerAbnormalAllList(@Param("userId") Integer userId, @Param("flag") Integer flag);

	/**
	 * 异常趟数详情
	 * @author  liuy
	 * @param routeId
	 * @return
	 * @date    2019/8/27 14:59
	 */
	JxcOwnerAbnormalVo ownerViewAbnormalProgress(Long routeId);

	/**
	 * 查询机械已接单和进行中的订单
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/6 10:24
	 */
	List<Map<String,Object>> getOwnerListByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 查出被解雇以及退出的订单 一并去除掉
	 * @param machineId
	 * @return
	 */
	List<Long> queryProjectOrderIdList(@Param("machineId") Integer machineId);

	/**
	 * 获取电子消纳券
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/10 16:36
	 */
	List<JxcSiteCouponVo> getSiteCouponByMachineId(Integer machineId);

	/**
	 * 订单详情
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/9/12 15:31
	 */
	JxcOwnerOrder getById(Long id);

	/**
	 * 查询机械已接单和进行中的订单
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/10/17 20:48
	 */
	Integer checkOwnerListByDriverId(@Param("driverId") Integer driverId);

}