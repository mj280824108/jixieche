package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 超过12小时机主申请退出,承租方未同意时强制更新为同意
 * @author liuy
 * @date 2019-09-16 14:04
 */
@JobHandler(value = "updateOwnerApplyQuitStatusHandler")
@Component
public class UpdateOwnerApplyQuitStatusHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.updateOwnerApplyQuitStatus();
		return SUCCESS;
	}
}
