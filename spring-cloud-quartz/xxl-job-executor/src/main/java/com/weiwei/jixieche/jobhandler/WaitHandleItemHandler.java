package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每天晚上0点运行一次，将待支付的金额加入待办事项
 * @author liuy
 * @date 2019-09-16 14:08
 */
@JobHandler(value = "waitHandleItemHandler")
@Component
public class WaitHandleItemHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.insertWaitHandleItem();
		return SUCCESS;
	}

}
