package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 正在进行中的订单所对应的机械没有绑定司机
 * @author liuy
 * @date 2019-09-16 14:02
 */
@JobHandler(value = "machineNoBindDriverHandler")
@Component
public class MachineNoBindDriverHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {

		jxcQuartzFeign.getOrderRunList();
		return SUCCESS;
	}
}
