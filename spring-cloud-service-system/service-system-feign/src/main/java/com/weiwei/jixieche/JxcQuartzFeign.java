package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcQuartzFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
* @Description: 定时任务
* @Author:      liuy
* @Date:  2019/9/16 13:45
*/
@FeignClient(value = "SYSTEM-SERVICE-PROVIDER", fallback = JxcQuartzFeignHystrix.class)
public interface JxcQuartzFeign {

    /**
     * 每晚12点更新承租方订单状态
     */
    @PostMapping("/quartz/updateOrderState")
    void updateOrderState();

    /**
     * 更新机械行驶证、营运证状态
     * @author  liuy
     * @return
     * @date    2019/9/2 15:39
     */
    @PostMapping("/quartz/updateMachine")
    void updateMachine();

    /**
     * 正在跑趟中的司机未打上班卡
     * @author  liuy
     * @return
     * @date    2019/9/2 15:41
     */
    @PostMapping("/quartz/getDriverNoClock")
    void getDriverNoClock();

    /**
     * 正在进行中的订单所对应的机械没有绑定司机
     * @author  liuy
     * @return
     * @date    2019/9/2 15:41
     */
    @PostMapping("/quartz/getOrderRunList")
    void getOrderRunList();

    /**
     * 定时将超过有效期的职位的状态改为无效
     * @author  liuy
     * @return
     * @date    2019/9/3 10:37
     */
    @PostMapping("/quartz/updateJobStatus")
    void updateJobStatus();

    /**
     * 强制打下班卡
     * @author  liuy
     * @return
     * @date    2019/9/3 13:47
     */
    @PostMapping("/quartz/forceClockOut")
    void forceClockOut();

    /**
     * 超过12小时机主申请退出,承租方未同意时强制更新为同意
     * @author  liuy
     * @return
     * @date    2019/9/4 11:41
     */
    @PostMapping("/quartz/updateOwnerApplyQuitStatus")
    void updateOwnerApplyQuitStatus();

    /**
     * 中午12点 下午16点  晚上20点分别运行一次 发催款通知
     */
    @PostMapping("/quartz/payNotice")
    void payNotice();

    /**
     * 每天晚上0点运行一次，将待支付的金额加入待办事项
     */
    @PostMapping("/quartz/insertWaitHandleItem")
    void insertWaitHandleItem();

    /**
     * 评价：计算平均分
     * @author  liuy
     * @return
     * @date    2019/9/5 15:37
     */
    @PostMapping("/quartz/calculateAvgScore")
    void calculateAvgScore();

    /**
     * 更新兼职职位状态为进行中
     * @author  liuy
     * @return
     * @date    2019/9/17 20:18
     */
    @PostMapping("/quartz/updateShortJobStateById")
    void updateShortJobStateById();

    /**
     * 生成司机账单
     * @author  liuy
     * @return
     * @date    2019/9/23 18:56
     */
    @PostMapping("/quartz/createDriverBill")
    void createDriverBill();

    /**
     * 每周二解冻上周金额
     */
    @PostMapping("/quartz/unfreeze")
    void unfreeze();

    /**
     * 订单开始提醒
     */
    @PostMapping("/quartz/orderStartNotice")
    void orderStartNotice();

    /**
     * 24小时未付款的承租方限制发单
     */
    @PostMapping("/quartz/stopSendOrder")
    void stopSendOrder();

    /**
     * 2小时以后未处理的消纳券订单强制更新状态为已取消
     * @author  liuy
     * @return
     * @date    2019/10/22 9:39
     */
    @PostMapping("/quartz/updateSiteOrderFlag")
    void updateSiteOrderFlag();
}
