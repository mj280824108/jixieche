package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcQuartzFeign;
import org.springframework.stereotype.Component;

/**
 * @author liuy
 * @date 2019-09-16 13:44
 */
@Component
public class JxcQuartzFeignHystrix implements JxcQuartzFeign {
	@Override
	public void updateOrderState() {
	}

	@Override
	public void updateMachine() {

	}

	@Override
	public void getDriverNoClock() {

	}

	@Override
	public void getOrderRunList() {

	}

	@Override
	public void updateJobStatus() {

	}

	@Override
	public void forceClockOut() {

	}

	@Override
	public void updateOwnerApplyQuitStatus() {

	}

	@Override
	public void payNotice() {

	}

	@Override
	public void insertWaitHandleItem() {

	}

	@Override
	public void calculateAvgScore() {

	}

	@Override
	public void updateShortJobStateById() {

	}

	@Override
	public void createDriverBill() {

	}

	@Override
	public void unfreeze() {

	}

	@Override
	public void orderStartNotice() {

	}

	@Override
	public void stopSendOrder() {

	}

	@Override
	public void updateSiteOrderFlag() {

	}
}
