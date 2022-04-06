package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 中午12点 下午16点  晚上20点分别运行一次 发催款通知
 * @author liuy
 * @date 2019-09-16 14:06
 */
@JobHandler(value = "payNoticeHandler")
@Component
public class PayNoticeHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.payNotice();
		return SUCCESS;
	}
}
