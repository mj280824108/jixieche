package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcOwnerApplyQuit;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcOwnerApplyQuitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="机主申请退出")
@RestController
@RequestMapping("jxcOwnerApplyQuit")
public class JxcOwnerApplyQuitController {
       @Resource
       private JxcOwnerApplyQuitService jxcOwnerApplyQuitService;


       @ApiOperation(httpMethod="POST", value="添加")
       @PostMapping("/insert")
       public ResponseMessage<JxcOwnerApplyQuit> insert(@RequestBody JxcOwnerApplyQuit jxcOwnerApplyQuit) {
              return this.jxcOwnerApplyQuitService.addObj(jxcOwnerApplyQuit);
       }

       @ApiOperation(httpMethod="POST", value="编辑jxc_owner_apply_quit")
       @PostMapping("/edit")
       public ResponseMessage<JxcOwnerApplyQuit> edit(@RequestBody JxcOwnerApplyQuit jxcOwnerApplyQuit) {
              return this.jxcOwnerApplyQuitService.modifyObj(jxcOwnerApplyQuit);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询jxc_owner_apply_quit")
       @GetMapping("/queryById")
       public ResponseMessage<JxcOwnerApplyQuit> queryById(Integer id) {
              return this.jxcOwnerApplyQuitService.queryObjById(id);
       }
}