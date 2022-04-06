package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcWaitHandleItemsFeign;
import com.weiwei.jixieche.bean.JxcWaitHandleItems;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.stereotype.Component;

/**
 * 待办事项
 * @author liuy
 * @date 2019-08-24 10:12
 */
@Component
public class JxcWaitHandleItemsFeignHystrix implements JxcWaitHandleItemsFeign {

	/**
	 * 待办事项
	 * @author  liuy
	 * @param jxcWaitHandleItems
	 * @return
	 * @date    2019/8/24 10:16
	 */
	@Override
	public ResponseMessage<JxcWaitHandleItems> insert(JxcWaitHandleItems jxcWaitHandleItems) {
		return null;
	}
}
