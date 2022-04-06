package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcLongJob;
import com.weiwei.jixieche.vo.DriverJobVo;

import java.util.List;

/**
* @Description: 长期司机招聘
* @Author:      liuy
* @Date:  2019/8/15 10:12
*/
public interface JxcLongJobMapper extends BaseMapper<JxcLongJob> {

	/**
	 * 长期司机招聘列表
	 * @author  liuy
	 * @param driverJobVo 查询条件
	 * @return
	 * @date    2019/8/15 11:37
	 */
	List<JxcLongJob> getLongJobList(DriverJobVo driverJobVo);
	Integer hasNextPage(DriverJobVo driverJobVo);
}