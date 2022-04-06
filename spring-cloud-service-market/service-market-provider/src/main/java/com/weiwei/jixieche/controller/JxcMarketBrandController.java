package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketBrand;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketBrandService;
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
@Api(tags="市场模块--市场品牌字典表")
@RestController
@RequestMapping("jxcMarketBrand")
public class JxcMarketBrandController {
       @Resource
       private JxcMarketBrandService jxcMarketBrandService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场品牌字典表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketBrand> findByPageForFront(@RequestBody JxcMarketBrand jxcMarketBrand) {
              return this.jxcMarketBrandService.findByPageForFront(jxcMarketBrand.getPageNo(),jxcMarketBrand.getPageSize(),jxcMarketBrand);
       }

       @ApiOperation(httpMethod="POST", value="查询市场品牌子类列表")
       @PostMapping("query")
       public ResponseMessage<JxcMarketBrand> query() {
              return this.jxcMarketBrandService.query();
       }

       @ApiOperation(httpMethod="POST", value="添加市场品牌字典表")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketBrand> insert(@RequestBody JxcMarketBrand jxcMarketBrand) {
              return this.jxcMarketBrandService.addObj(jxcMarketBrand);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场品牌字典表")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketBrand> edit(@RequestBody JxcMarketBrand jxcMarketBrand) {
              return this.jxcMarketBrandService.modifyObj(jxcMarketBrand);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询市场品牌字典表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketBrand> queryById(Integer id) {
              return this.jxcMarketBrandService.queryObjById(id);
       }
}