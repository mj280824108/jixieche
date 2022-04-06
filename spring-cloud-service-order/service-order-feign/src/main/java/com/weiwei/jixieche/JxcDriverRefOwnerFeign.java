package com.weiwei.jixieche;

import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.hystrix.JxcDriverRefOwnerFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-service-provider", fallback = JxcDriverRefOwnerFeignHystrix.class)
public interface JxcDriverRefOwnerFeign {

	/**
	 * 添加子账号与机主关联关系
	 * @author  liuy
	 * @param jxcDriverRefOwner
	 * @return
	 * @date    2019/8/29 11:16
	 */
	@PostMapping("/driver/addDriverRefOwner")
	ResponseMessage addDriverRefOwner(@RequestBody JxcDriverRefOwner jxcDriverRefOwner);
}
