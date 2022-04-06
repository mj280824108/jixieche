package com.weiwei.jixieche.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gavin
 * @date 2019-05-29 14:43
 */
@Data
public class SendShortMsgResult {
	private int code;
	private String message;
	public static Map<Integer, String> codeMsgContrastMap = new HashMap<>();

	static {
		codeMsgContrastMap.put(-1, "短信服务调用失败");
		codeMsgContrastMap.put(1, "手机号参数异常");
		codeMsgContrastMap.put(2, "短信类型参数异常");
		codeMsgContrastMap.put(3, "客户端类型参数异常");
		codeMsgContrastMap.put(4, "请求频率过高");
		codeMsgContrastMap.put(5, "超出每日发送限额");
		codeMsgContrastMap.put(6, "调用短信服务异常");
	}

	public SendShortMsgResult(int code) {
		this.code = code;
		this.message = codeMsgContrastMap.get(code);
	}

	public SendShortMsgResult() {
		this.code = 0;
		this.message = "短信发送成功";
	}


	public interface SendMsgCode {
		Integer SERVICE_ERROR = -1;
		Integer PHONE_PARAM＿ERROR = 1;
		Integer MSGTYPE_PARAM_ERROR = 2;
		Integer CLINET_TYPE_PARAM_ERROR = 3;
		Integer OUTOF_FREQUENCY_ERROR = 4;
		Integer OUTOF_DAILY_LIMIT = 5;
		Integer MSG_SERVER_ERROR = 6;
	}

}