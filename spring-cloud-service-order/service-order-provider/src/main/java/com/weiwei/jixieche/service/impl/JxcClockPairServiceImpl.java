package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.bean.JxcDriverRefOwner;
import com.weiwei.jixieche.bean.JxcExceptionAppeal;
import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcClockPairMapper;
import com.weiwei.jixieche.mapper.JxcDriverRefOwnerMapper;
import com.weiwei.jixieche.mapper.JxcExceptionAppealMapper;
import com.weiwei.jixieche.mapper.JxcShortJobMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockPairService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.vo.UpdateBatchClockPayStatusVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jxcClockPairService")
public class JxcClockPairServiceImpl implements JxcClockPairService {
       @Resource
       private JxcClockPairMapper jxcClockPairMapper;

       @Resource
       private JxcDriverRefOwnerMapper jxcDriverRefOwnerMapper;

       @Resource
       private JxcExceptionAppealMapper jxcExceptionAppealMapper;

       @Resource
       private JxcShortJobMapper jxcShortJobMapper;

       /**
        * 前端分页查询司机每一天的打卡记录与司机配对关联表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcClockPair 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcClockPair> findByPageForFront(Integer pageNo, Integer pageSize, JxcClockPair jxcClockPair) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcClockPair> list = this.jxcClockPairMapper.selectListByConditions(jxcClockPair);
              PageInfo<JxcClockPair> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }


       /**
        * 添加司机每一天的打卡记录与司机配对关联表
        * @param  jxcClockPair
        * @return
        */
       @Override
       public ResponseMessage<JxcClockPair> addObj(JxcClockPair jxcClockPair) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcClockPairMapper.insertSelective(jxcClockPair);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改司机每一天的打卡记录与司机配对关联表
        * @param jxcClockPair
        * @return
        */
       @Override
       public ResponseMessage<JxcClockPair> modifyObj(JxcClockPair jxcClockPair) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcClockPairMapper.updateByPrimaryKeySelective(jxcClockPair);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询司机每一天的打卡记录与司机配对关联表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcClockPair> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcClockPair model=this.jxcClockPairMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 批量更新台班支付状态
        * @param updateBatchClockPayStatusVo
        */
       @Override
       public void updateBatchClockPayStatus(UpdateBatchClockPayStatusVo updateBatchClockPayStatusVo) {
              this.jxcClockPairMapper.updateBatchClockPayStatus(updateBatchClockPayStatusVo);
       }

       /**
        * 更新台班支付状态
        * @author  liuy
        * @param clockId
        * @return
        * @date    2019/10/7 15:55
        */
       @Override
       public void updateClockPayStatus(Long clockId) {
              if(clockId != null) {
                     //更新台班支付状态
                     int res = this.jxcClockPairMapper.updateClockPayStatus(clockId);
                     if(res > 0) {
                            //查询台班信息
                            JxcClockPair jxcClockPair = jxcClockPairMapper.getByClockId(clockId);
                            if (jxcClockPair != null && jxcClockPair.getShortJobId() != null && jxcClockPair.getDriverId() != null) {
	                               JxcShortJob jxcShortJob = jxcShortJobMapper.selectByPrimaryKey(jxcClockPair.getShortJobId());
	                               //当前时间大于职位结束时间时解除司机与机主关系
	                               if(jxcShortJob != null && jxcShortJob.getWorkTimeEnd() != null) {
	                               	   if(DateUtils.differentDays(new Date(), jxcShortJob.getWorkTimeEnd()) > 0) {
		                                   int count = jxcClockPairMapper.getCountPayByDriverId(jxcClockPair.getDriverId(), jxcClockPair.getShortJobId());
		                                   if (count == 0) {
			                                   //支付完成后解除司机与机主关系
			                                   jxcDriverRefOwnerMapper.updateByShortJobIdAndDriverId(jxcClockPair.getShortJobId(), jxcClockPair.getDriverId());
		                                   }
	                                   }
	                               }
                            }
                     }
              }
       }

	/**
	 * 台班异常申诉
	 * @param clockId
	 * @return
	 */
	@Override
	@Transactional
	public ResponseMessage updateClockApplyStateById(Long clockId, String description) {
		JxcExceptionAppeal jxcExceptionAppeal = jxcExceptionAppealMapper.getById(clockId);
		if(jxcExceptionAppeal != null){
			return new ResponseMessage(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"已存在台班异常申诉信息");
		}

		//添加异常申诉信息
		jxcExceptionAppeal = new JxcExceptionAppeal();
		jxcExceptionAppeal.setClockId(clockId);
		//异常申请用户类型(0--承租方 1--机主 3--司机)
		jxcExceptionAppeal.setAppealUserType(1);
		jxcExceptionAppeal.setAbnormalDescription(description);
		//异常类型(0--里程异常 1--打卡异常 2--台班异常)
		jxcExceptionAppeal.setAbnormalType(2);
		jxcExceptionAppealMapper.insertSelective(jxcExceptionAppeal);

		//更新台班申诉状态为申诉中
		jxcClockPairMapper.updateClockApplyState(clockId);
		return new ResponseMessage();
	}
}