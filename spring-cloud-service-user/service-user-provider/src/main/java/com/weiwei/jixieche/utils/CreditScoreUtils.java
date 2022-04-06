package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcCreditScoreScored;
import com.weiwei.jixieche.bean.JxcCreditScoreTemplate;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcCreditScoreScoredMapper;
import com.weiwei.jixieche.mapper.JxcCreditScoreTemplateMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName CreditScore
 * @Description 添加信用分记录Utils
 * @Author houji
 * @Date 2019/8/14 14:13
 * @Version 2.0
 **/
@Component
public class CreditScoreUtils {

    @Resource
    private JxcCreditScoreTemplateMapper jxcCreditScoreTemplateMapper;

    @Resource
    private JxcCreditScoreScoredMapper jxcCreditScoreScoredMapper;


    //信用分只能添加一次信用分记录的模板id
    public static final List<Integer> templateIdList = Arrays.asList(8,9,10,11,12,13,14,15,16,17,18,19,24,25,27);

    /**
     * 验证信用分模板
     * @param userCreditScoreVo
     * @return
     */
    public boolean verifyCreditParam(UserCreditScoreVo userCreditScoreVo){
        //判断用户templateId是否存在
        Boolean bru = false;
        if(userCreditScoreVo.getTemplateId() == null){
            return bru;
        }
        if(userCreditScoreVo.getUserId() == null){
            return bru;
        }
        JxcCreditScoreTemplate jxcCreditScoreTemplate = this.jxcCreditScoreTemplateMapper.selectByPrimaryKey(userCreditScoreVo.getTemplateId());
        if(jxcCreditScoreTemplate == null){
            return bru;
        }else{
            bru = true;
        }
        return bru;
    }

    /**
     * 添加信用分记录
     * @param userCreditScoreVo
     * @return
     */
    public ResponseMessage verifyCreditScore(UserCreditScoreVo userCreditScoreVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //验证信用分模板
        if(verifyCreditParam(userCreditScoreVo)){
            if(templateIdList.stream().anyMatch(str -> str.equals(userCreditScoreVo.getTemplateId()))){
                //查询templateId信用分记录是否存在。存在则不添加返回正确，不存在则添加
                JxcCreditScoreScored jxcCreditScoreScored = new JxcCreditScoreScored();
                jxcCreditScoreScored.setUserId(userCreditScoreVo.getUserId());
                jxcCreditScoreScored.setTemplateId(userCreditScoreVo.getTemplateId());
                List<JxcCreditScoreScored> scoredList= this.jxcCreditScoreScoredMapper.selectListByConditions(jxcCreditScoreScored);
                if(CollectionUtils.isEmpty(scoredList)){
                    return insertCreditScoreScored(userCreditScoreVo);
                }
            }else{
                return insertCreditScoreScored(userCreditScoreVo);
            }
        }else{
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId(),ErrorCodeConstants.PARAM_EMPTY_ERROR.getDescript());
        }
        return result;
    }

    /**
     * 添加信用分记录
     * @param userCreditScoreVo
     * @return
     */
    public ResponseMessage insertCreditScoreScored(UserCreditScoreVo userCreditScoreVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        try{
            JxcCreditScoreTemplate jxcCreditScoreTemplate = this.jxcCreditScoreTemplateMapper.selectByPrimaryKey(userCreditScoreVo.getTemplateId());
            JxcCreditScoreScored jxcCreditScoreScored = new JxcCreditScoreScored();
            jxcCreditScoreScored.setTemplateId(jxcCreditScoreTemplate.getId());
            jxcCreditScoreScored.setScore(jxcCreditScoreTemplate.getScore());
            jxcCreditScoreScored.setUserId(userCreditScoreVo.getUserId());
            jxcCreditScoreScored.setTemplateTitle(jxcCreditScoreTemplate.getTitle());
            this.jxcCreditScoreScoredMapper.insertSelective(jxcCreditScoreScored);
            return result;
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(),ErrorCodeConstants.ADD_ERORR.getDescript());
        }
    }
}
