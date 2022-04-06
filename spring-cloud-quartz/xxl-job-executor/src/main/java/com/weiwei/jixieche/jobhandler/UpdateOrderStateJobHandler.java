package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 每晚12点更新承租方订单状态
* @Author:      liuy
* @Date:  2019/9/17 19:38
*/
@JobHandler(value = "updateOrderStateJobHandler")
@Component
public class UpdateOrderStateJobHandler extends IJobHandler {
	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {

		jxcQuartzFeign.updateOrderState();
		return SUCCESS;
	}
}
