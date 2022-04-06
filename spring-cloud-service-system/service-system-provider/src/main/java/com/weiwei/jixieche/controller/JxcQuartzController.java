package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.service.JxcQuartzService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 定时任务
 * @author liuy
 * @date 2019-09-16 13:34
 */
@RestController
@RequestMapping("/quartz")
public class JxcQuartzController {

	@Resource
	private JxcQuartzService jxcQuartzService;

	/**
	 * 每晚12点更新承租方订单状态
	 */
	@PostMapping("/updateOrderState")
	public void updateOrderState(){
		jxcQuartzService.updateOrderState();
	}

	/**
	 * 更新机械行驶证、营运证状态
	 * @author  liuy
	 * @return
	 * @date    2019/9/2 15:39
	 */
	@PostMapping("/updateMachine")
	public void updateMachine(){
		jxcQuartzService.updateMachine();
	}

	/**
	 * 正在跑趟中的司机未打上班卡
	 * @author  liuy
	 * @return
	 * @date    2019/9/2 15:41
	 */
	@PostMapping("/getDriverNoClock")
	public void getDriverNoClock(){
		jxcQuartzService.getDriverNoClock();
	}

	/**
	 * 正在进行中的订单所对应的机械没有绑定司机
	 * @author  liuy
	 * @return
	 * @date    2019/9/2 15:41
	 */
	@PostMapping("/getOrderRunList")
	public void getOrderRunList(){
		jxcQuartzService.getOrderRunList();
	}

	/**
	 * 定时将超过有效期的职位的状态改为无效
	 * @author  liuy
	 * @return
	 * @date    2019/9/3 10:37
	 */
	@PostMapping("/updateJobStatus")
	public void updateJobStatus(){
		jxcQuartzService.updateJobStatus();
	}

	/**
	 * 强制打下班卡
	 * @author  liuy
	 * @return
	 * @date    2019/9/3 13:47
	 */
	@PostMapping("/forceClockOut")
	public void forceClockOut(){
		jxcQuartzService.forceClockOut();
	}

	/**
	 * 超过12小时机主申请退出,承租方未同意时强制更新为同意
	 * @author  liuy
	 * @return
	 * @date    2019/9/4 11:41
	 */
	@PostMapping("/updateOwnerApplyQuitStatus")
	public void updateOwnerApplyQuitStatus(){
		jxcQuartzService.updateOwnerApplyQuitStatus();
	}

	/**
	 * 中午12点 下午16点  晚上20点分别运行一次 发催款通知
	 */
	@PostMapping("/payNotice")
	public void payNotice(){
		jxcQuartzService.payNotice();
	}

	/**
	 * 每天晚上0点运行一次，将待支付的金额加入待办事项
	 */
	@PostMapping("/insertWaitHandleItem")
	public void insertWaitHandleItem(){
		jxcQuartzService.insertWaitHandleItem();
	}

	/**
	 * 评价：计算平均分
	 * @author  liuy
	 * @return
	 * @date    2019/9/5 15:37
	 */
	@PostMapping("/calculateAvgScore")
	public void calculateAvgScore(){
		jxcQuartzService.calculateAvgScore();
	}

	/**
	 * 更新兼职职位状态为进行中
	 * @author  liuy
	 * @return
	 * @date    2019/9/17 20:18
	 */
	@PostMapping("/updateShortJobStateById")
	public void updateShortJobStateById(){
		jxcQuartzService.updateShortJobStateById();
	}

	/**
	 * 生成司机账单
	 * @author  liuy
	 * @return
	 * @date    2019/9/23 18:56
	 */
	@PostMapping("/createDriverBill")
	public void createDriverBill(){
		jxcQuartzService.createDriverBill();
	}

	/**
	 * 每周二解冻冻结金额
	 */
	@PostMapping("/unfreeze")
	public void unfreeze(){
		jxcQuartzService.unfreeze();
	}

	/**
	 * 订单开始提醒
	 */
	@PostMapping("/orderStartNotice")
	public void orderStartNotice(){
		jxcQuartzService.orderStartNotice();
	}

	/**
	 * 24小时未付款的承租方限制发单
	 */
	@PostMapping("/stopSendOrder")
	public void stopSendOrder(){
		jxcQuartzService.stopSendOrder();
	}

	/**
	 * 2小时以后未处理的消纳券订单强制更新状态为已取消
	 * @author  liuy
	 * @return
	 * @date    2019/10/22 9:39
	 */
	@PostMapping("/updateSiteOrderFlag")
	public void updateSiteOrderFlag() {
		jxcQuartzService.updateSiteOrderFlag();
	}
}
