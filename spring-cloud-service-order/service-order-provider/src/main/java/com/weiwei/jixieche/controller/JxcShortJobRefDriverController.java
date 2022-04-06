package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.bean.JxcShortJobRefDriver;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcShortJobRefDriverService;
import com.weiwei.jixieche.vo.ShortJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags="兼职职位与司机的关联表")
@RestController
@RequestMapping("jxcShortJobRefDriver")
public class JxcShortJobRefDriverController {


       @Resource
       private JxcShortJobRefDriverService jxcShortJobRefDriverService;

       /**
        * 兼职司机-接单
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/8/16 16:16
        */
       @ApiOperation(httpMethod="POST", value="兼职司机-接单")
       @PostMapping("/insert")
       public ResponseMessage<JxcShortJobRefDriver> insert(AuthorizationUser authorizationUser, @RequestBody JxcShortJobRefDriver jxcShortJobRefDriver) {
              jxcShortJobRefDriver.setDriverId(authorizationUser.getUserId());
              return this.jxcShortJobRefDriverService.addObj(jxcShortJobRefDriver);
       }

       /**
        * 司机-工作管理
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/8/16 16:15
        */
       @ApiOperation("司机-工作管理")
       @PostMapping("/getShortJobManager")
       public ResponseMessage getShortJobManager(AuthorizationUser authorizationUser, @RequestBody ShortJobVo shortJobVo){
              shortJobVo.setDriverUserId(authorizationUser.getUserId());
              return this.jxcShortJobRefDriverService.getShortJobManager(shortJobVo);
       }

       /**
        * 司机-工作管理详情
        * @author  liuy
        * @param shortJobId
        * @return
        * @date    2019/8/16 17:12
        */
       @ApiOperation("司机-工作管理详情")
       @PostMapping("/getShortJobDetail")
       public ResponseMessage getShortJobDetail(AuthorizationUser user, @RequestParam("shortJobId")Integer shortJobId){
              return this.jxcShortJobRefDriverService.getShortJobDetail(user, shortJobId);
       }

       /**
        * 司机-取消订单
        * @author  liuy
        * @param jxcShortJob 职位ID
        * @return
        * @date    2019/8/17 9:55
        */
       @ApiOperation("司机-取消订单")
       @PostMapping("/driverCancelOrder")
       public ResponseMessage driverCancelOrder(AuthorizationUser authorizationUser, @RequestBody JxcShortJob jxcShortJob){
              return this.jxcShortJobRefDriverService.driverCancelOrder(authorizationUser.getUserId(), jxcShortJob.getId());
       }

       /**
        * 工作管理--日历打标记
        *
        * @param user
        * @param jobId
        * @param yearMonth
        * @return
        */
       @ApiOperation("工作管理--日历打标记")
       @PostMapping("/singCalendar")
       public ResponseMessage singCalendar(AuthorizationUser user,@RequestParam(value = "driverUserId", required = false)Integer driverUserId,
                                           @RequestParam(required = false,name = "jobId") Integer jobId,
                                           @RequestParam(required = false,name = "yearMonth")  String yearMonth) {
       	      if(null == driverUserId){
       	      	driverUserId = user.getUserId();
              }
              return jxcShortJobRefDriverService.singCalendar(user,driverUserId, jobId, yearMonth);
       }

}