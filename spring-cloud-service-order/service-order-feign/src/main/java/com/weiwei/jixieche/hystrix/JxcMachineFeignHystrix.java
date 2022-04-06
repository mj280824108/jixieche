package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcMachineFeign;
import com.weiwei.jixieche.JxcMachineRouteFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UpdateBatchRoutePayStatusVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
* @Description: 更新机械状态
* @Author:      liuy
* @Date:  2019/9/29 15:12
*/
@Component
public class JxcMachineFeignHystrix implements JxcMachineFeign {

    @Override
    public ResponseMessage updateMachineById(Integer machineId) {
        return null;
    }
}
