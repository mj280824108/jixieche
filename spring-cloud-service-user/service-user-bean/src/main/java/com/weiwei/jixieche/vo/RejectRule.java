package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhouw
 * @date 2019年5月22日 下午5:55:35
 * @description
 */
@Getter
@Setter
@ApiModel("拒绝规则")
public class RejectRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 439010456694752152L;

	@ApiModelProperty("服务器时间戳")
	private long timestamp;

	@ApiModelProperty("规则，当值为-1时，不校验直接跳转到拒绝原因填写页面")
	private int condition;
}
