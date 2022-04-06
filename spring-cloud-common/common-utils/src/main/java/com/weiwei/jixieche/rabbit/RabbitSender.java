package com.weiwei.jixieche.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Gavin
 * @date 2019-06-21 17:03
 */
@Component
@Slf4j
public class RabbitSender {

	@Autowired
	RabbitTemplate rabbitTemplate;

	/**
	 * 发送短信
	 *
	 * @param jxcShortMsgVo
	 */
	public void sendShortMessage(JxcShortMsgVo jxcShortMsgVo) {
		try {
			byte[] bytes = ObjectUtils.ObjectFormByte(jxcShortMsgVo);
			rabbitTemplate.convertAndSend(QueueKey.SHORT_MSG_QUEUE, bytes);

		} catch (Exception e) {
			log.error("对象序列化失败{}", e);
		}
	}

	/***
	 * 发送推送信息
	 * @param jpushTemplateVo
	 */
	public void sendJpushMessage(JpushTemplateVo jpushTemplateVo) {
		try {
			byte[] bytes = ObjectUtils.ObjectFormByte(jpushTemplateVo);
			rabbitTemplate.convertAndSend(QueueKey.JPUSH_QUEUE, bytes);
			log.info("发送rabbitMq内容"+jpushTemplateVo);
		} catch (Exception e) {
			log.error("对象序列化失败{}", e);
		}
	}

	/**
	 * 百度鹰眼注册
	 * @param third
	 */
	public void sendEagleEyesRegister(String third) {
		rabbitTemplate.convertAndSend(QueueKey.REGISTER_EAGLE_EYES_QUEUE, third);
	}
}
