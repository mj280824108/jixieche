package com.weiwei.jixieche.hystrix;

import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import org.springframework.stereotype.Component;

/**
 * @ClassName JxcCreditScoreScoredFeignHystrix
 * @Description TODO
 * @Author houji
 * @Date 2019/8/14 14:17
 * @Version 1.0.1
 **/
@Component
public class JxcCreditScoreScoredFeignHystrix implements JxcCreditScoreScoredFeign {
    @Override
    public ResponseMessage insertCreditScoreScored(UserCreditScoreVo userCreditScoreVo) {
        return null;
    }

    @Override
    public ResponseMessage queryTotalScoreByUserId(Integer userId) {
        return null;
    }
}
