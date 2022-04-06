package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcBank;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--银行表")
@RestController
@RequestMapping("jxcBank")
public class JxcBankController {
       @Resource
       private JxcBankService jxcBankService;

       @ApiOperation(httpMethod="POST", value="前端分页查询银行表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcBank> findByPageForFront(@RequestBody JxcBank jxcBank) {
              return this.jxcBankService.findByPageForFront(jxcBank.getPageNo(),jxcBank.getPageSize(),jxcBank);
       }

       @ApiOperation(httpMethod="POST", value="添加银行表")
       @PostMapping("/insert")
       public ResponseMessage<JxcBank> insert(@RequestBody JxcBank jxcBank) {
              return this.jxcBankService.addObj(jxcBank);
       }

       @ApiOperation(httpMethod="POST", value="编辑银行表")
       @PostMapping("/edit")
       public ResponseMessage<JxcBank> edit(@RequestBody JxcBank jxcBank) {
              return this.jxcBankService.modifyObj(jxcBank);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询银行表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcBank> queryById(@RequestParam(value="id", required=true)Long id) {
              return this.jxcBankService.queryById(id);
       }
}