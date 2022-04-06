package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMachineRemind;
import com.weiwei.jixieche.bean.JxcMachineRemindRecord;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.JxcMachineRemindConstants;
import com.weiwei.jixieche.mapper.JxcMachineRemindMapper;
import com.weiwei.jixieche.mapper.JxcMachineRemindRecordMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMachineRemindRecordService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Service("jxcMachineRemindRecordService")
public class JxcMachineRemindRecordServiceImpl implements JxcMachineRemindRecordService {
	@Resource
	private JxcMachineRemindRecordMapper jxcMachineRemindRecordMapper;

	@Resource
	private JxcMachineRemindMapper jxcMachineRemindMapper;

	/**
	 * 前端分页查询车辆提醒记录表
	 *
	 * @param pageNo                 分页索引
	 * @param pageSize               每页显示数量
	 * @param jxcMachineRemindRecord 查询条件
	 * @return
	 */
	@Override
	public ResponseMessage<JxcMachineRemindRecord> findByPageForFront(Integer pageNo, Integer pageSize, JxcMachineRemindRecord jxcMachineRemindRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(pageNo, pageSize);
		List<JxcMachineRemindRecord> list = this.jxcMachineRemindRecordMapper.selectListByConditions(jxcMachineRemindRecord);
		PageInfo<JxcMachineRemindRecord> page = new PageInfo<>(list);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	/**
	 * 添加车辆提醒记录表
	 *
	 * @param jxcMachineRemindRecord
	 * @return
	 */
	@Override
	public ResponseMessage<JxcMachineRemindRecord> addObj(JxcMachineRemindRecord jxcMachineRemindRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcMachineRemindRecordMapper.insertSelective(jxcMachineRemindRecord);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
		return result;
	}

	/**
	 * 修改车辆提醒记录表
	 *
	 * @param jxcMachineRemindRecord
	 * @return
	 */
	@Override
	public ResponseMessage<JxcMachineRemindRecord> modifyObj(JxcMachineRemindRecord jxcMachineRemindRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcMachineRemindRecordMapper.updateByPrimaryKeySelective(jxcMachineRemindRecord);
		AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
		return result;
	}

	/**
	 * 根据ID查询车辆提醒记录表
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ResponseMessage<JxcMachineRemindRecord> queryObjById(int id) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		JxcMachineRemindRecord model = this.jxcMachineRemindRecordMapper.selectByPrimaryKey(id);
		AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
		result.setData(model);
		return result;
	}

	//分页查询机主车辆提醒记录状态
	@Override
	public ResponseMessage findMachineRemindStatusList(Integer pageNo, Integer pageSize, JxcMachineRemindListVo jxcMachineRemindListVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		PageHelper.startPage(pageNo, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//查询机主所有的车辆信息
		List<JxcMachineRemindListVo> listVos = this.jxcMachineRemindRecordMapper.findMachineRemindStatusList(jxcMachineRemindListVo);
		if (listVos != null && listVos.size() > 0) {
			listVos.forEach(remind -> {
				JxcMachineRemindVo jxcMachineRemindVo = new JxcMachineRemindVo();
				jxcMachineRemindVo.setOwnerId(remind.getOwnerId());
				jxcMachineRemindVo.setMachineId(remind.getMachineId());
				jxcMachineRemindVo.setMachineRemindId(JxcMachineRemindConstants.REMIND_INSPECTION.getId());
				List<JxcMachineRemindVo> remindList = this.jxcMachineRemindRecordMapper.findMachineRemindStatus(jxcMachineRemindVo);
				if (!remindList.isEmpty() && remindList.size() > 0) {
					JxcMachineRemindVo insRemind = remindList.get(0);
					if (insRemind.getId() != null && insRemind.getId() > 0) {
						remind.setInspectionStatus(JxcMachineRemindListVo.Status.SETTING);
						try {
							if (insRemind.getMachineRemindTime() == null) {
								remind.setInspectionStatus(JxcMachineRemindListVo.Status.NOT_SETTING);
							} else {
								//设置提醒时间小于当前时间，这提醒过期
								if (DateUtils.diff(sdf.parse(insRemind.getMachineRemindTime()), sdf.parse(sdf.format(new Date())))) {
									remind.setInspectionStatus(JxcMachineRemindListVo.Status.OVER_TIME);
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				jxcMachineRemindVo.setMachineRemindId(JxcMachineRemindConstants.REMIND_SAFE.getId());
				remindList = this.jxcMachineRemindRecordMapper.findMachineRemindStatus(jxcMachineRemindVo);
				if (!remindList.isEmpty() && remindList.size() > 0) {
					JxcMachineRemindVo safeRemind = remindList.get(0);
					if (safeRemind.getId() != null && safeRemind.getId() > 0) {
						remind.setSafeStatus(JxcMachineRemindListVo.Status.SETTING);
						try {
							if (safeRemind.getMachineRemindTime() == null) {
								remind.setSafeStatus(JxcMachineRemindListVo.Status.NOT_SETTING);
							} else {
								if (DateUtils.diff(sdf.parse(safeRemind.getMachineRemindTime()), sdf.parse(sdf.format(new Date())))) {
									remind.setSafeStatus(JxcMachineRemindListVo.Status.OVER_TIME);
								}
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				jxcMachineRemindVo.setMachineRemindId(JxcMachineRemindConstants.REMIND_CARE.getId());
				remindList = this.jxcMachineRemindRecordMapper.findMachineRemindStatus(jxcMachineRemindVo);
				if (!remindList.isEmpty() && remindList.size() > 0) {
					JxcMachineRemindVo careRemind = remindList.get(0);
					if (careRemind.getId() != null && careRemind.getId() > 0) {
						remind.setCareStatus(JxcMachineRemindListVo.Status.SETTING);
						try {
							if (careRemind.getMachineRemindTime() == null) {
								remind.setCareStatus(JxcMachineRemindListVo.Status.NOT_SETTING);
							} else {
								if (DateUtils.diff(sdf.parse(careRemind.getMachineRemindTime()), sdf.parse(sdf.format(new Date())))) {
									remind.setCareStatus(JxcMachineRemindListVo.Status.OVER_TIME);
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		}
		PageInfo<JxcMachineRemindListVo> page = new PageInfo<>(listVos);
		result.setData(new PageUtils<>(page).getPageViewDatatable());
		return result;
	}

	//查询机主已存在的车辆提醒记录状态
	@Override
	public MachineRemindRecodVo findExistRemindStatusList(MachineRemindRecodVo machineRemindRecodVo) {
		MachineMustRemindListVo mustRemindVo = new MachineMustRemindListVo();
		mustRemindVo.setRemindType(JxcMachineRemind.remindType.MUST);
		List<MachineMustRemindListVo> mustVoList = this.jxcMachineRemindMapper.queryMustRemindList(mustRemindVo);
		mustVoList.forEach(remind -> {
			JxcMachineRemindRecord jxcMachineRemindRecord = new JxcMachineRemindRecord();
			jxcMachineRemindRecord.setMachineRemindId(remind.getId());
			jxcMachineRemindRecord.setMachineId(machineRemindRecodVo.getMachineId());
			jxcMachineRemindRecord.setDeleted(JxcMachineRemindRecord.Delete.NOT_DELETE);
			List<JxcMachineRemindRecord> records = this.jxcMachineRemindRecordMapper.selectListByConditions(jxcMachineRemindRecord);
			if (!records.isEmpty() && records.size() > 0) {
				remind.setMachineRemindId(records.get(0).getMachineRemindId());
				remind.setMachineId(records.get(0).getMachineId());
				remind.setMachineRemindTime(records.get(0).getMachineRemindTime());
				remind.setMachineCardNo(records.get(0).getMachineCardNo());
				remind.setOwnerId(records.get(0).getOwnerId());
			}
		});
		machineRemindRecodVo.setMachineMustRemindList(mustVoList);
		//查询其他提醒已经存在的
		mustRemindVo.setMachineId(machineRemindRecodVo.getMachineId());
		List<MachineMustRemindListVo> otherVoList = this.jxcMachineRemindMapper.queryExistNotMustRemindList(mustRemindVo);
		machineRemindRecodVo.setMachineOtherRemindList(otherVoList);
		return machineRemindRecodVo;
	}

	//批量添加车辆提醒记录
	@Override
	@Transactional
	public ResponseMessage insertBatch(MachinRemindInsertBatchVo insertBatchVoList) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		//当批量添加时候，原来机械的所有记录则软删除状态
		MachinRemindDeleteBatchVo deleteBatchVo = new MachinRemindDeleteBatchVo();
		deleteBatchVo.setMachineId(insertBatchVoList.getMachineId());
		deleteBatchVo.setOwnerId(insertBatchVoList.getOwnerId());
		this.jxcMachineRemindRecordMapper.deleteBatch(deleteBatchVo);
		//批量添加
		Map<String, Object> map = new HashMap<>();
		map.put("machineId", insertBatchVoList.getMachineId());
		map.put("ownerId", insertBatchVoList.getOwnerId());
		map.put("itemList", insertBatchVoList.getBatchDataList());
		this.jxcMachineRemindRecordMapper.insertBatch(map);
		return result;
	}

	//机主批量删除机械提醒记录
	@Override
	public ResponseMessage deleteBatch(MachinRemindDeleteBatchVo machineNotExisitRemindVo) {
		//内部使用软删除
		ResponseMessage result = ResponseMessageFactory.newInstance();
		try {
			this.jxcMachineRemindRecordMapper.deleteBatch(machineNotExisitRemindVo);
		} catch (Exception ex) {
			result.setCode(ErrorCodeConstants.EDIT_ERORR.getId());
			result.setMessage("批量修改失败");
			ex.printStackTrace();
		}
		return result;
	}

	//查询机主未添加的车辆提醒类型
	@Override
	public List<MachineNotExisitRemindVo> findNotExistRemindList(MachineNotExisitRemindVo machineNotExisitRemindVo) {
		List<MachineNotExisitRemindVo> notExistRemindList = this.jxcMachineRemindRecordMapper.findNotExistRemindList(machineNotExisitRemindVo);
		return notExistRemindList;
	}

	/**
	 * 机主添加车辆提醒
	 *
	 * @param t
	 * @return
	 */
	@Override
	public int addObjRecord(JxcMachineRemindRecord t) {
		return this.jxcMachineRemindRecordMapper.insertSelective(t);
	}

	/**
	 * 机主修改车辆提醒
	 *
	 * @param t
	 * @return
	 */
	@Override
	public int modifyObjRecord(JxcMachineRemindRecord t) {
		return this.jxcMachineRemindRecordMapper.updateByPrimaryKeySelective(t);
	}
}