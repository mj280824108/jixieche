package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMachineRemind;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcMachineRemindService extends BaseService<JxcMachineRemind> {
       /**
     * 前端分页查询机械提醒类型
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMachineRemind 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRemind jxcMachineRemind);
}