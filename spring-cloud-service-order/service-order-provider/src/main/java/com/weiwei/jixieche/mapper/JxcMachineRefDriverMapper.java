package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.bean.JxcMachineRefDriver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 机械与司机或子账号绑定关系
* @Author:      liuy
* @Date:  2019/8/14 9:04
*/
public interface JxcMachineRefDriverMapper extends BaseMapper<JxcMachineRefDriver> {

	/**
	 * 解绑机械与司机关系
	 * @author  liuy
	 * @param jxcMachineRefDriver
	 * @return
	 * @date    2019/8/14 9:55
	 */
	int updateByMachineAndDriver(JxcMachineRefDriver jxcMachineRefDriver);

	/**
	 * 检查机械与司机是否存在关系
	 * @author  liuy
	 * @param jxcMachineRefDriver
	 * @return
	 * @date    2019/8/14 10:04
	 */
	JxcMachineRefDriver queryByMachineIdAndDriverId(JxcMachineRefDriver jxcMachineRefDriver);

	/**
	 * 查询司机所绑定的车辆信息
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/8/14 15:43
	 */
	JxcMachine getMachineCarNoById(@Param("userId") Integer userId);

	/**
	 * 绑车记录
	 * @author  liuy
	* @param userId
	 * @return
	 * @date    2019/8/22 17:25
	 */
	List<JxcMachineRefDriver> getMachineRefDriverList(@Param("userId") Integer userId);

	/**
	 * 解绑司机绑定的机械关系
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/10 15:38
	 */
	int updateByDriverId(Integer driverId);

	/**
	 * 查询司机所绑定的信息
	 * @author  liuy
	 * @param userId
	 * @return
	 * @date    2019/10/7 18:03
	 */
	JxcMachineRefDriver getMachineRefDriverById(@Param("userId") Integer userId);

	/**
	 * 机械是否绑定司机
	 * @author  liuy
	 * @param machineId
	 * @return
	 * @date    2019/9/25 13:59
	 */
	Integer getMachineBindDriver(@Param("machineId")Integer machineId);
}