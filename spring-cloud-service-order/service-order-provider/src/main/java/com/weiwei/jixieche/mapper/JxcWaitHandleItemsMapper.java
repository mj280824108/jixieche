package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.vo.JxcShortDriverPayVo;
import com.weiwei.jixieche.vo.JxcWaitHandleItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 待办事项
* @Author:      liuy
* @Date:  2019/8/24 10:08
*/
public interface JxcWaitHandleItemsMapper extends BaseMapper<JxcWaitHandleItems> {

	/**
	 * 待办事项(上班司机设置和未添加司机)
	 * @author  liuy
	* @param ownerUserId
	 * @return
	 * @date    2019/8/24 15:39
	 */
	List<JxcWaitHandleItems> getHandelItemsList(@Param("ownerUserId") Integer ownerUserId);

	/**
	 * 兼职司机工资未支付
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/24 11:30
	 */
	List<JxcShortDriverPayVo> getDriverNoPayList(@Param("ownerUserId")Integer ownerUserId);

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
	List<JxcWaitHandleItems> getOrderRunList(@Param("machineId") Integer machineId);

	/**
	 * 清除异常行程待办事项
	 * @param routeId
	 */
	void clearAbnormalRoute(@Param("routeId") Long routeId);

	/**
	 * 清除退场申请待办事项
	 * @param ownerOrderId
	 */
	void clearOwnerQuitApply(@Param("ownerOrderId") Long ownerOrderId);

	/**
	 * 承租方查询待办事项列表
	 * @param userId
	 * @return
	 */
	List<JxcWaitHandleItemsVo> selectTenantryWaitHandle(@Param("userId") Integer userId);

	/**
	 * 查询是否已经存在正在跑趟的司机没有打上班卡的记录
	 * @author  liuy
	 * @param jxcWaitHandleItems
	 * @return
	 * @date    2019/9/21 17:06
	 */
	int checkWaitHandleItemById(JxcWaitHandleItems jxcWaitHandleItems);

}