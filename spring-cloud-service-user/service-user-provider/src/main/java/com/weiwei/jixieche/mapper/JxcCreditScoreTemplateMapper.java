package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcCreditScoreTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName ss
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcCreditScoreTemplateMapper extends BaseMapper<JxcCreditScoreTemplate> {

	/**
	 * 查询有效的积分模板
	 *
	 * @param id
	 * @return
	 */
	JxcCreditScoreTemplate getEffectiveById(@Param("id") Integer id);
}