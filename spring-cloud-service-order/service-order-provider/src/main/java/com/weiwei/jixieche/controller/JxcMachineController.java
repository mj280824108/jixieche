package com.weiwei.jixieche.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.netflix.discovery.converters.Auto;
import com.weiwei.jixieche.bean.JxcMachine;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.jwt.User;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMachineService;
import com.weiwei.jixieche.vo.JxcMachineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="机械管理")
@RestController
@RequestMapping("jxcMachine")
public class JxcMachineController {
       @Resource
       private JxcMachineService jxcMachineService;

       @ApiOperation(httpMethod="POST", value="机械管理列表")
       @PostMapping("/getMachineList")
       public ResponseMessage<JxcMachineVo> getMachineList(AuthorizationUser authorizationUser, @RequestParam(value = "lastPageLastId",required = false) Integer lastPageLastId,
                                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false)Integer pageSize) {
              return this.jxcMachineService.getMachineList(authorizationUser.getUserId(), lastPageLastId, pageSize);
       }

       @ApiOperation(httpMethod="POST", value="添加车辆信息")
       @PostMapping("/insert")
       public ResponseMessage<JxcMachine> insert(AuthorizationUser authorizationUser, @RequestBody JxcMachine jxcMachine) {
              jxcMachine.setUserId(authorizationUser.getUserId());
              return this.jxcMachineService.addObj(jxcMachine);
       }

       @ApiOperation(httpMethod="POST", value="编辑车辆信息")
       @PostMapping("/edit")
       public ResponseMessage<JxcMachine> edit(AuthorizationUser authorizationUser, @RequestBody JxcMachine jxcMachine) {
              return this.jxcMachineService.modifyObj(jxcMachine);
       }

       @ApiOperation(httpMethod="POST", value="通过ID查询车辆详情")
       @PostMapping("/queryById")
       public ResponseMessage queryById(AuthorizationUser authorizationUser, @RequestParam("id") Integer id,
                                        @RequestParam(value = "projectOrderId",required = false) Long projectOrderId) {
              return this.jxcMachineService.getById(id, projectOrderId);
       }

       @ApiOperation(httpMethod="POST", value="删除机械")
       @PostMapping("/deleteById")
       public ResponseMessage deleteById(Integer id) {
              return this.jxcMachineService.deleteById(id);
       }

       @ApiOperation("下拉选择-车牌号码列表")
       @PostMapping("/selectMachineList")
       public ResponseMessage selectMachineList(AuthorizationUser authorizationUser){
              return  this.jxcMachineService.selectMachineList(authorizationUser.getUserId());
       }

       @ApiOperation(httpMethod="POST", value="车辆听单模式切换")
       @PostMapping("/machineModeSwitching")
       public ResponseMessage<JxcMachine> machineModeSwitching(AuthorizationUser authorizationUser, @RequestBody JxcMachine jxcMachine) {
              return this.jxcMachineService.modifyObj(jxcMachine);
       }

       /**
        * 更新机械状态为空闲中
        * @author  liuy
        * @param machineId
        * @return
        * @date    2019/9/29 15:07
        */
       @PostMapping("/updateMachineById")
       public ResponseMessage updateMachineById(@RequestParam("machineId") Integer machineId){
              return this.jxcMachineService.updateMachineById(machineId);
       }

       /**
        * 筛选车辆列表
        * @author  liuy
        * @param user
        * @return
        * @date    2019/10/8 11:45
        */
       @PostMapping("/getMachineSelectByUserId")
       public ResponseMessage getMachineSelectByUserId(AuthorizationUser user) {
              return this.jxcMachineService.getMachineSelectByUserId(user.getUserId());
       }
}