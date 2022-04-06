package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.config.PushXmConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.PushMiService;
import com.weiwei.jixieche.utils.XiaomiPushUtils;
import com.weiwei.jixieche.vo.PushXmVo;
import com.xiaomi.xmpush.server.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName JxcMsgServiceImpl
 * @Description TODO
 * @Author houji
 * @Date 2019/5/15 11:07
 * @Version 1.0.1
 **/
@Service("pushMiService")
public class PushMiServiceImpl implements PushMiService {

    @Autowired
    private XiaomiPushUtils xiaomiPushUtils;

    @Autowired
    private PushXmConfig pushXmConfig;

    @Override
    public ResponseMessage push(PushXmVo pushXmVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if(pushXmVo.getUserType().equals("") || pushXmVo.getUserType() == null) {
            return new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_EMPTY.getId(), ErrorCodeConstants.QUERY_PARAM_EMPTY.getDescript());
        }
        //开始推送
        Result miResult = null;
        if(pushXmVo.getUserType().equals(PushXmVo.Type.owner)){
            try{
                miResult = this.xiaomiPushUtils.sendMessageToAliases(pushXmConfig.getOwnerSecret(),pushXmConfig.getOwnerPage(),pushXmVo.getThirdIdList());
            }catch (Exception ex){
                result.setCode(ErrorCodeConstants.PUSH_EXCEPTION.getId());
                result.setMessage("小米推送异常");
                ex.printStackTrace();
            }
        }else if(pushXmVo.getUserType().equals(PushXmVo.Type.ten)){
            try{
                miResult = this.xiaomiPushUtils.sendMessageToAliases(pushXmConfig.getTenSecret(),pushXmConfig.getTenPage(),pushXmVo.getThirdIdList());
            }catch (Exception ex){
                result.setCode(ErrorCodeConstants.PUSH_EXCEPTION.getId());
                result.setMessage("小米推送异常");
                ex.printStackTrace();
            }
        }else{
            return  new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_ERROR.getId(),ErrorCodeConstants.QUERY_PARAM_ERROR.getDescript());
        }
        //推送返回结果值
        if(miResult.getErrorCode().getValue() == 0){
            return result;
        }else{
            result.setCode(ErrorCodeConstants.PUSH_EXCEPTION.getId());
            result.setMessage("小米推送失败");
        }
        return result;
    }
}
