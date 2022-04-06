package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcTradeOwnerService;
import com.weiwei.jixieche.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags="机主交易流水表")
@RestController
@RequestMapping("jxcTradeOwner")
public class JxcTradeOwnerController {
       @Resource
       private JxcTradeOwnerService jxcTradeOwnerService;

       @ApiOperation(httpMethod="POST", value="前端分页查询机主交易流水表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcTradeOwner> findByPageForFront(@RequestBody JxcTradeOwner jxcTradeOwner) {
              return this.jxcTradeOwnerService.findByPageForFront(jxcTradeOwner.getPageNo(),jxcTradeOwner.getPageSize(),jxcTradeOwner);
       }

       /*@ApiOperation(httpMethod="POST", value="添加机主交易流水表")
       @PostMapping("/insert")
       public ResponseMessage<JxcTradeOwner> insert(@RequestBody JxcTradeOwner jxcTradeOwner) {
              return this.jxcTradeOwnerService.addObj(jxcTradeOwner);
       }
*/
       /*@ApiOperation(httpMethod="POST", value="编辑机主交易流水表")
       @PostMapping("/edit")
       public ResponseMessage<JxcTradeOwner> edit(@RequestBody JxcTradeOwner jxcTradeOwner) {
              return this.jxcTradeOwnerService.modifyObj(jxcTradeOwner);
       }*/

       @ApiOperation(httpMethod="GET", value="通过ID查询机主交易流水表")
       @GetMapping("/queryById")
       public ResponseMessage<JxcTradeOwner> queryById(@RequestParam(value="id",required = true) Long id) {
              return this.jxcTradeOwnerService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="机主/司机查询我的钱包")
       @PostMapping("/queryOwnerWallet")
       public ResponseMessage<OwnerWalletVo> queryOwnerWallet(AuthorizationUser user,@RequestBody OwnerWalletVo ownerWalletVo) {
              return this.jxcTradeOwnerService.queryOwnerWallet(user,ownerWalletVo);
       }

       @ApiOperation(httpMethod="POST", value="机主/司机交易明细记录")
       @PostMapping("/queryOwnerTradeRecord")
       public ResponseMessage queryOwnerTradeRecord(AuthorizationUser user,@RequestBody OwnerTradeRecordListVo ownerTradeRecordVo) {
              return this.jxcTradeOwnerService.queryOwnerTradeRecord(user,ownerTradeRecordVo);
       }

       @ApiOperation(httpMethod="POST", value="查询机主月份收入支出总金额")
       @PostMapping("/queryOwnerTotalPay")
       public ResponseMessage queryOwnerTotalPay(@RequestBody OwnerTradePayVo ownerTradePayVo) {
              return this.jxcTradeOwnerService.queryOwnerTotalPay(ownerTradePayVo);
       }
}