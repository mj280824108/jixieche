package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import org.apache.ibatis.annotations.Param;

public interface JxcDriverRefOwnerMapper extends BaseMapper<JxcDriverRefOwner> {

	/**
	 * 解雇司机
	 * @author  liuy
	 * @param ownerId
	 * @param driverId
	 * @return
	 * @date    2019/9/10 15:29
	 */
	int updateByOwnerIdAndDriverId(@Param("ownerId") Integer ownerId,
	                               @Param("driverId") Integer driverId,
	                               @Param("state")Integer state,
	                               @Param("shortJobId") Integer shortJobId);

	/**
	 * 兼职司机支付完成后解除司机与机主关系
	 * @author  liuy
	 * @param shortJobId
	 * @param driverId
	 * @return
	 * @date    2019/9/10 15:29
	 */
	int updateByShortJobIdAndDriverId(@Param("shortJobId") Integer shortJobId,
	                               @Param("driverId") Integer driverId);
}