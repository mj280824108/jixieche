package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcScore;
import com.weiwei.jixieche.response.ResponseMessage;

/**
 * @Description: 用户评价评分
 * @Author:      liuy
 * @Date:  2019/8/23 10:40
 */
public interface JxcScoreService extends BaseService<JxcScore> {

    /**
     * 获取用户评论搜索热词
     * @return
     */
    ResponseMessage queryScoreFireWord();

}