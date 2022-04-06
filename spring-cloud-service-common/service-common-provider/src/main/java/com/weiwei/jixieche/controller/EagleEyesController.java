package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.EagleEyesService;
import com.weiwei.jixieche.vo.EagleEyesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EagleEyesController
 * @Description TODO
 * @Author houji
 * @Date 2019/5/24 9:46
 * @Version 1.0.1
 **/
@Api(tags="公共模块--百度鹰眼模块")
@RestController
@RequestMapping("/eagleEyes")
public class EagleEyesController {

    @Autowired
    private EagleEyesService eagleEyesService;

    @ApiOperation(httpMethod="POST", value="百度鹰眼查询轨迹")
    @PostMapping("/baiduEntity")
    public ResponseMessage baiduEntity(@RequestBody EagleEyesVo eagleEyesVo){
        return eagleEyesService.baiduEntity(eagleEyesVo);
    }
}
