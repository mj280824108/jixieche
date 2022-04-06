package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcExceptionAppeal;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcExceptionAppealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="异常订单申诉表")
@RestController
@RequestMapping("jxcExceptionAppeal")
public class JxcExceptionAppealController {
       @Resource
       private JxcExceptionAppealService jxcExceptionAppealService;

       @ApiOperation(httpMethod="POST", value="添加异常订单申诉表")
       @PostMapping("/insert")
       public ResponseMessage<JxcExceptionAppeal> insert(@RequestBody JxcExceptionAppeal jxcExceptionAppeal) {
              return this.jxcExceptionAppealService.addObj(jxcExceptionAppeal);
       }

       @ApiOperation(httpMethod="POST", value="编辑异常订单申诉表")
       @PostMapping("/edit")
       public ResponseMessage<JxcExceptionAppeal> edit(@RequestBody JxcExceptionAppeal jxcExceptionAppeal) {
              return this.jxcExceptionAppealService.modifyObj(jxcExceptionAppeal);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询异常订单申诉表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcExceptionAppeal> queryById(Integer id) {
              return this.jxcExceptionAppealService.queryObjById(id);
       }
}