package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouw
 * @date 2019年5月15日 下午4:23:38
 * @description
 */
@Getter
@Setter
public class AppVersionFormVo {

	@ApiModelProperty(value = "版本号", required = true)
	private String version;

	@ApiModelProperty(value = "客户端类型。1：机主，2：承租方", required = true)
	private int appClient;

	@ApiModelProperty(value = "0：正式版，1：测试版", required = true)
	private int isTest;
}
