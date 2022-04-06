package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcClockPairFeign;
import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import org.springframework.stereotype.Component;

@Component
public class JxcClockPairFeignHystrix implements JxcClockPairFeign {

    @Override
    public void updateBatchClockPayStatus(UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo) {

    }

    @Override
    public void updateClockPayStatus(Long clockId) {

    }
}
