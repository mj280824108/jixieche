package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMachineRemind;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMachineRemindService;
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
@Api(tags="机械提醒类型")
@RestController
@RequestMapping("jxcMachineRemind")
public class JxcMachineRemindController {
       @Resource
       private JxcMachineRemindService jxcMachineRemindService;

       @ApiOperation(httpMethod="POST", value="前端分页查询机械提醒类型")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMachineRemind> findByPageForFront(@RequestBody JxcMachineRemind jxcMachineRemind) {
              return this.jxcMachineRemindService.findByPageForFront(jxcMachineRemind.getPageNo(),jxcMachineRemind.getPageSize(),jxcMachineRemind);
       }

       @ApiOperation(httpMethod="POST", value="添加机械提醒类型")
       @PostMapping("/insert")
       public ResponseMessage<JxcMachineRemind> insert(@RequestBody JxcMachineRemind jxcMachineRemind) {
              return this.jxcMachineRemindService.addObj(jxcMachineRemind);
       }

       @ApiOperation(httpMethod="POST", value="编辑机械提醒类型")
       @PostMapping("/edit")
       public ResponseMessage<JxcMachineRemind> edit(@RequestBody JxcMachineRemind jxcMachineRemind) {
              return this.jxcMachineRemindService.modifyObj(jxcMachineRemind);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询机械提醒类型")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMachineRemind> queryById(Integer id) {
              return this.jxcMachineRemindService.queryObjById(id);
       }
}