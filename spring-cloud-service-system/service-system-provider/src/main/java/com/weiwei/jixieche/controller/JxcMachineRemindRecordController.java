package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcMachineRemindRecord;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMachineRemindRecordService;
import com.weiwei.jixieche.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName UserRegisterVo
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Api(tags = "车辆提醒记录表")
@RestController
@RequestMapping("jxcMachineRemindRecord")
public class JxcMachineRemindRecordController {
	@Resource
	private JxcMachineRemindRecordService jxcMachineRemindRecordService;

	@ApiOperation(httpMethod = "POST", value = "分页查询机主车辆提醒记录状态")
	@PostMapping("/findMachineRemindStatusList")
	public ResponseMessage<JxcMachineRemindListVo> findMachineRemindStatusList(@RequestBody JxcMachineRemindListVo jxcMachineRemindListVo) {
		return this.jxcMachineRemindRecordService.findMachineRemindStatusList(jxcMachineRemindListVo.getPageNo(), jxcMachineRemindListVo.getPageSize(), jxcMachineRemindListVo);
	}

	@ApiOperation(httpMethod = "POST", value = "查询机主已存在的车辆提醒记录状态")
	@PostMapping("/findExistRemindStatusList")
	public ResponseMessage<MachineRemindRecodVo> findExistRemindStatusList(@RequestBody MachineRemindRecodVo machineRemindRecodVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		result.setData(this.jxcMachineRemindRecordService.findExistRemindStatusList(machineRemindRecodVo));
		return result;
	}

	@ApiOperation(httpMethod = "POST", value = "机主批量添加机械提醒记录")
	@PostMapping("/insertBatch")
	public ResponseMessage insertBatch(@RequestBody MachinRemindInsertBatchVo machinRemindInsertBatchVo) {
		return this.jxcMachineRemindRecordService.insertBatch(machinRemindInsertBatchVo);
	}

	@ApiOperation(httpMethod = "POST", value = "机主批量删除机械提醒记录")
	@PostMapping("/deleteBatch")
	public ResponseMessage deleteBatch(@RequestBody MachinRemindDeleteBatchVo machineNotExisitRemindVo) {
		return this.jxcMachineRemindRecordService.deleteBatch(machineNotExisitRemindVo);
	}

	@ApiOperation(httpMethod = "POST", value = "查询机主未添加的车辆提醒类型")
	@PostMapping("/findNotExistRemindList")
	public ResponseMessage<MachineNotExisitRemindVo> findNotExistRemindList(@RequestBody MachineNotExisitRemindVo machineNotExisitRemindVo) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		List<MachineNotExisitRemindVo> list = this.jxcMachineRemindRecordService.findNotExistRemindList(machineNotExisitRemindVo);
		if (list != null && list.size() > 0) {
			result.setData(list);
		} else {
			result.setCode(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
			result.setMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript());
		}
		return result;
	}

	@ApiOperation(httpMethod = "POST", value = "添加车辆提醒记录表")
	@PostMapping("/insertRecord")
	public ResponseMessage insertRecord(@RequestBody JxcMachineRemindRecord jxcMachineRemindRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcMachineRemindRecordService.addObjRecord(jxcMachineRemindRecord);
		if (res > 0) {
			return result;
		} else {
			result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
			result.setMessage(ErrorCodeConstants.ADD_ERORR.getDescript());
			return result;
		}
	}

	@ApiOperation(httpMethod="POST", value="编辑车辆提醒记录表")
	@PostMapping("/editRecord")
	public ResponseMessage editRecord(@RequestBody JxcMachineRemindRecord jxcMachineRemindRecord) {
		ResponseMessage result = ResponseMessageFactory.newInstance();
		int res = this.jxcMachineRemindRecordService.modifyObjRecord(jxcMachineRemindRecord);
		if (res > 0) {
			return result;
		} else {
			result.setCode(ErrorCodeConstants.EDIT_ERORR.getId());
			result.setMessage(ErrorCodeConstants.EDIT_ERORR.getDescript());
			return result;
		}
	}

}