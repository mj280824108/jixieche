package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcDriverResume;
import com.weiwei.jixieche.response.ResponseMessage;

/**
* @Description: 司机简历
* @Author:      liuy
* @Date:  2019/8/21 11:09
*/
public interface JxcDriverResumeService extends BaseService<JxcDriverResume> {

	/**
	 * 查看司机简历
	 * @author  liuy
	 * @param driverId
	 * @return
	 * @date    2019/8/21 10:56
	 */
	ResponseMessage getDriverResumeById(Integer driverId);

	/**
	 * 司机简历列表
	 * @author  liuy
	 * @param jxcDriverResume
	 * @return
	 * @date    2019/8/21 11:36
	 */
	ResponseMessage getDriverResumeList(JxcDriverResume jxcDriverResume);

	/**
	 * 刷新简历
	 * @author  liuy
	* @param resumeId
	 * @return
	 * @date    2019/8/29 9:48
	 */
	ResponseMessage refreshDriverResume(Integer resumeId);
}