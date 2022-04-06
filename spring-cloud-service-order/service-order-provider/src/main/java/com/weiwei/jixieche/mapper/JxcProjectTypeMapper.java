package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcProjectType;

import java.util.List;

/**
* @Description: java类作用描述
* @Author:      liuy
* @Date:  2019/8/26 17:43
*/
public interface JxcProjectTypeMapper extends BaseMapper<JxcProjectType> {
	
	/**
	 * 方法实现说明
	 * @author  liuy
	* @param typeId
	 * @return  
	 * @date    2019/8/26 17:43
	 */
	List<String> getTypeNameList(String[] typeId);
}