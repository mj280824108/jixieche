package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcCreditScoreScored;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcCreditScoreScoredService;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
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
@Api(tags="用户模块--用户信用分记录表")
@RestController
@RequestMapping("jxcCreditScoreScored")
public class JxcCreditScoreScoredController {
       @Resource
       private JxcCreditScoreScoredService jxcCreditScoreScoredService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户信用分记录表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcCreditScoreScored> findByPageForFront(@RequestBody JxcCreditScoreScored jxcCreditScoreScored) {
              return this.jxcCreditScoreScoredService.findByPageForFront(jxcCreditScoreScored.getPageNo(),jxcCreditScoreScored.getPageSize(),jxcCreditScoreScored);
       }

       @ApiOperation(httpMethod="POST", value="添加用户信用分记录表")
       @PostMapping("/insert")
       public ResponseMessage<JxcCreditScoreScored> insert(@RequestBody JxcCreditScoreScored jxcCreditScoreScored) {
              return this.jxcCreditScoreScoredService.addObj(jxcCreditScoreScored);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户信用分记录表")
       @PostMapping("/edit")
       public ResponseMessage<JxcCreditScoreScored> edit(@RequestBody JxcCreditScoreScored jxcCreditScoreScored) {
              return this.jxcCreditScoreScoredService.modifyObj(jxcCreditScoreScored);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户信用分记录表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcCreditScoreScored> queryById(Integer id) {
              return this.jxcCreditScoreScoredService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="添加信用分记录(feign调用)")
       @PostMapping("/insertCreditScoreScored")
       public ResponseMessage insertCreditScoreScored(@RequestBody UserCreditScoreVo userCreditScoreVo) {
              return this.jxcCreditScoreScoredService.insertCreditScoreScored(userCreditScoreVo);
       }

       @ApiOperation(httpMethod="GET", value="根据userId查询用户积分总分数")
       @GetMapping("/queryTotalScoreByUserId")
       public ResponseMessage queryTotalScoreByUserId(Integer userId) {
              return this.jxcCreditScoreScoredService.queryTotalScoreByUserId(userId);
       }


}