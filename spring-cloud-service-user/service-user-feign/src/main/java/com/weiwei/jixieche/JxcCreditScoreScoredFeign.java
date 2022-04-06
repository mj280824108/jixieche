package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcCreditScoreScoredFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName JxcCreditScoreScoredFeign
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 14:17
 * @Version 1.0.1
 **/

@FeignClient(value = "user-service-provider", fallback = JxcCreditScoreScoredFeignHystrix.class)
public interface JxcCreditScoreScoredFeign {

    @ApiOperation(httpMethod="POST", value="添加信用分记录(feign调用)")
    @PostMapping("/jxcCreditScoreScored/insertCreditScoreScored")
    ResponseMessage insertCreditScoreScored(@RequestBody UserCreditScoreVo userCreditScoreVo);

    @ApiOperation(httpMethod="GET", value="根据userId查询用户积分总分数")
    @GetMapping("/jxcCreditScoreScored/queryTotalScoreByUserId")
    ResponseMessage queryTotalScoreByUserId(Integer userId);

}
