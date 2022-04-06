package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketPointRecord;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketPointRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="市场模块--用户点赞记录表")
@RestController
@RequestMapping("jxcMarketPointRecord")
public class JxcMarketPointRecordController {
       @Resource
       private JxcMarketPointRecordService jxcMarketPointRecordService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户点赞记录表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketPointRecord> findByPageForFront(@RequestBody JxcMarketPointRecord jxcMarketPointRecord) {
              return this.jxcMarketPointRecordService.findByPageForFront(jxcMarketPointRecord.getPageNo(),jxcMarketPointRecord.getPageSize(),jxcMarketPointRecord);
       }

       @ApiOperation(httpMethod="POST", value="添加用户点赞记录表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketPointRecord> insert(AuthorizationUser user,@RequestBody JxcMarketPointRecord jxcMarketPointRecord) {
              return this.jxcMarketPointRecordService.addObj(user,jxcMarketPointRecord);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户点赞记录表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketPointRecord> edit(@RequestBody JxcMarketPointRecord jxcMarketPointRecord) {
              return this.jxcMarketPointRecordService.modifyObj(jxcMarketPointRecord);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户点赞记录表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketPointRecord> queryById(Integer id) {
              return this.jxcMarketPointRecordService.queryObjById(id);
       }
}