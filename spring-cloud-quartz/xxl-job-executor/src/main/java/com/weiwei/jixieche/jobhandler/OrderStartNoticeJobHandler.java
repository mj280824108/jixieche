package com.weiwei.jixieche.jobhandler;


import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单开始提醒
 */
@JobHandler(value = "orderStartNoticeJobHandler")
@Component
public class OrderStartNoticeJobHandler extends IJobHandler {

	@Autowired
    JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.orderStartNotice();
		return SUCCESS;
	}


}
