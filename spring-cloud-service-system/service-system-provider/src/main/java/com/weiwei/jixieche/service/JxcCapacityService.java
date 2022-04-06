package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcCapacity;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcCapacityService extends BaseService<JxcCapacity> {
       /**
     * 前端分页查询方量字典表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcCapacity 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcCapacity jxcCapacity);

       /**
        *根据城市id查询方量
        * @param jxcCapacity
        * @return
        */
       ResponseMessage<JxcCapacity> queryByCityId(JxcCapacity jxcCapacity);

}