package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcRole;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcRoleService extends BaseService<JxcRole> {
       /**
     * 前端分页查询角色表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcRole 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcRole jxcRole);
}