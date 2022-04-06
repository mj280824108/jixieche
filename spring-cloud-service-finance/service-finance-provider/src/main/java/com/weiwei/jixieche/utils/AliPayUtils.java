package com.weiwei.jixieche.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.weiwei.jixieche.JxcClockPairFeign;
import com.weiwei.jixieche.JxcMachineRouteFeign;
import com.weiwei.jixieche.bean.JxcMachineRoute;
import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.config.AliPayConfig;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryRefMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName AliPayUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 11:30
 * @Version 1.0.1
 **/
@Component
public class AliPayUtils {

    private static Logger logger = LoggerFactory.getLogger(AliPayUtils.class);

    @Autowired
    private AliPayConfig aliPayConfig;

    @Autowired
    private JxcMachineRouteFeign jxcMachineRouteFeign;

    @Resource
    private JxcTradeTenantryMapper jxcTradeTenantryMapper;

    @Resource
    private JxcTradeOwnerMapper jxcTradeOwnerMapper;

    @Resource
    private JxcTradeTenantryRefMapper jxcTradeTenantryRefMapper;

    @Autowired
    private JxcClockPairFeign jxcClockPairFeign;

    //创建AlipayClient
    public AlipayClient changPayClient(Integer clientType){
        AlipayClient alipayClient = null;
        //机主支付
        if(clientType.equals(ClientTypeConstants.CLIENT_OWNER.getId())){
            alipayClient = new DefaultAlipayClient(
                    aliPayConfig.getOwnerGatewayUrl(),
                    aliPayConfig.getOwnerAppId(),
                    aliPayConfig.getOwnerAppPrivateKey(),
                    aliPayConfig.getFormate(),
                    aliPayConfig.getCharset(),
                    aliPayConfig.getOwnerAlipayPublicKey(),
                    aliPayConfig.getSignType()
            );
            logger.info("调用机主端支付宝支付.....");
        //承租方支付
        }else if (clientType.equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
            alipayClient = new DefaultAlipayClient(
                    aliPayConfig.getTenantryGatewayUrl(),
                    aliPayConfig.getTenantryAppId(),
                    aliPayConfig.getTenantryAppPrivateKey(),
                    aliPayConfig.getFormate(),
                    aliPayConfig.getCharset(),
                    aliPayConfig.getTenantryAlipayPublicKey(),
                    aliPayConfig.getSignType()
            );
            logger.info("调用承租方支付宝支付.....");
        }
        return alipayClient;
    }

    /**
     * 参数非空验证
     * @return
     */
    public String verifyParam(TradePayVo tradePayVo){
        String resStr = null;
        if(!VerifyStr.isDouble(tradePayVo.getTradeAmount()) ){
            return "待支付支付金额数据格式错误";
        }
        if(VerifyStr.strToInteger(tradePayVo.getTradeAmount()) < 0){
            return "支付金额不能为0";
        }
        if(tradePayVo.getTradeType() == null){
            return "交易类型不能为空";
        }
        if(tradePayVo.getClientType() == null){
            return "支付发起端标识不能为空";
        }
        if(tradePayVo.getPayerUserId() == null){
            return "支付发起人id不能为空";
        }

        //调用的第三方支付方式不能为空或不识别
        if(!tradePayVo.getPaymentMethod().equals(TradePayVo.PaymentMethod.PLATPAY)
                && !tradePayVo.getPaymentMethod().equals(TradePayVo.PaymentMethod.WXPAY)
                && !tradePayVo.getPaymentMethod().equals(TradePayVo.PaymentMethod.UNIONPAY)
                && !tradePayVo.getPaymentMethod().equals(TradePayVo.PaymentMethod.ALIPAY)){
            return "第三方支付类型不识别";
        }
        //承租方支付趟数记录金额
        if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.TEN_PAY)){
            if(CollectionUtils.isEmpty(tradePayVo.getRouteIdList())){
                return "承租方支付趟数id不能为空";
            }
            if(tradePayVo.getProjectId() == null){
                return "支付趟数项目id不能为空";
            }
            if(tradePayVo.getTenantryOrderId() == null){
                return "支付趟数金额承租方订单id不能为空";
            }
            //判断金额是否篡改或拦截
            ResponseMessage resRouteMoney = jxcMachineRouteFeign.queryTotalAmount(tradePayVo.getRouteIdList());
            if(resRouteMoney.getCode() != 200){
                return "根据趟数查询金额feign调用失败";
            }else{
                Integer routeMoney = Integer.parseInt(resRouteMoney.getData().toString());
                if(!tradePayVo.getTradeAmount().equals(VerifyStr.integerToStrHand(routeMoney))){
                    return "输入金额与实际金额不相同";
                }
            }
        }
        //机主支付台班工资
        if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.OWNER_PAY)){
            if(tradePayVo.getClockId() == null || tradePayVo.getClockId() == 0){
                return "机主支付台班id不能为空";
            }
            OwnerPayClockVo  ownerPayClockVo = new OwnerPayClockVo();
            ownerPayClockVo.setClockId(tradePayVo.getClockId());
            ownerPayClockVo = this.jxcTradeOwnerMapper.queryOwnerPayTeamAmount(ownerPayClockVo);
            if(ownerPayClockVo.getId() == null){
                return "台班不存在";
            }
            if(ownerPayClockVo.getDriverId() == null){
                return "打卡台班司机不存在";
            }
            //查询台班金额是否被篡改
            if(!tradePayVo.getTradeAmount().equals(VerifyStr.integerToStrHand(ownerPayClockVo.getFactAmount()))){
                return "台班金额与支付金额金额不一致";
            }
        }

        return resStr;
    }

    /**
     * 支付宝支付
     * @param tradePayVo
     * @return
     */
    public ResponseMessage pay(TradePayVo tradePayVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        logger.info("支付宝支付开始......");
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。
        // 以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //交易类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
        if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.TEN_PAY)){
            model.setBody(String.valueOf(tradePayVo.getTradeType()));
            model.setSubject("喂喂机械-承租方支付订单");
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.OWNER_TOKE)){
            model.setBody(String.valueOf(tradePayVo.getTradeType()));
            model.setSubject("喂喂机械-机主提现");
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.OWNER_PAY)){
            model.setBody(String.valueOf(tradePayVo.getTradeType()));
            model.setSubject("喂喂机械-机主支付工资");
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.DRIVER_TOKEN)){
            model.setBody(String.valueOf(tradePayVo.getTradeType()));
            model.setSubject("喂喂机械-司机提现");
        }
        //平台生成商户网站唯一订单号
        tradePayVo.setOutTradeNo(IDGenerator.getInstance().next()+"");
        logger.info("支付宝支付商户唯一订单号: " + tradePayVo.getOutTradeNo());
        model.setOutTradeNo(tradePayVo.getOutTradeNo());
        model.setTimeoutExpress("30m");
        logger.info("支付宝支付金额：" +tradePayVo.getTradeAmount());
        model.setTotalAmount(tradePayVo.getTradeAmount());
        request.setBizModel(model);
        if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_OWNER.getId())){
            request.setNotifyUrl(aliPayConfig.getOwnerNotifyUrl());//异步回调url
        }else if(tradePayVo.getClientType().equals(ClientTypeConstants.CLIENT_TENANTRY.getId())){
            request.setNotifyUrl(aliPayConfig.getTenantryNotifyUrl());//异步回调url
        }
        // 这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response;
        //根据clientType选择支付的客户端(1--机主支付客户端  2--承租方支付客户端)
        AlipayClient alipayClient = changPayClient(tradePayVo.getClientType());
        try{
            //把数据预存到平台交易记录流水表中
            Integer res = insertData(tradePayVo);
            if(res != null && res >0){
                logger.info("支付宝支付添加支付流水成功..... ");
            }
            response = alipayClient.sdkExecute(request);
            logger.info("返回给请求客户端的orderString==  "+response.getBody());
            AliTradePayReturnVo aliTradePayReturnVo = new AliTradePayReturnVo();
            aliTradePayReturnVo.setOrderString(response.getBody());
            aliTradePayReturnVo.setOutTradeNo(tradePayVo.getOutTradeNo());
            aliTradePayReturnVo.setTradeAmount(tradePayVo.getTradeAmount());
            result.setData(aliTradePayReturnVo);
            return result;
        }catch (AlipayApiException ex){
            ex.printStackTrace();
            result.setCode(ErrorCodeConstants.ALIPAY_PAY_ERROR.getId());
            result.setMessage(ErrorCodeConstants.ALIPAY_PAY_ERROR.getDescript());
            logger.error("支付宝支付添加支付流水错误..... ");
        }
        return result;
    }

    /**
     * 添加平台交易流水
     * @param tradePayVo
     */
    public Integer insertData(TradePayVo tradePayVo){
        Integer res = 0;
        if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.TEN_PAY)){
            //承租方支付订单
            JxcTradeTenantry jxcTradeTenantry = new JxcTradeTenantry();
            jxcTradeTenantry.setId(Long.valueOf(tradePayVo.getOutTradeNo()));
            jxcTradeTenantry.setProjectId(tradePayVo.getProjectId());
            jxcTradeTenantry.setTenantryOrderId(tradePayVo.getTenantryOrderId());
            jxcTradeTenantry.setPaymentMethod(tradePayVo.getPaymentMethod());
            jxcTradeTenantry.setTradeAmount(VerifyStr.strToInteger(tradePayVo.getTradeAmount()));
            jxcTradeTenantry.setPayerUserId(tradePayVo.getPayerUserId());
            //收款者是平台
            jxcTradeTenantry.setPayeeUserId(0);
            res = this.jxcTradeTenantryMapper.insertSelective(jxcTradeTenantry);
            //添加承租方和支付趟数的关联表
            Map<String,Object> map = new HashMap<>(2);
            map.put("id",Long.valueOf(tradePayVo.getOutTradeNo()));
            map.put("itemList",tradePayVo.getRouteIdList());
            this.jxcTradeTenantryRefMapper.insertBatch(map);
            res = 1;
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.OWNER_PAY)){
            //机主支付订单
            JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(Long.valueOf(tradePayVo.getOutTradeNo()));
            jxcTradeOwner.setTradeType(tradePayVo.getTradeType());
            jxcTradeOwner.setPayerUserId(tradePayVo.getPayerUserId());
            //根据打卡记录查询收款的用户
            OwnerPayClockVo ownerPayClockVo = new OwnerPayClockVo();
            ownerPayClockVo.setClockId(tradePayVo.getClockId());
            ownerPayClockVo =  this.jxcTradeOwnerMapper.queryOwnerPayTeamAmount(ownerPayClockVo);
            jxcTradeOwner.setPayeeUserId(ownerPayClockVo.getDriverId());
            jxcTradeOwner.setTradeAmount(VerifyStr.strToInteger(tradePayVo.getTradeAmount()));
            jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.UN_SUCCESS);
            jxcTradeOwner.setLockStatus(JxcTradeOwner.LockStatus.LOCK);
            jxcTradeOwner.setPaymentMethod(tradePayVo.getPaymentMethod());
            jxcTradeOwner.setClockId(tradePayVo.getClockId());
            res = this.jxcTradeOwnerMapper.insertSelective(jxcTradeOwner);
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.OWNER_TOKE)){
            //机主提现
            JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(IDGenerator.getInstance().next());
            jxcTradeOwner.setTradeType(tradePayVo.getTradeType());
            jxcTradeOwner.setPayeeUserId(0);
            jxcTradeOwner.setPayeeUserId(tradePayVo.getPayeeUserId());
            jxcTradeOwner.setTradeAmount(VerifyStr.strToInteger(tradePayVo.getTradeAmount()));
            //提现属于银联打款每笔收2块手续费
            jxcTradeOwner.setActualAmount(VerifyStr.strToInteger(tradePayVo.getTradeAmount()) - 200);
            jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.UN_SUCCESS);
            res = this.jxcTradeOwnerMapper.insertSelective(jxcTradeOwner);
        }else if(tradePayVo.getTradeType().equals(TradePayVo.tradeType.DRIVER_TOKEN)){
            //司机提现
            JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(IDGenerator.getInstance().next());
            jxcTradeOwner.setTradeType(tradePayVo.getTradeType());
            jxcTradeOwner.setPayeeUserId(0);
            jxcTradeOwner.setPayeeUserId(tradePayVo.getPayeeUserId());
            jxcTradeOwner.setTradeAmount(VerifyStr.strToInteger(tradePayVo.getTradeAmount()));
            jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.UN_SUCCESS);
            res = this.jxcTradeOwnerMapper.insertSelective(jxcTradeOwner);
        }
        return res;
    }

    /**
     * 用户支付回调操作
     * @param tradeType 支付类型(1--承租方支付订单 2--机主提现 3--机主支付工资 4--司机提现)
     * @param outTradeNo 商户唯一订单号
     * @param tradeNo 第三方支付流水号(银联，支付宝，微信)
     */
    public void notityAction(Integer tradeType ,String outTradeNo,String tradeNo){
        //当用户是承租方支付订单查询
        if(tradeType.equals(TradePayVo.tradeType.TEN_PAY)){
            JxcTradeTenantry jxcTradeTenantry = new JxcTradeTenantry();
            jxcTradeTenantry.setId(Long.parseLong(outTradeNo));
            jxcTradeTenantry.setTradeStatus(JxcTradeTenantry.tradeState.SUCCESS);
            jxcTradeTenantry.setThirdTradeNo(tradeNo);
            jxcTradeTenantry.setUpdateTime(new Date());
            this.jxcTradeTenantryMapper.updateByPrimaryKeySelective(jxcTradeTenantry);
            //查询根据唯一订单id查询所有的趟数集合
            List<Long> routeIdList  = this.jxcTradeTenantryRefMapper.queryRouteIdList(Long.parseLong(outTradeNo));
            this.jxcTradeTenantryRefMapper.updateBatchMachineRoute(routeIdList);
            //根据行程的异常状态判断支付的是正常的行程还是异常的行程，借此去更改不能种类的待办事项
            if(routeIdList != null && routeIdList.size() > 0){
                JxcMachineRoute machineRoute = jxcTradeTenantryMapper.queryJxcMachineRoute(routeIdList.get(0));
                if(machineRoute.getAbnormalType().equals(JxcMachineRoute.AbnormalType.NORMAL)){
                    //正常的趟数
                    String startDate = DateUtils.format(machineRoute.getStartTime());
                    //把待支付的待办事项查出来
                    JxcWaitHandleItemsVo vo = new JxcWaitHandleItemsVo();
                    vo.setProjectOrderId(machineRoute.getTenantryOrderId());
                    vo.setBillStartDate(startDate);
                    JxcWaitHandleItemsVo jxcWaitHandleItemsVo = jxcTradeTenantryMapper.queryJxcWaitHandle(vo);
                    //取出支付的金额，用待办事项金额减去支付金额
                    JxcTradeTenantry jxcTradeTenantry1 = jxcTradeTenantryMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                    Integer tradeAmount = jxcTradeTenantry1.getTradeAmount();
                    if(jxcWaitHandleItemsVo.getPayAmount() - tradeAmount > 0){
                        //相减的金额大于0，证明没有支付完成,那就修改待办事项金额
                        jxcTradeTenantryMapper.updatePayAmount(jxcWaitHandleItemsVo.getId(),(jxcWaitHandleItemsVo.getPayAmount() - tradeAmount));
                    }else {
                        //金额等于0或者小于0，则删除待办事项
                        jxcTradeTenantryMapper.delJxcWaitHandle(jxcWaitHandleItemsVo.getId());
                    }
                }else {
                    //异常趟数支付  直接进行循环删除待办事项
                    for (Long routeId : routeIdList){
                        JxcWaitHandleItemsVo vo = new JxcWaitHandleItemsVo();
                        vo.setRouteId(routeId);
                        JxcWaitHandleItemsVo jxcWaitHandleItemsVo = jxcTradeTenantryMapper.queryJxcWaitHandle(vo);
                        //删除待办事项
                        jxcTradeTenantryMapper.delJxcWaitHandle(jxcWaitHandleItemsVo.getId());
                    }
                }
            }
        }else if(tradeType.equals(TradePayVo.tradeType.OWNER_TOKE)
                || tradeType.equals(TradePayVo.tradeType.OWNER_PAY)
                || tradeType.equals(TradePayVo.tradeType.DRIVER_TOKEN)){
            JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(Long.parseLong(outTradeNo));
            jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.SUCCESS);
            jxcTradeOwner.setThirdTradeNo(tradeNo);
            jxcTradeOwner.setUpdateTime(new Date());
            this.jxcTradeOwnerMapper.updateByPrimaryKeySelective(jxcTradeOwner);
            //如果是机主支付订单，则打卡记录更新已支付
            if(tradeType.equals(TradePayVo.tradeType.OWNER_PAY)){
                JxcTradeOwner queryTradOwner = this.jxcTradeOwnerMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                jxcClockPairFeign.updateClockPayStatus(queryTradOwner.getClockId());
            }

        }
    }
}
