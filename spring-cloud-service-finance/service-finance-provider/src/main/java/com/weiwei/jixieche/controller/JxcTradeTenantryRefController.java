package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcTradeTenantryRef;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcTradeTenantryRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="承租方支付趟数关联表")
@RestController
@RequestMapping("jxcTradeTenantryRef")
public class JxcTradeTenantryRefController {
       @Resource
       private JxcTradeTenantryRefService jxcTradeTenantryRefService;

       @ApiOperation(httpMethod="POST", value="前端分页查询承租方支付趟数关联表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcTradeTenantryRef> findByPageForFront(@RequestBody JxcTradeTenantryRef jxcTradeTenantryRef) {
              return this.jxcTradeTenantryRefService.findByPageForFront(jxcTradeTenantryRef.getPageNo(),jxcTradeTenantryRef.getPageSize(),jxcTradeTenantryRef);
       }

       @ApiOperation(httpMethod="POST", value="添加承租方支付趟数关联表")
       @PostMapping("/insert")
       public ResponseMessage<JxcTradeTenantryRef> insert(@RequestBody JxcTradeTenantryRef jxcTradeTenantryRef) {
              return this.jxcTradeTenantryRefService.addObj(jxcTradeTenantryRef);
       }

       @ApiOperation(httpMethod="POST", value="编辑承租方支付趟数关联表")
       @PostMapping("/edit")
       public ResponseMessage<JxcTradeTenantryRef> edit(@RequestBody JxcTradeTenantryRef jxcTradeTenantryRef) {
              return this.jxcTradeTenantryRefService.modifyObj(jxcTradeTenantryRef);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询承租方支付趟数关联表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcTradeTenantryRef> queryById(Integer id) {
              return this.jxcTradeTenantryRefService.queryObjById(id);
       }
}