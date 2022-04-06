package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcMarketCollectionRecord;
import com.weiwei.jixieche.bean.JxcMarketRelease;
import com.weiwei.jixieche.bean.JxcMarketShops;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.RedisPrefixConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.redis.RedisUtil;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketShopsService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.ShopsUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketShopsService")
public class JxcMarketShopsServiceImpl implements JxcMarketShopsService {
       @Resource
       private JxcMarketShopsMapper jxcMarketShopsMapper;

       @Resource
       private JxcMarketCollectionRecordMapper jxcMarketCollectionRecordMapper;

       @Resource
       private JxcMarketReleaseMapper jxcMarketReleaseMapper;

       @Resource
       private JxcMarketMachineTypeMapper jxcMarketMachineTypeMapper;

       @Resource
       private JxcMarketBrandMapper jxcMarketBrandMapper;

       @Resource
       private JxcMarketResourceTypeMapper jxcMarketResourceTypeMapper;

       @Autowired
       private RedisUtil redisUtil;

       @Autowired
       private ShopsUtils shopsUtils;

       /**
        * 用户添加店铺
        * @param user
        * @param jxcMarketShops
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketShops> addObj(AuthorizationUser user, JxcMarketShops jxcMarketShops) {
              //用户不存在店铺，则添加店铺，用户有店铺则认证店铺
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user != null && user.getUserId() != null){
                     JxcMarketShops shopsExist = this.jxcMarketShopsMapper.queryShopsByUserId(user.getUserId());
                     if(shopsExist != null && shopsExist.getId() != null){
                            if(jxcMarketShops.getConfirmStatus() == JxcMarketShops.ShopConfirmStatus.CONFIRM){
                                   String resStr = this.shopsUtils.verifyParam(jxcMarketShops);
                                   if(resStr != null){
                                          result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                                          result.setMessage(resStr);
                                          return result;
                                   }
                                   int res=this.jxcMarketShopsMapper.updateByPrimaryKeySelective(jxcMarketShops);
                                   AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
                                   return result;
                            }else{
                                   result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                                   result.setMessage("店铺请求操作未识别");
                                   return result;
                            }
                     }else{
                            String resStr = this.shopsUtils.verifyParam(jxcMarketShops);
                            if(resStr != null){
                                   result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                                   result.setMessage(resStr);
                                   return result;
                            }
                            jxcMarketShops.setShopKeeperId(user.getUserId());
                            //用户图片用逗号存储，第一张图片作为门店图片
                            boolean status = jxcMarketShops.getShopSmallImgs().contains(",");
                            if(status){
                                String[] aa = jxcMarketShops.getShopSmallImgs().split("\\,");
                                jxcMarketShops.setShopBigImg(aa[0]);
                            }else{
                                jxcMarketShops.setShopBigImg(jxcMarketShops.getShopSmallImgs());
                            }
                            jxcMarketShops.setShopOpenTime(DateUtils.format(new Date(),DateUtils.DATE_PATTERN));
                            int res=this.jxcMarketShopsMapper.insertSelective(jxcMarketShops);
                            AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
                            return result;
                     }
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("token不存在或者解析错误");
                     return result;
              }
       }

       /**
        * 通过ID查询市场店铺表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketShops> queryObjById(int id) {
              return null;
       }

       /**
        * 前端分页查询市场店铺表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketShops 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketShops> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketShops jxcMarketShops) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcMarketShops> list = this.jxcMarketShopsMapper.selectListByConditions(jxcMarketShops);
              PageInfo<JxcMarketShops> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }



       /**
        * 添加市场店铺表
        * @param  jxcMarketShops
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketShops> addObj(JxcMarketShops jxcMarketShops) {
              return null;
       }

       /**
        * 修改市场店铺表
        * @param jxcMarketShops
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketShops> modifyObj(JxcMarketShops jxcMarketShops) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketShopsMapper.updateByPrimaryKeySelective(jxcMarketShops);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }
       /**
        * 店铺浏览量点击量+1操作
        * @param shopsPointNumberVo
        * @return
        */
       @Override
       public ResponseMessage operation(ShopsPointNumberVo shopsPointNumberVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              String key = null;
              if(shopsPointNumberVo.getOperationType() == ShopsPointNumberVo.operationType.VIEW){
                     key = RedisPrefixConstants.SHOPS_VIEW_COUNT.getPrefix();
              }else if(shopsPointNumberVo.getOperationType() == ShopsPointNumberVo.operationType.COLLECTION){
                     key = RedisPrefixConstants.SHOPS_COLLECTION_COUNT.getPrefix();
              }else if(shopsPointNumberVo.getOperationType() == ShopsPointNumberVo.operationType.POINT){
                     key = RedisPrefixConstants.SHOPS_POINT_COUNT.getPrefix();
              }else if(shopsPointNumberVo.getOperationType() == ShopsPointNumberVo.operationType.SHARE){
                     key = RedisPrefixConstants.SHOPS_SHARE_COUNT.getPrefix();
              }
              if(key == null){
                   result.setCode(ErrorCodeConstants.REDIS_KEY_NULL.getId());
                   result.setMessage(ErrorCodeConstants.REDIS_KEY_NULL.getDescript());
                   return result;
              }else{
                     if(redisUtil.hasKey(key)){
                            if(shopsPointNumberVo.getOperationStatus() == ShopsPointNumberVo.operationStatus.ADD){
                                   redisUtil.zSetUpdateScore(key,
                                           shopsPointNumberVo.getShopsId(),
                                           (int)redisUtil.zGetScore(key,shopsPointNumberVo.getShopsId())+1);
                            }else if(shopsPointNumberVo.getOperationStatus() == ShopsPointNumberVo.operationStatus.CANNEL){
                                   redisUtil.zSetUpdateScore(key,shopsPointNumberVo.getShopsId(),
                                           (int)redisUtil.zGetScore(key,shopsPointNumberVo.getShopsId())-1);
                            }
                     }else{
                            if(shopsPointNumberVo.getOperationStatus() == ShopsPointNumberVo.operationStatus.ADD){
                                redisUtil.zSet(key,shopsPointNumberVo.getShopsId(),1);
                            }
                     }
              }
              return result;
       }

       /**
        * 店铺搜索认证(距离,人气,标题)
        * @param searchShopsListVo
        * @return
        */
       @Override
       public ResponseMessage<SearchShopsListVo> searchShopsList(SearchShopsListVo searchShopsListVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(searchShopsListVo.getPageNo(),searchShopsListVo.getPageSize());
              List<SearchShopsListVo> listVos = this.jxcMarketShopsMapper.searchShopsList(searchShopsListVo);
              if(!CollectionUtils.isEmpty(listVos)){
                     listVos.forEach(shops -> {
                            //人员头像作为店铺的头像
                            shops.setShopBigImg(this.jxcMarketShopsMapper.queryHeadImgByUserId(shops.getShopKeeperId()));

                            JxcMarketCollectionRecord jxcMarketCollectionRecord = new JxcMarketCollectionRecord();
                            jxcMarketCollectionRecord.setCollectionType(JxcMarketCollectionRecord.collectionType.SHOPS);
                            jxcMarketCollectionRecord.setShopId(shops.getShopsId());
                            jxcMarketCollectionRecord.setCollectionDisabled(JxcMarketCollectionRecord.collectionDisabled.COLLECTION);
                            //获取店铺的收藏量
                            List<JxcMarketCollectionRecord> list =  this.jxcMarketCollectionRecordMapper.selectListByConditions(jxcMarketCollectionRecord);
                            if(!CollectionUtils.isEmpty(list)){
                                   shops.setCollectionNumber(list.size());
                            }else{
                                   shops.setCollectionNumber(0);
                            }

                     });
                     PageInfo<SearchShopsListVo> page = new PageInfo<>(listVos);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
              }else{
                     PageInfo<SearchShopsListVo> page = new PageInfo<>(listVos);
                     result.setData(new PageUtils<>(page).getPageViewDatatable());
              }
              return result;
       }

       /**
        * 查询我的店铺
        * @param user
        * @param userShopsInfoVo
        * @return
        */
       @Override
       public ResponseMessage<UserShopsInfoVo> queryOwnShopsInfo(AuthorizationUser user, UserShopsInfoVo userShopsInfoVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user != null && user.getUserId() != null){
                     //用户查询我的店铺信息
                     JxcMarketShops jxcMarketShops = new JxcMarketShops();
                     jxcMarketShops.setShopKeeperId(user.getUserId());
                     List<JxcMarketShops> userShopList = this.jxcMarketShopsMapper.selectListByConditions(jxcMarketShops);
                     if(!CollectionUtils.isEmpty(userShopList)){
                            jxcMarketShops = userShopList.get(0);
                            UserShopsVo userShopsVo = new UserShopsVo();
                            BeanUtils.copyProperties(jxcMarketShops,userShopsVo);

                            userShopsInfoVo.setUserShopsVo(userShopsVo);
                            PageHelper.startPage(userShopsInfoVo.getPageNo(),userShopsInfoVo.getPageSize());
                            PageHelper.orderBy("create_time DESC");
                            //查询店铺用户信息
                            userShopsInfoVo.setUserId(user.getUserId());
                            //上架商品
                            List<JxcMarketReleaseVo> marketReleaseList  = this.jxcMarketReleaseMapper.queryOwnShopMachineList(userShopsInfoVo);
                            if(!CollectionUtils.isEmpty(marketReleaseList)){
                                   marketReleaseList.forEach(releaseMachine -> {
                                          if(releaseMachine.getBrandTypeId() != null){
                                                 releaseMachine.setBrandTypeName(this.jxcMarketBrandMapper.selectByPrimaryKey(releaseMachine.getBrandTypeId()).getBrandName());
                                          }
                                          if(releaseMachine.getSourceId() != null){
                                                 releaseMachine.setSourceName(this.jxcMarketResourceTypeMapper.selectByPrimaryKey(releaseMachine.getSourceId()).getResourceName());
                                          }
                                          if(releaseMachine.getMachineTypeId() != null){
                                                 releaseMachine.setMachineTypeName(this.jxcMarketMachineTypeMapper.selectByPrimaryKey(releaseMachine.getMachineTypeId()).getMachineName());
                                          }
                                          if(releaseMachine.getDistrictId() != null){
                                                 AreaNameVo areaNameVo = this.jxcMarketReleaseMapper.queryAreaName(releaseMachine.getDistrictId());
                                                 releaseMachine.setAreaName(areaNameVo.getPName()+areaNameVo.getCName()+areaNameVo.getDName());
                                          }
                                   });
                            }
                            PageInfo<JxcMarketReleaseVo> page = new PageInfo<>(marketReleaseList);
                            userShopsInfoVo.setPageCount(new PageUtils<>(page).getPageViewDatatable().getPageCount());
                            userShopsInfoVo.setUserShopsVo(userShopsVo);
                            userShopsInfoVo.setMarketReleaseVoList(marketReleaseList);
                            result.setData(userShopsInfoVo);
                     }else{
                            result.setCode(ErrorCodeConstants.USER_SHOP_EMPTY.getId());
                            result.setMessage("用户不存在店铺");
                            return result;
                     }
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("token不存在或者解析错误");
                     return result;
              }
              return result;
       }

       /**
        * 查询用户店铺是否存在
        * @param user
        * @return
        */
       @Override
       public ResponseMessage queryUserShopsExist(AuthorizationUser user) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user != null && user.getUserId() != null){
                     JxcMarketShops jxcMarketShops = this.jxcMarketShopsMapper.queryShopsByUserId(user.getUserId());
                     //如果用户存在，返回1 ，如果不存在0
                     if(jxcMarketShops != null && jxcMarketShops.getId() != null){
                            result.setCode(1);
                            return result;
                     }else{
                            result.setCode(0);
                            return result;
                     }
              }else{
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("token不存在或者解析错误");
                     return result;
              }
       }


       @Override
       public ResponseMessage<JxcMarketShops> queryObjById(ShopDetailsVo shopDetailsVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(shopDetailsVo.getId() == null || "".equals(shopDetailsVo.getId())){
                     result.setCode(ErrorCodeConstants.PARAM_EMPTY_ERROR.getId());
                     result.setMessage("店铺id不能为空");
                     return result;
              }
              JxcMarketShops model=this.jxcMarketShopsMapper.selectByPrimaryKey(shopDetailsVo.getId());
              MarketShopsVo marketShopsVo = new MarketShopsVo();
              BeanUtils.copyProperties(model,marketShopsVo);
              //查询用户是否收藏
              if(shopDetailsVo.getUserId() != null){
                     JxcMarketCollectionRecord jxcMarketCollectionRecord = new JxcMarketCollectionRecord();
                     jxcMarketCollectionRecord.setShopId(shopDetailsVo.getId());
                     jxcMarketCollectionRecord.setUserId(shopDetailsVo.getUserId());
                     jxcMarketCollectionRecord.setCollectionDisabled(JxcMarketCollectionRecord.collectionDisabled.COLLECTION);
                     List<JxcMarketCollectionRecord> list = this.jxcMarketCollectionRecordMapper.selectListByConditions(jxcMarketCollectionRecord);
                     if(!CollectionUtils.isEmpty(list) && list.size() >0){
                            marketShopsVo.setCollectionStatus(JxcMarketCollectionRecord.collectionDisabled.COLLECTION);
                     }
              }
              //店铺人员的头像
              marketShopsVo.setShopBigImg(this.jxcMarketShopsMapper.queryHeadImgByUserId(this.jxcMarketShopsMapper.queryUserIdByShopId(shopDetailsVo.getId())));
              //查询店铺收藏人数
              marketShopsVo.setCollectionNumber(this.jxcMarketCollectionRecordMapper.queryShopCollectionCount(shopDetailsVo.getId()));
              AssertUtil.notNull(marketShopsVo,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(marketShopsVo);
              return result;
       }
}