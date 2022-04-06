package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcShortMsgRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcShortMsgRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
@Api(tags="公共模块--短信记录表")
@RestController
@RequestMapping("jxcShortMsgRecord")
public class JxcShortMsgRecordController {
       @Resource
       private JxcShortMsgRecordService jxcShortMsgRecordService;

       @ApiOperation(httpMethod="POST", value="前端分页查询短信记录表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcShortMsgRecord> findByPageForFront(@RequestBody JxcShortMsgRecord jxcShortMsgRecord) {
              return this.jxcShortMsgRecordService.findByPageForFront(jxcShortMsgRecord.getPageNo(),jxcShortMsgRecord.getPageSize(),jxcShortMsgRecord);
       }

       @ApiOperation(httpMethod="POST", value="添加短信记录表")
       @PostMapping("/insert")
       public ResponseMessage<JxcShortMsgRecord> insert(@RequestBody JxcShortMsgRecord jxcShortMsgRecord) {
              return this.jxcShortMsgRecordService.addObj(jxcShortMsgRecord);
       }

       @ApiOperation(httpMethod="POST", value="编辑短信记录表")
       @PostMapping("/edit")
       public ResponseMessage<JxcShortMsgRecord> edit(@RequestBody JxcShortMsgRecord jxcShortMsgRecord) {
              return this.jxcShortMsgRecordService.modifyObj(jxcShortMsgRecord);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询短信记录表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcShortMsgRecord> queryById(Integer id) {
              return this.jxcShortMsgRecordService.queryObjById(id);
       }
}