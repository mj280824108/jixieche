package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcExceptionAppeal;

/**
* @Description: 异常申诉信息
* @Author:      liuy
* @Date:  2019/10/9 9:18
*/
public interface JxcExceptionAppealMapper extends BaseMapper<JxcExceptionAppeal> {

	/**
	 * 异常申诉信息
	 * @param clockId
	 * @return
	 */
	JxcExceptionAppeal getById(Long clockId);
}