package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcShortJobRefDriver;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ShortJobVo;

public interface JxcShortJobRefDriverService extends BaseService<JxcShortJobRefDriver> {
       /**
     * 前端分页查询兼职职位与司机的关联表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcShortJobRefDriver 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcShortJobRefDriver jxcShortJobRefDriver);


       /**
        * 司机-工作管理
        * @author  liuy
        * @param shortJobVo 查询条件
        * @return
        * @date    2019/8/16 15:06
        */
       ResponseMessage getShortJobManager(ShortJobVo shortJobVo);

       /**
        * 司机-工作管理详情
        * @author  liuy
        * @param shortJobId 职位ID
        * @return
        * @date    2019/8/16 16:20
        */
       ResponseMessage getShortJobDetail(AuthorizationUser user, Integer shortJobId);

       /**
        * 司机取消订单
        * @author  liuy
        * @param driverUserId 司机用户ID
        * @param shortJobId 职位ID
        * @return
        * @date    2019/8/17 9:11
        */
       ResponseMessage driverCancelOrder(Integer driverUserId, Integer shortJobId);

       /**
        * 工作管理--日历打标记
        * @author  liuy
        * @param jobId
        * @return
        * @date    2019/9/3 19:27
        */
       ResponseMessage  singCalendar(AuthorizationUser user, Integer driverUserId, Integer jobId, String yearMonth);
}