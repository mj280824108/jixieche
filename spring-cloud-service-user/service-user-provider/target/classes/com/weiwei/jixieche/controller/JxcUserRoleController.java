package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcUserRole;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcUserRoleService;
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
@Api(tags="用户模块--用户角色表")
@RestController
@RequestMapping("jxcUserRole")
public class JxcUserRoleController {
       @Resource
       private JxcUserRoleService jxcUserRoleService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户角色表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcUserRole> findByPageForFront(@RequestBody JxcUserRole jxcUserRole) {
              return this.jxcUserRoleService.findByPageForFront(jxcUserRole.getPageNo(),jxcUserRole.getPageSize(),jxcUserRole);
       }

       @ApiOperation(httpMethod="POST", value="添加用户角色表")
       @PostMapping("/insert")
       public ResponseMessage<JxcUserRole> insert(@RequestBody JxcUserRole jxcUserRole) {
              return this.jxcUserRoleService.addObj(jxcUserRole);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户角色表")
       @PostMapping("/edit")
       public ResponseMessage<JxcUserRole> edit(@RequestBody JxcUserRole jxcUserRole) {
              return this.jxcUserRoleService.modifyObj(jxcUserRole);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户角色表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcUserRole> queryById(Integer id) {
              return this.jxcUserRoleService.queryObjById(id);
       }
}