package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcWaitHandleItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 待办事项
* @Author:      liuy
* @Date:  2019/8/24 10:09
*/
@Api(tags="待办事项")
@RestController
@RequestMapping("jxcWaitHandleItems")
public class JxcWaitHandleItemsController {
       @Resource
       private JxcWaitHandleItemsService jxcWaitHandleItemsService;

       /**
        * 待办事项
        * @author  liuy
       * @param jxcWaitHandleItems
        * @return
        * @date    2019/8/24 10:11
        */
       @ApiOperation(httpMethod="POST", value="添加待办事项")
       @PostMapping("/insert")
       public ResponseMessage<JxcWaitHandleItems> insert(@RequestBody JxcWaitHandleItems jxcWaitHandleItems) {
              return this.jxcWaitHandleItemsService.addObj(jxcWaitHandleItems);
       }

       /**
        * 更新待办事项为已处理(已删除)
        * @author  liuy
       * @param jxcWaitHandleItems
        * @return
        * @date    2019/8/24 10:19
        */
       @ApiOperation(httpMethod="POST", value="编辑待处理事项")
       @PostMapping("/edit")
       public ResponseMessage<JxcWaitHandleItems> edit(@RequestBody JxcWaitHandleItems jxcWaitHandleItems) {
              return this.jxcWaitHandleItemsService.modifyObj(jxcWaitHandleItems);
       }

       /**
        * 机主待办事项列表
        * @author  liuy
        * @param authorizationUser
        * @return
        * @date    2019/8/24 10:24
        */
       @ApiOperation("机主待办事项列表")
       @PostMapping("/getOwnerHandelItemsList")
       public ResponseMessage getOwnerHandelItemsList(AuthorizationUser authorizationUser,
                                                      @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
              return this.jxcWaitHandleItemsService.getOwnerHandelItemsList(authorizationUser.getUserId(), pageNo, pageSize);
       }

       /**
        * @Author 钟焕
        * @Description
        * @Date 16:49 2019-09-05
        **/
       @ApiOperation("承租方查询待办事项列表")
       @PostMapping("/selectTenantryWaitHandleList")
       public ResponseMessage selectTenantryWaitHandleList(AuthorizationUser user,
                                                      @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
              return this.jxcWaitHandleItemsService.selectTenantryWaitHandleList(user, pageNo, pageSize);
       }

}