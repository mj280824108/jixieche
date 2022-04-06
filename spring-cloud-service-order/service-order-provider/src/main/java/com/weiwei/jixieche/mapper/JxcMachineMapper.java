package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.bean.JxcProject;
import com.weiwei.jixieche.bean.JxcProjectOrder;
import com.weiwei.jixieche.vo.JxcMachineSelectVo;
import com.weiwei.jixieche.vo.JxcMachineVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 车辆管理
 * @Author: liuy
 * @Date: 2019/8/14 9:03
 */
public interface JxcMachineMapper extends BaseMapper<JxcMachine> {

	/**
	 * 根据机械ID查询项目相关信息
	 *
	 * @param machineId 机械ID
	 * @return
	 * @author liuy
	 * @date 2019/8/14 18:58
	 */
	JxcProject getProjectById(@Param("machineId") Integer machineId);

	/**
	 * 根据机械ID查询正在进行中的消纳场地址
	 *
	 * @param machineId 机械ID
	 * @return
	 * @author liuy
	 * @date 2019/8/14 19:23
	 */
	String getSiteAddressById(@Param("machineId") Integer machineId);

	/**
	 * 机械列表查询
	 * @author  liuy
	 * @param ownerUserId 机主用户ID
	 * @param lastPageLastId 最后一条机械ID
	 * @param pageSize 每页显示条数
	 * @return
	 * @date    2019/8/14 20:00
	 */
	List<JxcMachineVo> getMachineList(@Param("ownerUserId") Integer ownerUserId, @Param("lastPageLastId") Integer lastPageLastId, @Param("pageSize") Integer pageSize);
	Integer hasNextPage(@Param("ownerUserId") Integer ownerUserId, @Param("lastPageLastId") Integer lastPageLastId);

	/**
	 * 车辆下拉列表
	 * @author  liuy
	 * @param ownerUserId
	 * @return
	 * @date    2019/8/28 16:53
	 */
	List<Map<String,Object>> selectMachineList(@Param("ownerUserId")Integer ownerUserId);

	/**
	 * 检查是否绑定司机
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/9/9 16:49
	 */
	Integer checkBindDriver(@Param("userId") Integer userId);

	/**
	 * 查询机械审核模式 1:自动; 2:手动
	 * @author  liuy
	 * @return
	 * @date    2019/9/11 15:23
	 */
	Integer getSetAuditMode();

	/**
	 * 解绑司机
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/12 16:00
	 */
	int updateMachineById(@Param("machineId") Integer machineId);

	/**
	 * 查询机械是否有未完成订单
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/28 18:38
	 */
	Integer checkOwnerOrderByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 查询机械是否有进行中订单(没有则更新订单状态为空闲中)
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/28 18:38
	 */
	Integer checkMachineRunByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 筛选车辆列表
	 * @author  liuy
	* @param userId
	 * @return
	 * @date    2019/10/8 11:40
	 */
	List<JxcMachineSelectVo> getMachineSelectAllByUserId(@Param("userId") Integer userId);

	/**
	 * 查询承租方订单信息
	 * @author  liuy
	 * @param projectOrderId
	 * @return
	 * @date    2019/10/14 15:24
	 */
	JxcProjectOrder getProjectOrderById(@Param("projectOrderId") Long projectOrderId);
}