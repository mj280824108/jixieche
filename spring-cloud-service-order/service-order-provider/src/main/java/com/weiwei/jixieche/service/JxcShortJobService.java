package com.weiwei.jixieche.service;


import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.DriverJobVo;

/**
* @Description: 短期职位招聘
* @Author:      liuy
* @Date:  2019/8/15 13:51
*/
public interface JxcShortJobService {

	/**
	 * 短期职位招聘列表
	 * @author  liuy
	 * @param driverJobVo 查询条件
	 * 	 * @return
	 * @date    2019/8/15 13:58
	 */
	ResponseMessage getShortJobList(AuthorizationUser user, DriverJobVo driverJobVo);

	/**
	 * 发布短期职位信息
	 * @author  liuy
	 * @param jxcShortJob
	 * @return
	 * @date    2019/8/15 13:50
	 */
	ResponseMessage insert(JxcShortJob jxcShortJob);

	/**
	 * 更新短期职位信息
	 * @author  liuy
	 * @param jxcShortJob
	 * @return
	 * @date    2019/8/15 13:50
	 */
	ResponseMessage update(JxcShortJob jxcShortJob);

	/**
	 * 短期职位详情
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/15 13:54
	 */
	ResponseMessage getById(Integer id);

	/**
	 * 删除
	 * @author  liuy
	 * @param id
	 * @return
	 * @date    2019/8/15 15:13
	 */
	ResponseMessage deleteById(Integer id);
	
	/**
	 * 查询司机台班费用
	 * @author  liuy
	 * @param cityId
	 * @return  
	 * @date    2019/8/28 10:03
	 */
	ResponseMessage queryDriverTeamCost(Integer cityId);

	/**
	 * 关闭职位
	 * @author  liuy
	 * @param
	 * @return
	 * @date    2019/8/28 11:36
	 */
	ResponseMessage closeShortJob(Integer shortJobId);

	/**
	 * 接单职位司机列表
	 * @author  liuy
	* @param shortJobId
	 * @return
	 * @date    2019/8/30 14:01
	 */
	ResponseMessage getShortDriverList(AuthorizationUser user, Integer shortJobId, Integer pageNo, Integer pageSize);

	/**
	 *  接单职位司机-详情
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/30 15:11
	 */
	ResponseMessage getShortDriverDetail(AuthorizationUser user, Integer driverId, Integer shortJobId);

}
