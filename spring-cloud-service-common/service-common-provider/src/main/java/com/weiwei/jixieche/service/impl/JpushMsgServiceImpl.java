package com.weiwei.jixieche.service.impl;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.weiwei.jixieche.bean.JxcPushRecord;
import com.weiwei.jixieche.config.JpushConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcPushRecordMapper;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JpushMsgService;
import com.weiwei.jixieche.utils.JpushUtils;
import com.weiwei.jixieche.utils.PushTempateUtils;
import com.weiwei.jixieche.utils.TemplateCacheUtils;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.JpushCustomMsgVo;
import com.weiwei.jixieche.vo.PushTemplateVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JpushMsgService
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 17:52
 * @Version 1.0.1
 **/
@Service
@Slf4j
public class JpushMsgServiceImpl implements JpushMsgService {

	private static Logger logger = LoggerFactory.getLogger(JpushMsgServiceImpl.class);

	@Resource
	private JpushUtils jpushUtils;

	@Resource
	private JpushConfig jpushConfig;

	@Resource
	private JxcPushRecordMapper jxcPushRecordMapper;

	@Autowired
	private TemplateCacheUtils templateCacheUtils;

	@Autowired
	private PushTempateUtils pushTempateUtils;

	@Override
	public ResponseMessage jpushNotice(JpushTemplateVo jpushTemplateVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		System.out.println("form-----------------" + jpushTemplateVo.getParams());
		int appClient = jpushTemplateVo.getAppClient();

		if (appClient != 0 && appClient != 1 && appClient != 2) {
			return new ResponseMessage(-1, "app接收方编号不正确");
		}
		try {
			jpushUtils.checkAppKeyAndMasterSecret(appClient);
		} catch (Exception e) {
			return new ResponseMessage(-1, "极光推送配置异常");
		}
		if (!jpushTemplateVo.isSendToAll() && VerifyStr.isEmpty(jpushTemplateVo.getAliases())) {
			return new ResponseMessage(-1, "指定别名推送模式下别名数组不能为空");
		}

		PushTemplateVo pushTemplate = pushTempateUtils.getPushTemplateMap().get(jpushTemplateVo.getTemplateCode());
		if (pushTemplate == null) {
			return new ResponseMessage(-1, "无此模版");
		}
		String notificationBody = pushTemplate.getTemplate();
		try{
			for (String field : pushTemplate.getFields()) {
				log.info("field"+field);
				if (field != null && jpushTemplateVo.getParams() != null) {
					notificationBody = notificationBody.replaceAll("\\$\\(" + field + "\\)", jpushTemplateVo.getParams().getString(field));
				}
			}}catch ( Exception e){
			log.error("短信模板异常{}",e);

		}
		log.info("notificationBody+++++++++++++++++++++++" + notificationBody);
		JSONObject jsonBody = JSON.parseObject(notificationBody);
		Notification.Builder notificationBuilder = Notification.newBuilder();
		Notification notification = null;
		AndroidNotification.Builder androidNotificationBuilder = AndroidNotification.newBuilder();
		IosNotification.Builder iosNotificationBuilder = IosNotification.newBuilder();
		androidNotificationBuilder.setTitle("");
		iosNotificationBuilder.incrBadge(1);
		androidNotificationBuilder.setAlert(jsonBody.getString("content"));
		iosNotificationBuilder.setAlert(jsonBody.getString("content"));
		JsonObject js2 = new JsonObject();
		if (jsonBody.getJSONObject("extraParams") != null && jsonBody.getJSONObject("extraParams").size() > 0) {
			for (Map.Entry<String, Object> entry : jsonBody.getJSONObject("extraParams").entrySet()) {
				js2.addProperty(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		androidNotificationBuilder.addExtra("data", js2);//安卓添加额外参数
		iosNotificationBuilder.addExtra("data", js2);//ios添加额外参数
		androidNotificationBuilder.addExtra("pushType", jpushTemplateVo.getTemplateCode());
		iosNotificationBuilder.addExtra("pushType", jpushTemplateVo.getTemplateCode());
		notificationBuilder.addPlatformNotification(androidNotificationBuilder.build()).addPlatformNotification(iosNotificationBuilder.build());
		notification = notificationBuilder.build();
		Options options = Options.newBuilder().setApnsProduction(jpushConfig.getFlag()).build();
		PushPayload.Builder pushPayloadBuilder = jpushUtils.initPushPayloadBuilder(notification, null, options);
		//PushPayload pushPayload = null;
		if (jpushTemplateVo.isSendToAll() == true) {
			pushPayloadBuilder.setAudience(Audience.all());
			try {
				jpushUtils.sendWithClient(jpushTemplateVo.getAppClient(), pushPayloadBuilder);
			} catch (Exception e) {
				return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(), e.getMessage());
			}
		} else {
			String[] aliasArr = jpushTemplateVo.getAliases().split(",");
			List<String> aliasArrCut = new ArrayList<>();
			int aliasTotalCount = aliasArr.length;
			for (int i = 0; i < aliasArr.length; i++) {
				aliasArrCut.add(aliasArr[i]);
				if (aliasArrCut.size() == 200 || i == aliasTotalCount - 1) {
					pushPayloadBuilder.setAudience(Audience.alias(aliasArrCut));
					try {
						jpushUtils.sendWithClient(jpushTemplateVo.getAppClient(), pushPayloadBuilder);
					} catch (Exception e) {
						return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(), e.getMessage());
					}
					aliasArrCut.clear();
				}
			}
			pushPayloadBuilder.setAudience(Audience.alias(jpushTemplateVo.getAliases().split(",")));
		}
		return result;
	}

	//推送自定义消息
	@Override
	public ResponseMessage jpushMessage(JpushMessageVo jpushMessageVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int appClient = jpushMessageVo.getAppClient();
		if(appClient != 0 && appClient != 1 && appClient != 2 ){
			return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(),"app接收方编号不正确");
		}
		try {
			jpushUtils.checkAppKeyAndMasterSecret(appClient);
		} catch (Exception e) {
			return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(), "极光推送配置异常");
		}

		if (!jpushMessageVo.isSendToAll() && VerifyStr.isEmpty(jpushMessageVo.getAliases())) {
			return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(), "指定别名推送模式下别名数组不能为空");
		}

		if (jpushMessageVo.getTimeToLive() < 0 || jpushMessageVo.getTimeToLive() > 864000) {
			return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(), "离线保存时长格式不正确");
		}

		Message message = null;
		Options options = null;
		//如果有消息参数
		if (!VerifyStr.isEmpty(jpushMessageVo.getMessageContent())) {
			Message.Builder messageBuilder = Message.newBuilder();

			if (!VerifyStr.isEmpty(jpushMessageVo.getMessageTitle())) {
				messageBuilder.setTitle(jpushMessageVo.getMessageTitle());
			}

			if (!VerifyStr.isEmpty(jpushMessageVo.getMessageContent())) {
				messageBuilder.setMsgContent(jpushMessageVo.getMessageContent());
			}

			if (jpushMessageVo.getMessageExtraParams() != null && jpushMessageVo.getMessageExtraParams().size() > 0) {
				for (Map.Entry<String, Object> entry : jpushMessageVo.getMessageExtraParams().entrySet()) {
					messageBuilder.addExtra(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
			message = messageBuilder.build();
		}
		if (message == null) {
			return new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_EMPTY.getId(), "消息相关参数不为空");
		}
		options = Options.newBuilder().setTimeToLive(jpushMessageVo.getTimeToLive()).setApnsProduction(jpushConfig.getFlag()).build();
		PushPayload.Builder pushPayloadBuilder = jpushUtils.initPushPayloadBuilder(null, message, options);
		//判断是否全体发送
		if (jpushMessageVo.isSendToAll() == true) {
			pushPayloadBuilder.setAudience(Audience.all());
			try {
				jpushUtils.sendWithClient(jpushMessageVo.getAppClient(),pushPayloadBuilder);
			}catch (Exception e){
				return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(),e.getMessage());
			}
		} else {
			//根据别名发送
			String[] aliasArr = jpushMessageVo.getAliases().split(",");
			List<String> aliasArrCut = new ArrayList<>();
			int aliasTotalCount = aliasArr.length;
			for (int i = 0; i < aliasArr.length; i++) {
				aliasArrCut.add(aliasArr[i]);
				if (aliasArrCut.size() == 200 || i == aliasTotalCount - 1) {
					pushPayloadBuilder.setAudience(Audience.alias(aliasArrCut));
					try {
						jpushUtils.sendWithClient(jpushMessageVo.getAppClient(),pushPayloadBuilder);
					}catch (Exception e){
						return new ResponseMessage(ErrorCodeConstants.JPUSH_CONFIG_ERROR.getId(),e.getMessage());
					}
					aliasArrCut.clear();
				}
			}
			pushPayloadBuilder.setAudience(Audience.alias(jpushMessageVo.getAliases().split(",")));
		}
		return result;
	}

	//极光推送站内信
	@Override
	public ResponseMessage jpushCustomMsg(JpushCustomMsgVo jpushCustomMsgVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		try {
			Integer userId = jpushCustomMsgVo.getUserId();
			int serviceId = jpushCustomMsgVo.getServiceCode();
			PushTemplateVo pushTemplateVo = PushTempateUtils.pushTemplateVoMap.get(serviceId);
			int pid = pushTemplateVo.getPid();
			String msgBody = pushTemplateVo.getTemplate();
			//添加站内信记录
			JxcPushRecord jxcPushRecord = new JxcPushRecord();
			jxcPushRecord.setUserId(userId);
			jxcPushRecord.setTemplatePid(pid);
			jxcPushRecord.setTemplateId(serviceId);
			//替换模版上的字段
			for(String field:pushTemplateVo.getFields()){
				msgBody = msgBody.replaceAll("\\$\\(" + field + "\\)",jpushCustomMsgVo.getParam().getString(field));
			}
			jxcPushRecord.setContent(msgBody);
			this.jxcPushRecordMapper.insertSelective(jxcPushRecord);
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseMessage<>(ErrorCodeConstants.PUSH_CUSTOM_ERROR.getId(),ErrorCodeConstants.PUSH_CUSTOM_ERROR.getDescript());
		}
		return result;
	}


}
