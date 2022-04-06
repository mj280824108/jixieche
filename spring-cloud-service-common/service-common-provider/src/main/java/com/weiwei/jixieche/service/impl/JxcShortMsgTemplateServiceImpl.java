package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.weiwei.jixieche.bean.JxcShortMsgRecord;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.bean.JxcShortMsgTemplate;
import com.weiwei.jixieche.generate.ConfirmCodeUtil;
import com.weiwei.jixieche.mapper.JxcShortMsgRecordMapper;
import com.weiwei.jixieche.mapper.JxcShortMsgTemplateMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseException;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcShortMsgTemplateService;
import com.weiwei.jixieche.utils.AliSmsUtils;
import com.weiwei.jixieche.utils.ShortMsgUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.AliShortMsgVo;
import com.weiwei.jixieche.vo.ShortMsgTemplate;
import com.weiwei.jixieche.vo.ShortMsgTemplateVo;
import com.weiwei.jixieche.vo.ShortMsgTestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/12 9:36
 * @Version 2.0
 **/
@Service("jxcShortMsgTemplateService")
@Slf4j
public class JxcShortMsgTemplateServiceImpl implements JxcShortMsgTemplateService {


       @Resource
       private JxcShortMsgTemplateMapper jxcShortMsgTemplateMapper;

       @Resource
       private JxcShortMsgRecordMapper jxcShortMsgRecordMapper;

       @Autowired
       private RedisUtil redisUtil;

       @Autowired
       private ShortMsgUtils shortMsgUtils;

       @Autowired
       private AliSmsUtils aliSmsUtils;

       //前端分页查询短信模板
       @Override
       public ResponseMessage<JxcShortMsgTemplate> findByPageForFront(Integer pageNo, Integer pageSize, JxcShortMsgTemplate jxcShortMsgTemplate) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcShortMsgTemplate> list = this.jxcShortMsgTemplateMapper.selectListByConditions(jxcShortMsgTemplate);
              PageInfo<JxcShortMsgTemplate> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       //添加短信模板
       @Override
       public ResponseMessage<JxcShortMsgTemplate> addObj(JxcShortMsgTemplate t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcShortMsgTemplateMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改短信模板
       @Override
       public ResponseMessage<JxcShortMsgTemplate> modifyObj(JxcShortMsgTemplate t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcShortMsgTemplateMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       //根据ID查询短信模板
       @Override
       public ResponseMessage<JxcShortMsgTemplate> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcShortMsgTemplate model=this.jxcShortMsgTemplateMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 短信发送
        * @param shortMsgTemplateVo
        * @return
        * @throws Exception
        */
       @Override
       public ResponseMessage sendShortMsg(ShortMsgTemplateVo shortMsgTemplateVo) throws Exception {
              if (shortMsgTemplateVo.getMsgTypeId() == null) {
                     log.error("【异常】 短信类型为空  {} ", shortMsgTemplateVo);
                     throw new ResponseException(ErrorCodeConstants.QUERY_PARAM_UNKNOW.getId(),ErrorCodeConstants.QUERY_PARAM_UNKNOW.getDescript());
              }
              if (shortMsgTemplateVo.getClient() == null) {
                     log.error("【异常】 客户端类型为空  {} ", shortMsgTemplateVo);
                     throw new ResponseException(ErrorCodeConstants.QUERY_PARAM_UNKNOW.getId(),ErrorCodeConstants.QUERY_PARAM_UNKNOW.getDescript());
              }
              if (shortMsgTemplateVo.getPhone() == null) {
                     log.error("【异常】 手机号为空  {} ", shortMsgTemplateVo);
                     throw new ResponseException(ErrorCodeConstants.QUERY_PARAM_UNKNOW.getId(),ErrorCodeConstants.QUERY_PARAM_UNKNOW.getDescript());
              }
              //是否需要限定短信的标志
              boolean isLimit = false;

              // 2. 计算客户端类型和Redis缓存Key的前缀
              int client = shortMsgTemplateVo.getClient();
              //根据tmepid取出对应的operationTYpe
              Integer operationType = shortMsgUtils.templateMap.get(shortMsgTemplateVo.getMsgTypeId()).getOperationType();


              String codePrefix =  shortMsgUtils.codePrefixMap.get(operationType);
              String countPrefix =  shortMsgUtils.countPrefixMap.get(operationType);

              isLimit = (codePrefix == null || countPrefix == null) ? true : false;
              String redisCodeKey = null;
              String redisCountKey = null;
              if (isLimit) {
                     String now = LocalDate.now().toString();
                     redisCodeKey = codePrefix + shortMsgTemplateVo.getPhone();
                     redisCountKey = countPrefix + now + "." + shortMsgTemplateVo.getPhone();
                     if(countPrefix != null && !countPrefix.equals("")) {
                            // 3. 判断发送次数和频率
                            if (redisUtil.hasKey(redisCodeKey)) {
                                   long elapse = RedisExpireTimeConstants.MOBILE_CODE_EXPIRE.getExpireTime() - redisUtil.getExpire(redisCodeKey);
                                   if (elapse < RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime()) {
                                          log.error("【异常】 手机号{}发送短信，频率过高", shortMsgTemplateVo.getPhone());
                                          throw new ResponseException(ErrorCodeConstants.TOO_OFTEN_MOBILE_MSG.getId(),ErrorCodeConstants.TOO_OFTEN_MOBILE_MSG.getDescript());
                                   }
                            }
                            if (redisUtil.hasKey(redisCountKey)) {
                                   int redisCount = (int) redisUtil.get(redisCountKey);
                                   if (redisCount > RedisCountConstants.MOBILE_CODE_COUNT_LIMIT.getCount()) {
                                          log.error("【异常】手机号{} 发送短信数量超出限制", shortMsgTemplateVo.getPhone());
                                          throw new ResponseException(ErrorCodeConstants.TOO_MUCH_MOBILE_MSG.getId(),ErrorCodeConstants.TOO_MUCH_MOBILE_MSG.getDescript());
                                   }
                            } else {
                                   // 创建该手机当天count 有效期24小时
                                   redisUtil.set(redisCountKey, 0, RedisExpireTimeConstants.MOBILE_COUNT_EXPIRE.getExpireTime());
                            }
                     }

              }
              Optional.of(shortMsgTemplateVo).
                      orElseThrow(() -> new
                              ResponseException(ErrorCodeConstants.PARAM_EMPTY.getId(), ErrorCodeConstants.PARAM_EMPTY.getDescript()));
              ShortMsgTemplate template = shortMsgUtils.templateMap.get(shortMsgTemplateVo.getMsgTypeId());
              String msg = template.getTemplateBody();

              // 填充短信模板的参数应包含原有的msgType和clientName
              JSONObject params = JSONObject.parseObject(shortMsgTemplateVo.getParams());
              if (params == null) {
                     params = new JSONObject();
              }
              params.put("msgType", template.getMsgType());
              params.put("clientName", shortMsgUtils.clientMap.get(shortMsgTemplateVo.getClient()));
              String fieldVal;
              if(template.getTemplateFields() !=null && !(template.getTemplateFields().isEmpty()) ) {
                     for (
                             String field : template.getTemplateFields()) {
                            //从map中获取参数
                            fieldVal = params.getString(field);
                            if (!VerifyStr.isEmpty(fieldVal)) {
                                   msg = msg.replaceAll("\\$\\(" + field + "\\)", fieldVal);
                            }
                     }
              }
              MultivaluedMapImpl formData = new MultivaluedMapImpl();
              try {
                     formData.add("mobile", shortMsgTemplateVo.getPhone());
                     formData.add("message", msg);
                     String error = shortMsgUtils.sendAndParseResult(formData);
                     if (error.equals("0")) {
                            JxcShortMsgRecord jxcShortMsgRecord = new JxcShortMsgRecord();
                            jxcShortMsgRecord.setClient(shortMsgTemplateVo.getClient());
                            jxcShortMsgRecord.setMsgTypeId(shortMsgTemplateVo.getMsgTypeId());
                            jxcShortMsgRecord.setPhone( shortMsgTemplateVo.getPhone());
                            jxcShortMsgRecord.setMsg(msg);
                            this.jxcShortMsgRecordMapper.insertSelective(jxcShortMsgRecord);
                            // 200表示成功

                            // mark：短信服务成功响应，1.计数加一，2.将生成的验证码存入缓存，3.设置过期时间
                            // mark：redisCount： 24小时内的总发送次数，发送成功后重新计时（在准备发送短信的时候要判断）
                            // mark：redisCode：验证码，发送成功后重新计时
                            if (isLimit) {
                                   int redisCount = 0;
                                   if (redisUtil.hasKey(redisCountKey)) {
                                          redisCount = (int) (redisUtil.get(redisCountKey));
                                   }
                                   redisUtil.set(redisCountKey, ++redisCount, RedisExpireTimeConstants.MOBILE_COUNT_EXPIRE.getExpireTime());
                            }
                     }
              } catch (Exception e) {
                     log.error("【异常】 调用短信服务异常，手机号{}", shortMsgTemplateVo.getPhone());
                     throw new ResponseException(ErrorCodeConstants.SERVICE_EXCEPTION_ERROR.getId(),ErrorCodeConstants.SERVICE_EXCEPTION_ERROR.getDescript());
              }
              return new ResponseMessage();
       }

       //初始化短信模板
       @Override
       public void initShortMsgTemplateDatas() {
              List<ShortMsgTemplate> templateList = this.jxcShortMsgTemplateMapper.selectShortMsgTemplateList();

              Pattern pattern = Pattern.compile("\\$\\((.*?)\\)");
              Matcher matcher = null;
              String field = null;

              for (ShortMsgTemplate template : templateList) {
                     matcher = pattern.matcher(template.getTemplateBody());
                     while (matcher.find()) {
                            field = matcher.group(1);
                            template.getTemplateFields().add(field);
                     }
                     shortMsgUtils.templateMap.put(template.getTemplateId(), template);
              }
       }

       //获取消息模板集合
       @Override
       public Map<Integer, ShortMsgTemplate> getTemplateList() {
              return shortMsgUtils.templateMap;
       }

       //根据id获取短信模板
       @Override
       public ShortMsgTemplate getTemplate(int msgType) {
              return shortMsgUtils.templateMap.get(msgType);
       }

       /**
        * 发送短信验证码(测试所用)
        * @return
        */
       @Override
       public ResponseMessage sendShortMsgCodeTest(ShortMsgTestVo shortMsgTestVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JSONObject jsonObject = new JSONObject();
              jsonObject.put("date","2019-09-16");
              jsonObject.put("money","100");
              this.aliSmsUtils.SendAliSms("13168701286","SMS_173246818",jsonObject.toString());
              return result;
       }


       /**
        * 阿里云发送短信
        * @param aliShortMsgVo
        * @return
        */
       @Override
       public ResponseMessage aliSendShortMsg(AliShortMsgVo aliShortMsgVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(!aliSmsUtils.paramVerify(aliShortMsgVo)){
                     result.setCode(ErrorCodeConstants.PARAM_MUST_EMPTY.getId());
                     result.setMessage(ErrorCodeConstants.PARAM_MUST_EMPTY.getDescript());
                     return result;
              }
              //发送短信
              if(aliShortMsgVo.getClientType() == AliShortMsgVo.ClientType.APP){
                     String code = ConfirmCodeUtil.getNumberCode(6);
                     //判断app的codeTyp类型
                     if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.LOGIN){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_LOGIN.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.REGISTER){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_REGISTER.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.FORGET_PWD){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_FORGET_PWD.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.BING_CARD){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_BIND_CARD.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.INSERT_MANAGER){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_ADD_MANAGER.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.INSERT_LONG_DRIVER){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_ADD_LONG_DRIVER.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }else if(aliShortMsgVo.getCodeType() == AliShortMsgVo.CodeType.USER_SET_PAY_PWD){
                            redisUtil.set(RedisKeyConstants.SHORT_CODE_USER_SET_PAY_PWD.getPrefix()+aliShortMsgVo.getPhone(),code,RedisExpireTimeConstants.LIMIT_INTERVAL.getExpireTime());
                     }

                     result = aliSmsUtils.SendAliSms(aliShortMsgVo.getPhone(),"SMS_173145534","{\"code\":\""+ code +"\"}");
              }else if(aliShortMsgVo.getClientType() == AliShortMsgVo.ClientType.BACK){
                     //查询后台模板是否存在
                     JxcShortMsgTemplate jxcShortMsgTemplate = this.jxcShortMsgTemplateMapper.selectByPrimaryKey(aliShortMsgVo.getTemplateId());
                     if(jxcShortMsgTemplate != null && jxcShortMsgTemplate.getTemplateCode() != null){
                            result = aliSmsUtils.SendAliSms(aliShortMsgVo.getPhone(),jxcShortMsgTemplate.getTemplateCode(),aliShortMsgVo.getTemplateParam());
                     }else {
                            result.setCode(ErrorCodeConstants.PARAM_MUST_EMPTY.getId());
                            result.setMessage("模板id不存在");
                            return result;
                     }
              }
              return result;
       }


}