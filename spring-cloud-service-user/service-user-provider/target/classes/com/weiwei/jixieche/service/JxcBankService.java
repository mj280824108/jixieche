package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcBank;
import com.weiwei.jixieche.response.ResponseMessage;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcBankService extends BaseService<JxcBank> {
       /**
     * 前端分页查询银行表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcBank 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcBank jxcBank);

       /**
        * 根据id查询银行信息
        * @param id
        * @return
        */
       ResponseMessage<JxcBank> queryById(Long id);
}