package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcInforCollectionRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcInforCollectionRecordService;
import com.weiwei.jixieche.vo.InforCollectionRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
@Api(tags="资讯信息收藏记录表")
@RestController
@RequestMapping("jxcInforCollectionRecord")
public class JxcInforCollectionRecordController {
       @Resource
       private JxcInforCollectionRecordService jxcInforCollectionRecordService;

       @ApiOperation(httpMethod="POST", value="添加资讯信息收藏记录表")
       @PostMapping("/insert")
       public ResponseMessage<JxcInforCollectionRecord> insert(@RequestBody JxcInforCollectionRecord jxcInforCollectionRecord) {
              return this.jxcInforCollectionRecordService.addObj(jxcInforCollectionRecord);
       }

       @ApiOperation(httpMethod="POST", value="查询用户收藏资讯信息")
       @PostMapping("/queryUserCollection")
       public ResponseMessage<InforCollectionRecordVo> queryUserCollection(@RequestBody InforCollectionRecordVo inforCollectionRecordVo) {
              return this.jxcInforCollectionRecordService.queryUserCollection(inforCollectionRecordVo);
       }



}