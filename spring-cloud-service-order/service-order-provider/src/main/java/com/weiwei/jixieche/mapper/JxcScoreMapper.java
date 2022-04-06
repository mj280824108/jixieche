package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcScore;

import java.util.List;

/**
* @Description: 用户评价评分
* @Author:      liuy
* @Date:  2019/8/23 10:40
*/
public interface JxcScoreMapper extends BaseMapper<JxcScore> {

    /**
     * 查询最新100条用户评论数据
     * @return
     */
    List<JxcScore> queryTopScoreList();
}