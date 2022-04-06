package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcPushDeviceToken;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcPushDeviceTokenService extends BaseService<JxcPushDeviceToken> {
       /**
     * 前端分页查询推送设备token表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcPushDeviceToken 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcPushDeviceToken jxcPushDeviceToken);
}