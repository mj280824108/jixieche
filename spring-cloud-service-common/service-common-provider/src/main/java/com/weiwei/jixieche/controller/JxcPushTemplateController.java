package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPushTemplate;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPushTemplateService;
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
@Api(tags="公共模块--消息推送模板")
@RestController
@RequestMapping("jxcPushTemplate")
public class JxcPushTemplateController {
       @Resource
       private JxcPushTemplateService jxcPushTemplateService;

       @ApiOperation(httpMethod="POST", value="前端分页查询消息推送模板")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPushTemplate> findByPageForFront(@RequestBody JxcPushTemplate jxcPushTemplate) {
              return this.jxcPushTemplateService.findByPageForFront(jxcPushTemplate.getPageNo(),jxcPushTemplate.getPageSize(),jxcPushTemplate);
       }

       @ApiOperation(httpMethod="POST", value="添加消息推送模板")
       @PostMapping("/insert")
       public ResponseMessage<JxcPushTemplate> insert(@RequestBody JxcPushTemplate jxcPushTemplate) {
              return this.jxcPushTemplateService.addObj(jxcPushTemplate);
       }

       @ApiOperation(httpMethod="POST", value="编辑消息推送模板")
       @PostMapping("/edit")
       public ResponseMessage<JxcPushTemplate> edit(@RequestBody JxcPushTemplate jxcPushTemplate) {
              return this.jxcPushTemplateService.modifyObj(jxcPushTemplate);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询消息推送模板")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPushTemplate> queryById(Integer id) {
              return this.jxcPushTemplateService.queryObjById(id);
       }
}