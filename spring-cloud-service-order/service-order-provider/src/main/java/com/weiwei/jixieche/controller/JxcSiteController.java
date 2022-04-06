package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcSite;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcSiteService;
import com.weiwei.jixieche.vo.JxcSiteOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Author 钟焕
 * @Description
 * @Date 17:05 2019-08-19
 **/
@Api(tags="消纳场表")
@RestController
@RequestMapping("jxcSite")
public class JxcSiteController {
       @Resource
       private JxcSiteService jxcSiteService;

       @ApiOperation(httpMethod="POST", value="前端分页查询消纳场表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcSite> findByPageForFront(@RequestBody JxcSite jxcSite) {
              return this.jxcSiteService.findByPageForFront(jxcSite.getPageNo(),jxcSite.getPageSize(),jxcSite);
       }

       @ApiOperation(httpMethod="POST", value="承租方查询我的消纳场列表")
       @PostMapping("/mySiteList")
       public ResponseMessage mySiteList(AuthorizationUser user) {
              return this.jxcSiteService.selectMySiteList(user);
       }

       @ApiOperation(httpMethod="POST", value="承租方查询更多消纳场列表查询消纳场详情(城市ID,经度，纬度)")
       @PostMapping("/moreSiteList")
       public ResponseMessage moreSiteList(AuthorizationUser user,@RequestBody JxcSite jxcSite) {
              return this.jxcSiteService.selectMoreSiteList(user,jxcSite);
       }

       @ApiOperation(httpMethod="GET", value="查询消纳场详情(参数：id,经度，纬度)")
       @PostMapping("/querySiteDetail")
       public ResponseMessage<JxcSite> queryById(AuthorizationUser user,@RequestBody JxcSite jxcSite) {
              return this.jxcSiteService.querySiteDetailById(user,jxcSite);
       }

       @ApiOperation(httpMethod="POST", value="下单买消纳券")
       @PostMapping("/addSiteOrder")
       public ResponseMessage addSiteOrder(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              return this.jxcSiteService.addSiteOrder(user,siteOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="查询某个消纳场的订单列表")
       @PostMapping("/mySiteOrderList")
       public ResponseMessage mySiteOrderList(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              siteOrderVo.setTenantryId(user.getUserId());
              return this.jxcSiteService.selectSiteOrderListBySiteId(siteOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="查询消纳券订单详情")
       @PostMapping("/querySiteOrderDetail")
       public ResponseMessage querySiteOrderDetailById(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              siteOrderVo.setTenantryId(user.getUserId());
              return this.jxcSiteService.querySiteOrderByOrderId(siteOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="取消消纳券订单（参数：id）")
       @PostMapping("/cancelSiteOrder")
       public ResponseMessage cancelSiteOrder(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              siteOrderVo.setTenantryId(user.getUserId());
              return this.jxcSiteService.cancelSiteOrderById(siteOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="我的消纳券列表")
       @PostMapping("/mySiteCouponList")
       public ResponseMessage mySiteCouponList(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              siteOrderVo.setTenantryId(user.getUserId());
              return this.jxcSiteService.mySiteCoupon(siteOrderVo,1);
       }

       @ApiOperation(httpMethod="POST", value="历史消纳券列表")
       @PostMapping("/historySiteCouponList")
       public ResponseMessage historySiteCouponList(AuthorizationUser user,@RequestBody JxcSiteOrderVo siteOrderVo) {
              siteOrderVo.setTenantryId(user.getUserId());
              return this.jxcSiteService.mySiteCoupon(siteOrderVo,2);
       }

       @ApiOperation(httpMethod="POST", value="获取消纳场信息")
       @PostMapping("/getSiteByUserId")
       public ResponseMessage getSiteByUserId(AuthorizationUser user){
              return this.jxcSiteService.getSiteByUserId(user.getUserId());
       }


}