package com.weiwei.jixieche.service.impl;

import com.hankcs.hanlp.HanLP;
import com.weiwei.jixieche.bean.JxcScore;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.RedisKeyConstants;
import com.weiwei.jixieche.mapper.JxcScoreMapper;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcScoreService;
import com.weiwei.jixieche.verify.AssertUtil;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 用户评价评分
 * @Author:      liuy
 * @Date:  2019/8/23 10:40
 */
@Service("jxcScoreService")
public class JxcScoreServiceImpl implements JxcScoreService {
       @Resource
       private JxcScoreMapper jxcScoreMapper;

       @Autowired
       private RedisUtil redisUtil;

       /**
        * 添加用户评价评分表
        * @param  jxcScore
        * @return
        */
       @Override
       public ResponseMessage<JxcScore> addObj(JxcScore jxcScore) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcScoreMapper.insertSelective(jxcScore);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改用户评价评分表
        * @param jxcScore
        * @return
        */
       @Override
       public ResponseMessage<JxcScore> modifyObj(JxcScore jxcScore) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcScoreMapper.updateByPrimaryKeySelective(jxcScore);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户评价评分表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcScore> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcScore model=this.jxcScoreMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 获取用户评论搜索热词
        * @return
        */
       @Override
       public ResponseMessage queryScoreFireWord() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<JxcScore> contentList = this.jxcScoreMapper.queryTopScoreList();
              StringBuffer sbContent = new StringBuffer();
              if(!contentList.isEmpty() && contentList.size() >0){
                     contentList.forEach(content -> {
                            sbContent.append(content).append("\n");
                     });
                     List<String> fireWord = HanLP.extractPhrase(sbContent.toString(), 5);
                     redisUtil.set(RedisKeyConstants.REDIS_HOT_WORD_SCORE.getPrefix(),fireWord);
              }
              if(redisUtil.hasKey(RedisKeyConstants.REDIS_HOT_WORD_SCORE.getPrefix())){
                     result.setData(redisUtil.get(RedisKeyConstants.REDIS_HOT_WORD_SCORE.getPrefix()));
              }else{
                     result.setData(new ArrayList<String>());
//                     result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
//                     result.setMessage("redis的热词key不存在");
              }
              return result;
       }
}