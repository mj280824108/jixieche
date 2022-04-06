package com.weiwei.jixieche;

import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.hystrix.JxcWaitHandleItemsFeignHystrix;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 待办事项
 * @author liuy
 * @date 2019-08-24 10:12
 */
@FeignClient(value = "order-service-provider", fallback = JxcWaitHandleItemsFeignHystrix.class)
public interface JxcWaitHandleItemsFeign {

	/**
	 * 待办事项
	 * @author  liuy
	 * @param jxcWaitHandleItems
	 * @return
	 * @date    2019/8/24 10:11
	 */
	@PostMapping("/jxcWaitHandleItems/insert")
	ResponseMessage<JxcWaitHandleItems> insert(@RequestBody JxcWaitHandleItems jxcWaitHandleItems);
}
