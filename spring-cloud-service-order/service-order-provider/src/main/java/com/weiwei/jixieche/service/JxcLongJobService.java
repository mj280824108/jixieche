package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcLongJob;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.DriverJobVo;

/**
* @Description: 长期司机招聘表
* @Author:      liuy
* @Date:  2019/8/15 10:12
*/
public interface JxcLongJobService extends BaseService<JxcLongJob> {
       /**
     * 前端分页查询长期司机招聘表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcLongJob 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcLongJob jxcLongJob);

       /**
        * 长期司机招聘列表
        * @author  liuy
        * @param driverJobVo 查询条件
        * @return
        * @date    2019/8/15 11:37
        */
       ResponseMessage getLongJobList(DriverJobVo driverJobVo);

       /**
        * 删除
        * @author  liuy
        * @param id
        * @return
        * @date    2019/8/15 15:13
        */
       ResponseMessage deleteById(Integer id);

       /**
        * 工程类型列表
        * @author  liuy
        * @return
        * @date    2019/8/27 14:07
        */
       ResponseMessage getProjectTypeList();
}