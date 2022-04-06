package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMachineRefDriver;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JxcMachineRefDriverService extends BaseService<JxcMachineRefDriver> {

       /**
        * 绑车记录
        * @author  liuy
        * @param jxcMachineRefDriver
        * @return
        * @date    2019/8/22 17:25
        */
       ResponseMessage getMachineRefDriverList(JxcMachineRefDriver jxcMachineRefDriver);

       /**
        * 根据机械ID查询所绑定的司机列表
        * @author  liuy
       * @param machineId
        * @return
        * @date    2019/8/24 14:35
        */
       ResponseMessage getDriverListByMachineId(Integer machineId);

       /**
        * 查询司机用户所绑定的车牌号码
        * @author  liuy
        * @param userId
        * @return
        * @date    2019/9/21 11:24
        */
       ResponseMessage getMachineCarNoByUserId(Integer userId);

       /**
        * 绑车记录-解绑
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/10/7 17:16
        */
       ResponseMessage updateDriverAndOwner(AuthorizationUser authorizationUser, Integer ownerUserId, Integer machineId);

       /**
        * 绑车记录-查询司机已绑定车信息和绑定的机主信息
        * @param authorizationUser
        * @return
        */
       ResponseMessage getDriverBindMachineById(AuthorizationUser authorizationUser);


}