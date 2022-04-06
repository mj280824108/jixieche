package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketMachineType;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketMachineTypeService;
import com.weiwei.jixieche.vo.MachineTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Api(tags="市场模块--市场机械类型字典表")
@RestController
@RequestMapping("jxcMarketMachineType")
public class JxcMarketMachineTypeController {
       @Resource
       private JxcMarketMachineTypeService jxcMarketMachineTypeService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场机械类型字典表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketMachineType> findByPageForFront(@RequestBody JxcMarketMachineType jxcMarketMachineType) {
              return this.jxcMarketMachineTypeService.findByPageForFront(jxcMarketMachineType.getPageNo(),jxcMarketMachineType.getPageSize(),jxcMarketMachineType);
       }

       @ApiOperation(httpMethod="POST", value="根据机械父类品牌查询子类品牌")
       @PostMapping("/queryMachineType")
       public ResponseMessage queryMachineType(@RequestBody MachineTypeVo machineTypeVo) {
              return this.jxcMarketMachineTypeService.queryMachineType(machineTypeVo);
       }

       @ApiOperation(httpMethod="POST", value="查询机械父类品牌")
       @PostMapping("/queryParentMachineType")
       public ResponseMessage queryParentMachineType() {
              return this.jxcMarketMachineTypeService.queryParentMachineType();
       }

       @ApiOperation(httpMethod="POST", value="查询市场机械类型字典表(不分页)")
       @PostMapping("/query")
       public ResponseMessage<JxcMarketMachineType> query() {
              JxcMarketMachineType jxcMarketMachineType =  new JxcMarketMachineType();
              return this.jxcMarketMachineTypeService.query(jxcMarketMachineType);
       }

       @ApiOperation(httpMethod="POST", value="添加市场机械类型字典表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketMachineType> insert(@RequestBody JxcMarketMachineType jxcMarketMachineType) {
              return this.jxcMarketMachineTypeService.addObj(jxcMarketMachineType);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场机械类型字典表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketMachineType> edit(@RequestBody JxcMarketMachineType jxcMarketMachineType) {
              return this.jxcMarketMachineTypeService.modifyObj(jxcMarketMachineType);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询市场机械类型字典表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketMachineType> queryById(Integer id) {
              return this.jxcMarketMachineTypeService.queryObjById(id);
       }
}