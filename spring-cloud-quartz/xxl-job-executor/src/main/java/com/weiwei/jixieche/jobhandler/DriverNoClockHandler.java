package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 正在跑趟中的司机未打上班卡
 * @author liuy
 * @date 2019-09-16 13:59
 */
@JobHandler(value = "driverNoClockHandler")
@Component
public class DriverNoClockHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {

		jxcQuartzFeign.getDriverNoClock();
		return SUCCESS;
	}
}
