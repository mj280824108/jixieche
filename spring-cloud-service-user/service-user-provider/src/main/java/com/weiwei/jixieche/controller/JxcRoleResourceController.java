package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcRoleResource;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--角色资源表")
@RestController
@RequestMapping("jxcRoleResource")
public class JxcRoleResourceController {
       @Resource
       private JxcRoleResourceService jxcRoleResourceService;

       @ApiOperation(httpMethod="POST", value="前端分页查询角色资源表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcRoleResource> findByPageForFront(@RequestBody JxcRoleResource jxcRoleResource) {
              return this.jxcRoleResourceService.findByPageForFront(jxcRoleResource.getPageNo(),jxcRoleResource.getPageSize(),jxcRoleResource);
       }

       @ApiOperation(httpMethod="POST", value="添加角色资源表")
       @PostMapping("/insert")
       public ResponseMessage<JxcRoleResource> insert(@RequestBody JxcRoleResource jxcRoleResource) {
              return this.jxcRoleResourceService.addObj(jxcRoleResource);
       }

       @ApiOperation(httpMethod="POST", value="编辑角色资源表")
       @PostMapping("/edit")
       public ResponseMessage<JxcRoleResource> edit(@RequestBody JxcRoleResource jxcRoleResource) {
              return this.jxcRoleResourceService.modifyObj(jxcRoleResource);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询角色资源表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcRoleResource> queryById(Integer id) {
              return this.jxcRoleResourceService.queryObjById(id);
       }
}