package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcUserConfirm;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcUserConfirmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="用户模块--用户认证信息表")
@RestController
@RequestMapping("jxcUserConfirm")
public class JxcUserConfirmController {
       @Resource
       private JxcUserConfirmService jxcUserConfirmService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户认证信息表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcUserConfirm> findByPageForFront(@RequestBody JxcUserConfirm jxcUserConfirm) {
              return this.jxcUserConfirmService.findByPageForFront(jxcUserConfirm.getPageNo(),jxcUserConfirm.getPageSize(),jxcUserConfirm);
       }

       @ApiOperation(httpMethod="POST", value="添加用户认证信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcUserConfirm> insert(@RequestBody JxcUserConfirm jxcUserConfirm) {
              return this.jxcUserConfirmService.addObj(jxcUserConfirm);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户认证信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcUserConfirm> edit(@RequestBody JxcUserConfirm jxcUserConfirm) {
              return this.jxcUserConfirmService.modifyObj(jxcUserConfirm);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户认证信息表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcUserConfirm> queryById(@RequestParam(value = "id",required = true) Integer id) {
              return this.jxcUserConfirmService.queryObjById(id);
       }
}