package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcAreaService;
import com.weiwei.jixieche.vo.*;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Api(tags="公共模块--省市区域表")
@RestController
@RequestMapping("jxcArea")
public class JxcAreaController {

       @Resource
       private JxcAreaService jxcAreaService;

       @ApiOperation(httpMethod="GET", value="根据id查询城市id下的所有节点")
       @GetMapping("/getAreaTreeByRootId")
       public ResponseMessage getAreaTree(Integer id){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              AreaNodeVo areaNode = this.jxcAreaService.getAreaTreeByRootId(id);
              result.setData(areaNode);
              return result;
       }

       @ApiOperation(httpMethod="GET", value="根据跟Pid查询子节点记录")
       @GetMapping("/getAreaChildrenByPid")
       public ResponseMessage getAreaChildrenByPid(@RequestParam("id") String pid){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<AreaVo> list = this.jxcAreaService.getAreaChildrenByPid(Integer.parseInt(pid));
              result.setData(list);
              return result;
       }

       @ApiOperation(httpMethod="GET", value="根据城市拼音首字母查询城市列表")
       @GetMapping("/getFirstLetterCityList")
       public ResponseMessage getFirstLetterCityList(){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<FirstLetterCityListVo> list = this.jxcAreaService.getFirstLetterCityList();
              result.setData(list);
              return result;
       }

       @ApiOperation(httpMethod="GET", value="查询开放城市列表")
       @GetMapping("/getOpenedCityList")
       public ResponseMessage<OpenedProvinceVo> getOpenedCityList(){
              return jxcAreaService.getOpenedCityList();
       }


}