package com.weiwei.jixieche.service;

import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.AppVersionFormVo;

/**
 * @Author 钟焕 
 * @Description
 * @Date 14:14 2019-10-14
 **/
public interface AppVersionService {

	/**
	 * IOS检查版本更新
	 * @param formVo
	 * @return
	 */
	ResponseMessage checkIosVersion(AppVersionFormVo formVo);

	/**
	 * IOS检查版本更新
	 * @param formVo
	 * @return
	 */
	ResponseMessage checkAndroidVersion(AppVersionFormVo formVo);
	
}
