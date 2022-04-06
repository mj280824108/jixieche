package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcMachineFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @Description: 更新机械状态
* @Author:      liuy
* @Date:  2019/9/29 15:13
*/
@FeignClient(value = "order-service-provider", fallback = JxcMachineFeignHystrix.class)
public interface JxcMachineFeign {

    /**
     * 更新机械状态为空闲中
     * @author  liuy
     * @param machineId
     * @return
     * @date    2019/9/29 15:12
     */
    @PostMapping("/jxcMachine/updateMachineById")
    ResponseMessage updateMachineById(@RequestParam("machineId") Integer machineId);
}
