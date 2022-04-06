package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketBanner;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="市场banner表")
@RestController
@RequestMapping("jxcMarketBanner")
public class JxcMarketBannerController {
       @Resource
       private JxcMarketBannerService jxcMarketBannerService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场banner表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketBanner> findByPageForFront(@RequestBody JxcMarketBanner jxcMarketBanner) {
              return this.jxcMarketBannerService.findByPageForFront(jxcMarketBanner.getPageNo(),jxcMarketBanner.getPageSize(),jxcMarketBanner);
       }

       @ApiOperation(httpMethod="POST", value="添加市场banner表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketBanner> insert(@RequestBody JxcMarketBanner jxcMarketBanner) {
              return this.jxcMarketBannerService.addObj(jxcMarketBanner);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场banner表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketBanner> edit(@RequestBody JxcMarketBanner jxcMarketBanner) {
              return this.jxcMarketBannerService.modifyObj(jxcMarketBanner);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询市场banner表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketBanner> queryById(Integer id) {
              return this.jxcMarketBannerService.queryObjById(id);
       }
}