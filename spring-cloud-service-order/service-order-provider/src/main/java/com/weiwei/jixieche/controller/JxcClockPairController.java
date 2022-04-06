package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcClockPairService;
import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "司机每一天的打卡记录与司机配对关联表")
@RestController
@RequestMapping("jxcClockPair")
public class JxcClockPairController {
	@Resource
	private JxcClockPairService jxcClockPairService;

	@ApiOperation(httpMethod = "POST", value = "前端分页查询司机每一天的打卡记录与司机配对关联表")
	@PostMapping("/front/findByPage")
	public ResponseMessage<JxcClockPair> findByPageForFront(@RequestBody JxcClockPair jxcClockPair) {
		return this.jxcClockPairService.findByPageForFront(jxcClockPair.getPageNo(), jxcClockPair.getPageSize(), jxcClockPair);
	}

	@ApiOperation(httpMethod = "POST", value = "添加司机每一天的打卡记录与司机配对关联表")
	@PostMapping("/insert")
	public ResponseMessage<JxcClockPair> insert(@RequestBody JxcClockPair jxcClockPair) {
		return this.jxcClockPairService.addObj(jxcClockPair);
	}

	@ApiOperation(httpMethod = "POST", value = "编辑司机每一天的打卡记录与司机配对关联表")
	@PostMapping("/edit")
	public ResponseMessage<JxcClockPair> edit(@RequestBody JxcClockPair jxcClockPair) {
		return this.jxcClockPairService.modifyObj(jxcClockPair);
	}

	@ApiOperation(httpMethod = "GET", value = "通过ID查询司机每一天的打卡记录与司机配对关联表")
	@GetMapping("/queryById")
	public ResponseMessage<JxcClockPair> queryById(Integer id) {
		return this.jxcClockPairService.queryObjById(id);
	}

	@ApiOperation(httpMethod = "POST", value = "批量更新台班支付状态")
	@PostMapping("/updateBatchClockPayStatus")
	public void updateBatchClockPayStatus(@RequestBody UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo) {
		this.jxcClockPairService.updateBatchClockPayStatus(updateBatchClockPayStatusVo);
	}

	@ApiOperation(httpMethod = "POST", value = "更新台班支付状态")
	@PostMapping("/updateClockPayStatus")
	public void updateClockPayStatus(@RequestParam("clockId") Long clockId) {
		this.jxcClockPairService.updateClockPayStatus(clockId);
	}

	/**
	 * 台班异常申诉
	 *
	 * @param clockId
	 * @param description 申诉原因
	 * @return
	 */
	@PostMapping("/updateClockApplyStateById")
	public ResponseMessage updateClockApplyStateById(AuthorizationUser user, @RequestParam("clockId") Long clockId, @RequestParam("description") String description){
		return this.jxcClockPairService.updateClockApplyStateById(clockId, description);
	}

}