package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.JxcLongJob;
import com.weiwei.jixieche.bean.JxcProjectType;
import com.weiwei.jixieche.bean.JxcShortJob;
import com.weiwei.jixieche.constant.CreditScoreTemplateConstants;
import com.weiwei.jixieche.jwt.User;
import com.weiwei.jixieche.mapper.DriverMapper;
import com.weiwei.jixieche.mapper.JxcProjectTypeMapper;
import com.weiwei.jixieche.utils.JxcProjectTypeUtils;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.mapper.JxcLongJobMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcLongJobService;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.vo.DriverJobVo;
import com.weiwei.jixieche.vo.UserCreditScoreVo;
import com.weiwei.jixieche.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
* @Description: 长期司机招聘表
* @Author:      liuy
* @Date:  2019/8/15 10:13
*/
@Service("jxcLongJobService")
public class JxcLongJobServiceImpl implements JxcLongJobService {
       @Resource
       private JxcLongJobMapper jxcLongJobMapper;

       @Resource
       private JxcProjectTypeMapper jxcProjectTypeMapper;

       @Autowired
       private JxcUserFeign jxcUserFeign;

       @Resource
       private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

       @Resource
       private DriverMapper driverMapper;


       //前端分页查询长期司机招聘表
       @Override
       public ResponseMessage<JxcLongJob> findByPageForFront(Integer pageNo, Integer pageSize, JxcLongJob jxcLongJob) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcLongJob> list = this.jxcLongJobMapper.selectListByConditions(jxcLongJob);
              PageInfo<JxcLongJob> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 长期司机招聘列表
        * @author  liuy
        * @param driverJobVo 查询条件
        * @return
        * @date    2019/8/15 11:44
        */
       @Override
       public ResponseMessage getLongJobList(DriverJobVo driverJobVo) {
              //返回结果
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(driverJobVo.getPageNo(), driverJobVo.getPageSize());
              List<JxcLongJob> list = this.jxcLongJobMapper.getLongJobList(driverJobVo);
              if (!CollectionUtils.isEmpty(list)) {
                     list.stream().forEach(longJob -> {
                            //工程类型ID转换名称
                            if (longJob.getProjectType() != null && !longJob.getProjectType().equals("")) {
                                   List<String> typeNameList = new ArrayList<>();
                                   String[]  typeId;
                                   if (longJob.getProjectType().indexOf(",") != -1) {
                                          typeId = longJob.getProjectType().split(",");
                                   }else{
                                          typeId = new String[]{longJob.getProjectType()};
                                   }
                                   typeNameList = jxcProjectTypeMapper.getTypeNameList(typeId);
                                   longJob.setProjectTypeList(typeNameList);
                            }
                     });
              }
              PageInfo<JxcLongJob> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加长期司机招聘表
        * @author  liuy
        * @param t
        * @return
        * @date    2019/8/15 10:57
        */
       @Override
       public ResponseMessage<JxcLongJob> addObj(JxcLongJob t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcLongJobMapper.insertSelective(t);
              if(res > 0){
                     //添加信用分记录
//                     UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
//                     userCreditScoreVo.setUserId(t.getUserId());
//                     userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_OWNER_FIRST_RELEASE_EMPLOY.getId());
//                     jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
              }
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       //修改长期司机招聘表
       @Override
       public ResponseMessage<JxcLongJob> modifyObj(JxcLongJob t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              t.setUpdateTime(new Date());
              int res=this.jxcLongJobMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询长期司机招聘表
        * @author  liuy
        * @param id
        * @return
        * @date    2019/8/15 11:35
        */
       @Override
       public ResponseMessage<JxcLongJob> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcLongJob model=this.jxcLongJobMapper.selectByPrimaryKey(id);
              //工程类型ID转换名称
              if (model.getProjectType() != null && !model.getProjectType().equals("")) {
                     List<String> typeNameList = new ArrayList<>();
                     String[]  typeId;
                     if (model.getProjectType().indexOf(",") != -1) {
                            typeId = model.getProjectType().split(",");
                     }else{
                            typeId = new String[]{model.getProjectType()};
                     }
                     typeNameList = jxcProjectTypeMapper.getTypeNameList(typeId);
                     model.setProjectTypeList(typeNameList);
              }
              if(model.getContactPhone() != null){
                     //获取用户认证信息
                     UserInfoVo userInfoVo = driverMapper.getUserInfoByPhone(model.getContactPhone());
                     if(userInfoVo != null) {
                            model.setScore(userInfoVo.getScore());
                            model.setOwnerHeadImg(userInfoVo.getHeadImg());
                            model.setOwnerConfirmStatus(userInfoVo.getOwnerConfirmStatus());
                     }
              }
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 删除
        * @author  liuy
        * @param id
        * @return
        * @date    2019/8/15 15:14
        */
       @Override
       public ResponseMessage deleteById(Integer id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcLongJob jxcLongJob = new JxcLongJob();
              jxcLongJob.setId(id);
              jxcLongJob.setDeleted(JxcLongJob.deleted.deleted);
              int res = this.jxcLongJobMapper.updateByPrimaryKeySelective(jxcLongJob);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.DELETE_ERORR.getDescript(),ErrorCodeConstants.DELETE_ERORR.getId());
              return result;
       }

       /**
        * 工程类型列表
        * @author  liuy
        * @return
        * @date    2019/8/27 14:07
        */
       @Override
       public ResponseMessage getProjectTypeList() {
              List<JxcProjectType> list = jxcProjectTypeMapper.selectListByConditions(null);
              return new ResponseMessage(list);
       }
}