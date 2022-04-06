package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcShortMsgTemplateService;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import com.weiwei.jixieche.vo.ShortMsgTemplateVo;
import com.weiwei.jixieche.vo.ShortMsgTestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 1.0.1
 **/
@Api(tags="公共模块--短信模板")
@RestController
@RequestMapping("jxcShortMsgTemplate")
public class JxcShortMsgTemplateController {
       @Resource
       private JxcShortMsgTemplateService jxcShortMsgTemplateService;

       @ApiOperation(httpMethod = "POST", value = "根据短信模板发送短信")
       @PostMapping(value = "/sendShortMsg")
       public ResponseMessage sendShortMsg(@RequestBody ShortMsgTemplateVo shortMsgTemplateVo) throws Exception {
              return jxcShortMsgTemplateService.sendShortMsg(shortMsgTemplateVo);
       }

       @ApiOperation(httpMethod = "POST", value = "发送短信验证码(测试所用)")
       @PostMapping(value = "/sendShortMsgCodeTest")
       public ResponseMessage sendShortMsgCodeTest(@RequestBody ShortMsgTestVo shortMsgTestVo){
              return jxcShortMsgTemplateService.sendShortMsgCodeTest(shortMsgTestVo);
       }

       @ApiOperation(httpMethod = "POST", value = "阿里云发送短信App调用")
       @PostMapping(value = "/aliSendShortMsg")
       public ResponseMessage aliSendShortMsg(@RequestBody AliShortMsgVo aliShortMsgVo){
              return jxcShortMsgTemplateService.aliSendShortMsg(aliShortMsgVo);
       }




}