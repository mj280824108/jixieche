package com.weiwei.jixieche.service;

/**
 * @Author zhong huan
 * @Date 2019-09-02 14:21
 * @Description 定时任务用
 */
public interface JxcQuartzService {
    /**
     * 每晚12点更新承租方订单状态
     */
    void updateOrderState();

    /**
     * 更新机械行驶证、营运证状态
     * @author  liuy
     * @return
     * @date    2019/9/2 15:39
     */
    void updateMachine();

    /**
     * 正在跑趟中的司机未打上班卡
     * @author  liuy
     * @return
     * @date    2019/9/2 15:41
     */
    void getDriverNoClock();

    /**
     * 正在进行中的订单所对应的机械没有绑定司机
     * @author  liuy
     * @return
     * @date    2019/9/2 15:41
     */
    void getOrderRunList();

    /**
     * 定时将超过有效期的职位的状态改为无效
     * @author  liuy
     * @return
     * @date    2019/9/3 10:37
     */
    void updateJobStatus();

    /**
     * 强制打下班卡
     * @author  liuy
     * @return
     * @date    2019/9/3 13:47
     */
    void forceClockOut();

    /**
     * 超过12小时机主申请退出,承租方未同意时强制更新为同意
     * @author  liuy
     * @return
     * @date    2019/9/4 11:41
     */
    void updateOwnerApplyQuitStatus();

    /**
     * 中午12点 下午16点  晚上20点分别运行一次 发催款通知
     */
    void payNotice();

    /**
     * 每天晚上0点运行一次，将待支付的金额加入待办事项
     */
    void insertWaitHandleItem();

    /**
     * 评价：计算平均分
     * @author  liuy
     * @return
     * @date    2019/9/5 15:37
     */
    void calculateAvgScore();

    /**
     * 更新兼职职位状态为进行中
     * @author  liuy
     * @return
     * @date    2019/9/17 20:18
     */
    void updateShortJobStateById();

    /**
     * 生成司机账单
     * @author  liuy
     * @return
     * @date    2019/9/23 18:56
     */
    void createDriverBill();

    /**
     * 每周二将上周冻结金额转到可用余额
     */
    void unfreeze();

    /**
     * 订单即将开始提醒
     */
    void orderStartNotice();

    /**
     * 24小时未付款的承租方限制发单
     */
    void stopSendOrder();

    /**
     * 2小时以后未处理的订单更新消纳券订单状态为已取消
     * @author  liuy
     * @return
     * @date    2019/10/22 9:39
     */
    void updateSiteOrderFlag();
}
