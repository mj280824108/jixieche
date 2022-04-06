package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.response.ResponseMessage;

public interface JxcMachineService extends BaseService<JxcMachine> {
       /**
     * 前端分页查询机械表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMachine 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMachine jxcMachine);

       /**
        * 机械信息列表
        * @author  liuy
        * @param ownerUserId 机主用户Id
        * @param lastPageLastId 最后一条machineId
        * @param pageSize  每页显示条数
        * @return
        * @date    2019/8/14 19:49
        */
       ResponseMessage getMachineList(Integer ownerUserId, Integer lastPageLastId, Integer pageSize);

       /**
        * 删除机械
        * @author  liuy
        * @param id 机械ID
        * @return
        * @date    2019/8/14 19:33
        */
       ResponseMessage deleteById(Integer id);

       /**
        * 下拉机械列表接口
        * @author  liuy
       * @param ownerUserId
        * @return
        * @date    2019/8/20 17:00
        */
       ResponseMessage selectMachineList(Integer ownerUserId);

       /**
        * 更新机械状态为空闲中
        * @author  liuy
        * @param machineId
        * @return
        * @date    2019/9/29 15:07
        */
       ResponseMessage updateMachineById(Integer machineId);

       /**
        * 筛选车辆列表
        * @author  liuy
        * @param userId
        * @return
        * @date    2019/10/8 11:45
        */
       ResponseMessage getMachineSelectByUserId(Integer userId);

       /**
        * 车辆详情
        * @param id
        * @param projectOrderId
        * @return
        */
       ResponseMessage getById(Integer id, Long projectOrderId);

}