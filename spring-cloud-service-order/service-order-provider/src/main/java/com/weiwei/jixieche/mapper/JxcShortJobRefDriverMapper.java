package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcShortJobRefDriver;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ShortJobVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 兼职职位与司机的关联
* @Author:      liuy
* @Date:  2019/8/16 15:04
*/
public interface JxcShortJobRefDriverMapper extends BaseMapper<JxcShortJobRefDriver> {

	/**
	 * 司机-工作管理
	 * @author  liuy
	 * @param shortJobVo 查询条件
	 * @return
	 * @date    2019/8/16 15:06
	 */
	List<ShortJobVo> getShortJobManager(ShortJobVo shortJobVo);

	/**
	 * 查询司机工作的是否有未支付的台班
	 * @author  liuy
	 * @param shortJobVo
	 * @return
	 * @date    2019/8/16 15:52
	 */
	int checkShortJobPayState(ShortJobVo shortJobVo);

	/**
	 * 司机-工作管理详情
	 * @author  liuy
	 * @param userId 司机ID
	 * @param shortJobId 职位ID
	 * @return
	 * @date    2019/8/16 16:20
	 */
	ShortJobVo getShortJobDetail(@Param("userId")Integer userId, @Param("shortJobId")Integer shortJobId);

	/**
	 * 临时司机取消订单
	 * @author  liuy
	 * @param driverUserId
	 * @param shortJobId
	 * @return
	 * @date    2019/8/17 9:48
	 */
	int deleteByShortIdUserId(@Param("driverUserId") Integer driverUserId, @Param("shortJobId") Integer shortJobId);

	/**
	 * 判断该职位是否已经被该司机接过
	 * @author  liuy
	 * @param shortJobId
	 * @return
	 * @date    2019/8/28 11:22
	 */
	Integer checkDriverTookOver(@Param("shortJobId") Integer shortJobId, @Param("driverId")Integer driverId);

	/**
	 * 查询司机正在进行中的职位ID
	 * @param driverId
	 * @return
	 */
	Integer getShortJobIdByDriverId(@Param("driverId") Integer driverId);

	/**
	 * 查询司机对应的机主的职位ID
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/9/17 10:57
	 */
	Integer getShortJobIdByDriverIdAndOwnerId(@Param("driverId") Integer driverId, @Param("ownerId") Integer ownerId);

	/**
	 * 解雇司机
	 * @author  liuy
	 * @param shortJobId
	 * @param driverId
	 * @return
	 * @date    2019/9/17 10:33
	 */
	void updateShortJobDriverById(@Param("state")Integer state, @Param("shortJobId") Integer shortJobId,
	                              @Param("driverId") Integer driverId, @Param("reason") String reason);

	/**
	 * 查询司机是否有接兼职职位
	 * @author  liuy
	* @param driverId
	 * @return
	 * @date    2019/9/17 18:18
	 */
	Integer queryShortJobByDriverId(@Param("driverId")Integer driverId);

	/**
	 * 删除兼职职位与司机关系
	 * @author  liuy
	 * @param shortJobId
	 * @return
	 * @date    2019/9/25 11:34
	 */
	void delShortJobRefDriverById(@Param("shortJobId")Integer shortJobId, @Param("driverId")Integer driverId);

}