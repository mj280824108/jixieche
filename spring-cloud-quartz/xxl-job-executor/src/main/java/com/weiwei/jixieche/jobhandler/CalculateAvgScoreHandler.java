package com.weiwei.jixieche.jobhandler;

import com.weiwei.jixieche.JxcQuartzFeign;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 评价：计算平均分
 * @author liuy
 * @date 2019-09-16 14:09
 */
@JobHandler(value = "calculateAvgScoreHandler")
@Component
public class CalculateAvgScoreHandler extends IJobHandler {

	@Autowired
	JxcQuartzFeign jxcQuartzFeign;

	@Override
	public ReturnT<String> execute(String s) throws Exception {

		jxcQuartzFeign.calculateAvgScore();
		return SUCCESS;
	}
}
