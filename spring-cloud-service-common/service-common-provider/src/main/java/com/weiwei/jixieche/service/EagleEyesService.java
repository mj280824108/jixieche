package com.weiwei.jixieche.service;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.EagleEyesVo;

/**
 * @ClassName EagleEyesService
 * @Description TODO
 * @Author houji
 * @Date 2019/5/27 11:20
 * @Version 1.0.1
 **/
public interface EagleEyesService {
    /**
     * 鹰眼轨迹服务
     * @param eagleEyesVo
     * @return
     */
    ResponseMessage baiduEntity(EagleEyesVo eagleEyesVo);
}
