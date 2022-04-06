package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketReleaseService;
import com.weiwei.jixieche.vo.MachineDetailsVo;
import com.weiwei.jixieche.vo.MarketTradeTabVo;
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
@Api(tags="市场模块--市场发布(出售)出租信息表")
@RestController
@RequestMapping("jxcMarketRelease")
public class JxcMarketReleaseController {
       @Resource
       private JxcMarketReleaseService jxcMarketReleaseService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场发布(出售)出租信息表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketRelease> findByPageForFront(@RequestBody JxcMarketRelease jxcMarketRelease) {
              return this.jxcMarketReleaseService.findByPageForFront(jxcMarketRelease.getPageNo(),jxcMarketRelease.getPageSize(),jxcMarketRelease);
       }

       @ApiOperation(httpMethod="POST", value="添加市场发布(出售)出租信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketRelease> insert(AuthorizationUser user, @RequestBody JxcMarketRelease jxcMarketRelease) {
              return this.jxcMarketReleaseService.addObj(user,jxcMarketRelease);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场发布(出售)出租信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketRelease> edit(@RequestBody JxcMarketRelease jxcMarketRelease) {
              return this.jxcMarketReleaseService.modifyObj(jxcMarketRelease);
       }

       @ApiOperation(httpMethod="POST", value="通过ID查询市场发布(出售)出租信息表")
       @PostMapping("/queryById")
       public ResponseMessage<JxcMarketRelease> queryById(@RequestBody MachineDetailsVo machineDetailsVo) {
              return this.jxcMarketReleaseService.queryObjById(machineDetailsVo);
       }

       @ApiOperation(httpMethod="POST", value="筛选机械/租赁/资源交易列表")
       @PostMapping("/queryMarketTrade")
       public ResponseMessage queryMarketTrade(@RequestBody MarketTradeTabVo marketTradeTabVo) {
              return this.jxcMarketReleaseService.queryMarketTrade(marketTradeTabVo);
       }

       @ApiOperation(httpMethod="POST", value="查询市场搜索的Tab")
       @PostMapping("/queryMarketSearchTab")
       public ResponseMessage queryMarketSearchTab() {
              return this.jxcMarketReleaseService.queryMarketSearchTab();
       }

}