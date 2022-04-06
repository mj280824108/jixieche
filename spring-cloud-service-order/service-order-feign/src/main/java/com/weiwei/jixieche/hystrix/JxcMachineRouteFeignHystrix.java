package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcMachineRouteFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @ClassName JxcMachineRouteFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 21:41
 * @Version 1.0.1
 **/
@Component
public class JxcMachineRouteFeignHystrix implements JxcMachineRouteFeign {


    @Override
    public ResponseMessage queryTotalAmount(ArrayList<Long> routeIdList) {
        return null;
    }

    @Override
    public void updateBatchPayStatus(UpdateBatchRoutePayStatusVo updateBatchRoutePayStatusVo) {

    }

    @Override
    public ResponseMessage getMachineCarNoByUserId(Integer userId) {
        return null;
    }
}
