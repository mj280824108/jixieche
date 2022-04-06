package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.bean.JxcClockPair;
import com.weiwei.jixieche.bean.JxcDriverResume;
import com.weiwei.jixieche.constant.CreditScoreTemplateConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.DriverMapper;
import com.weiwei.jixieche.mapper.JxcDriverResumeMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcDriverResumeService;
import com.weiwei.jixieche.util.AgeBirthdayUtil;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.JxcAreaVo;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
* @Description: 司机简历
* @Author:      liuy
* @Date:  2019/8/21 10:54
*/
@Service("jxcDriverResumeService")
public class JxcDriverResumeServiceImpl implements JxcDriverResumeService {
       @Resource
       private JxcDriverResumeMapper jxcDriverResumeMapper;

       @Resource
       private DriverMapper driverMapper;

       @Resource
       private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

       /**
        * 添加司机求职信息表
        * @param  jxcDriverResume
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverResume> addObj(JxcDriverResume jxcDriverResume) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(jxcDriverResume.getPayMoney() != null){
                     jxcDriverResume.setPayMoney(jxcDriverResume.getPayMoney()*100);
              }
              int res=this.jxcDriverResumeMapper.insertSelective(jxcDriverResume);
              if(res > 0){
                     //添加信用分记录
//                     UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
//                     userCreditScoreVo.setUserId(jxcDriverResume.getDriverId());
//                     userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_DRIVER_FIRST_RELEASE_RESUME.getId());
//                     jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
              }
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改司机求职信息表
        * @param jxcDriverResume
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverResume> modifyObj(JxcDriverResume jxcDriverResume) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              jxcDriverResume.setUpdateTime(new Date());
              if(jxcDriverResume.getPayMoney() != null){
                     jxcDriverResume.setPayMoney(jxcDriverResume.getPayMoney()*100);
              }
              int res=this.jxcDriverResumeMapper.updateByPrimaryKeySelective(jxcDriverResume);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询司机求职信息表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcDriverResume> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcDriverResume model=this.jxcDriverResumeMapper.selectByPrimaryKey(id);
              if(model != null){
                     try {
                            if(model.getBirthday() != null){
                                   model.setAge(AgeBirthdayUtil.getAgeByBirth(model.getBirthday()));
                            }
                            if(model.getPayMoney() != null){
                                   model.setPayMoney(DataTypeEmptyUtils.emptyMoneyInteger(model.getPayMoney()));
                            }
                     } catch (ParseException e) {
                            e.printStackTrace();
                     }
                     if(model.getAreaId() != null) {
                            JxcAreaVo jxcAreaVo = this.driverMapper.queryCityById(model.getAreaId());
                            model.setJobLocation(jxcAreaVo.getProvinceName() + jxcAreaVo.getCityName());
                     }
              }
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 查看司机简历
        * @author  liuy
       * @param driverId
        * @return
        * @date    2019/8/21 10:57
        */
       @Override
       public ResponseMessage getDriverResumeById(Integer driverId) {
              JxcDriverResume jxcDriverResume = new JxcDriverResume();
              jxcDriverResume.setDriverId(driverId);
              List<JxcDriverResume> driverResumeList = this.jxcDriverResumeMapper.selectListByConditions(jxcDriverResume);
              if(!CollectionUtils.isEmpty(driverResumeList)){
                     jxcDriverResume = driverResumeList.get(0);
                     try {
                            if(jxcDriverResume.getBirthday() != null){
                                   jxcDriverResume.setAge(AgeBirthdayUtil.getAgeByBirth(jxcDriverResume.getBirthday()));
                            }
                            if(jxcDriverResume.getPayMoney() != null){
                                   jxcDriverResume.setPayMoney(DataTypeEmptyUtils.emptyMoneyInteger(jxcDriverResume.getPayMoney()));
                            }
                            if(jxcDriverResume.getAreaId() != null) {
                                   JxcAreaVo jxcAreaVo = this.driverMapper.queryCityById(jxcDriverResume.getAreaId());
                                   jxcDriverResume.setJobLocation(jxcAreaVo.getProvinceName() + jxcAreaVo.getCityName());
                            }
                     } catch (ParseException e) {
                            e.printStackTrace();
                     }
              }else{
                     return new ResponseMessage();
              }
              return new ResponseMessage(jxcDriverResume);
       }

       /**
        * 司机简历列表
        * @author  liuy
        * @param jxcDriverResume
        * @return
        * @date    2019/8/21 11:36
        */
       @Override
       public ResponseMessage getDriverResumeList(JxcDriverResume jxcDriverResume) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(jxcDriverResume.getStartMoney() != null) {
                     jxcDriverResume.setStartMoney(jxcDriverResume.getStartMoney()*100);
              }
              if(jxcDriverResume.getEndMoney() != null){
                     jxcDriverResume.setEndMoney(jxcDriverResume.getEndMoney()*100);
              }
              PageHelper.orderBy(" update_time desc");
              PageHelper.startPage(jxcDriverResume.getPageNo(),jxcDriverResume.getPageSize());
              List<JxcDriverResume> list = this.jxcDriverResumeMapper.selectListByConditions(jxcDriverResume);
              if(!CollectionUtils.isEmpty(list)){
                     list.stream().forEach(jxcDriverResume1 -> {
                            if(jxcDriverResume1.getPayMoney() != null){
                                   jxcDriverResume1.setPayMoney(DataTypeEmptyUtils.emptyMoneyInteger(jxcDriverResume1.getPayMoney()));
                            }
                     });
              }
              PageInfo<JxcDriverResume> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 刷新简历
        * @author  liuy
        * @param resumeId
        * @return
        * @date    2019/8/29 9:48
        */
       @Override
       public ResponseMessage refreshDriverResume(Integer resumeId) {
              JxcDriverResume jxcDriverResume = new JxcDriverResume();
              jxcDriverResume.setResumeId(resumeId);
              jxcDriverResume.setUpdateTime(new Date());
              int res = this.jxcDriverResumeMapper.updateByPrimaryKeySelective(jxcDriverResume);
              AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
              return new ResponseMessage();
       }
}