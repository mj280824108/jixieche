package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcBank;
import com.weiwei.jixieche.bean.JxcBankCard;
import com.weiwei.jixieche.config.UnionBankCardConfig;
import com.weiwei.jixieche.constant.CreditScoreTemplateConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.mapper.JxcBankCardMapper;
import com.weiwei.jixieche.mapper.JxcBankMapper;
import com.weiwei.jixieche.mapper.JxcUserRoleMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcBankCardService;
import com.weiwei.jixieche.utils.CreditScoreUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
@Service("jxcBankCardService")
public class JxcBankCardServiceImpl implements JxcBankCardService {
       @Resource
       private JxcBankCardMapper jxcBankCardMapper;

       @Autowired
       private UnionBankCardConfig unionBankCardConfig;

       @Resource
       private JxcBankMapper jxcBankMapper;

       @Resource
       private JxcUserRoleMapper jxcUserRoleMapper;

       @Autowired
       private CreditScoreUtils creditScoreUtils;

       /**
        * 前端分页查询用户银行卡
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcBankCard 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcBankCard> findByPageForFront(Integer pageNo, Integer pageSize, JxcBankCard jxcBankCard) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcBankCard> list = this.jxcBankCardMapper.selectListByConditions(jxcBankCard);
              PageInfo<JxcBankCard> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询用户银行卡列表信息(不分页)
        * @param jxcBankCard
        * @return
        */
       @Override
       public ResponseMessage<JxcBankCard> queryBankCardList(JxcBankCard jxcBankCard) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<JxcBankCard> list = this.jxcBankCardMapper.selectListByConditions(jxcBankCard);
              if(CollectionUtils.isEmpty(list)){
                     PageInfo<JxcBankCard> page = new PageInfo<>(list);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
                     return result;
              }else{
                     PageInfo<JxcBankCard> page = new PageInfo<>(list);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
              }
              return result;
       }

       /**
        * 添加用户银行卡
        * @param  jxcBankCard
        * @return
        */
       @Override
       public ResponseMessage<JxcBankCard> addObj(JxcBankCard jxcBankCard) {

              ResponseMessage result = ResponseMessageFactory.newInstance();
              //用户绑卡验证码
              /*if(jxcBankCard.getCode()){

              }*/
              if(jxcBankCard.getCardNumber() == null){
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY.getId());
                     result.setMessage("银行卡卡号不能为空");
                     return result;
              }
              //验证银行卡是否正确
              String resCardInfo = this.unionBankCardConfig.getBankCardInfo(jxcBankCard.getCardNumber());
              if(resCardInfo == null){
                     result.setCode(ErrorCodeConstants.PARAM_MUST_EMPTY.getId());
                     result.setMessage("银行卡卡号未识别");
                     return result;
              }
              //获取银行卡信息数据
              JSONObject jsonCardInfo = JSONObject.parseObject(resCardInfo);
              //判断银行卡编号是否存在，如果不存在，则添加
              JxcBank jxcBank = this.jxcBankMapper.selectByPrimaryKey(jsonCardInfo.getString("issInsId"));
              if(jxcBank == null){
                     jxcBank = new JxcBank();
                     jxcBank.setId(jsonCardInfo.getString("issInsId"));
                     jxcBank.setBankName(jsonCardInfo.getString("issAbbr"));
                     this.jxcBankMapper.insertSelective(jxcBank);
              }
              //判断银行卡是否添加过
              JxcBankCard existBankCard = new JxcBankCard();
              existBankCard.setUserId(jxcBankCard.getUserId());
              existBankCard.setCardNumber(jxcBankCard.getCardNumber());
              existBankCard.setStatus(JxcBankCard.BankCardStats.ENABLE);
              List<JxcBankCard> list = this.jxcBankCardMapper.selectListByConditions(existBankCard);
              if(CollectionUtils.isEmpty(list)){
                     //添加用户银行卡
                     jxcBankCard.setBankName(jsonCardInfo.getString("issAbbr"));
                     jxcBankCard.setBankId(jsonCardInfo.getString("issInsId"));
                     this.jxcBankCardMapper.insertSelective(jxcBankCard);
                     //用户首次添加银行卡，信用分
                     JxcBankCard recordCard = new JxcBankCard();
                     recordCard.setUserId(jxcBankCard.getUserId());
                     List<JxcBankCard> firstBankCardList = this.jxcBankCardMapper.selectListByConditions(recordCard);
                     if(firstBankCardList.size() == 1){
                            Integer roleId = jxcUserRoleMapper.queryRoleByUserId(jxcBankCard.getUserId());
                            //添加信用分
                            UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
                            userCreditScoreVo.setUserId(jxcBankCard.getUserId());
                            if(roleId == UserRoleContants.TEN.getRoleId()){
                                   userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_TEN_BIND_BANK_CARD.getId());
                            }else if(roleId == UserRoleContants.DRIVER.getRoleId()){
                                   userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_DRIVER_BIND_BANK_CARD.getId());
                            }else if(roleId == UserRoleContants.OWNER.getRoleId()){
                                   userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_OWNER_BIND_BANK_CARD.getId());
                            }
                            this.creditScoreUtils.insertCreditScoreScored(userCreditScoreVo);
                     }
                     return result;
              }else {
                     result.setCode(ErrorCodeConstants.QUERY_DATA_EXISIT.getId());
                     result.setMessage("用户银行卡已存在，请勿重复添加");
                     return result;
              }
       }

       /**
        * 修改用户银行卡
        * @param jxcBankCard
        * @return
        */
       @Override
       public ResponseMessage<JxcBankCard> modifyObj(JxcBankCard jxcBankCard) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcBankCardMapper.updateByPrimaryKeySelective(jxcBankCard);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询用户银行卡
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcBankCard> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcBankCard model=this.jxcBankCardMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }
}