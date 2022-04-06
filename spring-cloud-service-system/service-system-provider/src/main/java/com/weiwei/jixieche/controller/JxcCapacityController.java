package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcCapacity;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcCapacityService;
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
@Api(tags="方量字典表")
@RestController
@RequestMapping("jxcCapacity")
public class JxcCapacityController {
       @Resource
       private JxcCapacityService jxcCapacityService;

       @ApiOperation(httpMethod="POST", value="前端分页查询方量字典表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcCapacity> findByPageForFront(@RequestBody JxcCapacity jxcCapacity) {
              return this.jxcCapacityService.findByPageForFront(jxcCapacity.getPageNo(),jxcCapacity.getPageSize(),jxcCapacity);
       }

       @ApiOperation(httpMethod="POST", value="根据城市id查询方量")
       @PostMapping("/queryByCityId")
       public ResponseMessage<JxcCapacity> queryByCityId(@RequestBody JxcCapacity jxcCapacity) {
              return this.jxcCapacityService.queryByCityId(jxcCapacity);
       }

       @ApiOperation(httpMethod="POST", value="添加方量字典表")
       @PostMapping("/insert")
       public ResponseMessage<JxcCapacity> insert(@RequestBody JxcCapacity jxcCapacity) {
              return this.jxcCapacityService.addObj(jxcCapacity);
       }

       @ApiOperation(httpMethod="POST", value="编辑方量字典表")
       @PostMapping("/edit")
       public ResponseMessage<JxcCapacity> edit(@RequestBody JxcCapacity jxcCapacity) {
              return this.jxcCapacityService.modifyObj(jxcCapacity);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询方量字典表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcCapacity> queryById(Integer id) {
              return this.jxcCapacityService.queryObjById(id);
       }
}