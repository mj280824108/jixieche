package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPlatformFeedback;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPlatformFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Api(tags="平台反馈信息表")
@RestController
@RequestMapping("jxcPlatformFeedback")
public class JxcPlatformFeedbackController {
       @Resource
       private JxcPlatformFeedbackService jxcPlatformFeedbackService;

       @ApiOperation(httpMethod="POST", value="前端分页查询平台反馈信息表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPlatformFeedback> findByPageForFront(@RequestBody JxcPlatformFeedback jxcPlatformFeedback) {
              return this.jxcPlatformFeedbackService.findByPageForFront(jxcPlatformFeedback.getPageNo(),jxcPlatformFeedback.getPageSize(),jxcPlatformFeedback);
       }

       @ApiOperation(httpMethod="POST", value="添加平台反馈信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcPlatformFeedback> insert(@RequestBody JxcPlatformFeedback jxcPlatformFeedback) {
              return this.jxcPlatformFeedbackService.addObj(jxcPlatformFeedback);
       }

       @ApiOperation(httpMethod="POST", value="编辑平台反馈信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcPlatformFeedback> edit(@RequestBody JxcPlatformFeedback jxcPlatformFeedback) {
              return this.jxcPlatformFeedbackService.modifyObj(jxcPlatformFeedback);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询平台反馈信息表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPlatformFeedback> queryById(Integer id) {
              return this.jxcPlatformFeedbackService.queryObjById(id);
       }
}