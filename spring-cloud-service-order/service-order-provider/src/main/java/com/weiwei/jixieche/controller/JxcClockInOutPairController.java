package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcClockInOutPair;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcClockInOutPairService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Api(tags="司机台班打卡配对表")
@RestController
@RequestMapping("jxcClockInOutPair")
public class JxcClockInOutPairController {
       @Resource 
       private JxcClockInOutPairService jxcClockInOutPairService;

       @ApiOperation(httpMethod="POST", value="前端分页查询司机台班打卡配对表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcClockInOutPair> findByPageForFront(@RequestBody JxcClockInOutPair jxcClockInOutPair) {
              return this.jxcClockInOutPairService.findByPageForFront(jxcClockInOutPair.getPageNo(),jxcClockInOutPair.getPageSize(),jxcClockInOutPair);
       }

       @ApiOperation(httpMethod="POST", value="添加司机台班打卡配对表")
       @PostMapping("/insert")
       public ResponseMessage<JxcClockInOutPair> insert(@RequestBody JxcClockInOutPair jxcClockInOutPair) {
              return this.jxcClockInOutPairService.addObj(jxcClockInOutPair);
       }

       @ApiOperation(httpMethod="POST", value="编辑司机台班打卡配对表")
       @PostMapping("/edit")
       public ResponseMessage<JxcClockInOutPair> edit(@RequestBody JxcClockInOutPair jxcClockInOutPair) {
              return this.jxcClockInOutPairService.modifyObj(jxcClockInOutPair);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询司机台班打卡配对表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcClockInOutPair> queryById(Integer id) {
              return this.jxcClockInOutPairService.queryObjById(id);
       }
}