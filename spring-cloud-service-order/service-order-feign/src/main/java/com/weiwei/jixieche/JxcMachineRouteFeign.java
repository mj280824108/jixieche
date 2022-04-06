package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcMachineRouteFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@FeignClient(value = "order-service-provider", fallback = JxcMachineRouteFeignHystrix.class)
public interface JxcMachineRouteFeign {

    @ApiOperation(httpMethod="POST", value="根据机械行程趟数集合查询趟数总金额")
    @PostMapping("/jxcMachineRoute/queryTotalAmount")
    ResponseMessage queryTotalAmount(@RequestBody ArrayList<Long> routeIdList);

    @ApiOperation(httpMethod="POST", value="根据机械行程趟数集合批量修改机械行程支付状态")
    @PostMapping("/jxcMachineRoute/updateBatchPayStatus")
    void updateBatchPayStatus(@RequestBody UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo);

    @ApiOperation(httpMethod="POST", value="查询司机用户所绑定的车牌号码")
    @PostMapping("/jxcMachineRefDriver/getMachineCarNoByUserId")
    ResponseMessage getMachineCarNoByUserId(@RequestParam("userId") Integer userId);


}
