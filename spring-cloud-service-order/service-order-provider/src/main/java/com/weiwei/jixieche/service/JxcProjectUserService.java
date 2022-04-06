package com.weiwei.jixieche.service;

import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JxcProjectUserVo;
import com.weiwei.jixieche.vo.JxcSendSiteCouponVo;
import com.weiwei.jixieche.vo.SiteClockVo;
import com.weiwei.jixieche.vo.TabPageListVo;

/**
* @Description: 承租方管理员
* @Author:      liuy
* @Date:  2019/8/26 11:01
*/
public interface JxcProjectUserService {

	/**
	 * 承方管理员扫一扫
	 * @author  liuy
	* @param machineCarNo
	 * @return
	 * @date    2019/8/26 11:02
	 */
	ResponseMessage projectUserScan(AuthorizationUser user, String machineCarNo, Integer flag);

	/**
	 * 确认补上一趟的卡
	 * @param machineCarNo
	 * @return
	 */
	ResponseMessage confirmClock(String machineCarNo);

	/**
	 * 手动发券机械列表与手动发券当日记录列表
	 * @param user
	 * @param vo
	 * @return
	 */
	ResponseMessage waitSendCouponMachineList(AuthorizationUser user, JxcSendSiteCouponVo vo);

	/**
	 * 实体卡
	 * @author  liuy
	 * @param machineCarNo
	 * @return
	 * @date    2019/8/26 14:45
	 */
	ResponseMessage entityCardClock(String machineCarNo);

	/**
	 * 已入驻的消纳场的消纳场管理员扫码
	 * @param siteClockVo
	 * @return
	 */
	ResponseMessage siteAdminScan(AuthorizationUser user, SiteClockVo siteClockVo);

	/**
	 * 已入驻的消纳场的消纳场打卡 传消纳场管理员的token
	 * 未入驻的消纳场，传当前司机的token 以及扫码以后获取的消纳场ID
	 * @param user
	 * @param siteClockVo
	 * @return
	 */
	ResponseMessage siteClockIn(AuthorizationUser user, SiteClockVo siteClockVo);

	/**
	 * 消纳场管理员查看消纳券记录
	 * @param user
	 * @param tabPageListVo flag 1-正常记录 2-异常记录
	 * @return
	 */
	ResponseMessage selectSiteCouponList(AuthorizationUser user, TabPageListVo tabPageListVo);

}
