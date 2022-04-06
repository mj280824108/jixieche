package com.weiwei.jixieche.jobhandler;


import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 定时将超过有效期的职位的状态改为无效
* @Author:      liuy
* @Date:  2019/9/16 13:49
*/
@JobHandler(value = "updateJobStatusHandler")
@Component
public class UpdateJobStatusHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.updateJobStatus();
		return SUCCESS;
	}
}
