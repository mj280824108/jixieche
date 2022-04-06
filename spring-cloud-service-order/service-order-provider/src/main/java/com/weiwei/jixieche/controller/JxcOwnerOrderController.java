package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcOwnerOrder;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcOwnerOrderService;
import com.weiwei.jixieche.vo.JxcOwnerOrderVo;
import com.weiwei.jixieche.vo.JxcRobOrderVo;
import com.weiwei.jixieche.vo.OwnerStatisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 机主订单管理
 * @Author: liuy
 * @Date: 2019/8/20 17:31
 */
@Api(tags = "机主订单表")
@RestController
@RequestMapping("jxcOwnerOrder")
public class JxcOwnerOrderController {
	@Resource
	private JxcOwnerOrderService jxcOwnerOrderService;

	/**
	 * 机主订单管理列表
	 *
	 * @param authorizationUser
	 * @param lastPageLastId
	 * @param orderType         1:已接单; 2:进行中; 3:已完结
	 * @return
	 * @author liuy
	 * @date 2019/8/20 15:45
	 */
	@ApiOperation("机主订单管理列表")
	@PostMapping("/getOwnerOrderList")
	public ResponseMessage getOwnerOrderList(AuthorizationUser authorizationUser,
	                                         @RequestParam(value = "lastPageLastId", required = false) String lastPageLastId,
	                                         @RequestParam("orderType") Integer orderType,
	                                         @RequestParam(value = "machineId", required = false) Integer machineId) {
		return this.jxcOwnerOrderService.getOwnerOrderList(authorizationUser, lastPageLastId, orderType, machineId);
	}

	/**
	 * 添加机主订单表
	 *
	 * @param jxcOwnerOrder
	 * @return
	 * @author liuy
	 * @date 2019/8/23 11:07
	 */
	@ApiOperation(httpMethod = "POST", value = "添加机主订单表")
	@PostMapping("/insert")
	public ResponseMessage<JxcOwnerOrder> insert(AuthorizationUser authorizationUser, @RequestBody JxcOwnerOrder jxcOwnerOrder) {
		return this.jxcOwnerOrderService.addOwnerOrder(authorizationUser, jxcOwnerOrder);
	}

	/**
	 * 机主订单-请假
	 *
	 * @param jxcOwnerOrder
	 * @return
	 * @author liuy
	 * @date 2019/8/23 11:07
	 */
	@ApiOperation(httpMethod = "POST", value = "机主订单-请假")
	@PostMapping("/edit")
	public ResponseMessage<JxcOwnerOrder> edit(@RequestBody JxcOwnerOrder jxcOwnerOrder) {
		return this.jxcOwnerOrderService.modifyObj(jxcOwnerOrder);
	}

	/**
	 * 订单详情
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/23 11:06
	 */
	@ApiOperation("订单详情")
	@PostMapping("/getOwnerOrderDetail")
	public ResponseMessage getOwnerOrderDetail(@RequestParam("ownerOrderId") Long ownerOrderId) {
		return this.jxcOwnerOrderService.getOwnerOrderDetail(ownerOrderId);
	}

	/**
	 * 接单列表
	 *
	 * @param jxcRobOrderVo
	 * @return
	 * @author liuy
	 * @date 2019/8/22 16:39
	 */
	@ApiOperation("接单列表")
	@PostMapping("/getProjectOrderList")
	public ResponseMessage getProjectOrderList(AuthorizationUser authorizationUser, @RequestBody JxcRobOrderVo jxcRobOrderVo) {
		return this.jxcOwnerOrderService.getProjectOrderList(jxcRobOrderVo);
	}

	@ApiOperation("取消订单、解雇司机、取消职位前的验证")
	@PostMapping("/cancelConfirm")
	public ResponseMessage cancelConfirm(AuthorizationUser user,@RequestParam("cancelType") Integer cancelType,@RequestParam("startDate") String startDate) {
		return this.jxcOwnerOrderService.cancelConfirm(user,cancelType,startDate);
	}

	/**
	 * 取消原因列表
	 *
	 * @param
	 * @return
	 * @author liuy
	 * @date 2019/8/23 10:13
	 */
	@ApiOperation("取消原因列表")
	@PostMapping("/getCancelReason")
	public ResponseMessage getCancelReason() {
		return this.jxcOwnerOrderService.getCancelReason();
	}

	/**
	 * 取消订单
	 *
	 * @param ownerOrderId 订单ID
	 * @param cancelId     取消原因ID
	 * @return
	 * @author liuy
	 * @date 2019/8/23 11:00
	 */
	@ApiOperation("取消订单")
	@PostMapping("/cancelOrder")
	public ResponseMessage cancelOrder(AuthorizationUser authorizationUser, @RequestParam("ownerOrderId") Long ownerOrderId, @RequestParam("cancelId") Long cancelId) {
		return this.jxcOwnerOrderService.cancelOrder(authorizationUser, ownerOrderId, cancelId);
	}

	/**
	 * 申请退出
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/23 14:57
	 */
	@ApiOperation("申请退出")
	@PostMapping("/applyOutOrder")
	public ResponseMessage applyOutOrder(@RequestParam("ownerOrderId") Long ownerOrderId, @RequestParam("leaveReason") String leaveReason) {
		return this.jxcOwnerOrderService.applyOutOrder(ownerOrderId, leaveReason);
	}

	/**
	 * 取消申请退出
	 *
	 * @param ownerOrderId
	 * @return
	 * @author liuy
	 * @date 2019/8/23 14:57
	 */
	@ApiOperation("取消申请退出")
	@PostMapping("/cancelApplyOrder")
	public ResponseMessage cancelApplyOrder(Long ownerOrderId) {
		return this.jxcOwnerOrderService.cancelApplyOrder(ownerOrderId);
	}

	/**
	 * 机主查看异常趟数列表
	 * @author  liuy
	 * @param flag 0-未处理 1-已处理
	 * @return
	 * @date    2019/8/27 13:44
	 */
	@ApiOperation("机主查看异常趟数列表")
	@PostMapping("/ownerAbnormalList")
	public ResponseMessage ownerAbnormalList(AuthorizationUser authorizationUser, @RequestParam(value = "lastPageLastId",required = false) Long lastPageLastId, @RequestParam("flag") Integer flag){
		return jxcOwnerOrderService.ownerAbnormalList(authorizationUser.getUserId(), lastPageLastId, flag);
	}

	/**
	 * 异常趟数详情
	 * @author  liuy
	 * @param routeId
	 * @return
	 * @date    2019/8/27 14:59
	 */
	@ApiOperation("异常趟数详情")
	@GetMapping("/ownerViewAbnormalProgress")
	public ResponseMessage ownerViewAbnormalProgress(@RequestParam("routeId") Long routeId){
		return jxcOwnerOrderService.ownerViewAbnormalProgress(routeId);
	}

	/**
	 * 机主首页收入数据统计按时间(车辆)
	 * @author  liuy
	 * @param ownerStatisticsVo
	 * @return
	 * @date    2019/9/2 10:46
	 */
	@ApiOperation("机主首页收入数据统计按时间(车辆)")
	@PostMapping("/ownerStatistics")
	public ResponseMessage<OwnerStatisticsVo> ownerStatistics(@RequestBody OwnerStatisticsVo ownerStatisticsVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		ownerStatisticsVo = jxcOwnerOrderService.getOwnerStatistics(ownerStatisticsVo);
		if (ownerStatisticsVo != null) {
			result.setData(ownerStatisticsVo);
		} else {
			result.setCode(ErrorCodeConstants.QUERY_ERORR.getId());
			result.setMessage(ErrorCodeConstants.QUERY_ERORR.getDescript());
		}
		return result;
	}

	/**
	 * 申请为机主验证
	 * @author  liuy
	 * @param user
	 * @return
	 * @date    2019/10/17 21:02
	 */
	@ApiOperation("申请为机主验证")
	@PostMapping("/checkOwnerOrderByDriverId")
    public ResponseMessage checkOwnerOrderByDriverId(AuthorizationUser user){
		return jxcOwnerOrderService.checkOwnerOrderByDriverId(user.getUserId());
    }

}