package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.bean.JxcTradeOwner;
import com.weiwei.jixieche.bean.JxcTradeTenantry;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcTradeOwnerMapper;
import com.weiwei.jixieche.mapper.JxcTradeTenantryMapper;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.sdk.AcpService;
import com.weiwei.jixieche.sdk.LogUtil;
import com.weiwei.jixieche.sdk.SDKConfig;
import com.weiwei.jixieche.sdk.SDKConstants;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UnionPayUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/8/16 11:30
 * @Version 1.0.1
 **/
@Component
public class UnionPayUtils {

    private static Logger logger = LoggerFactory.getLogger(UnionPayUtils.class);

    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    private static String encoding = "UTF-8";

    private static String merId = "802440373720519";

    private static String version = SDKConfig.getConfig().getVersion();

    private static String backUrl = SDKConfig.getConfig().getBackUrl();

    @Autowired
    private AliPayUtils aliPayUtils;

    @Resource
    private JxcTradeTenantryMapper jxcTradeTenantryMapper;

    @Resource
    private JxcTradeOwnerMapper jxcTradeOwnerMapper;

    @Autowired
    private ChinaPayUtil chinaPayUtil;

    /**
     * 银联支付
     * @param tradePayVo
     * @return
     */
    public ResponseMessage unionPay(TradePayVo tradePayVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        logger.info("银联预支付开始,商户订单tradeNo  " + tradePayVo.getOutTradeNo());
        //加载配置文件项(重要必不可少)
        SDKConfig.getConfig().loadPropertiesFromSrc();

        Map<String, String> contentData = new HashMap<String, String>();
        //版本号
        contentData.put("version", version);
        //字符集编码 可以使用UTF-8,GBK两种方式
        contentData.put("encoding", encoding);
        //签名方法  01:RSA证书方式  11：支持散列方式验证SHA-256 12：支持散列方式验证SM3
        contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());
        //交易类型 95-银联加密公钥更新查询
        contentData.put("txnType", "01");
        //交易子类型  默认00
        contentData.put("txnSubType", "01");
        //业务类型  默认
        contentData.put("bizType", "000201");
        //渠道类型
        contentData.put("channelType", "08");

        /***商户接入参数***/
        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        contentData.put("merId", merId);
        //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        contentData.put("accessType", "0");
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        contentData.put("orderId", tradePayVo.getOutTradeNo());
        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        contentData.put("txnTime", df.format(new Date()));
        //账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
        contentData.put("accType", "01");
        //交易金额 单位为分，不能带小数点
        contentData.put("txnAmt", String.valueOf(VerifyStr.strToInteger(tradePayVo.getTradeAmount())));
        logger.info("银联预支付金额==="+ tradePayVo.getTradeAmount());
        //境内商户固定 156 人民币
        contentData.put("currencyCode", "156");
        contentData.put("backUrl", backUrl);

        //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        Map<String, String> reqData = AcpService.sign(contentData, encoding);
        //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();
        //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;
        // 这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        Map<String, String> rspData = AcpService.post(reqData, requestAppUrl, encoding);
        String tn = "";
        //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, encoding)){
                LogUtil.writeLog("验证签名成功");
                String respCode = rspData.get("respCode") ;
                if(("00").equals(respCode)){
                    //成功,获取tn号
                    logger.info("银联预支付成功,预支付返回值:  " + rspData);
                    Integer res = this.aliPayUtils.insertData(tradePayVo);
                    if(res == null || res  <= 0){
                        result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
                        result.setMessage("添加银联预支付记录数据失败!");
                        return result;
                    }
                    tn = rspData.get("tn");
                    Map<String,Object> returnData = new HashMap<String,Object>();
                    returnData.put("tn",tn);
                    result.setData(returnData);
                    return result;
                }else{
                    //其他应答码为失败请排查原因或做失败处理
                    //TODO
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
                //TODO 检查验证签名失败的原因
            }
        }else{
            //未返回正确的http状态
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
        }
        return result;
    }

    /**
     * 支付回调
     * @param req
     * @return
     */
    public String notify(HttpServletRequest req){
        LogUtil.writeLog("接收到银联后台回调通知...");
        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题
            return "fail";
        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            //获取后台通知的数据，其他字段也可用类似方式获取
            String orderId =reqParam.get("orderId");
            String respCode = reqParam.get("respCode");

            /*System.out.println("orderId:"+orderId);
            System.out.println("respCode:"+respCode);*/
            if (respCode.equals("00")){
                //开始主动查询支付结果
                String merId1 = req.getParameter("merId");
                String orderId1 = req.getParameter("orderId");
                String txnTime = req.getParameter("txnTime");
                Map<String, String> data = new HashMap<String, String>();
                /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
                //版本号
                data.put("version", version);
                //字符集编码
                data.put("encoding", encoding);
                //签名方法
                data.put("signMethod", SDKConfig.getConfig().getSignMethod());
                //交易类型
                data.put("txnType", "00");
                //交易子类型
                data.put("txnSubType", "00");
                //业务类型
                data.put("bizType", "000802");
                /***商户接入参数***/
                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
                data.put("merId", merId);
                //接入类型，商户接入固定填0，不需修改
                data.put("accessType", "0");
                /***要调通交易以下字段必须修改***/
                //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
                data.put("orderId", orderId1);
                //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
                data.put("txnTime", txnTime);
                /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
                //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
                Map<String, String> reqData = AcpService.sign(data,encoding);
                //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
                String url = SDKConfig.getConfig().getSingleQueryUrl();
                //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;
                // 这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
                Map<String, String> rspData = AcpService.post(reqData,url,encoding);
                /**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
                //应答码规范参考open.unionpay.com帮助中心 下载 产品接口规范 《平台接入接口规范-第5部分-附录》
                logger.error("------------------银联支付回调结果：       "+rspData.toString());
                if(!rspData.isEmpty()){
                    if(AcpService.validate(rspData, encoding)){
                        LogUtil.writeLog("验证签名成功");
                        if(("00").equals(rspData.get("respCode"))){//如果查询交易成功
                            String origRespCode = rspData.get("origRespCode");
                            if(("00").equals(origRespCode)){
                                //总金额
                                String txnAmt = rspData.get("txnAmt");
                                //第三方交易流水
                                String traceNo = rspData.get("traceNo");
                                //商户订单id
                                String outTradeNo = orderId1;

                                JxcTradeTenantry jxcTradeTenantry = this.jxcTradeTenantryMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                                if(jxcTradeTenantry != null){
                                    if(Integer.parseInt(txnAmt) != jxcTradeTenantry.getTradeAmount()){
                                        logger.error("银联回调与本地数据不匹配,银联流水号： thirdTradeNo " + traceNo + "  银联支付金额(分)： " + txnAmt);
                                        return "fail";
                                    }
                                    if(jxcTradeTenantry.getTradeStatus() == JxcTradeTenantry.tradeState.UN_SUCCESS){
                                        this.aliPayUtils.notityAction(TradePayVo.tradeType.TEN_PAY,outTradeNo,traceNo);
                                    }
                                }else{
                                    JxcTradeOwner jxcTradeOwner = this.jxcTradeOwnerMapper.selectByPrimaryKey(Long.parseLong(outTradeNo));
                                    if(Integer.parseInt(txnAmt) != jxcTradeOwner.getTradeAmount()){
                                        logger.error("银联回调与本地数据不匹配,银联流水号： thirdTradeNo " + traceNo + "  银联支付金额(分)： " + txnAmt);
                                        return "fail";
                                    }
                                    if(jxcTradeOwner.getTradeStatus() == JxcTradeOwner.TradeStatus.UN_SUCCESS){
                                        this.aliPayUtils.notityAction(jxcTradeOwner.getTradeType(),outTradeNo,traceNo);
                                    }
                                }
                            }else if(("03").equals(origRespCode) || ("04").equals(origRespCode)|| ("05").equals(origRespCode)){
                                //订单处理中或交易状态未明，需稍后发起交易状态查询交易 【如果最终尚未确定交易是否成功请以对账文件为准】
                                LogUtil.writeErrorLog("处理超时，请稍后查询。<br> ");
                                //TODO
                            }else{
                                //其他应答码为交易失败
                                //TODO
                                LogUtil.writeErrorLog("交易失败：" + rspData.get("origRespMsg") + "。<br> ");
                            }
                        }else if(("34").equals(rspData.get("respCode"))){
                            //订单不存在，可认为交易状态未明，需要稍后发起交易状态查询，或依据对账结果为准
                            LogUtil.writeErrorLog("订单不存在。<br> ");
                        }else{//查询交易本身失败，如应答码10/11检查查询报文是否正确
                            //TODO
                            LogUtil.writeErrorLog("失败：" + rspData.get("respMsg") + "。<br> ");
                        }
                    }else{
                        LogUtil.writeErrorLog("验证签名失败");
                    }
                }else{
                    LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
                }
            }
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
            return "ok";
        }
    }

    /**
     * 银联回调获取请求参数方法
     * @param request
     * @return
     */
    private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                //System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 验证参数
     * @param tradePayVo
     * @return
     */
    public String verifyParam(TradePayVo tradePayVo){
        String resStr = null;
        if(tradePayVo.getTradeType() == null){
            resStr = "app发起支付类型为空";
            return resStr;
        }
        if(!VerifyStr.isDouble(tradePayVo.getTradeAmount()) ){
            resStr = "待支付支付金额数据格式错误";
            return resStr;
        }
        if(VerifyStr.strToInteger(tradePayVo.getTradeAmount()) < 0){
            resStr = "支付金额不能为0";
            return resStr;
        }
        if(tradePayVo.getTradeType() == null){
            resStr = "交易类型不能为空";
            return resStr;
        }
        return resStr;
    }

    /**
     * 提现参数校验
     * @param user
     * @param cashApplyVo
     * @return
     */
    public String verifyCashParam(AuthorizationUser user,CashApplyVo cashApplyVo){
        String resStr = null;
        if(user == null){
            resStr = "token不存在或解析错误";
            return resStr;
        }
        if(user.getRoleId() != UserRoleContants.OWNER.getRoleId() && user.getRoleId() != UserRoleContants.DRIVER.getRoleId()){
            resStr = "提现角色不识别";
            return resStr;
        }
        if (new BigDecimal(cashApplyVo.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            resStr = "提现金额不能为空或0";
            return resStr;
        }
        if (cashApplyVo.getCardId() == null) {
            resStr = "提现的银行卡id不能为空";
            return resStr;
        }

        //查询用户基本信息
        CashApplyUserInfoVo cashApplyUserInfoVo = this.jxcTradeOwnerMapper.queryCashApplyUserInfo(user.getUserId());
        if(cashApplyUserInfoVo == null){
            resStr = "查询提现用户信息为空";
            return resStr;
        }
        if(cashApplyUserInfoVo.getState() == 0){
            resStr = "用户被禁用";
            return resStr;
        }
        if(cashApplyUserInfoVo.getPayPwd() == null){
            resStr = "用户未设置支付密码";
            return resStr;
        }
        if(cashApplyUserInfoVo.getRealName() == null || "".equals(cashApplyUserInfoVo.getRealName())){
            resStr = "用户真实姓名为空";
            return resStr;
        }
        if(!cashApplyUserInfoVo.getPayPwd().equals(cashApplyVo.getPayPassword())){
            resStr = "支付密码错误";
            return resStr;
        }
        //机主提现
        if(user.getRoleId() == UserRoleContants.OWNER.getRoleId()){
            //机主总收入未冻结金额
            UserTradeAmountVo totalUnLockAmount= new UserTradeAmountVo();
            totalUnLockAmount.setPayerUserId(0);
            totalUnLockAmount.setPayeeUserId(user.getUserId());
            totalUnLockAmount.setTradeType(UserTradeAmountVo.WalletType.PLAT);
            totalUnLockAmount.setLockStatus(UserTradeAmountVo.lockStatus.UNLOCK);
            totalUnLockAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(totalUnLockAmount);
            //机主支付金额
            UserTradeAmountVo payAmount= new UserTradeAmountVo();
            payAmount.setPayerUserId(user.getUserId());
            payAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_PAY);
            payAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(payAmount);
            //机主提现金额
            UserTradeAmountVo takeAmount= new UserTradeAmountVo();
            takeAmount.setPayeeUserId(user.getUserId());
            takeAmount.setTradeType(UserTradeAmountVo.WalletType.OWNER_TAKE);
            takeAmount = this.jxcTradeOwnerMapper.queryUserTradeAmount(takeAmount);

            //查询机主提现未处理金额
            Integer unDoneAmount =  this.jxcTradeOwnerMapper.queryUnDoneAmountByUserId(user.getUserId());
            //机主可用余额 = 机主未冻结总金额  - 机主支付工资金额 -  机主提现金额 - 机主提现未处理金额 - 银联提现手续费(默认2元200分)
            Integer OwnerUnLockAmount = totalUnLockAmount.getTotalAmount() - payAmount.getTotalAmount() - takeAmount.getTotalAmount()- unDoneAmount - 200;
            if(OwnerUnLockAmount < 0){
                resStr = "可用余额不足";
                return resStr;
            }
            if(VerifyStr.strToInteger(cashApplyVo.getAmount()) > OwnerUnLockAmount){
                resStr = "提现金额不能大于可用余额";
                return resStr;
            }
        }else if(user.getRoleId() == UserRoleContants.DRIVER.getRoleId()){
            //司机提现
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

            //查询司机提现未处理金额
            Integer unDoneAmount =  this.jxcTradeOwnerMapper.queryUnDoneAmountByUserId(user.getUserId());

            //司机可用余额 =  司机解冻总金额 - 司机提现金额
            Integer unLockAmount = driverUnLockAmount.getTotalAmount() - driverTakeAmount.getTotalAmount() - unDoneAmount - 200;
            if(unLockAmount < 0){
                resStr = "可用余额不足";
                return resStr;
            }
            if(VerifyStr.strToInteger(cashApplyVo.getAmount()) > unLockAmount){
                resStr = "提现金额不能大于可用余额";
                return resStr;
            }
        }
        return resStr;
    }

    /**
     * 用户提现
     * @param user 用户
     * @param cashApplyVo 提现参数
     * @return
     */
    public ResponseMessage cashApply(AuthorizationUser user,CashApplyVo cashApplyVo){
        ResponseMessage result = ResponseMessageFactory.newInstance();

        UserBankCardInfoVo userBankCardInfoVo = new UserBankCardInfoVo();
        userBankCardInfoVo.setId(cashApplyVo.getCardId());
        //查询用户银行卡信息
        userBankCardInfoVo = this.jxcTradeOwnerMapper.queryBankCard(userBankCardInfoVo);
        if(userBankCardInfoVo.getCardNumber() == null || userBankCardInfoVo.getName() == null){
            result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
            result.setMessage("银行卡号或用户真实姓名不能为空");
            return result;
        }
        logger.info("提现用户银行卡信息........"+ userBankCardInfoVo);

        String outTradeNo = String.valueOf(IDGenerator.getInstance().next());
        //提现先扣除用户用户可用余额，不然造成重复提现
        logger.info("用户： "+user.getUserId()+"角色:  "+user.getRoleId()+"   提现开始....." + "提现商户订单号：" + outTradeNo);
        logger.info("提现申请金额(元) " + cashApplyVo.getAmount());
        JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
        //机主提现
        jxcTradeOwner.setId(Long.valueOf(outTradeNo));
        if(user.getRoleId() == UserRoleContants.OWNER.getRoleId()){
            jxcTradeOwner.setTradeType(JxcTradeOwner.UserTradeType.OWNER_TAKE);
        }else if(user.getRoleId() == UserRoleContants.DRIVER.getRoleId()){
            jxcTradeOwner.setTradeType(JxcTradeOwner.UserTradeType.DRIVER_TAKE);
        }
        jxcTradeOwner.setPayerUserId(0);
        jxcTradeOwner.setPayeeUserId(user.getUserId());
        //扣除金额状态暂时不成功
        jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.UN_SUCCESS);
        jxcTradeOwner.setCashStatus(JxcTradeOwner.CashStatus.NO_DONE);
        //交易金额+2元的银联手续费
        jxcTradeOwner.setTradeAmount(VerifyStr.strToInteger(cashApplyVo.getAmount())+200);
        jxcTradeOwner.setBankCardId(cashApplyVo.getCardId());
        this.jxcTradeOwnerMapper.insertSelective(jxcTradeOwner);

        Map<String, String> transferMap = new HashMap<>(5);
        transferMap.put("merSeqId", outTradeNo);
        transferMap.put("cardNo", userBankCardInfoVo.getCardNumber());
        transferMap.put("usrName", userBankCardInfoVo.getName());
        transferMap.put("openBank",userBankCardInfoVo.getBankName());
        transferMap.put("transAmt", VerifyStr.strToInteger(cashApplyVo.getAmount()).toString());
        ChinaPayResult chinaPayResult = chinaPayUtil.treatPay(transferMap);
        int rs = chinaPayResult.getRs();
        if(rs == 1){
            logger.info("提现成功");
            Map<String,Object> respVals = (Map<String, Object>) chinaPayResult.getData();
            //返回商户交易唯一订单号
            Long notifyOutTradeNo = Long.valueOf(String.valueOf(respVals.get("merSeqId")));
            logger.info("提现成功,返回商户唯一订单号:"+notifyOutTradeNo);
            //银联支付交易流水号
            String thirdTradeNo = (String)respVals.get("cpSeqId");
            logger.info("提现成功,返回银联流水号:"+thirdTradeNo);
            //实际到账金额
            String tradeAmount = (String)respVals.get("transAmt");
            logger.info("提现成功,返回实际到账金额(元):"+ VerifyStr.strToStr(tradeAmount));
            jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(notifyOutTradeNo);
            jxcTradeOwner.setThirdTradeNo(thirdTradeNo);
            jxcTradeOwner.setActualAmount(Integer.valueOf(tradeAmount));
            jxcTradeOwner.setTradeStatus(JxcTradeOwner.TradeStatus.SUCCESS);
            jxcTradeOwner.setCashStatus(JxcTradeOwner.CashStatus.DONE);
            this.jxcTradeOwnerMapper.updateByPrimaryKeySelective(jxcTradeOwner);
            logger.info("提现成功处理提现结果成功");
        }else {
            //提现失败 返还金额
            logger.info("提现失败，处理失败结果..........");
            jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(Long.valueOf(outTradeNo));
            jxcTradeOwner.setCashStatus(JxcTradeOwner.CashStatus.DONE);
            this.jxcTradeOwnerMapper.updateByPrimaryKeySelective(jxcTradeOwner);
            logger.info("提现失败。。。处理失败结果成功");
            result.setCode(ErrorCodeConstants.CASH_APPLY_ERROR.getId());
            result.setMessage(chinaPayResult.getMsg());
            return result;
        }
        return result;
    }

}
