package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcDriverResume;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcDriverResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 司机求职信息
* @Author:      liuy
* @Date:  2019/8/21 11:07
*/
@Api(tags="司机求职信息表")
@RestController
@RequestMapping("jxcDriverResume")
public class JxcDriverResumeController {
       @Resource
       private JxcDriverResumeService jxcDriverResumeService;

       /**
        * 添加司机求职信息表
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/8/21 11:07
        */
       @ApiOperation(httpMethod="POST", value="添加司机求职信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcDriverResume> insert(AuthorizationUser authorizationUser, @RequestBody JxcDriverResume jxcDriverResume) {
              jxcDriverResume.setDriverId(authorizationUser.getUserId());
              return this.jxcDriverResumeService.addObj(jxcDriverResume);
       }

       /**
        * 编辑司机求职信息表
        * @author  liuy
       * @param jxcDriverResume
        * @return
        * @date    2019/8/21 11:08
        */
       @ApiOperation(httpMethod="POST", value="编辑司机求职信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcDriverResume> edit(@RequestBody JxcDriverResume jxcDriverResume) {
              return this.jxcDriverResumeService.modifyObj(jxcDriverResume);
       }

       /**
        * 通过ID查询司机求职信息表
        * @author  liuy
        * @param id
        * @return
        * @date    2019/8/21 11:08
        */
       @ApiOperation(httpMethod="POST", value="通过ID查询司机求职信息表")
       @PostMapping("/queryById")
       public ResponseMessage<JxcDriverResume> queryById(Integer id) {
              return this.jxcDriverResumeService.queryObjById(id);
       }

       /**
        * 司机查看简历
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/8/21 11:08
        */
       @ApiOperation(httpMethod="POST", value="司机查看简历")
       @PostMapping("/getDriverResumeById")
       public ResponseMessage<JxcDriverResume> getDriverResumeById(AuthorizationUser authorizationUser) {
              return this.jxcDriverResumeService.getDriverResumeById(authorizationUser.getUserId());
       }

       /**
        * 司机简历列表
        * @author  liuy
        * @param jxcDriverResume
        * @return
        * @date    2019/8/21 11:08
        */
       @ApiOperation(httpMethod="POST", value="司机简历列表")
       @PostMapping("/getDriverResumeList")
       public ResponseMessage<JxcDriverResume> getDriverResumeList(@RequestBody(required = false) JxcDriverResume jxcDriverResume) {
              return this.jxcDriverResumeService.getDriverResumeList(jxcDriverResume);
       }

       /**
        * 刷新简历
        * @author  liuy
       * @param resumeId
        * @return
        * @date    2019/8/29 9:52
        */
       @ApiOperation(httpMethod="POST", value="刷新简历")
       @PostMapping("/refreshDriverResume")
       public ResponseMessage<JxcDriverResume> refreshDriverResume(@RequestParam("resumeId")Integer resumeId) {
              return this.jxcDriverResumeService.refreshDriverResume(resumeId);
       }
}