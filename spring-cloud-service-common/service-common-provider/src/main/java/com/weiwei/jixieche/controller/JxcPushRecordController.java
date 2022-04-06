package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPushRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPushRecordService;
import com.weiwei.jixieche.vo.JpushRecordUpdateVo;
import com.weiwei.jixieche.vo.PushRecordTypeUnReadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/

@Api(tags="公共模块--消息推送记录表")
@RestController
@RequestMapping("jxcPushRecord")
public class JxcPushRecordController {
       @Resource
       private JxcPushRecordService jxcPushRecordService;

       @ApiOperation(httpMethod="POST", value="前端分页查询消息推送记录表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPushRecord> findByPageForFront(@RequestBody JxcPushRecord jxcPushRecord) {
              return this.jxcPushRecordService.findByPageForFront(jxcPushRecord.getPageNo(),jxcPushRecord.getPageSize(),jxcPushRecord);
       }

       @ApiOperation(httpMethod="POST", value="查询用户未读消息类型列表")
       @PostMapping("/queryUnReadPushRecordType")
       public ResponseMessage<PushRecordTypeUnReadVo> queryUnReadPushRecordType(@RequestBody PushRecordTypeUnReadVo pushRecordTypeUnReadVo) {
              return this.jxcPushRecordService.queryUnReadPushRecordType(pushRecordTypeUnReadVo);
       }

       @ApiOperation(httpMethod="POST", value="批量修改用户消息记录读取状态")
       @PostMapping("/updateBatchPushRecordStatus")
       public ResponseMessage updateBatchPushRecordStatus(@RequestBody JpushRecordUpdateVo jpushRecordUpdateVo) {
              return this.jxcPushRecordService.updateBatchPushRecordStatus(jpushRecordUpdateVo);
       }

       @ApiOperation(httpMethod="POST", value="添加消息推送记录表")
       @PostMapping("/insert")
       public ResponseMessage<JxcPushRecord> insert(@RequestBody JxcPushRecord jxcPushRecord) {
              return this.jxcPushRecordService.addObj(jxcPushRecord);
       }

       @ApiOperation(httpMethod="POST", value="编辑消息推送记录表")
       @PostMapping("/edit")
       public ResponseMessage<JxcPushRecord> edit(@RequestBody JxcPushRecord jxcPushRecord) {
              return this.jxcPushRecordService.modifyObj(jxcPushRecord);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询消息推送记录表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPushRecord> queryById(Integer id) {
              return this.jxcPushRecordService.queryObjById(id);
       }


}