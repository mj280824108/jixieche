package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: 更新机械行驶证、营运证状态
* @Author:      liuy
* @Date:  2019/9/16 13:58
*/
@JobHandler(value = "updateMachineStateJobHandler")
@Component
public class UpdateMachineStateJobHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {
		jxcQuartzFeign.updateMachine();
		return SUCCESS;
	}
}
