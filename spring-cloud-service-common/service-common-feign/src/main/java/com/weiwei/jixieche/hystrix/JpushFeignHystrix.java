package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.XgPushFeign;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.JpushCustomMsgVo;
import com.weiwei.jixieche.vo.PushXgVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JpushMsgFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/5/28 17:15
 * @Version 1.0.1
 **/
@Component
public class JpushFeignHystrix implements JpushMsgFeign {

    @Override
    public ResponseMessage jpushNotice(JpushTemplateVo jpushTemplateVo) {
        return null;
    }

    @Override
    public ResponseMessage jpushMessage(JpushMessageVo jpushMessageVo) {
        return null;
    }

    @Override
    public ResponseMessage jpushCustomMsg(JpushCustomMsgVo jpushCustomMsgVo) {
        return null;
    }
}
