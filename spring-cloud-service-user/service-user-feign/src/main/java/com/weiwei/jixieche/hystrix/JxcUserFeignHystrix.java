package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.ManagerRegisterVo;
import com.weiwei.jixieche.vo.OwnerRegisterDriverVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JxcUserHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 11:53
 * @Version 1.0.1
 **/
@Component
public class JxcUserFeignHystrix implements JxcUserFeign {

    @Override
    public ResponseMessage ownerRegisterDriver(OwnerRegisterDriverVo userLoginVo) {
        return null;
    }

    @Override
    public ResponseMessage managerRegister(ManagerRegisterVo managerRegisterVo) {
        return null;
    }

    @Override
    public ResponseMessage queryUserInfo(Integer userId) {
        return null;
    }
}
