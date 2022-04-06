package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcTradeOwnerService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

@Service("jxcTradeOwnerService")
public class JxcTradeOwnerServiceImpl implements JxcTradeOwnerService {

       private static Logger logger = LoggerFactory.getLogger(JxcTradeOwnerServiceImpl.class);

       @Resource
       private JxcTradeOwnerMapper jxcTradeOwnerMapper;

       /**
        * 前端分页查询机主交易流水表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcTradeOwner 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeOwner> findByPageForFront(Integer pageNo, Integer pageSize, JxcTradeOwner jxcTradeOwner) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcTradeOwner> list = this.jxcTradeOwnerMapper.selectListByConditions(jxcTradeOwner);
              PageInfo<JxcTradeOwner> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       @Override
       public ResponseMessage<JxcTradeOwner> queryObjById(Long id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcTradeOwner model=this.jxcTradeOwnerMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 添加机主交易流水表
        * @param  jxcTradeOwner
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeOwner> addObj(JxcTradeOwner jxcTradeOwner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeOwnerMapper.insertSelective(jxcTradeOwner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改机主交易流水表
        * @param jxcTradeOwner
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeOwner> modifyObj(JxcTradeOwner jxcTradeOwner) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcTradeOwnerMapper.updateByPrimaryKeySelective(jxcTradeOwner);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询机主交易流水表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcTradeOwner> queryObjById(int id) {
              return null;
       }

       /**
        * 机主查询我的钱包
        * @param ownerWalletVo
        * @return
        */
       @Override
       public ResponseMessage<OwnerWalletVo>  queryOwnerWallet(AuthorizationUser user,OwnerWalletVo ownerWalletVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user.getUserId() != null){
                     if(user.getRoleId() == UserRoleContants.OWNER.getRoleId()){
                            //机主总收入解冻金额
                            UserTradeAmountVo totalAmount= new UserTradeAmountVo();
                            totalAmount.setPayerUserId(0);
                            totalAmount.setPayeeUserId(user.getUserId());
                            totalAmount.setTradeType(UserTradeAmountVo.WalletType.PLAT);
                            totalAmount.setLockStatus(UserTradeAmountVo.lockStatus.UNLOCK);
                            totalAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(totalAmount);
                            //机主冻结金额
                            UserTradeAmountVo lockAmount= new UserTradeAmountVo();
                            lockAmount.setPayerUserId(0);
                            lockAmount.setPayeeUserId(user.getUserId());
                            lockAmount.setLockStatus(UserTradeAmountVo.lockStatus.LOCK);
                            lockAmount.setTradeType(UserTradeAmountVo.WalletType.PLAT);
                            lockAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(lockAmount);

                            //查询机主支付工资金额
                            UserTradeAmountVo payAmount= new UserTradeAmountVo();
                            payAmount.setPayerUserId(user.getUserId());
                            payAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_PAY);
                            payAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(payAmount);

                            //机主提现金额
                            UserTradeAmountVo takeAmount= new UserTradeAmountVo();
                            takeAmount.setPayeeUserId(user.getUserId());
                            takeAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_TAKE);
                            takeAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(takeAmount);

                            ownerWalletVo.setLockAmount(VerifyStr.integerToStrHand(lockAmount.getTotalAmount()));
                            //机主可用余额 = 机主解冻总金额  - 机主支付工资金额 -  机主提现金额
                            Integer OwnerUnLockAmount = totalAmount.getTotalAmount() - payAmount.getTotalAmount() - takeAmount.getTotalAmount();
                            ownerWalletVo.setUnLockAmount(VerifyStr.integerToStrHand(OwnerUnLockAmount));
                            //机主总资产 =  机主冻结金额 + 机主可用余额
                            ownerWalletVo.setTotalAmount(VerifyStr.integerToStrHand(OwnerUnLockAmount + lockAmount.getTotalAmount()));
                     }else if(user.getRoleId() == UserRoleContants.DRIVER.getRoleId()){

                            //司机的冻结金额 =  机主支付台班工资金额
                            UserTradeAmountVo driverLockAmount= new UserTradeAmountVo();
                            driverLockAmount.setPayeeUserId(user.getUserId());
                            driverLockAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_PAY);
                            driverLockAmount.setLockStatus(UserTradeAmountVo.lockStatus.LOCK);
                            driverLockAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(driverLockAmount);

                            //司机解冻总金额
                            UserTradeAmountVo driverUnLockAmount= new UserTradeAmountVo();
                            driverUnLockAmount.setPayeeUserId(user.getUserId());
                            driverUnLockAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_PAY);
                            driverUnLockAmount.setLockStatus(UserTradeAmountVo.lockStatus.UNLOCK);
                            driverUnLockAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(driverUnLockAmount);

                            //司机提现金额
                            UserTradeAmountVo driverTakeAmount= new UserTradeAmountVo();
                            driverTakeAmount.setPayeeUserId(user.getUserId());
                            driverTakeAmount.setPayerUserId(0);
                            driverTakeAmount.setTradeType(UserTradeAmountVo.WalletType.DRIVER_TAKE);
                            driverTakeAmount =  this.jxcTradeOwnerMapper.queryUserTradeAmount(driverTakeAmount);
                            //司机冻结金额
                            ownerWalletVo.setLockAmount(VerifyStr.integerToStrHand(driverLockAmount.getTotalAmount()));
                            //司机可用余额 =  司机解冻总金额 - 司机提现金额
                            Integer unLockAmount = driverUnLockAmount.getTotalAmount() - driverTakeAmount.getTotalAmount();
                            ownerWalletVo.setUnLockAmount(VerifyStr.integerToStrHand(unLockAmount));
                            //司机总资产 = 司机冻结金额 + 司机可用余额
                            ownerWalletVo.setTotalAmount(VerifyStr.integerToStrHand(driverLockAmount.getTotalAmount() + unLockAmount));
                     }
                     result.setData(ownerWalletVo);
              }else{
                     result.setCode(ErrorCodeConstants.TOKEN_EMPTY.getId());
                     result.setMessage("token解析错误");
                     return result;
              }
              return result;
       }

       /**
        * 机主交易明细记录
        * @param ownerTradeRecordListVo
        * @return
        */
       @Override
       public ResponseMessage queryOwnerTradeRecord(AuthorizationUser user,OwnerTradeRecordListVo ownerTradeRecordListVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user == null || user.getRoleId()== null){
                     result.setCode(ErrorCodeConstants.TOKEN_CHANGE.getId());
                     result.setMessage("token为空或解析错误");
                     return  result;
              }
              //分页查询机主月份交易记录
              PageHelper.startPage(ownerTradeRecordListVo.getPageNo(),ownerTradeRecordListVo.getPageSize());
              OwnerTradeRecordVo ownerTradeRecordVo = new OwnerTradeRecordVo();
              ownerTradeRecordVo.setUserId(ownerTradeRecordListVo.getUserId());
              ownerTradeRecordVo.setDateTime(ownerTradeRecordListVo.getDateTime());
              ownerTradeRecordVo.setSearchType(ownerTradeRecordListVo.getSearchType());
              List<OwnerTradeRecordVo> list =  this.jxcTradeOwnerMapper.queryOwnerTradeRecord(ownerTradeRecordVo);
              list.forEach(recordVo -> {
                     //机主角色
                     //交易类型是机主收入，则金额显示+500,并查询出机主收款的项目名称
                     logger.info("用户角色："+user.getRoleId() +"=======订单类型:"+recordVo.getTradeType());
                     if(user.getRoleId() == UserRoleContants.OWNER.getRoleId()){
                            if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.GET_MONEY){
                                   recordVo.setTradeAmount("+"+recordVo.getTradeAmount());
                                   TenProjectInfoVo tenProjectInfoVo = new TenProjectInfoVo();
                                   tenProjectInfoVo.setTenOrderId(recordVo.getTenantryOrderId());
                                   tenProjectInfoVo = this.jxcTradeOwnerMapper.queryTenProjectInfo(tenProjectInfoVo);
                                   recordVo.setProjectName(tenProjectInfoVo.getProjectName());
                            }else if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.OWNER_PAY){
                                   recordVo.setTradeAmount("-"+recordVo.getTradeAmount());
                                   //当机主支付工资时,查询收款人的姓名
                                   if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.OWNER_PAY){
                                          UserPayeeInfoVo userPayeeInfoVo = new UserPayeeInfoVo();
                                          userPayeeInfoVo.setUserId(recordVo.getPayeeUserId());
                                          userPayeeInfoVo = this.jxcTradeOwnerMapper.queryPayeeInfo(userPayeeInfoVo);
                                          recordVo.setPayeeUserName(userPayeeInfoVo.getUserName());
                                          recordVo.setPayeeHeadImg(userPayeeInfoVo.getHeadImg());
                                   }
                            }else if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.TAKE_MONEY){
                                   recordVo.setTradeAmount("-"+recordVo.getTradeAmount());
                            }

                     }else if(user.getRoleId() == UserRoleContants.DRIVER.getRoleId()){
                            //司机角色
                            if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.OWNER_PAY){
                                   recordVo.setTradeAmount("+"+recordVo.getTradeAmount());
                                   //当机主支付工资时,查询付款人的姓名
                                   if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.OWNER_PAY){
                                          UserPayeeInfoVo userPayeeInfoVo = new UserPayeeInfoVo();
                                          userPayeeInfoVo.setUserId(recordVo.getPayerUserId());
                                          userPayeeInfoVo = this.jxcTradeOwnerMapper.queryPayeeInfo(userPayeeInfoVo);
                                          recordVo.setPayerHeadImg(userPayeeInfoVo.getHeadImg());
                                          recordVo.setPayerUserName(userPayeeInfoVo.getUserName());
                                   }
                            }else{
                                   recordVo.setTradeAmount("-"+recordVo.getTradeAmount());
                            }
                     }
                     //机主和司机提现都需要查询银行卡信息
                     if(recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.TAKE_MONEY
                             || recordVo.getTradeType() == OwnerTradeRecordVo.OwnerTradeType.DRIVER_TAKE){
                            //查询银行卡信息
                            if(recordVo.getBankCardId() != null){
                                   UserBankCardInfoVo userBankCardInfoVo = new UserBankCardInfoVo();
                                   userBankCardInfoVo.setId(recordVo.getBankCardId());
                                   userBankCardInfoVo = this.jxcTradeOwnerMapper.queryBankCard(userBankCardInfoVo);
                                   recordVo.setBankCardInfo(userBankCardInfoVo.getBankName()+"("+userBankCardInfoVo.getCardNumber().substring(userBankCardInfoVo.getCardNumber().length() - 4,userBankCardInfoVo.getCardNumber().length())+")");
                                   recordVo.setLogo(userBankCardInfoVo.getLogo());
                            }
                     }
              });
              ownerTradeRecordListVo.setTradeRecordList(list);
              //获取查询数据总页码数
              PageInfo<OwnerTradeRecordVo> page = new PageInfo<>(list);
              ownerTradeRecordListVo.setPageCount(new PageUtils<>(page).getPageViewDatatable().getPageCount());
              //查询收入
              OwnerTradePayVo payTrade = new OwnerTradePayVo();
              payTrade.setTradeMonth(ownerTradeRecordListVo.getDateTime());
              payTrade.setPayerUserId(ownerTradeRecordListVo.getUserId());
              payTrade = this.jxcTradeOwnerMapper.queryOwnerTotalPay(payTrade);
              if(payTrade != null){
                     ownerTradeRecordListVo.setPayAmount(payTrade.getTotalAmount());
              }
              //查询支出
              OwnerTradePayVo takeTrade = new OwnerTradePayVo();
              takeTrade.setTradeMonth(ownerTradeRecordListVo.getDateTime());
              takeTrade.setPayeeUserId(ownerTradeRecordListVo.getUserId());
              takeTrade = this.jxcTradeOwnerMapper.queryOwnerTotalPay(takeTrade);
              if(takeTrade != null){
                     ownerTradeRecordListVo.setIncomeAmount(takeTrade.getTotalAmount());
              }
              result.setData(ownerTradeRecordListVo);
              return result;
       }

       /**
        * 查询机主月份收入支出总金额
        * @param ownerTradePayVo
        * @return
        */
       @Override
       public ResponseMessage queryOwnerTotalPay(OwnerTradePayVo ownerTradePayVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              ownerTradePayVo = this.jxcTradeOwnerMapper.queryOwnerTotalPay(ownerTradePayVo);
              result.setData(ownerTradePayVo);
              return result;
       }

}