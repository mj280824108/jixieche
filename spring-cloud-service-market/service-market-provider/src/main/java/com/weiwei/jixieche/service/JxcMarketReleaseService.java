package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.MachineDetailsVo;
import com.weiwei.jixieche.vo.MarketTradeTabVo;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
public interface JxcMarketReleaseService extends BaseService<JxcMarketRelease> {

       /**
        * 发布市场
        * @param jxcMarketRelease
        * @return
        */
       ResponseMessage<JxcMarketRelease> addObj(AuthorizationUser user, JxcMarketRelease jxcMarketRelease);
       /**
     * 前端分页查询市场发布(出售)出租信息表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcMarketRelease 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketRelease jxcMarketRelease);

       /**
        * 查询市场交易列表
        * @param marketTradeTabVo
        * @return
        */
       ResponseMessage queryMarketTrade(MarketTradeTabVo marketTradeTabVo);

       /**
        * 查询市场搜索的Tab
        * @return
        */
       ResponseMessage queryMarketSearchTab();

       /**
        * 查询详情
        * @param machineDetailsVo
        * @return
        */
       ResponseMessage<JxcMarketRelease> queryObjById(MachineDetailsVo machineDetailsVo);
}