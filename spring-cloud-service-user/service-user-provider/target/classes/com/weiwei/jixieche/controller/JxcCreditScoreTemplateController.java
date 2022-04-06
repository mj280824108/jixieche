package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcCreditScoreTemplate;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcCreditScoreTemplateService;
import com.weiwei.jixieche.vo.RejectRule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--信用分模板表")
@RestController
@RequestMapping("jxcCreditScoreTemplate")
public class JxcCreditScoreTemplateController {
       @Resource
       private JxcCreditScoreTemplateService jxcCreditScoreTemplateService;

       @ApiOperation(httpMethod="POST", value="前端分页查询信用分模板表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcCreditScoreTemplate> findByPageForFront(@RequestBody JxcCreditScoreTemplate jxcCreditScoreTemplate) {
              return this.jxcCreditScoreTemplateService.findByPageForFront(jxcCreditScoreTemplate.getPageNo(),jxcCreditScoreTemplate.getPageSize(),jxcCreditScoreTemplate);
       }

       @ApiOperation(httpMethod="POST", value="添加信用分模板表")
       @PostMapping("/insert")
       public ResponseMessage<JxcCreditScoreTemplate> insert(@RequestBody JxcCreditScoreTemplate jxcCreditScoreTemplate) {
              return this.jxcCreditScoreTemplateService.addObj(jxcCreditScoreTemplate);
       }

       @ApiOperation(httpMethod="POST", value="编辑信用分模板表")
       @PostMapping("/edit")
       public ResponseMessage<JxcCreditScoreTemplate> edit(@RequestBody JxcCreditScoreTemplate jxcCreditScoreTemplate) {
              return this.jxcCreditScoreTemplateService.modifyObj(jxcCreditScoreTemplate);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询信用分模板表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcCreditScoreTemplate> queryById(Integer id) {
              return this.jxcCreditScoreTemplateService.queryObjById(id);
       }

       @ApiOperation("获取拒绝规则 cancelType 1:机主拒绝/解雇司机，2:机主取消承租方订单，3：司机取消已接受的短期职位 4：承租方取消已有司机接单的订单")
       @PostMapping("/getRule")
       public ResponseMessage<RejectRule> getRule(AuthorizationUser user,@RequestParam("cancelType") Integer cancelType) {
              int condition = jxcCreditScoreTemplateService.getRejectCondition(user.getRoleId(),cancelType);
              RejectRule rejectRule = new RejectRule();
              rejectRule.setTimestamp(System.currentTimeMillis());
              rejectRule.setCondition(condition);
              return new ResponseMessage<>(rejectRule);
       }
}