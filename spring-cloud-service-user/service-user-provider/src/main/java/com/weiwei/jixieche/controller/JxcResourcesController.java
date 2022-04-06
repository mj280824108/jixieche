package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcResources;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--资源(菜单)表")
@RestController
@RequestMapping("jxcResources")
public class JxcResourcesController {
       @Resource
       private JxcResourcesService jxcResourcesService;

       @ApiOperation(httpMethod="POST", value="前端分页查询资源(菜单)表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcResources> findByPageForFront(@RequestBody JxcResources jxcResources) {
              return this.jxcResourcesService.findByPageForFront(jxcResources.getPageNo(),jxcResources.getPageSize(),jxcResources);
       }

       @ApiOperation(httpMethod="POST", value="添加资源(菜单)表")
       @PostMapping("/insert")
       public ResponseMessage<JxcResources> insert(@RequestBody JxcResources jxcResources) {
              return this.jxcResourcesService.addObj(jxcResources);
       }

       @ApiOperation(httpMethod="POST", value="编辑资源(菜单)表")
       @PostMapping("/edit")
       public ResponseMessage<JxcResources> edit(@RequestBody JxcResources jxcResources) {
              return this.jxcResourcesService.modifyObj(jxcResources);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询资源(菜单)表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcResources> queryById(Integer id) {
              return this.jxcResourcesService.queryObjById(id);
       }
}