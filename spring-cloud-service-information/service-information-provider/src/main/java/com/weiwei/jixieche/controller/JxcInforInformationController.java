package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcInforInformationService;
import com.weiwei.jixieche.vo.InforFireListVo;
import com.weiwei.jixieche.vo.InforParamVo;
import com.weiwei.jixieche.vo.InforPointNumberVo;
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
@Api(tags="咨询信息表")
@RestController
@RequestMapping("jxcInforInformation")
public class JxcInforInformationController {
       @Resource
       private JxcInforInformationService jxcInforInformationService;

       @ApiOperation(httpMethod="POST", value="前端分页查询咨询信息表")
       @PostMapping("/front/findByPage")
       public ResponseMessage<JxcInforInformation> findByPageForFront(@RequestBody JxcInforInformation jxcInforInformation) {
              return this.jxcInforInformationService.findByPageForFront(jxcInforInformation.getPageNo(),jxcInforInformation.getPageSize(),jxcInforInformation);
       }

       @ApiOperation(httpMethod="POST", value="查询机主首页轮播资讯")
       @PostMapping("/queryInforFireList")
       public ResponseMessage<InforFireListVo> queryInforFireList() {
              return this.jxcInforInformationService.queryInforFireList();
       }

       @ApiOperation(httpMethod="POST", value="添加咨询信息表")
       @PostMapping("/insert")
       public ResponseMessage<JxcInforInformation> insert(@RequestBody JxcInforInformation jxcInforInformation) {
              return this.jxcInforInformationService.addObj(jxcInforInformation);
       }

       @ApiOperation(httpMethod="POST", value="编辑咨询信息表")
       @PostMapping("/edit")
       public ResponseMessage<JxcInforInformation> edit(@RequestBody JxcInforInformation jxcInforInformation) {
              return this.jxcInforInformationService.modifyObj(jxcInforInformation);
       }

       @ApiOperation(httpMethod="POST", value="资讯点赞(浏览)(分享)量增+1")
       @PostMapping("/operation")
       public ResponseMessage operation(@RequestBody InforPointNumberVo inforPointNumberVo) {
              return this.jxcInforInformationService.operation(inforPointNumberVo);
       }

       @ApiOperation(httpMethod="POST", value="通过ID查询咨询信息表")
       @PostMapping("/queryById")
       public ResponseMessage queryById(@RequestBody InforParamVo inforParamVo) {
              return this.jxcInforInformationService.queryById(inforParamVo);
       }
}