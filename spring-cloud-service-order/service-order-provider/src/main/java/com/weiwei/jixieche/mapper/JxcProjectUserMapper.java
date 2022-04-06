package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.vo.JxcProjectOrderVo;
import com.weiwei.jixieche.vo.JxcProjectUserVo;
import com.weiwei.jixieche.vo.JxcSendSiteCouponVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Description: 承租方管理员扫一扫
* @Author:      liuy
* @Date:  2019/8/26 11:21
*/
public interface JxcProjectUserMapper {

	/**
	 * 根据车牌号码查询相关信息
	 * @author  liuy
	 * @param machineCarNo
	 * @return
	 * @date    2019/8/26 11:23
	 */
	JxcProjectUserVo getInfoByMachineCarNo(@Param("machineCarNo") String machineCarNo);

	/**
	 * 获取消纳劵电子券号
	 * @author  liuy
	 * @param jxcProjectUserVo
	 * @return
	 * @date    2019/8/26 14:20
	 */
	Long getCouponNumById(JxcProjectUserVo jxcProjectUserVo);

	/**
	 * 获取消纳劵电子券剩余数量
	 * @author  liuy
	 * @param jxcProjectUserVo
	 * @return
	 * @date    2019/8/26 14:23
	 */
	Integer getTotalCountCoupon(JxcProjectUserVo jxcProjectUserVo);
	
	/**
	 * 更新消纳劵电子券
	 * @author  liuy
	 * @param id
	 * @param machineId
	 * @return
	 * @date    2019/8/27 9:53
	 */
	int updateCouponById(@Param("id") Long id, @Param("machineId") Integer machineId, @Param("projectId") Integer projectId);

	/**
	 * 根据机械ID查询有未核销消纳劵
	 * @author  liuy
	 * @return
	 * @date    2019/9/5 17:57
	 */
	List<Long> getCouponByMachineCarNo(@Param("machineCarNo") String machineCarNo);

	/**
	 * 查询订单ID
	 * @param machineCarNo
	 * @return
	 */
	Long getOrderIdByMachineNo(@Param("machineCardNo") String machineCarNo);

	/**
	 * 更新消纳劵电子券 未使用
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/9/5 18:13
	 */
	Integer updateSiteCouponById(@Param("id") Long id);

	/**
	 * 根据车牌号查询车辆ID
	 * @param machineCarNo
	 * @return
	 */
	Integer getMachineIdByMachineCardNo(@Param("machineCarNo") String machineCarNo);

	/**
	 * 查询机械是否有正在上班的司机
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/21 9:42
	 */
	Integer getDriverIdByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 查询承租方管理员管理的项目ID
	 * @param UserId
	 * @return
	 */
	Integer getProjectIdByTenAdmin(@Param("userId") Integer UserId);

	/**
	 * 查询项目ID以及承租方ID
	 * @param UserId
	 * @return
	 */
	Map<String,Object> getProjectIdAndTenId(@Param("userId") Integer UserId);

	/**
	 * 查询承租方ID
	 * @param orderId
	 * @return
	 */
	Integer getTenIdByOrderId(@Param("orderId") Long orderId);

	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 */
	JxcProjectOrderVo getJxcProjectOrderVoById(@Param("orderId") Long orderId);

	/**
	 * 获取当前订单绑定的siteId
	 * @param orderId
	 * @return
	 */
	Integer getSiteIdByOrderId(@Param("orderId") Long orderId);

	/**
	 * 查询目前车辆身上的消纳券对应的消纳场
	 * @param machineId
	 * @return
	 */
	List<Integer> getSiteIdByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 统计消纳券总张数
	 * @param vo
	 * @return
	 */
	Integer countTotalSiteCoupon(@Param("vo") JxcSendSiteCouponVo vo);

	/**
	 * 统计今日已使用的张数
	 * @param vo
	 * @return
	 */
	Integer countUsedSiteCoupon(@Param("vo") JxcSendSiteCouponVo vo);

	/**
	 * 查询待发券的机械列表
	 * @param vo
	 * @return
	 */
	List<JxcSendSiteCouponVo> selectWaitSendCouponMachineList(@Param("vo") JxcSendSiteCouponVo vo);

	/**
	 * 查询已发券列表
	 * @param vo
	 * @return
	 */
	List<JxcSendSiteCouponVo> selectUsedCouponMachineList(@Param("vo") JxcSendSiteCouponVo vo);

}
