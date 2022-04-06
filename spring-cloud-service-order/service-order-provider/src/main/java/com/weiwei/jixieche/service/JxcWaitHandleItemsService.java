package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;

/**
 * @Description: 待办事项
 * @Author: liuy
 * @Date: 2019/8/24 10:08
 */
public interface JxcWaitHandleItemsService extends BaseService<JxcWaitHandleItems> {

    /**
     * 机主待办事项列表
     *
     * @param ownerUserId
     * @return
     * @author liuy
     * @date 2019/8/24 10:24
     */
    ResponseMessage getOwnerHandelItemsList(Integer ownerUserId, Integer pageNo, Integer pageSize);

    /**
     * 正在跑趟中的司机未打上班卡
     *
     * @return
     * @author liuy
     * @date 2019/8/24 13:54
     */
    ResponseMessage getDriverNoClock();

    /**
     * 正在进行中的订单所对应的机械没有绑定司机
     *
     * @return
     * @author liuy
     * @date 2019/8/24 13:54
     */
    ResponseMessage getOrderRunList(Integer machineId);

    /**
     * 承租方查询待办事项列表
     * @param user
     * @param pageNo
     * @param pageSize
     * @return
     */
    ResponseMessage selectTenantryWaitHandleList(AuthorizationUser user,Integer pageNo,Integer pageSize);
}