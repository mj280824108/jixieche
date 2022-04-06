package com.weiwei.jixieche.jobhandler;


import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 2小时以后未处理的消纳券订单强制更新状态为已取消
* @Author:      liuy
* @Date:  2019/10/22 9:44
*/
@JobHandler(value = "updateSiteOrderFlagHandler")
@Component
public class UpdateSiteOrderFlagHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.updateSiteOrderFlag();
		return SUCCESS;
	}
}
