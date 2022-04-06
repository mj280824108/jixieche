package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcManagerRefTenantry;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcManagerRefTenantryService;
import com.weiwei.jixieche.vo.TenManagerInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="用户模块--承租方管理员与承租方关联表")
@RestController
@RequestMapping("jxcManagerRefTenantry")
public class JxcManagerRefTenantryController {
       @Resource
       private JxcManagerRefTenantryService jxcManagerRefTenantryService;

       @ApiOperation(httpMethod="POST", value="前端分页查询承租方管理员与承租方关联表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcManagerRefTenantry> findByPageForFront(@RequestBody JxcManagerRefTenantry jxcManagerRefTenantry) {
              return this.jxcManagerRefTenantryService.findByPageForFront(jxcManagerRefTenantry.getPageNo(),jxcManagerRefTenantry.getPageSize(),jxcManagerRefTenantry);
       }

       @ApiOperation(httpMethod="POST", value="添加承租方管理员与承租方关联表")
       @PostMapping("/insert")
       public ResponseMessage<JxcManagerRefTenantry> insert(@RequestBody JxcManagerRefTenantry jxcManagerRefTenantry) {
              return this.jxcManagerRefTenantryService.addObj(jxcManagerRefTenantry);
       }

       @ApiOperation(httpMethod="POST", value="编辑承租方管理员与承租方关联表")
       @PostMapping("/edit")
       public ResponseMessage<JxcManagerRefTenantry> edit(@RequestBody JxcManagerRefTenantry jxcManagerRefTenantry) {
              return this.jxcManagerRefTenantryService.modifyObj(jxcManagerRefTenantry);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询承租方管理员与承租方关联表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcManagerRefTenantry> queryById(Integer id) {
              return this.jxcManagerRefTenantryService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="查询承租方管理员信息详情")
       @PostMapping("/queryTenManagerInfo")
       public ResponseMessage<JxcManagerRefTenantry> queryTenManagerInfo(@RequestBody TenManagerInfoVo tenManagerInfoVo) {
              return this.jxcManagerRefTenantryService.queryTenManagerInfo(tenManagerInfoVo);
       }
}