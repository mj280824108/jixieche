package com.weiwei.jixieche;

import com.weiwei.jixieche.hystrix.JxcAreaFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "COMMON-SERVICE-PROVIDER", fallback = JxcAreaFeignHystrix.class)
public interface JxcAreaFeign {

    /**
     * 根据城市id查询城市id下的所有节点
     * @param id
     * @return
     */
    @GetMapping("/jxcArea/getAreaTreeByRootId")
    ResponseMessage getAreaTree(@RequestParam("id") String id);

    /**
     * 根据跟Pid查询子节点记录
     * @param pid
     * @return
     */
    @GetMapping("/jxcArea/getAreaChildrenByPid")
    ResponseMessage getAreaChildrenByPid(@RequestParam("id") String pid);

    /**
     * 查询城市节点记录
     * @return
     */
    @GetMapping("/area/getFirstLetterCityList")
    ResponseMessage getFirstLetterCityList();

    /**
     * 查询开放城市列表
     * @return
     */
    @GetMapping("/area/getOpenedCityList")
    ResponseMessage getOpenedCityList();

}
