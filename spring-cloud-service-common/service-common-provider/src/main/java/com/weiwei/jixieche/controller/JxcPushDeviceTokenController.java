package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPushDeviceToken;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPushDeviceTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Api(tags="公共模块--推送设备token表")
@RestController
@RequestMapping("jxcPushDeviceToken")
public class JxcPushDeviceTokenController {
       @Resource
       private JxcPushDeviceTokenService jxcPushDeviceTokenService;

       @ApiOperation(httpMethod="POST", value="前端分页查询推送设备token表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPushDeviceToken> findByPageForFront(@RequestBody JxcPushDeviceToken jxcPushDeviceToken) {
              return this.jxcPushDeviceTokenService.findByPageForFront(jxcPushDeviceToken.getPageNo(),jxcPushDeviceToken.getPageSize(),jxcPushDeviceToken);
       }

       @ApiOperation(httpMethod="POST", value="添加推送设备token表")
       @PostMapping("/insert")
       public ResponseMessage<JxcPushDeviceToken> insert(@RequestBody JxcPushDeviceToken jxcPushDeviceToken) {
              return this.jxcPushDeviceTokenService.addObj(jxcPushDeviceToken);
       }

       @ApiOperation(httpMethod="POST", value="编辑推送设备token表")
       @PostMapping("/edit")
       public ResponseMessage<JxcPushDeviceToken> edit(@RequestBody JxcPushDeviceToken jxcPushDeviceToken) {
              return this.jxcPushDeviceTokenService.modifyObj(jxcPushDeviceToken);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询推送设备token表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPushDeviceToken> queryById(Integer id) {
              return this.jxcPushDeviceTokenService.queryObjById(id);
       }
}