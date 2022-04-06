package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcMachineRemindRecord;
import com.weiwei.jixieche.vo.JxcMachineRemindListVo;
import com.weiwei.jixieche.vo.JxcMachineRemindVo;
import com.weiwei.jixieche.vo.MachinRemindDeleteBatchVo;
import com.weiwei.jixieche.vo.MachineNotExisitRemindVo;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
public interface JxcMachineRemindRecordMapper extends BaseMapper<JxcMachineRemindRecord> {

	/**
	 * 分页查询机主车辆提醒记录状态
	 * @param jxcMachineRemindListVo
	 * @return
	 */
	List<JxcMachineRemindListVo> findMachineRemindStatusList(JxcMachineRemindListVo jxcMachineRemindListVo);

	/**
	 * 根据车牌号和机主账号查询车辆提醒记录id
	 */
	List<JxcMachineRemindVo> findMachineRemindStatus(JxcMachineRemindVo jxcMachineRemindVo);

	/**
	 * 机主批量删除机械提醒记录
	 * @param machinRemindDeleteBatchVo
	 */
	void deleteBatch(MachinRemindDeleteBatchVo machinRemindDeleteBatchVo);

	/**
	 * 批量添加机械提醒记录
	 * @param map
	 */
	void insertBatch(@Param("map") Map<String,Object> map);

	/**
	 * 根据机主账号和车辆Id查询该车辆已经存在的提醒类型id
	 * @param machineNotExisitRemindVo
	 * @return
	 */
	List<MachineNotExisitRemindVo> findNotExistRemindList(MachineNotExisitRemindVo machineNotExisitRemindVo);
}