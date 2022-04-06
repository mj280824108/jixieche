package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcChildRefOwner;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcChildRefOwnerService;
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
@Api(tags="用户模块--子账号与机主关联关系表")
@RestController
@RequestMapping("jxcChildRefOwner")
public class JxcChildRefOwnerController {
       @Resource
       private JxcChildRefOwnerService jxcChildRefOwnerService;

       @ApiOperation(httpMethod="POST", value="前端分页查询子账号与机主关联关系表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcChildRefOwner> findByPageForFront(@RequestBody JxcChildRefOwner jxcChildRefOwner) {
              return this.jxcChildRefOwnerService.findByPageForFront(jxcChildRefOwner.getPageNo(),jxcChildRefOwner.getPageSize(),jxcChildRefOwner);
       }

       @ApiOperation(httpMethod="POST", value="添加子账号与机主关联关系表")
       @PostMapping("/insert")
       public ResponseMessage<JxcChildRefOwner> insert(@RequestBody JxcChildRefOwner jxcChildRefOwner) {
              return this.jxcChildRefOwnerService.addObj(jxcChildRefOwner);
       }

       @ApiOperation(httpMethod="POST", value="编辑子账号与机主关联关系表")
       @PostMapping("/edit")
       public ResponseMessage<JxcChildRefOwner> edit(@RequestBody JxcChildRefOwner jxcChildRefOwner) {
              return this.jxcChildRefOwnerService.modifyObj(jxcChildRefOwner);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询子账号与机主关联关系表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcChildRefOwner> queryById(Integer id) {
              return this.jxcChildRefOwnerService.queryObjById(id);
       }
}