package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcTradeTenantryService;
import com.weiwei.jixieche.vo.TenTradeRecordListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="承租方交易流水表")
@RestController
@RequestMapping("jxcTradeTenantry")
public class JxcTradeTenantryController {
       @Resource
       private JxcTradeTenantryService jxcTradeTenantryService;

       @ApiOperation(httpMethod="POST", value="前端分页查询承租方交易流水表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcTradeTenantry> findByPageForFront(@RequestBody JxcTradeTenantry jxcTradeTenantry) {
              return this.jxcTradeTenantryService.findByPageForFront(jxcTradeTenantry.getPageNo(),jxcTradeTenantry.getPageSize(),jxcTradeTenantry);
       }

       @ApiOperation(httpMethod="POST", value="查询承租方交易明细")
       @PostMapping("/queryTenTradeRecord")
       public ResponseMessage queryTenTradeRecord(@RequestBody TenTradeRecordListVo tenTradeRecordListVo) {
              return this.jxcTradeTenantryService.queryTenTradeRecord(tenTradeRecordListVo);
       }

      /* @ApiOperation(httpMethod="POST", value="添加承租方交易流水表")
       @PostMapping("/insert")
       public ResponseMessage<JxcTradeTenantry> insert(@RequestBody JxcTradeTenantry jxcTradeTenantry) {
              return this.jxcTradeTenantryService.addObj(jxcTradeTenantry);
       }*/

       /*@ApiOperation(httpMethod="POST", value="编辑承租方交易流水表")
       @PostMapping("/edit")
       public ResponseMessage<JxcTradeTenantry> edit(@RequestBody JxcTradeTenantry jxcTradeTenantry) {
              return this.jxcTradeTenantryService.modifyObj(jxcTradeTenantry);
       }*/

       @ApiOperation(httpMethod="GET", value="通过ID查询承租方交易流水表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcTradeTenantry> queryById(@RequestParam(value="id",required = true) Integer id) {
              return this.jxcTradeTenantryService.queryObjById(id);
       }
}