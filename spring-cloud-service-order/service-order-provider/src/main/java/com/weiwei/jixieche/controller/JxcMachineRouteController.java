package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMachineRoute;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMachineRouteService;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Api(tags="机械行程表")
@RestController
@RequestMapping("jxcMachineRoute")
public class JxcMachineRouteController {
       @Resource
       private JxcMachineRouteService jxcMachineRouteService;

       @ApiOperation(httpMethod="POST", value="前端分页查询机械行程表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMachineRoute> findByPageForFront(@RequestBody JxcMachineRoute jxcMachineRoute) {
              return this.jxcMachineRouteService.findByPageForFront(jxcMachineRoute.getPageNo(),jxcMachineRoute.getPageSize(),jxcMachineRoute);
       }

       @ApiOperation(httpMethod="POST", value="添加机械行程表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMachineRoute> insert(@RequestBody JxcMachineRoute jxcMachineRoute) {
              return this.jxcMachineRouteService.addObj(jxcMachineRoute);
       }

       @ApiOperation(httpMethod="POST", value="编辑机械行程表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMachineRoute> edit(@RequestBody JxcMachineRoute jxcMachineRoute) {
              return this.jxcMachineRouteService.modifyObj(jxcMachineRoute);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询机械行程表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMachineRoute> queryById(Integer id) {
              return this.jxcMachineRouteService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="根据机械行程趟数集合查询趟数总金额")
       @PostMapping("/queryTotalAmount")
       public ResponseMessage queryTotalAmount(@RequestBody ArrayList<Long> routeIdList) {
              return this.jxcMachineRouteService.queryTotalAmount(routeIdList);
       }

       @ApiOperation(httpMethod="POST", value="根据机械行程趟数集合批量修改机械行程支付状态")
       @PostMapping("/updateBatchPayStatus")
       public void updateBatchPayStatus(@RequestBody UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo) {
              this.jxcMachineRouteService.updateBatchPayStatus(updateBatchRoutePayStatusVo);
       }

       @ApiOperation(httpMethod="POST", value="给行程记录的日历打标记")
       @PostMapping("/viewRouteSign")
       public ResponseMessage viewRouteSign(AuthorizationUser user, @RequestParam("ownerOrderId") Long ownerOrderId,@RequestParam(value = "driverId",required = false) Integer driverId, @RequestParam("yearMonth") String yearMonth) {
              return this.jxcMachineRouteService.viewRouteSign(user,ownerOrderId,driverId,yearMonth);
       }
}