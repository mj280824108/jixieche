package com.weiwei.jixieche.rabbit;

/**
 * @author Gavin
 * @date 2019-06-21 15:34
 */
public interface QueueKey {
	/**
	 * 消息推送的队列名称
	 */
	public static final String JPUSH_QUEUE = "JPUSH_QUEUE";

	/**
	 * 短信发送额队列名称
	 */
	public static final String SHORT_MSG_QUEUE = "MESSAGE_QUEUE";


	/**
	 * 百度鹰眼注册队名称
	 */
	public static final String REGISTER_EAGLE_EYES_QUEUE = "REGISTER_EAGLE_EYES_QUEUE";

}



