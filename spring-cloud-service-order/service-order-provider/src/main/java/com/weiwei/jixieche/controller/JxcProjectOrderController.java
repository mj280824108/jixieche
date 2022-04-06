package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcAbnormalOrderAppeal;
import com.weiwei.jixieche.bean.JxcOwnerApplyQuit;
import com.weiwei.jixieche.bean.JxcProjectOrder;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcProjectOrderService;
import com.weiwei.jixieche.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @Author 钟焕
 * @Description
 * @Date 16:37 2019-08-21
 **/
@Api(tags="承租方订单表")
@RestController
@RequestMapping("jxcProjectOrder")
public class JxcProjectOrderController {
       @Resource
       private JxcProjectOrderService jxcProjectOrderService;

       @ApiOperation(httpMethod="POST", value="前端分页查询承租方订单表")
       @PostMapping("/front/findByPage")
       public ResponseMessage findByPageForFront(@RequestBody JxcProjectOrder jxcProjectOrder) {
              return this.jxcProjectOrderService.findByPageForFront(jxcProjectOrder.getPageNo(),jxcProjectOrder.getPageSize(),jxcProjectOrder);
       }

       @ApiOperation(httpMethod="POST", value="根据预计公里数计算预计价格")
       @PostMapping("/getEstimatePrice")
       public ResponseMessage getEstimatePrice(@RequestBody JxcSiteVo jxcSiteVo) {
              return this.jxcProjectOrderService.estimatePrice(jxcSiteVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方发布订单")
       @PostMapping("/addJxcProjectOrder")
       public ResponseMessage<JxcProjectOrder> insert(AuthorizationUser user,@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              jxcProjectOrderVo.setUserId(user.getUserId());
              return this.jxcProjectOrderService.addJxcProjectOrder(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方查询订单列表")
       @PostMapping("/projectOrderList")
       public ResponseMessage projectOrderList(AuthorizationUser user,@RequestBody ProjectOrderListVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.selectProjectOrderList(user,jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="GET", value="承租方查询订单详情")
       @GetMapping("/queryById")
       public ResponseMessage queryById(Long id) {
              return this.jxcProjectOrderService.queryJxcProjectOrderById(id);
       }

       @ApiOperation(httpMethod="POST", value="承租方查询接单车辆列表（分页参数+id+orderState）")
       @PostMapping("/selectMachineList")
       public ResponseMessage selectMachineListInOrder(@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.selectMachineListInOrder(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方解雇机械")
       @PostMapping("/fireMachine")
       public ResponseMessage fireMachine(@RequestParam("orderId") Long orderId,@RequestParam("machineId") Integer machineId,@RequestParam("reason") String reason) {
              return this.jxcProjectOrderService.fireMachine(orderId,machineId,reason);
       }

       @ApiOperation(httpMethod="POST", value="承租方停止继续要车")
       @PostMapping("/stopContinueCar")
       public ResponseMessage stopContinueCar(@RequestParam("orderId") Long orderId) {
              return this.jxcProjectOrderService.stopContinueCar(orderId);
       }

       @ApiOperation(httpMethod="POST", value="承租方申请停工")
       @PostMapping("/applyStopWork")
       public ResponseMessage applyStopWork(@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.applyStopWork(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方申请提前完工")
       @PostMapping("/applyEndWork")
       public ResponseMessage applyEndWork(@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.applyEndWork(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方取消订单检查")
       @PostMapping("/tenCancelOrderConfirm")
       public ResponseMessage tenCancelOrderConfirm(@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.tenCancelOrderConfirm(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方取消订单")
       @PostMapping("/cancelProjectOrder")
       public ResponseMessage cancelProjectOrder(AuthorizationUser user,@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.cancelProjectOrder(user.getUserId(),jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方更换消纳场验证")
       @PostMapping("/changeSiteConfirm")
       public ResponseMessage changeSiteConfirm(AuthorizationUser user, @RequestParam("id") Long orderId, @RequestParam("siteId") Integer siteId) {
              return this.jxcProjectOrderService.confirmSiteCoupon(user,orderId,siteId);
       }

       @ApiOperation(httpMethod="POST", value="承租方更换消纳场")
       @PostMapping("/changeSite")
       public ResponseMessage changeSite(AuthorizationUser user, @RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.changeSite(user,jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方查看装车记录")
       @PostMapping("/selectRouteRecord")
       public ResponseMessage selectRouteRecord(@RequestBody JxcProjectOrderVo jxcProjectOrderVo) {
              return this.jxcProjectOrderService.selectRouteRecord(jxcProjectOrderVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方查看趟数记录列表")
       @PostMapping("/selectRouteRecordList")
       public ResponseMessage selectRouteRecordList(@RequestBody MachineRouteRecord machineRouteRecord) {
              return this.jxcProjectOrderService.selectRouteRecordList(machineRouteRecord);
       }

       @ApiOperation(httpMethod="POST", value="承租方查看异常待支付趟数记录列表")
       @PostMapping("/selectAbnormalRouteRecordList")
       public ResponseMessage selectAbnormalRouteRecordList(@RequestParam("orderId") Long orderId) {
              return this.jxcProjectOrderService.selectAbnormalRouteRecordList(orderId);
       }

       @ApiOperation(httpMethod="POST", value="承租方查询异常详情")
       @PostMapping("/getAbnormalRouteDetail")
       public ResponseMessage getAbnormalRouteDetail(AuthorizationUser user, @RequestParam("routeId") Long routeId) {
              return this.jxcProjectOrderService.getAbnormalRouteDetail(user, routeId);
       }

       @ApiOperation(httpMethod="POST", value="承租方申诉异常行程")
       @PostMapping("/applyAbnormalRoute")
       public ResponseMessage applyAbnormalRoute(@RequestBody JxcAbnormalOrderAppeal jxcAbnormalOrderAppeal) {
              return this.jxcProjectOrderService.applyAbnormalRoute(jxcAbnormalOrderAppeal);
       }

       @ApiOperation(httpMethod="POST", value="承租方确认异常行程")
       @PostMapping("/confirmAbnormalRoute")
       public ResponseMessage confirmAbnormalRoute(AuthorizationUser user,@RequestParam("routeId") Long routeId) {
              return this.jxcProjectOrderService.confirmAbnormalRoute(user,routeId);
       }

       @ApiOperation(httpMethod="POST", value="承租方订单详情查询退场申请记录")
       @PostMapping("/selectOwnerQuitRecordList")
       public ResponseMessage selectOwnerQuitRecordList(AuthorizationUser user,@RequestBody JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo) {
              return this.jxcProjectOrderService.selectOwnerQuitRecordList(user, jxcOwnerApplyQuitVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方查看单个的退场申请（用于待办事项中处理退场申请）")
       @PostMapping("/getOwnerQuitRecord")
       public ResponseMessage getOwnerQuitRecord(AuthorizationUser user,@RequestBody JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo) {
              return this.jxcProjectOrderService.selectOwnerQuitRecordList(user, jxcOwnerApplyQuitVo);
       }

       @ApiOperation(httpMethod="POST", value="承租方处理机主退场申请 applyState  1:同意; 2:驳回")
       @PostMapping("/dealOwnerQuitApply")
       public ResponseMessage dealOwnerQuitApply(@RequestBody JxcOwnerApplyQuit jxcOwnerApplyQuit) {
              return this.jxcProjectOrderService.dealOwnerQuitApply(jxcOwnerApplyQuit);
       }


}