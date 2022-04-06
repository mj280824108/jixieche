package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcOpenedCity;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcOpenedCityService;
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
@Api(tags="开放城市车载容量表")
@RestController
@RequestMapping("jxcOpenedCity")
public class JxcOpenedCityController {
       @Resource
       private JxcOpenedCityService jxcOpenedCityService;

       @ApiOperation(httpMethod="POST", value="前端分页查询开放城市车载容量表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcOpenedCity> findByPageForFront(@RequestBody JxcOpenedCity jxcOpenedCity) {
              return this.jxcOpenedCityService.findByPageForFront(jxcOpenedCity.getPageNo(),jxcOpenedCity.getPageSize(),jxcOpenedCity);
       }

       @ApiOperation(httpMethod="POST", value="添加开放城市车载容量表")
       @PostMapping("/insert")
       public ResponseMessage<JxcOpenedCity> insert(@RequestBody JxcOpenedCity jxcOpenedCity) {
              return this.jxcOpenedCityService.addObj(jxcOpenedCity);
       }

       @ApiOperation(httpMethod="POST", value="编辑开放城市车载容量表")
       @PostMapping("/edit")
       public ResponseMessage<JxcOpenedCity> edit(@RequestBody JxcOpenedCity jxcOpenedCity) {
              return this.jxcOpenedCityService.modifyObj(jxcOpenedCity);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询开放城市车载容量表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcOpenedCity> queryById(Integer id) {
              return this.jxcOpenedCityService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="根据订单查询应付给机主的实际金额")
       @PostMapping("/getToOwnerAmount")
       public Integer getToOwnerAmount(@RequestParam("orderId") Long orderId, @RequestParam("amount") Integer amount) {
              return this.jxcOpenedCityService.getToOwnerAmount(orderId,amount);
       }
}