package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcCreditScoreScored;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcCreditScoreScoredMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcCreditScoreScoredService;
import com.weiwei.jixieche.utils.CreditScoreUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.UserCreditScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Service("jxcCreditScoreScoredService")
public class JxcCreditScoreScoredServiceImpl implements JxcCreditScoreScoredService {
       @Resource
       private JxcCreditScoreScoredMapper jxcCreditScoreScoredMapper;

       @Autowired
       private CreditScoreUtils creditScoreUtils;

       /**
        * 前端分页查询用户信用分记录表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcCreditScoreScored 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreScored> findByPageForFront(Integer pageNo, Integer pageSize, JxcCreditScoreScored jxcCreditScoreScored) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcCreditScoreScored> list = this.jxcCreditScoreScoredMapper.selectListByConditions(jxcCreditScoreScored);
              PageInfo<JxcCreditScoreScored> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加用户信用分记录表
        * @param  jxcCreditScoreScored
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreScored> addObj(JxcCreditScoreScored jxcCreditScoreScored) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCreditScoreScoredMapper.insertSelective(jxcCreditScoreScored);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改用户信用分记录表
        * @param jxcCreditScoreScored
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreScored> modifyObj(JxcCreditScoreScored jxcCreditScoreScored) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcCreditScoreScoredMapper.updateByPrimaryKeySelective(jxcCreditScoreScored);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户信用分记录表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcCreditScoreScored> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcCreditScoreScored model=this.jxcCreditScoreScoredMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 添加信用分记录
        * @param userCreditScoreVo
        * @return
        */
       @Override
       public ResponseMessage insertCreditScoreScored(UserCreditScoreVo userCreditScoreVo) {
              return this.creditScoreUtils.verifyCreditScore(userCreditScoreVo);
       }

       /**
        * 根据userId查询用户积分总分数
        * @param userId
        * @return
        */
       @Override
       public ResponseMessage queryTotalScoreByUserId(Integer userId) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              Integer totalScore = this.jxcCreditScoreScoredMapper.queryTotalScoreByUserId(userId);
              if(totalScore != null && totalScore > 0){
                     result.setData(totalScore);
              }
              return result;
       }
}