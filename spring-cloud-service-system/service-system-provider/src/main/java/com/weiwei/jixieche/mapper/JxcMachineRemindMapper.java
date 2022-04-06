package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMachineRemind;
import com.weiwei.jixieche.vo.MachinRemindDeleteBatchVo;
import com.weiwei.jixieche.vo.MachineMustRemindListVo;

import java.util.List;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcMachineRemindMapper extends BaseMapper<JxcMachineRemind> {

	/**
	 *查询机械已经存在的必填提醒记录
	 * @param machineMustRemindListVo
	 * @return
	 */
	List<MachineMustRemindListVo> queryMustRemindList(MachineMustRemindListVo machineMustRemindListVo);

	/**
	 * 查询机械已经存在的非必填提醒记录
	 * @param machineMustRemindListVo
	 * @return
	 */
	List<MachineMustRemindListVo> queryExistNotMustRemindList(MachineMustRemindListVo machineMustRemindListVo);



}