package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketShops;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketShopsService;
import com.weiwei.jixieche.vo.SearchShopsListVo;
import com.weiwei.jixieche.vo.ShopDetailsVo;
import com.weiwei.jixieche.vo.ShopsPointNumberVo;
import com.weiwei.jixieche.vo.UserShopsInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Api(tags="市场模块--市场店铺表")
@RestController
@RequestMapping("jxcMarketShops")
public class JxcMarketShopsController {
       @Resource
       private JxcMarketShopsService jxcMarketShopsService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场店铺表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketShops> findByPageForFront(@RequestBody JxcMarketShops jxcMarketShops) {
              return this.jxcMarketShopsService.findByPageForFront(jxcMarketShops.getPageNo(),jxcMarketShops.getPageSize(),jxcMarketShops);
       }

       @ApiOperation(httpMethod="POST", value="添加市场店铺表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketShops> insert(AuthorizationUser user, @RequestBody JxcMarketShops jxcMarketShops) {
              return this.jxcMarketShopsService.addObj(user,jxcMarketShops);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场店铺表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketShops> edit(@RequestBody JxcMarketShops jxcMarketShops) {
              return this.jxcMarketShopsService.modifyObj(jxcMarketShops);
       }

       @ApiOperation(httpMethod="POST", value="通过ID查询市场店铺表")
       @PostMapping("/queryById")
       public ResponseMessage<JxcMarketShops> queryById(@RequestBody ShopDetailsVo shopDetailsVo) {
              return this.jxcMarketShopsService.queryObjById(shopDetailsVo);
       }

       @ApiOperation(httpMethod="POST", value="店铺浏览量点击量+1操作")
       @PostMapping("/operation")
       public ResponseMessage operation(@RequestBody ShopsPointNumberVo shopsPointNumberVo) {
              return this.jxcMarketShopsService.operation(shopsPointNumberVo);
       }

       @ApiOperation(httpMethod="POST", value="店铺搜索认证(距离,人气,标题)")
       @PostMapping("/searchShopsList")
       public ResponseMessage<SearchShopsListVo> searchShopsList(@RequestBody SearchShopsListVo searchShopsListVo) {
              return this.jxcMarketShopsService.searchShopsList(searchShopsListVo);
       }

       @ApiOperation(httpMethod="POST", value="查询我的店铺")
       @PostMapping("/queryOwnShopsInfo")
       public ResponseMessage<UserShopsInfoVo> queryOwnShopsInfo(AuthorizationUser user,@RequestBody UserShopsInfoVo userShopsInfoVo) {
              return this.jxcMarketShopsService.queryOwnShopsInfo(user,userShopsInfoVo);
       }

       @ApiOperation(httpMethod="POST", value="查询用户店铺是否存在")
       @PostMapping("/queryUserShopsExist")
       public ResponseMessage queryUserShopsExist(AuthorizationUser user) {
              return this.jxcMarketShopsService.queryUserShopsExist(user);
       }


}