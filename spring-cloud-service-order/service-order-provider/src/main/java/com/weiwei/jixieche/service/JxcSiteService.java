package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcSite;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JxcSiteOrderVo;

/**
 * @Author 钟焕 
 * @Description
 * @Date 19:39 2019-08-19
 **/
public interface JxcSiteService extends BaseService<JxcSite> {
     /**
     * 前端分页查询消纳场表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcSite 查询条件
     * @return 
     */
     ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcSite jxcSite);

     /**
      * 承租方查询我的消纳场列表
      * @param user
      * @return
      */
     ResponseMessage selectMySiteList(AuthorizationUser user);

     /**
      * 查询更多消纳场
      * @param user
      * @param jxcSite
      * @return
      */
     ResponseMessage selectMoreSiteList(AuthorizationUser user,JxcSite jxcSite);

     /**
      * 查询单个消纳场详情
      * @param user
      * @param site
      * @return
      */
     ResponseMessage querySiteDetailById(AuthorizationUser user,JxcSite site);

     /**
      * 消纳场券下单
      * @param user
      * @param siteOrderVo
      * @return
      */
     ResponseMessage addSiteOrder(AuthorizationUser user,JxcSiteOrderVo siteOrderVo);

     /**
      * 查询某个消纳场的消纳券订单列表
      * @param siteOrderVo
      * @return
      */
     ResponseMessage selectSiteOrderListBySiteId(JxcSiteOrderVo siteOrderVo);

     /**
      * 根据消纳场券订单号查询订单详情
      * @param siteOrderVo
      * @return
      */
     ResponseMessage querySiteOrderByOrderId(JxcSiteOrderVo siteOrderVo);

     /**
      * 取消消纳场券订单
      * @param siteOrderVo
      * @return
      */
     ResponseMessage cancelSiteOrderById(JxcSiteOrderVo siteOrderVo);

     /**
      * 查询我的消纳券列表
      * @param siteOrderVo
      * @param flag 1:我的消纳券列表 2：历史消纳券列表
      * @return
      */
     ResponseMessage mySiteCoupon(JxcSiteOrderVo siteOrderVo,Integer flag);

     /**
      * 获取消纳场名称
      * @author  liuy
      * @param userId
      * @return
      * @date    2019/10/7 18:57
      */
     ResponseMessage getSiteByUserId(Integer userId);
}