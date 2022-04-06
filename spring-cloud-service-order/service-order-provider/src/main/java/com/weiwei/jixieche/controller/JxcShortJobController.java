package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.service.JxcShortJobService;
import com.weiwei.jixieche.vo.DriverJobVo;
import com.weiwei.jixieche.vo.JxcShortJobDriverVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Driver;

/**
 * @Description: 短期职位招聘
 * @Author: liuy
 * @Date: 2019/8/15 15:04
 */
@Api(tags = "短期职位招聘")
@RestController
@RequestMapping("jxcShortJob")
public class JxcShortJobController {

	@Resource
	private JxcShortJobService jxcShortJobService;

	/**
	 * 短期职位招聘表
	 *
	 * @param driverJobVo 机主用户ID
	 * @return
	 * @author liuy
	 * @date 2019/8/15 15:04
	 */
	@PostMapping("/getShortJobList")
	public ResponseMessage<JxcShortJob> getShortJobList(AuthorizationUser authorizationUser, @RequestBody(required = false) DriverJobVo driverJobVo) {
		if (null == driverJobVo) {
			driverJobVo = new DriverJobVo();
		}
		//司机角色时不需要传机主用户ID
		if(authorizationUser.getRoleId() == 2) {
			driverJobVo.setOwnerUserId(authorizationUser.getUserId());
		}else{
			driverJobVo.setDriverId(authorizationUser.getUserId());
		}
		return jxcShortJobService.getShortJobList(authorizationUser, driverJobVo);
	}

	/**
	 * 添加短期职位招聘表
	 *
	 * @param authorizationUser 机主用户ID
	 * @param jxcShortJob       短期职位信息
	 * @return
	 * @author liuy
	 */
	@PostMapping("/insert")
	public ResponseMessage<JxcShortJob> insert(AuthorizationUser authorizationUser, @RequestBody JxcShortJob jxcShortJob) {
		jxcShortJob.setOwnerId(authorizationUser.getUserId());
		return this.jxcShortJobService.insert(jxcShortJob);
	}

	/**
	 * 编辑短期职位招聘
	 *
	 * @param jxcShortJob 短期职位招聘信息
	 * @return
	 * @author liuy
	 */
	@PostMapping("/edit")
	public ResponseMessage<JxcShortJob> edit(@RequestBody JxcShortJob jxcShortJob) {
		return this.jxcShortJobService.update(jxcShortJob);
	}

	/**
	 * 通过ID查询短期职位招聘
	 *
	 * @param id
	 * @return
	 * @author liuy
	 */
	@PostMapping("/getById")
	public ResponseMessage<JxcShortJob> getById(Integer id) {
		return this.jxcShortJobService.getById(id);
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 * @author liuy
	 */
	@PostMapping("/deleteById")
	public ResponseMessage<JxcShortJob> deleteById(Integer id) {
		return this.jxcShortJobService.deleteById(id);
	}

	@ApiOperation("查询司机台班费用")
	@GetMapping("/queryDriverTeamCost")
	public ResponseMessage queryDriverTeamCost(@RequestParam("cityId") Integer cityId) {
		return this.jxcShortJobService.queryDriverTeamCost(cityId);
	}

	/**
	 * 关闭职位
	 *
	 * @param shortJobId
	 * @return
	 * @author liuy
	 * @date 2019/8/28 11:36
	 */
	@ApiOperation("关闭职位")
	@PostMapping("/closeShortJob")
	public ResponseMessage closeShortJob(@RequestParam("shortJobId") Integer shortJobId) {
		return this.jxcShortJobService.closeShortJob(shortJobId);
	}

	/**
	 * 接单职位司机列表
	 * @author  liuy
	 * @param jxcShortJobDriverVo
	 * @return
	 * @date    2019/8/30 14:01
	 */
	@ApiOperation("接单职位司机列表")
	@PostMapping("/getShortDriverList")
	public ResponseMessage getShortDriverList(AuthorizationUser user, @RequestBody JxcShortJobDriverVo jxcShortJobDriverVo){
		return this.jxcShortJobService.getShortDriverList(user, jxcShortJobDriverVo.getShortJobId(),jxcShortJobDriverVo.getPageNo(), jxcShortJobDriverVo.getPageSize());
	}

	/**
	 *  接单职位司机-详情
	 * @author  liuy
	 * @param user
	 * @return
	 * @date    2019/8/30 14:01
	 */
	@ApiOperation(" 接单职位司机-详情")
	@PostMapping("/getShortDriverDetail")
	public ResponseMessage getShortDriverDetail(AuthorizationUser user, @RequestParam("driverId")Integer driverId, @RequestParam("shortJobId")Integer shortJobId){
		return this.jxcShortJobService.getShortDriverDetail(user, driverId, shortJobId);
	}

}