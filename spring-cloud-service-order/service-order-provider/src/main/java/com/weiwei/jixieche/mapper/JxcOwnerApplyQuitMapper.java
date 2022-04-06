package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcOwnerApplyQuit;
import com.weiwei.jixieche.vo.JxcOwnerApplyQuitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description: 机主申请退出
* @Author:      liuy
* @Date:  2019/9/4 10:13
*/
public interface JxcOwnerApplyQuitMapper extends BaseMapper<JxcOwnerApplyQuit> {

	/**
	 * 删除机主申请退出记录
	 * @author  liuy
	 * @param ownerOrderId
	 * @return
	 * @date    2019/9/4 10:54
	 */
	int deleteByOrderId(Long ownerOrderId);

	/**
	 * 承租方查询机主退场申请记录
	 * @param userId
	 * @param orderId
	 * @param ownerOrderId
	 * @return
	 */
	List<JxcOwnerApplyQuitVo> selectQuitList(@Param("userId") Integer userId,@Param("orderId") Long orderId,@Param("ownerOrderId") Long ownerOrderId);


	/**
	 * 查询退出申请记录
	 * @param ownerOrderId
	 * @return
	 */
	JxcOwnerApplyQuitVo queryByOwnerOrderId(@Param("ownerOrderId") Long ownerOrderId);

}