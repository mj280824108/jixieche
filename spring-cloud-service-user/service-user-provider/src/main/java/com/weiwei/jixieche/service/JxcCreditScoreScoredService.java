package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcCreditScoreScored;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.UserCreditScoreVo;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcCreditScoreScoredService extends BaseService<JxcCreditScoreScored> {
       /**
     * 前端分页查询用户信用分记录表
     * @param pageNo  分页索引
     * @param pageSize  每页显示数量
     * @param jxcCreditScoreScored 查询条件
     * @return 
     */
       ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcCreditScoreScored jxcCreditScoreScored);

       /**
        * 添加用户信用分
        * @param userCreditScoreVo
        * @return
        */
       ResponseMessage insertCreditScoreScored(UserCreditScoreVo userCreditScoreVo);

       /**
        * 根据userId查询用户积分总分数
        * @param userId
        * @return
        */
       ResponseMessage queryTotalScoreByUserId(Integer userId);
}