package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcDriverRefOwnerFeign;
import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.stereotype.Component;

/**
 * @author liuy
 * @date 2019-08-29 11:14
 */
@Component
public class JxcDriverRefOwnerFeignHystrix implements JxcDriverRefOwnerFeign {

	@Override
	public ResponseMessage addDriverRefOwner(JxcDriverRefOwner jxcDriverRefOwner) {
		return null;
	}
}
