package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcCreditScoreScored;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcCreditScoreScoredMapper extends BaseMapper<JxcCreditScoreScored> {

    /**
     * 根据userId查询用户总信用分
     * @param userId
     * @return
     */
    Integer queryTotalScoreByUserId(@Param("userId")Integer userId);
}