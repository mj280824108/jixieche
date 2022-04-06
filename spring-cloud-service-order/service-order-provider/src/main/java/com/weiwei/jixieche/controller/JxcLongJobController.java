package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcLongJob;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcLongJobService;
import com.weiwei.jixieche.vo.DriverJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* @Description: 长期司机招聘
* @Author:      liuy
* @Date:  2019/8/15 10:13
*/
@Api(tags="长期司机招聘表")
@RestController
@RequestMapping("jxcLongJob")
public class JxcLongJobController {
       @Resource
       private JxcLongJobService jxcLongJobService;

       /**
        * 长期司机招聘列表
        * @author  liuy
        * @param authorizationUser 机主用户ID
        * @param driverJobVo 查询条件
        * @return
        * @date    2019/8/15 11:44
        */
       @PostMapping("/getLongJobList")
       public ResponseMessage<JxcLongJob> getLongJobList(AuthorizationUser authorizationUser, @RequestBody DriverJobVo driverJobVo){
	       //司机角色时不需要传机主用户ID
	       if(authorizationUser.getRoleId() == 2) {
		       driverJobVo.setOwnerUserId(authorizationUser.getUserId());
	       }
	       return jxcLongJobService.getLongJobList(driverJobVo);
       }

       /**
        * 添加长期司机招聘表
        * @author  liuy
        * @param authorizationUser 机主用户ID
        * @param jxcLongJob 长期司机信息
        * @return  
        * @date    2019/8/15 10:55
        */
       @ApiOperation(httpMethod="POST", value="添加长期司机招聘表")
       @PostMapping("/insert")
       public ResponseMessage<JxcLongJob> insert(AuthorizationUser authorizationUser, @RequestBody JxcLongJob jxcLongJob) {
              jxcLongJob.setUserId(authorizationUser.getUserId());
              return this.jxcLongJobService.addObj(jxcLongJob);
       }

       /**
        * 编辑长期司机招聘
        * @author  liuy
        * @param jxcLongJob 司机招聘信息
        * @return
        * @date    2019/8/15 11:50
        */
       @ApiOperation(httpMethod="POST", value="编辑长期司机招聘表")
       @PostMapping("/edit")
       public ResponseMessage<JxcLongJob> edit(@RequestBody JxcLongJob jxcLongJob) {
              return this.jxcLongJobService.modifyObj(jxcLongJob);
       }

       /**
        * 通过ID查询长期司机招聘
        * @author  liuy
        * @param id
        * @return
        * @date    2019/8/15 11:51
        */
       @ApiOperation(httpMethod="POST", value="通过ID查询长期司机招聘表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcLongJob> queryById(Integer id) {
              return this.jxcLongJobService.queryObjById(id);
       }

       /**
        * 删除
        * @author  liuy
        * @param id
        * @return
        */
       @PostMapping("/deleteById")
       public ResponseMessage<JxcLongJob> deleteById(@RequestParam("id") Integer id) {
              return this.jxcLongJobService.deleteById(id);
       }

       /**
        * 工程类型列表
        * @author  liuy
        * @return
        * @date    2019/8/27 14:12
        */
       @ApiOperation("工程类型列表")
       @PostMapping("/getProjectTypeList")
       public ResponseMessage getProjectTypeList(){
              return jxcLongJobService.getProjectTypeList();
       }
}