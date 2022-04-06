package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcBankCard;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcBankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Api(tags="用户模块--用户银行卡")
@RestController
@RequestMapping("jxcBankCard")
public class JxcBankCardController {
       @Resource
       private JxcBankCardService jxcBankCardService;

       @ApiOperation(httpMethod="POST", value="前端分页查询用户银行卡")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcBankCard> findByPageForFront(@RequestBody JxcBankCard jxcBankCard) {
              return this.jxcBankCardService.findByPageForFront(jxcBankCard.getPageNo(),jxcBankCard.getPageSize(),jxcBankCard);
       }

       @ApiOperation(httpMethod="POST", value="查询用户银行卡列表信息(不分页)")
       @PostMapping("/queryBankCardList")
       public ResponseMessage<JxcBankCard> queryBankCardList(@RequestBody JxcBankCard jxcBankCard) {
              return this.jxcBankCardService.queryBankCardList(jxcBankCard);
       }

       @ApiOperation(httpMethod="POST", value="添加用户银行卡")
       @PostMapping("/insert")
       public ResponseMessage<JxcBankCard> insert(@RequestBody JxcBankCard jxcBankCard) {
              return this.jxcBankCardService.addObj(jxcBankCard);
       }

       @ApiOperation(httpMethod="POST", value="编辑用户银行卡")
       @PostMapping("/edit")
       public ResponseMessage<JxcBankCard> edit(@RequestBody JxcBankCard jxcBankCard) {
              return this.jxcBankCardService.modifyObj(jxcBankCard);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询用户银行卡")
       @GetMapping("/queryById")
       public ResponseMessage<JxcBankCard> queryById(Integer id) {
              return this.jxcBankCardService.queryObjById(id);
       }




}