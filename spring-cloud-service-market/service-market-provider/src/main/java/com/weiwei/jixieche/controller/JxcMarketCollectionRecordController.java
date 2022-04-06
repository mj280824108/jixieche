package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMarketCollectionRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcMarketCollectionRecordService;
import com.weiwei.jixieche.vo.UserCollectionInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Api(tags="市场模块--市场收藏记录")
@RestController
@RequestMapping("jxcMarketCollectionRecord")
public class JxcMarketCollectionRecordController {
       @Resource
       private JxcMarketCollectionRecordService jxcMarketCollectionRecordService;

       @ApiOperation(httpMethod="POST", value="前端分页查询市场收藏记录")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcMarketCollectionRecord> findByPageForFront(@RequestBody JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              return this.jxcMarketCollectionRecordService.findByPageForFront(jxcMarketCollectionRecord.getPageNo(),jxcMarketCollectionRecord.getPageSize(),jxcMarketCollectionRecord);
       }

       @ApiOperation(httpMethod="POST", value="分页查询用户收藏资讯/机械/店铺信息")
       @PostMapping("/queryCollectionInfo")
       public ResponseMessage<JxcMarketCollectionRecord> queryCollectionInfo(@RequestBody UserCollectionInfoVo userCollectionInfoVo) {
              return this.jxcMarketCollectionRecordService.queryCollectionInfo(userCollectionInfoVo);
       }

       @ApiOperation(httpMethod="POST", value="添加市场收藏记录")
       @PostMapping("/insert")
       public ResponseMessage<JxcMarketCollectionRecord> insert(@RequestBody JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              return this.jxcMarketCollectionRecordService.addObj(jxcMarketCollectionRecord);
       }

       @ApiOperation(httpMethod="POST", value="编辑市场收藏记录")
       @PostMapping("/edit")
       public ResponseMessage<JxcMarketCollectionRecord> edit(@RequestBody JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              return this.jxcMarketCollectionRecordService.modifyObj(jxcMarketCollectionRecord);
       }

       @ApiOperation(httpMethod="GET", value="通过ID查询市场收藏记录")
       @GetMapping("/queryById")
       public ResponseMessage<JxcMarketCollectionRecord> queryById(Integer id) {
              return this.jxcMarketCollectionRecordService.queryObjById(id);
       }

       @ApiOperation(httpMethod="POST", value="用户收藏/取消收藏资讯/机械/店铺")
       @PostMapping("/editUserCollectionRecord")
       public ResponseMessage<JxcMarketCollectionRecord> editUserCollectionRecord(@RequestBody JxcMarketCollectionRecord jxcMarketCollectionRecord) {
              return this.jxcMarketCollectionRecordService.editUserCollectionRecord(jxcMarketCollectionRecord);
       }


}