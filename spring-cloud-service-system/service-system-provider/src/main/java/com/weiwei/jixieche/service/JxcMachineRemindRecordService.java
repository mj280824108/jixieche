package com.weiwei.jixieche.service;

import com.weiwei.jixieche.bean.JxcMachineRemindRecord;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.vo.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcMachineRemindRecordService extends BaseService<JxcMachineRemindRecord> {
	/**
	 * 前端分页查询车辆提醒记录表
	 *
	 * @param pageNo                 分页索引
	 * @param pageSize               每页显示数量
	 * @param jxcMachineRemindRecord 查询条件
	 * @return
	 */
	ResponseMessage findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRemindRecord jxcMachineRemindRecord);

	/**
	 * 分页查询机主车辆提醒记录状态
	 *
	 * @param jxcMachineRemindListVo
	 * @return
	 */
	ResponseMessage findMachineRemindStatusList(Integer pageNo, Integer pageSize, JxcMachineRemindListVo jxcMachineRemindListVo);

	/**
	 * 查询机主已存在的车辆提醒记录状态
	 *
	 * @param machineRemindRecodVo
	 * @return
	 */
	MachineRemindRecodVo findExistRemindStatusList(MachineRemindRecodVo machineRemindRecodVo);

	/**
	 * 批量添加车辆提醒记录
	 *
	 * @param insertBatchVoList
	 * @return
	 */
	ResponseMessage insertBatch(MachinRemindInsertBatchVo insertBatchVoList);

	/**
	 * 查询机主批量删除机械提醒记录
	 *
	 * @param machineNotExisitRemindVo
	 * @return
	 */
	ResponseMessage deleteBatch(MachinRemindDeleteBatchVo machineNotExisitRemindVo);

	/**
	 * 查询机主车辆不存在的提醒类型
	 *
	 * @param machineNotExisitRemindVo
	 * @return
	 */
	List<MachineNotExisitRemindVo> findNotExistRemindList(MachineNotExisitRemindVo machineNotExisitRemindVo);

	/**
	 * 机主添加车辆提醒
	 *
	 * @param t
	 * @return
	 */
	int addObjRecord(JxcMachineRemindRecord t);

	/**
	 * 机主修改车辆提醒
	 *
	 * @param t
	 * @return
	 */
	int modifyObjRecord(JxcMachineRemindRecord t);
}