package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcAreaFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import org.springframework.stereotype.Component;

@Component
public class JxcAreaFeignHystrix implements JxcAreaFeign {

    @Override
    public ResponseMessage getAreaTree(String id) {
        return null;
    }

    @Override
    public ResponseMessage getAreaChildrenByPid(String pid) {
        return null;
    }

    @Override
    public ResponseMessage getFirstLetterCityList() {
        return null;
    }

    @Override
    public ResponseMessage getOpenedCityList() {
        return null;
    }

}
