package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 强制打下班卡的定时任务
* @Author:      liuy
* @Date:  2019/9/16 13:48
*/
@JobHandler(value = "forceClockOutHandler")
@Component
public class ForceClockOutJobHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {

		jxcQuartzFeign.forceClockOut();
		return SUCCESS;
	}

}
