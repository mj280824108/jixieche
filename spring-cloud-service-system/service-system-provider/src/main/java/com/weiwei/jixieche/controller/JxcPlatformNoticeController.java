package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcPlatformNotice;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcPlatformNoticeService;
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
@Api(tags="平台公告信息表")
@RestController
@RequestMapping("jxcPlatformNotice")
public class JxcPlatformNoticeController {
       @Resource
       private JxcPlatformNoticeService jxcPlatformNoticeService;

       @ApiOperation(httpMethod="POST", value="前端分页查询平台公告信息表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcPlatformNotice> findByPageForFront(@RequestBody JxcPlatformNotice jxcPlatformNotice) {
              return this.jxcPlatformNoticeService.findByPageForFront(jxcPlatformNotice.getPageNo(),jxcPlatformNotice.getPageSize(),jxcPlatformNotice);
       }

       @ApiOperation(httpMethod="POST", value="添加平台公告信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcPlatformNotice> insert(@RequestBody JxcPlatformNotice jxcPlatformNotice) {
              return this.jxcPlatformNoticeService.addObj(jxcPlatformNotice);
       }

       @ApiOperation(httpMethod="POST", value="编辑平台公告信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcPlatformNotice> edit(@RequestBody JxcPlatformNotice jxcPlatformNotice) {
              return this.jxcPlatformNoticeService.modifyObj(jxcPlatformNotice);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询平台公告信息表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcPlatformNotice> queryById(Integer id) {
              return this.jxcPlatformNoticeService.queryObjById(id);
       }
}