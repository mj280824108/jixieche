package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcRole;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcRoleService;
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
@Api(tags="用户模块--角色表")
@RestController
@RequestMapping("jxcRole")
public class JxcRoleController {
       @Resource
       private JxcRoleService jxcRoleService;

       @ApiOperation(httpMethod="POST", value="前端分页查询角色表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcRole> findByPageForFront(@RequestBody JxcRole jxcRole) {
              return this.jxcRoleService.findByPageForFront(jxcRole.getPageNo(),jxcRole.getPageSize(),jxcRole);
       }

       @ApiOperation(httpMethod="POST", value="添加角色表")
       @PostMapping("/insert")
       public ResponseMessage<JxcRole> insert(@RequestBody JxcRole jxcRole) {
              return this.jxcRoleService.addObj(jxcRole);
       }

       @ApiOperation(httpMethod="POST", value="编辑角色表")
       @PostMapping("/edit")
       public ResponseMessage<JxcRole> edit(@RequestBody JxcRole jxcRole) {
              return this.jxcRoleService.modifyObj(jxcRole);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询角色表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcRole> queryById(Integer id) {
              return this.jxcRoleService.queryObjById(id);
       }
}