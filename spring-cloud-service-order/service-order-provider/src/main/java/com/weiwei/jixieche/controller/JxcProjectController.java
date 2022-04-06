package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcProject;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcProjectService;
import com.weiwei.jixieche.vo.JxcMachineListVo;
import com.weiwei.jixieche.vo.TabPageListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Author 钟焕
 * @Description
 * @Date 11:51 2019-08-14
 **/
@Api(tags="承租方项目表")
@RestController
@RequestMapping("jxcProject")
public class JxcProjectController {
       @Resource
       private JxcProjectService jxcProjectService;

       @ApiOperation(httpMethod="POST", value="前端分页查询承租方项目表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcProject> findByPageForFront(AuthorizationUser user,@RequestBody JxcProject jxcProject) {
              jxcProject.setUserId(user.getUserId());
              return this.jxcProjectService.findByPageForFront(jxcProject.getPageNo(),jxcProject.getPageSize(),jxcProject);
       }

       @ApiOperation(httpMethod="POST", value="添加/编辑承租方项目表")
       @PostMapping("/addOrEditJxcProject")
       public ResponseMessage<JxcProject> insert(AuthorizationUser user,@RequestBody JxcProject jxcProject) {
              jxcProject.setUserId(user.getUserId());
              return this.jxcProjectService.addOrEditJxcProject(jxcProject);
       }

       @ApiOperation(httpMethod="POST", value="查询未竣工或已竣工列表 flag 0:未竣工 1：已竣工")
       @PostMapping("/selectJxcProjectList")
       public ResponseMessage<JxcProject> selectJxcProjectList(AuthorizationUser user,@RequestBody TabPageListVo tabPageListVo) {
              return this.jxcProjectService.selectJxcProjectList(user,tabPageListVo);
       }

       @ApiOperation(httpMethod="POST", value="查询项目列表（不分页）")
       @PostMapping("/selectJxcProjectListNoPage")
       public ResponseMessage<JxcProject> selectJxcProjectList(AuthorizationUser user) {
              return this.jxcProjectService.selectJxcProjectListNoPage(user);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询承租方项目表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcProject> queryById(AuthorizationUser user,@RequestParam(value = "id",required = false)Integer id) {
              return this.jxcProjectService.queryProjectById(user,id);
       }

       @ApiOperation(httpMethod="POST", value="查询项目中的车辆 项目ID  tabFlag 1-待接单 2-进行中 3-已完结")
       @PostMapping("/selectMachineListByProjectId")
       public ResponseMessage selectMachineListByProjectId(@RequestBody JxcMachineListVo jxcMachineListVo) {
              return this.jxcProjectService.selectMachineListByProjectId(jxcMachineListVo);
       }

       @ApiOperation("承租方查询项目进度")
       @PostMapping("/selectProjectProgress")
       public ResponseMessage selectProjectProgress(@RequestParam("id") Integer projectId,@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
              return this.jxcProjectService.selectProjectProgress(projectId, pageNo, pageSize);
       }

       @ApiOperation("承租方删除项目")
       @PostMapping("/delProject")
       public ResponseMessage selectProjectProgress(@RequestParam("id") Integer projectId){
              return this.jxcProjectService.delProject(projectId);
       }

       @ApiOperation("校验承租方发单是否被限制")
       @PostMapping("/checkIsForbidSendOrder")
       public ResponseMessage checkIsForbidSendOrder(AuthorizationUser user){
              return this.jxcProjectService.checkIsForbidSendOrder(user);
       }

}