package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author 钟焕 
 * @Description
 * @Date 15:32 2019-10-16
 **/
@JobHandler(value = "stopSendOrderHandler")
@Component
public class StopSendOrderHandler extends IJobHandler {
	@Autowired
    JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.stopSendOrder();
		return SUCCESS;
	}
}
