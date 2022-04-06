package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.CreditScoreTemplateConstants;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMachineService;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author 钟焕
 * @Description
 * @Date 20:49 2019-08-13
 **/
@Service("jxcMachineService")
public class JxcMachineServiceImpl implements JxcMachineService {
       @Resource
       private JxcMachineMapper jxcMachineMapper;

       @Resource
       private DriverMapper driverMapper;

       @Resource
       private JxcMachineRouteMapper jxcMachineRouteMapper;

       @Resource
       private JxcMachineRefDriverMapper jxcMachineRefDriverMapper;

       @Resource
       private JxcUserFeign jxcUserFeign;

       @Resource
       private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

       @Resource
       private JxcScoreMapper jxcScoreMapper;

       @Resource
       private JxcProjectOrderMapper jxcProjectOrderMapper;


       /**
        * @Author 钟焕
        * @Description
        * @Date 20:48 2019-08-13
        * @Param [pageNo, pageSize, jxcMachine]
        * @return result
        **/
       @Override
       public ResponseMessage<JxcMachine> findByPageForFront(Integer pageNo, Integer pageSize, JxcMachine jxcMachine) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMachine> list = this.jxcMachineMapper.selectListByConditions(jxcMachine);
              PageInfo<JxcMachine> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 机械信息列表
        * @author  liuy
        * @param ownerUserId 机主用户Id
        * @param lastPageLastId 最后一条machineId
        * @param pageSize  每页显示条数
        * @return
        * @date    2019/8/14 19:49
        */
       @Override
       public ResponseMessage getMachineList(Integer ownerUserId, Integer lastPageLastId, Integer pageSize) {
              //返回结果
              Map<String,Object> resultMap = new HashMap<>();
              List<JxcMachineVo> list = this.jxcMachineMapper.getMachineList(ownerUserId, lastPageLastId, pageSize);
              //是否分页查询
              if(pageSize != null) {
                     int hasNextPage = 0;
                     if (!CollectionUtils.isEmpty(list)) {
                            list.stream().forEach(jxcMachineVo -> {
                                   //车辆绑定司机信息
                                   List<DriverVo> driverVoList = getDriverListByMachineId(jxcMachineVo.getId());
                                   jxcMachineVo.setDriverList(driverVoList);
                            });
                            if(list.size() == pageSize) {
                                   //mark 将最后一条数据作为下次查询的条件
                                   Integer nextPageFirstId = this.jxcMachineMapper.hasNextPage(ownerUserId, list.get(pageSize - 1).getId());
                                   if (null != nextPageFirstId) {
                                          hasNextPage = 1;
                                          resultMap.put("lastPageLastId", list.get(pageSize - 1).getId());
                                   }
                            }
                     }
                     resultMap.put("hasNextPage", hasNextPage);
              }
              resultMap.put("machineList", list);
              return new ResponseMessage(resultMap);
       }

       @Override
       /**
        * 删除机械
        * @author  liuy
        * @param id
        * @return  com.weiwei.jixieche.response.ResponseMessage
        * @date    2019/8/14 19:33
        */
       public ResponseMessage deleteById(Integer id) {
              Integer count = jxcMachineMapper.checkOwnerOrderByMachineId(id);
              if(count > 0){
                     return new ResponseMessage(true);
              }

              JxcMachine jxcMachine = new JxcMachine();
              jxcMachine.setId(id);
              jxcMachine.setDeleteStatus(1);
              this.jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine);

              //解绑司机
              jxcMachineMapper.updateMachineById(id);
              return new ResponseMessage();
       }

       /**
        * 查询机主所有的机械
        * @author  liuy
        * @param ownerUserId
        * @return
        * @date    2019/8/20 17:01
        */
       @Override
       public ResponseMessage selectMachineList(Integer ownerUserId) {
              List<Map<String,Object>> list = this.jxcMachineMapper.selectMachineList(ownerUserId);
              return new ResponseMessage(list);
       }

       /**
        * 更新机械状态为空闲中
        * @author  liuy
        * @param machineId
        * @return
        * @date    2019/9/29 15:09
        */
       @Override
       public ResponseMessage updateMachineById(Integer machineId) {
              //查询是否有正在进行中的机械订单
              Integer count = jxcMachineMapper.checkMachineRunByMachineId(machineId);
              if(count == 0){
                     JxcMachine jxcMachine = new JxcMachine();
                     jxcMachine.setId(machineId);
                     //空闲中
                     jxcMachine.setStatus(JxcMachine.carStatus.IN_IDLE);
                     jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine);
              }
              return new ResponseMessage();
       }

       /**
        * @Author 钟焕
        * @Description
        * @Date 20:47 2019-08-13
        * @Param [t]
        * @return result
        **/
       @Override
       @Transactional
       public ResponseMessage<JxcMachine> addObj(JxcMachine t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();

              JxcMachine jxcMachine = new JxcMachine();
              jxcMachine.setMachineCardNo(t.getMachineCardNo());
              jxcMachine.setDeleteStatus(0);
              List<JxcMachine> list = jxcMachineMapper.selectListByConditions(jxcMachine);
              if(!CollectionUtils.isEmpty(list)){
                     return new ResponseMessage<>(ErrorCodeConstants.QUERY_DATA_EXISIT.getId(),"该车牌号码已存在");
              }
              //审核模式 1:自动; 2:手动
	          Integer auditMode = jxcMachineMapper.getSetAuditMode();
              if(auditMode == 2){
              	//手动模式时设置为审核中
              	t.setConfirmState(0);
              }
              int res=this.jxcMachineMapper.insertSelective(t);
              if(res > 0){
                     if(t.getDriverId() != null && t.getDriverId() != 0){
                            //绑定司机
                            JxcMachineRefDriver jxcMachineRefDriver = new JxcMachineRefDriver();
                            jxcMachineRefDriver.setMachineId(t.getId());
                            jxcMachineRefDriver.setUserId(t.getDriverId());
                            jxcMachineRefDriver.setBindTime(new Date());
                            jxcMachineRefDriverMapper.insertSelective(jxcMachineRefDriver);

                            //更新车辆状态为空闲中
                            JxcMachine jxcMachine1 = new JxcMachine();
                            jxcMachine1.setStatus(JxcMachine.carStatus.IN_IDLE);
                            jxcMachine1.setId(t.getId());
                            jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine1);
                     }
                     //添加信用分记录
//                     UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
//                     userCreditScoreVo.setUserId(t.getUserId());
//                     userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_OWNER_FIRST_RELEASE_AUTH_MACHINE.getId());
//                     jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
              }
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * @Author 钟焕
        * @Description 修改机械表
        * @Date 20:40 2019-08-13
        * @Param [t]
        * @return result
        **/
       @Override
       public ResponseMessage<JxcMachine> modifyObj(JxcMachine t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              t.setUpdateTime(new Date());
              int res=this.jxcMachineMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * @Author 刘阳
        * @Description
        * @Date 20:47 2019-08-13
        * @Param [id]
        * @return result
        **/
       @Override
       public ResponseMessage queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
//              JxcMachineVo jxcMachineVo = new JxcMachineVo();
//              //机械基础信息
//              JxcMachine model=this.jxcMachineMapper.selectByPrimaryKey(id);
//              if(null == model){
//                     return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(),"该车辆不存在");
//              }
//              jxcMachineVo.setAreaCode(model.getAreaCode());
//              if(model.getAreaCode() != null){
//                     JxcAreaVo jxcAreaVo = driverMapper.queryCityById(model.getAreaCode());
//                     if(jxcAreaVo != null){
//                            jxcMachineVo.setWorkArea(jxcAreaVo.getProvinceName() + jxcAreaVo.getCityName());
//                     }
//              }
//              jxcMachineVo.setId(model.getId());
//              jxcMachineVo.setMachineCapacity(model.getMachineCapacity());
//              jxcMachineVo.setMachineCardNo(model.getMachineCardNo());
//              jxcMachineVo.setMachineProductTime(model.getMachineProductTime());
//              jxcMachineVo.setMachineDrivingLicenseBehind(model.getMachineDrivingLicenseBehind());
//              jxcMachineVo.setMachineDrivingLicenseFront(model.getMachineDrivingLicenseFront());
//              jxcMachineVo.setMachineOperationCertificateBehind(model.getMachineOperationCertificateBehind());
//              jxcMachineVo.setMachineOperationCertificateFront(model.getMachineOperationCertificateFront());
//              jxcMachineVo.setDrivingLicenseValidTime(model.getDrivingLicenseValidTime());
//              jxcMachineVo.setOperationCertificateValidTime(model.getOperationCertificateValidTime());
//              jxcMachineVo.setDrivingLicenseState(model.getDrivingLicenseState());
//              jxcMachineVo.setOperationCertificateState(model.getOperationCertificateState());
//              jxcMachineVo.setStatus(model.getStatus());
//              jxcMachineVo.setConfirmState(model.getConfirmState());
//              jxcMachineVo.setScore(model.getScore());
//
//              //车辆绑定司机信息
//              List<DriverVo> driverVoList = getDriverListByMachineId(model.getId());
//              jxcMachineVo.setDriverList(driverVoList);
//
//              //项目相关信息
//              JxcProject project = this.jxcMachineMapper.getProjectById(model.getId());
//              if(project != null) {
//                     jxcMachineVo.setProjectName(project.getProjectName());
//                     jxcMachineVo.setStartDate(project.getStartDate());
//                     jxcMachineVo.setEndDate(project.getEndDate());
//                     jxcMachineVo.setProjectAddress(project.getProjectAddress());
//                     jxcMachineVo.setOwnerOrderId(project.getOwnerOrderId());
//              }
//
//              //消纳场地址
//              String siteAddress = this.jxcMachineMapper.getSiteAddressById(model.getId());
//              jxcMachineVo.setSiteAddress(siteAddress);
//
//              //总趟数
//              int machineTotalCount = jxcMachineRouteMapper.getTotalCountByMachineId(model.getId());
//              jxcMachineVo.setMachineTotalCount(machineTotalCount);
//
//              //获取机主信息
//              if(model.getUserId() != null){
//                     ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(model.getUserId());
//                     if(userInfoVoResponseMessage.getData() != null){
//                            UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
//                            if(userInfoVo != null){
//                                   jxcMachineVo.setOwnerUserName(userInfoVo.getRealName());
//                                   jxcMachineVo.setOwnerPhone(userInfoVo.getPhone());
//                            }
//                     }
//              }
//
//              //查询是否评价过
//              JxcScore jxcScore = new JxcScore();
//              jxcScore.setTargetId(id);
//              List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
//              //评价状态(1:已评价; 0:未评价)
//              if(CollectionUtils.isEmpty(jxcScores)){
//                     jxcMachineVo.setEvaluationStatus(0);
//              }else{
//                     jxcMachineVo.setEvaluationStatus(1);
//              }

//              AssertUtil.notNull(jxcMachineVo,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
//              result.setData(jxcMachineVo);
              return result;
       }

       /**
        * 车辆绑定司机信息
        * @author  liuy
        * @param machineId 机械ID
        * @return
        * @date    2019/8/14 19:46
        */
       private List<DriverVo> getDriverListByMachineId(Integer machineId) {
              //车辆绑定司机信息
              List<DriverVo> driverList = driverMapper.getDriverListByMachineId(machineId);
              if(!CollectionUtils.isEmpty(driverList)){
                     for(DriverVo driverVo : driverList){
                            //查询司机工作状态
                            int count = driverMapper.getDriverWorkStateById(driverVo.getDriverId());
                            if(count > 0){
                                   driverVo.setWorkState(1);
                            }else{
                                   driverVo.setWorkState(0);
                            }
                     }
              }
              return driverList;
       }

       /**
        * 筛选车辆列表
        * @author  liuy
        * @param userId
        * @return
        * @date    2019/10/8 11:45
        */
       @Override
       public ResponseMessage getMachineSelectByUserId(Integer userId) {
              List<JxcMachineSelectVo> list = jxcMachineMapper.getMachineSelectAllByUserId(userId);
              return new ResponseMessage(list);
       }

       /**
        * 车辆详情
        * @author  liuy
        * @param projectOrderId
        * @return
        * @date    2019/10/14 15:19
        */
       @Override
       public ResponseMessage getById(Integer id, Long projectOrderId) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMachineVo jxcMachineVo = new JxcMachineVo();
              //机械基础信息
              JxcMachine model=this.jxcMachineMapper.selectByPrimaryKey(id);
              if(null == model){
                     return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(),"该车辆不存在");
              }
              jxcMachineVo.setAreaCode(model.getAreaCode());
              if(model.getAreaCode() != null){
                     JxcAreaVo jxcAreaVo = driverMapper.queryCityById(model.getAreaCode());
                     if(jxcAreaVo != null){
                            jxcMachineVo.setWorkArea(jxcAreaVo.getProvinceName() + jxcAreaVo.getCityName());
                     }
              }
              jxcMachineVo.setId(model.getId());
              jxcMachineVo.setMachineCapacity(model.getMachineCapacity());
              jxcMachineVo.setMachineCardNo(model.getMachineCardNo());
              jxcMachineVo.setMachineProductTime(model.getMachineProductTime());
              jxcMachineVo.setMachineDrivingLicenseBehind(model.getMachineDrivingLicenseBehind());
              jxcMachineVo.setMachineDrivingLicenseFront(model.getMachineDrivingLicenseFront());
              jxcMachineVo.setMachineOperationCertificateBehind(model.getMachineOperationCertificateBehind());
              jxcMachineVo.setMachineOperationCertificateFront(model.getMachineOperationCertificateFront());
              jxcMachineVo.setDrivingLicenseValidTime(model.getDrivingLicenseValidTime());
              jxcMachineVo.setOperationCertificateValidTime(model.getOperationCertificateValidTime());
              jxcMachineVo.setDrivingLicenseState(model.getDrivingLicenseState());
              jxcMachineVo.setOperationCertificateState(model.getOperationCertificateState());
              jxcMachineVo.setStatus(model.getStatus());
              jxcMachineVo.setConfirmState(model.getConfirmState());
              jxcMachineVo.setScore(model.getScore());

              //车辆绑定司机信息
              List<DriverVo> driverVoList = getDriverListByMachineId(model.getId());
              jxcMachineVo.setDriverList(driverVoList);

              //项目相关信息
              JxcProject project = this.jxcMachineMapper.getProjectById(model.getId());
              if(project != null) {
                     jxcMachineVo.setProjectName(project.getProjectName());
                     jxcMachineVo.setStartDate(project.getStartDate());
                     jxcMachineVo.setEndDate(project.getEndDate());
                     jxcMachineVo.setProjectAddress(project.getProjectAddress());
                     jxcMachineVo.setOwnerOrderId(project.getOwnerOrderId());
              }

              //消纳场地址
              String siteAddress = this.jxcMachineMapper.getSiteAddressById(model.getId());
              jxcMachineVo.setSiteAddress(siteAddress);

              //总趟数
              int machineTotalCount = jxcMachineRouteMapper.getTotalCountByMachineId(model.getId());
              jxcMachineVo.setMachineTotalCount(machineTotalCount);

              //获取机主信息
              if(model.getUserId() != null){
                     ResponseMessage<UserInfoVo> userInfoVoResponseMessage = jxcUserFeign.queryUserInfo(model.getUserId());
                     if(userInfoVoResponseMessage.getData() != null){
                            UserInfoVo userInfoVo = userInfoVoResponseMessage.getData();
                            if(userInfoVo != null){
                                   jxcMachineVo.setOwnerUserName(userInfoVo.getRealName());
                                   jxcMachineVo.setOwnerPhone(userInfoVo.getPhone());
                            }
                     }
              }

              //承租方订单ID
              if(projectOrderId != null){
                     //查询是否评价过
                     JxcScore jxcScore = new JxcScore();
                     jxcScore.setTargetId(id);
                     jxcScore.setOrderId(String.valueOf(projectOrderId));
                     List<JxcScore> jxcScores = jxcScoreMapper.selectListByConditions(jxcScore);
                     //评价状态(1:已评价; 0:未评价)
                     if(CollectionUtils.isEmpty(jxcScores)){
                            jxcMachineVo.setEvaluationStatus(0);
                     }else{
                            jxcMachineVo.setEvaluationStatus(1);
                     }
                     //查询机主订单状态
                     PageHelper.clearPage();
                     JxcOwnerOrder jxcOwnerOrder = jxcProjectOrderMapper.queryOwnerOrderStateByProjectId(projectOrderId, id);
                     if(jxcOwnerOrder != null) {
                            jxcMachineVo.setOwnerOrderState(jxcOwnerOrder.getOrderState());
                     }else{
                            //取消订单状态
                            jxcMachineVo.setOwnerOrderState(1);
                     }
              }

              AssertUtil.notNull(jxcMachineVo,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(jxcMachineVo);
              return result;
       }

}