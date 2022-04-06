package com.weiwei.jixieche.jobhandler;


import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 每周二将上周冻结金额解冻
* @Author:      liuy
* @Date:  2019/9/16 13:49
*/
@JobHandler(value = "unfreezeHandler")
@Component
public class UnfreezeHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.unfreeze();
		return SUCCESS;
	}
}
