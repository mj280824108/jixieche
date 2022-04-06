package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcClockInOutRecord;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.vo.ClockRecordListVo;
import com.weiwei.jixieche.vo.ClockRecordVo;
import com.weiwei.jixieche.vo.JxcDriverChangeWorkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags="司机打卡记录表")
@RestController
@RequestMapping("jxcClockInOutRecord")
public class JxcClockInOutRecordController {
       @Resource
       private JxcClockInOutRecordService jxcClockInOutRecordService;

       /**
        * 上班打卡
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/8/17 10:22
        */
       @ApiOperation("上班打卡")
       @PostMapping("/clockIn")
       public ResponseMessage clockIn(AuthorizationUser authorizationUser, @RequestBody JxcClockInOutRecord jxcClockInOutRecord){
              jxcClockInOutRecord.setDriverId(authorizationUser.getUserId());
              return jxcClockInOutRecordService.clockIn(jxcClockInOutRecord);
       }

       /**
        * 下班打卡
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/8/17 11:03
        */
       @ApiOperation("下班打卡")
       @PostMapping("/clockOut")
       public ResponseMessage clockOut(AuthorizationUser authorizationUser, @RequestBody JxcClockInOutRecord jxcClockInOutRecord){
              jxcClockInOutRecord.setDriverId(authorizationUser.getUserId());
              return jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
       }

       /**
        * 司机打卡记录
        * @author  liuy
       * @param authorizationUser
        * @return
        * @date    2019/8/17 15:02
        */
       @ApiOperation("司机打卡记录")
       @PostMapping("/getClockListByDriver")
       public ResponseMessage<ClockRecordVo> getClockListByDriver(AuthorizationUser authorizationUser, @RequestBody ClockRecordListVo clockRecordListVo){
       	      if(null == clockRecordListVo.getDriverUserId()){
	              clockRecordListVo.setDriverUserId(authorizationUser.getUserId());
              }
              return jxcClockInOutRecordService.getClockListByDriver(clockRecordListVo);
       }

       /**
        * 司机正在跑趟中交接班
        * @author  liuy
        * @param jxcDriverChangeWorkVo
        * @return
        * @date    2019/8/24 14:43
        */
       @ApiOperation("司机正在跑趟中交接班")
       @PostMapping("driverChangeWork")
       public ResponseMessage driverChangeWork(AuthorizationUser authorizationUser, @RequestBody JxcDriverChangeWorkVo jxcDriverChangeWorkVo) {
              jxcDriverChangeWorkVo.setDriverId(authorizationUser.getUserId());
              return jxcClockInOutRecordService.driverChangeWork(jxcDriverChangeWorkVo);
       }

       /**
        * 机主设置司机上班
        * @author  liuy
        * @param jxcClockInOutRecord
        * @return
        * @date    2019/9/26 9:35
        */
       @ApiOperation("机主设置司机上班")
       @PostMapping("setUpDriverWorkIn")
       public ResponseMessage setUpDriverWorkIn(AuthorizationUser authorizationUser, @RequestBody JxcClockInOutRecord jxcClockInOutRecord){
       	      jxcClockInOutRecord.setOwnerUserId(authorizationUser.getUserId());
              return jxcClockInOutRecordService.setUpDriverWorkIn(jxcClockInOutRecord);
       }

}