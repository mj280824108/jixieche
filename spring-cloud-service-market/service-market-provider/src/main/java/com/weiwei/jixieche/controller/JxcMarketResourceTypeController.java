package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketResourceType;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketResourceTypeService;
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
@Api(tags="市场模块--市场资源类型表")
@RestController
@RequestMapping("jxcMarketResourceType")
public class JxcMarketResourceTypeController {
       @Resource
       private JxcMarketResourceTypeService jxcMarketResourceTypeService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场资源类型表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketResourceType> findByPageForFront(@RequestBody JxcMarketResourceType jxcMarketResourceType) {
              return this.jxcMarketResourceTypeService.findByPageForFront(jxcMarketResourceType.getPageNo(),jxcMarketResourceType.getPageSize(),jxcMarketResourceType);
       }

       @ApiOperation(httpMethod="POST", value="查询市场资源类型表(不分页)")
       @PostMapping("/query")
       public ResponseMessage<JxcMarketResourceType> query() {
              return this.jxcMarketResourceTypeService.query();
       }

       @ApiOperation(httpMethod="POST", value="添加市场资源类型表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketResourceType> insert(@RequestBody JxcMarketResourceType jxcMarketResourceType) {
              return this.jxcMarketResourceTypeService.addObj(jxcMarketResourceType);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场资源类型表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketResourceType> edit(@RequestBody JxcMarketResourceType jxcMarketResourceType) {
              return this.jxcMarketResourceTypeService.modifyObj(jxcMarketResourceType);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询市场资源类型表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketResourceType> queryById(Integer id) {
              return this.jxcMarketResourceTypeService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="查询市场资源类型列表")
       @PostMapping("/queryMarketResourceTypeList")
       public ResponseMessage queryMarketResourceTypeList() {
              return this.jxcMarketResourceTypeService.queryMarketResourceTypeList();
       }

}