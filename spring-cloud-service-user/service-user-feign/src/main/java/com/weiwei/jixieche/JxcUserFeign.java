package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcUserFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ManagerRegisterVo;
import com.weiwei.jixieche.vo.OwnerRegisterDriverVo;
import com.weiwei.jixieche.vo.UserInfoVo;
import com.weiwei.jixieche.vo.UserRegisterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName JxcUserFeign
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 11:53
 * @Version 1.0.1
 **/
@FeignClient(value = "user-service-provider", fallback = JxcUserFeignHystrix.class)
public interface JxcUserFeign {

    @ApiOperation(httpMethod="POST", value="机主注册临时司机")
    @PostMapping("/jxcUser/ownerRegisterDriver")
    ResponseMessage ownerRegisterDriver(@RequestBody OwnerRegisterDriverVo userLoginVo);

    @ApiOperation(httpMethod="POST", value="注册承租方管理员")
    @PostMapping("/jxcUser/managerRegister")
    ResponseMessage managerRegister(@RequestBody ManagerRegisterVo managerRegisterVo);

    @ApiOperation(httpMethod="GET", value="查询用户基本信息")
    @GetMapping("/jxcUser/queryUserInfo")
    ResponseMessage<UserInfoVo> queryUserInfo(@RequestParam("userId") Integer userId);
}
