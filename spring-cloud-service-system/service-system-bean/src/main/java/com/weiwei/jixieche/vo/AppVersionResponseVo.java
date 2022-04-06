package com.weiwei.jixieche.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouw
 * @date 2019年5月15日 下午5:29:35
 * @description
 */
@Getter
@Setter
@ApiModel(value = "检查App版本的结果")
public class AppVersionResponseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5744263851323900755L;

	@ApiModelProperty("是否需要更新。0：不需要，1：可选，2：强制")
	private int needUpdate;

	@ApiModelProperty("安卓：下载地址")
	private String downloadUrl;

	@ApiModelProperty("安卓：版本描述")
	private List<String> desc;

	@ApiModelProperty("IOS：版本描述")
	private String iosDesc;

	@ApiModelProperty("安卓：版本大小")
	private String apkSize;
}
