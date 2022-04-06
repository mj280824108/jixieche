package com.weiwei.jixieche.service.impl;

import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.UnionPayService;
import com.weiwei.jixieche.utils.UnionPayUtils;
import com.weiwei.jixieche.vo.CashApplyVo;
import com.weiwei.jixieche.vo.TradePayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service("unionPayService")
public class UnionPayServiceImpl implements UnionPayService {

    @Autowired
    private UnionPayUtils unionPayUtils;


    /**
     * 银联支付
     * @param tradePayVo
     * @return
     */
    @Override
    public ResponseMessage pay(TradePayVo tradePayVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //验证支付请求参数
        String resStr = this.unionPayUtils.verifyParam(tradePayVo);
        if(resStr != null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setData(resStr);
            return result;
        }else{
            tradePayVo.setOutTradeNo(IDGenerator.getInstance().next()+"");
            result = this.unionPayUtils.unionPay(tradePayVo);
        }
        return result;
    }

    /**
     * 银联回调
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public String notify(HttpServletRequest req) throws Exception {
        return unionPayUtils.notify(req);
    }

    @Override
    @Transactional
    public ResponseMessage cashApply(AuthorizationUser user, CashApplyVo cashApplyVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //提现参数验证
        String resStr = this.unionPayUtils.verifyCashParam(user,cashApplyVo);
        if(resStr != null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage(resStr);
            return result;
        }
        //开始提现
        result= this.unionPayUtils.cashApply(user,cashApplyVo);
        return result;
    }


}