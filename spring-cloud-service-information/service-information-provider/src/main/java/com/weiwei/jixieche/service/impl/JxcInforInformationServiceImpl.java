package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcInforInformation;
import com.weiwei.jixieche.config.InfoStaticHtmlConfig;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.RedisPrefixConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcInforInformationMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcInforInformationService;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.InforDetailVo;
import com.weiwei.jixieche.vo.InforFireListVo;
import com.weiwei.jixieche.vo.InforParamVo;
import com.weiwei.jixieche.vo.InforPointNumberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName s
 * @Description TODO
 * @Author houji
 * @Date 2019/8/20 16:32
 * @Version 2.0
 **/
@Service("jxcInforInformationService")
public class JxcInforInformationServiceImpl implements JxcInforInformationService {

       @Autowired
       private InfoStaticHtmlConfig infoStaticHtmlConfig;

       @Resource
       private JxcInforInformationMapper jxcInforInformationMapper;

       @Autowired
       private RedisUtil redisUtil;

       /**
        * 前端分页查询咨询信息表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcInforInformation 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcInforInformation> findByPageForFront(Integer pageNo, Integer pageSize, JxcInforInformation jxcInforInformation) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              PageHelper.orderBy("create_time DESC");
              List<JxcInforInformation> list = this.jxcInforInformationMapper.selectListByConditions(jxcInforInformation);
              PageInfo<JxcInforInformation> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询机主首页轮播资讯
        * @param
        * @return
        */
       @Override
       public ResponseMessage<InforFireListVo> queryInforFireList() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              List<InforFireListVo> fireList = this.jxcInforInformationMapper.queryInforFireList();
              result.setData(fireList);
              return result;
       }


       /**
        * 添加咨询信息表
        * @param  jxcInforInformation
        * @return
        */
       @Override
       public ResponseMessage<JxcInforInformation> addObj(JxcInforInformation jxcInforInformation) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcInforInformationMapper.insertSelective(jxcInforInformation);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }

       /**
        * 修改咨询信息表
        * @param jxcInforInformation
        * @return
        */
       @Override
       public ResponseMessage<JxcInforInformation> modifyObj(JxcInforInformation jxcInforInformation) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcInforInformationMapper.updateByPrimaryKeySelective(jxcInforInformation);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       /**
        * 根据ID查询咨询信息表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcInforInformation> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcInforInformation model=this.jxcInforInformationMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 资讯点赞(浏览)(分享)量增+1
        * @param inforPointNumberVo
        * @return
        */
       @Override
       public ResponseMessage operation(InforPointNumberVo inforPointNumberVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              String key = null;
              //判断属于点赞浏览分享操作
              if(inforPointNumberVo.getOperationType() == InforPointNumberVo.operationType.POINT){
                     key = RedisPrefixConstants.INFORMATION_POINT_COUNT+"_"+inforPointNumberVo.getInforId();
              }else if(inforPointNumberVo.getOperationType() == InforPointNumberVo.operationType.VIEW){
                     key = RedisPrefixConstants.INFORMATION_VIEW_COUNT+"_"+inforPointNumberVo.getInforId();
              }else if(inforPointNumberVo.getOperationType() == InforPointNumberVo.operationType.SHARE){
                     key = RedisPrefixConstants.INFORMATION_SHARE_COUNT+"_"+inforPointNumberVo.getInforId();
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_UNKNOW.getId());
                     result.setMessage(ErrorCodeConstants.PARAM_UNKNOW.getDescript());
                     return result;
              }
              //判断redis是否存在
              if(redisUtil.hasKey(key)){
                     if(inforPointNumberVo.getOperationStatus() ==InforPointNumberVo.operationStatus.ADD){
                            redisUtil.set(key,Integer.parseInt(redisUtil.get(key).toString())+1);
                     }else{
                            redisUtil.set(key,Integer.parseInt(redisUtil.get(key).toString())-1);
                     }
              }else{
                     if(inforPointNumberVo.getOperationStatus() ==InforPointNumberVo.operationStatus.ADD){
                            redisUtil.set(key,1);
                     }
              }
              return result;
       }

       /**
        * 查询资讯详情
        * @param inforParamVo
        * @return
        */
       @Override
       public ResponseMessage queryById(InforParamVo inforParamVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(inforParamVo.getId()== null || inforParamVo.getAppClientType() == null){
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("用户id和客户端类型不能为空");
                     return result;
              }
              //查询资讯信息
              JxcInforInformation inforInformation = this.jxcInforInformationMapper.selectByPrimaryKey(inforParamVo.getId());
              InforDetailVo inforDetail =  new InforDetailVo();
              inforDetail.setTitle(inforInformation.getTitle());
              inforDetail.setId(inforInformation.getId());
              inforDetail.setLogoImgUrl("http://app.vvjx.cn/ceshi/adminimg/f9b7e40f3e084c10b13f94e34ce49a4e-20190928194731750.jpg");
              if(inforParamVo.getUserId() != null){
                     //用户是否收藏
                     Map<String,Object> map = new HashMap<>();
                     map.put("inforId",inforParamVo.getId());
                     map.put("userId",inforParamVo.getUserId());
                     Integer collectId = this.jxcInforInformationMapper.queryCollection(map);
                     if(collectId != null && collectId >0){
                            inforDetail.setCollectionId(collectId);
                     }else{
                            inforDetail.setCollectionId(0);
                     }
                     //用户是否点赞
                     Integer pointId = this.jxcInforInformationMapper.queryPointId(map);
                     if(pointId != null && pointId >0){
                            inforDetail.setPointId(pointId);
                     }else{
                            inforDetail.setPointId(0);
                     }
                     //用户收藏量和点赞量
                     inforDetail.setCollectionCount(this.jxcInforInformationMapper.queryInfoCollectionNum(inforDetail.getId()));
                     inforDetail.setPointCount(this.jxcInforInformationMapper.queryInfoPointNum(inforDetail.getId()));

              }
              inforDetail.setInforUrl(infoStaticHtmlConfig.getHtmlInforUrl()+"?userType="+inforParamVo.getAppClientType()+"&id="+inforParamVo.getId());
              inforDetail.setShareInforUrl(infoStaticHtmlConfig.getDetailInforUrl()+"?userType="+inforParamVo.getAppClientType()+"&id="+inforParamVo.getId());
              AssertUtil.notNull(inforDetail,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(inforDetail);
              return result;
       }
}