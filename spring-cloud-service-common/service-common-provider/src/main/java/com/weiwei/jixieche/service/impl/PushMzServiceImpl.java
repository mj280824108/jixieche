package com.weiwei.jixieche.service.impl;

import com.meizu.push.sdk.server.IFlymePush;
import com.meizu.push.sdk.server.constant.ResultPack;
import com.meizu.push.sdk.server.model.push.PushResult;
import com.meizu.push.sdk.server.model.push.VarnishedMessage;
import com.weiwei.jixieche.config.PushMzConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.PushMzService;
import com.weiwei.jixieche.vo.PushMzVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName JxcMsgServiceImpl
 * @Description TODO
 * @Author houji
 * @Date 2019/5/15 11:07
 * @Version 1.0.1
 **/
@Service("pushMzService")
public class PushMzServiceImpl implements PushMzService {

    @Autowired
    private PushMzConfig pushMzConfig;

    @Override
    public ResponseMessage push(PushMzVo pushMzVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if(pushMzVo.getPushType().equals("") || pushMzVo.getPushType() == null){
            return new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_ERROR.getId(),ErrorCodeConstants.QUERY_PARAM_ERROR.getDescript());
        }
        //参数校验
        IFlymePush push = null;
        VarnishedMessage message = null;
        if(pushMzVo.getPushType() == PushMzVo.pushType.ten){
            push = new IFlymePush(pushMzConfig.getTenSecret());
            message  = new VarnishedMessage.Builder()
                    .appId(pushMzConfig.getTenId())
                    .title(pushMzVo.getTitle())
                    .content(pushMzVo.getContent())
                    .url("http://push.meizu.com").build();
        }else if(pushMzVo.getPushType() == PushMzVo.pushType.owner){
            push = new IFlymePush(pushMzConfig.getOwnerSecret());
            message  = new VarnishedMessage.Builder()
                    .appId(pushMzConfig.getOwnerId())
                    .title(pushMzVo.getTitle())
                    .content(pushMzVo.getContent())
                    //.url("http://push.meizu.com")
                    .build();
        }else{
            return new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_UNKNOW.getId(),ErrorCodeConstants.QUERY_PARAM_UNKNOW.getDescript());
        }

        try{
            ResultPack<PushResult> mzResult = push.pushMessageByAlias(message,pushMzVo.getThirdIdList());
            System.out.println(mzResult);
            if(mzResult.isSucceed()){
                return result;
            }else{
                result.setCode(ErrorCodeConstants.PUSH_EXCEPTION.getId());
                result.setMessage("魅族推送失败");
                return result;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
