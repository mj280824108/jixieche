package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPlatDescription;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPlatDescriptionService;
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
@Api(tags="平台说明")
@RestController
@RequestMapping("jxcPlatDescription")
public class JxcPlatDescriptionController {
       @Resource
       private JxcPlatDescriptionService jxcPlatDescriptionService;

       @ApiOperation(httpMethod="POST", value="前端分页查询平台说明")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPlatDescription> findByPageForFront(@RequestBody JxcPlatDescription jxcPlatDescription) {
              return this.jxcPlatDescriptionService.findByPageForFront(jxcPlatDescription.getPageNo(),jxcPlatDescription.getPageSize(),jxcPlatDescription);
       }

       @ApiOperation(httpMethod="POST", value="添加平台说明")
       @PostMapping("/insert")
       public ResponseMessage<JxcPlatDescription> insert(@RequestBody JxcPlatDescription jxcPlatDescription) {
              return this.jxcPlatDescriptionService.addObj(jxcPlatDescription);
       }

       @ApiOperation(httpMethod="POST", value="编辑平台说明")
       @PostMapping("/edit")
       public ResponseMessage<JxcPlatDescription> edit(@RequestBody JxcPlatDescription jxcPlatDescription) {
              return this.jxcPlatDescriptionService.modifyObj(jxcPlatDescription);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询平台说明")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPlatDescription> queryById(Integer id) {
              return this.jxcPlatDescriptionService.queryObjById(id);
       }
}