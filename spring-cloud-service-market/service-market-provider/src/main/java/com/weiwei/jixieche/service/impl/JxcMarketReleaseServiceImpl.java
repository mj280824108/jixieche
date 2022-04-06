package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JxcUserFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcMarketReleaseService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.utils.MachineBuyUtils;
import com.weiwei.jixieche.verify.AssertUtil;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName dd
 * @Description TODO
 * @Author houji
 * @Date 2019/8/21 14:00
 * @Version 2.0
 **/
@Service("jxcMarketReleaseService")
public class JxcMarketReleaseServiceImpl implements JxcMarketReleaseService {
       @Resource
       private JxcMarketReleaseMapper jxcMarketReleaseMapper;

       @Resource
       private JxcMarketMachineTypeMapper jxcMarketMachineTypeMapper;

       @Resource
       private JxcMarketBrandMapper jxcMarketBrandMapper;

       @Resource
       private JxcMarketResourceTypeMapper jxcMarketResourceTypeMapper;

       @Autowired
       private MachineBuyUtils machineBuyUtils;

       @Resource
       private JxcMarketCollectionRecordMapper jxcMarketCollectionRecordMapper;

       @Resource
       private JxcMarketShopsMapper jxcMarketShopsMapper;

       @Autowired
       private JxcUserFeign jxcUserFeign;


       /**
        * 发布机械租赁资源
        * @param user
        * @param jxcMarketRelease
        * @return
        */
       @Transactional
       @Override
       public ResponseMessage<JxcMarketRelease> addObj(AuthorizationUser user, JxcMarketRelease jxcMarketRelease) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              if(user == null || user.getUserId() == null){
                     result.setCode(ErrorCodeConstants.PARAM_MUST_EMPTY.getId());
                     result.setMessage("token不存在或解析错误");
                     return result;
              }
              if(jxcMarketRelease.getId() != null && jxcMarketRelease.getId() >0){
                     int res=this.jxcMarketReleaseMapper.updateByPrimaryKeySelective(jxcMarketRelease);
                     AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
                     return result;
              }else {
                     jxcMarketRelease.setShopKeeperId(user.getUserId());
                     //必填系统参数校验
                     String resStr = machineBuyUtils.verifyParam(jxcMarketRelease);
                     if (resStr != null) {
                            result.setCode(ErrorCodeConstants.PARAM_MUST_EMPTY.getId());
                            result.setMessage(resStr);
                            return result;
                     } else {
                            //发布商品之前查看用户店铺是否存在
                            JxcMarketShops jxcMarketShops = this.jxcMarketShopsMapper.queryShopsByUserId(user.getUserId());
                            if (jxcMarketShops == null || jxcMarketShops.getId() == null) {
                                   ResponseMessage<UserInfoVo> userRes = this.jxcUserFeign.queryUserInfo(user.getUserId());
                                   if (userRes.getCode() == 200) {
                                          UserInfoVo userInfoVo = (UserInfoVo) userRes.getData();
                                          jxcMarketShops = new JxcMarketShops();
                                          jxcMarketShops.setShopKeeperId(user.getUserId());
                                          jxcMarketShops.setShopBigImg(userInfoVo.getHeadImg());
                                          jxcMarketShops.setShopName(userInfoVo.getRealName() + "的店铺");
                                          this.jxcMarketShopsMapper.insertSelective(jxcMarketShops);
                                          jxcMarketRelease.setShopsId(jxcMarketShops.getId());
                                          //插入市的数据
                                          if(jxcMarketRelease.getDistrictId() != null){
                                                 jxcMarketRelease.setCityId(this.jxcMarketReleaseMapper.queryCityIdByDistrictId(jxcMarketRelease.getDistrictId()));
                                          }
                                          Integer res = this.jxcMarketReleaseMapper.insertSelective(jxcMarketRelease);
                                          if (res != null && res > 0) {
                                                 result.setData((Integer) jxcMarketRelease.getId());
                                          } else {
                                                 result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
                                                 result.setMessage("发布机械失败");
                                                 return result;
                                          }
                                   } else {
                                          result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
                                          result.setMessage("店铺添加失败，发布机械失败");
                                          return result;
                                   }
                            } else {
                                   jxcMarketRelease.setShopsId(jxcMarketShops.getId());
                                   //插入市的数据
                                   if(jxcMarketRelease.getDistrictId() != null){
                                          jxcMarketRelease.setCityId(this.jxcMarketReleaseMapper.queryCityIdByDistrictId(jxcMarketRelease.getDistrictId()));
                                   }
                                   Integer res = this.jxcMarketReleaseMapper.insertSelective(jxcMarketRelease);
                                   if (res != null && res > 0) {
                                          result.setData((Integer) jxcMarketRelease.getId());
                                   } else {
                                          result.setCode(ErrorCodeConstants.ADD_ERORR.getId());
                                          result.setMessage("发布机械失败");
                                          return result;
                                   }
                            }
                            return result;
                     }
              }
       }

       /**
        * 前端分页查询市场发布(出售)出租信息表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcMarketRelease 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketRelease> findByPageForFront(Integer pageNo, Integer pageSize, JxcMarketRelease jxcMarketRelease) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize,"create_time DESC");
              //机械求购不分新机和二手机
              if(jxcMarketRelease.getRealeseType() != null && jxcMarketRelease.getRealeseType() ==1){
                  jxcMarketRelease.setNewDegreeType(null);
              }
              List<JxcMarketRelease> list = this.jxcMarketReleaseMapper.selectListByConditions(jxcMarketRelease);
              if(!CollectionUtils.isEmpty(list)){
                     list.forEach(releaseMachine -> {
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

              PageInfo<JxcMarketRelease> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 添加市场发布(出售)出租信息表
        * @param  jxcMarketRelease
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketRelease> addObj(JxcMarketRelease jxcMarketRelease) {
              return null;
       }

       /**
        * 修改市场发布(出售)出租信息表
        * @param jxcMarketRelease
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketRelease> modifyObj(JxcMarketRelease jxcMarketRelease) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcMarketReleaseMapper.updateByPrimaryKeySelective(jxcMarketRelease);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }

       @Override
       public ResponseMessage<JxcMarketRelease> queryObjById(int id) {
              return null;
       }

       /**
        * 根据ID查询市场发布(出售)出租信息表
        * @param machineDetailsVo
        * @return
        */
       @Override
       public ResponseMessage<JxcMarketRelease> queryObjById(MachineDetailsVo machineDetailsVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcMarketRelease model=this.jxcMarketReleaseMapper.selectByPrimaryKey(machineDetailsVo.getId());
              //把一个实体的值复制给另外一个实体
              MarketReleaseMachineDetailVo marketReleaseMachineDetailVo = new MarketReleaseMachineDetailVo();
              BeanUtils.copyProperties(model, marketReleaseMachineDetailVo);
              if(model.getBrandTypeId() != null){
                     marketReleaseMachineDetailVo.setBrandTypeName(this.jxcMarketBrandMapper.selectByPrimaryKey(model.getBrandTypeId()).getBrandName());
              }
              if(model.getSourceId() != null){
                     marketReleaseMachineDetailVo.setSourceName(this.jxcMarketResourceTypeMapper.selectByPrimaryKey(model.getSourceId()).getResourceName());
              }
              if(model.getMachineTypeId() != null){
                     marketReleaseMachineDetailVo.setMachineTypeName(this.jxcMarketMachineTypeMapper.selectByPrimaryKey(model.getMachineTypeId()).getMachineName());
              }
              if(model.getDistrictId() != null){
                     AreaNameVo areaNameVo = this.jxcMarketReleaseMapper.queryAreaName(model.getDistrictId());
                     marketReleaseMachineDetailVo.setAreaName(areaNameVo.getPName()+areaNameVo.getCName()+areaNameVo.getDName());
              }
              //判断用户是否收藏
              if(machineDetailsVo.getUserId() != null){
                     JxcMarketCollectionRecord jxcMarketCollectionRecord = new JxcMarketCollectionRecord();
                     jxcMarketCollectionRecord.setCollectionType(JxcMarketCollectionRecord.collectionType.MACHINE);
                     jxcMarketCollectionRecord.setMarkeRealseId(machineDetailsVo.getId());
                     jxcMarketCollectionRecord.setUserId(machineDetailsVo.getUserId());
                     jxcMarketCollectionRecord.setCollectionDisabled(JxcMarketCollectionRecord.collectionDisabled.COLLECTION);
                     List<JxcMarketCollectionRecord> collectionRecords = this.jxcMarketCollectionRecordMapper.selectListByConditions(jxcMarketCollectionRecord);
                     if(!CollectionUtils.isEmpty(collectionRecords)){
                            jxcMarketCollectionRecord = collectionRecords.get(0);
                            marketReleaseMachineDetailVo.setUserCollectionStatus(MarketReleaseMachineDetailVo.UserCollectionStatus.COLLECTION);
                     }else{
                            marketReleaseMachineDetailVo.setUserCollectionStatus(MarketReleaseMachineDetailVo.UserCollectionStatus.UNCOLLECTION);
                     }
              }else{
                     marketReleaseMachineDetailVo.setUserCollectionStatus(MarketReleaseMachineDetailVo.UserCollectionStatus.UNCOLLECTION);
              }
              AssertUtil.notNull(marketReleaseMachineDetailVo,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(marketReleaseMachineDetailVo);
              return result;
       }

       /**
        * 查询市场交易列表
        * @param marketTradeTabVo
        * @return
        */
       @Override
       public ResponseMessage queryMarketTrade(MarketTradeTabVo marketTradeTabVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              String orderBy = " create_time" + " DESC";
              if(marketTradeTabVo.getSourcePriceOrder() != null && marketTradeTabVo.getSourcePriceOrder() == 1){
                     //资源交易求购
                     if(marketTradeTabVo.getSourceTrade().getSourceTradeType().equals(5)){
                            orderBy = " source_buy_down_price ASC";
                     }else {
                            orderBy = " source_sale_price ASC";
                     }
              }else if(marketTradeTabVo.getSourcePriceOrder() != null && marketTradeTabVo.getSourcePriceOrder() == 2){
                     if(marketTradeTabVo.getSourceTrade().getSourceTradeType().equals(5)) {
                            orderBy = " source_buy_down_price DESC";
                     }else {
                            orderBy = " source_sale_price DESC";
                     }
              }else if(marketTradeTabVo.getMachinePriceOrder() != null && marketTradeTabVo.getMachinePriceOrder() == 2){
                     orderBy = " sale_price DESC";
              }else if(marketTradeTabVo.getMachinePriceOrder() != null && marketTradeTabVo.getMachinePriceOrder() == 1){
                     orderBy = " sale_price ASC";
              }

              PageHelper.startPage(marketTradeTabVo.getPageNo(),marketTradeTabVo.getPageSize(),orderBy);

              List<JxcMarketReleaseVo> releaseList = this.jxcMarketReleaseMapper.queryMarketTrade(marketTradeTabVo);
              releaseList.forEach(market -> {
                     //当租赁价格类型不为空，则赋值给取值类型
                     if(marketTradeTabVo.getRentTrade().getRentLeaseType() != null){
                            market.setRentType(marketTradeTabVo.getRentTrade().getRentLeaseType());
                     }
                     //查询资源名称
                     if(market.getSourceId() != null){
                            market.setSourceName(this.jxcMarketResourceTypeMapper.selectByPrimaryKey(market.getSourceId()).getResourceName());
                     }
                     //查询品牌名称
                     if(market.getBrandTypeId() != null){
                            market.setBrandTypeName(this.jxcMarketBrandMapper.selectByPrimaryKey(market.getBrandTypeId()).getBrandName());
                     }
                     //根据市或者区查询省市区(如:湖北省武汉市洪山区)
                     if(market.getDistrictId() != null){
                            AreaNameVo areaNameVo = this.jxcMarketReleaseMapper.queryAreaName(market.getDistrictId());
                            market.setAreaName(areaNameVo.getPName()+areaNameVo.getCName()+areaNameVo.getDName());
                     }
                     //查询机械类型名称
                     if(market.getMachineTypeId() != null){
                            market.setMachineTypeName(this.jxcMarketMachineTypeMapper.selectByPrimaryKey(market.getMachineTypeId()).getMachineName());
                     }
                     //判断是否新上架
                     if(DateUtils.format(market.getCreateTime()).equals(DateUtils.format(new Date()))){
                            market.setNewUpperLower(1);
                     }
              });
              PageInfo<JxcMarketReleaseVo> page = new PageInfo<>(releaseList);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询市场搜索的Tab
        * @return
        */
       @Override
       public ResponseMessage queryMarketSearchTab() {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              //开始组装数据
              MarketTabListVo marketTabListVo = new MarketTabListVo();
              MachineTradeTypeVo machineTradeTypeVo = new MachineTradeTypeVo();
              //机械交易类型：1--机械求购 2--机械出售
              machineTradeTypeVo.setId(2);
              machineTradeTypeVo.setName("出售");
              marketTabListVo.getMachineTradeType().add(machineTradeTypeVo);
              machineTradeTypeVo = new MachineTradeTypeVo();
              machineTradeTypeVo.setId(1);
              machineTradeTypeVo.setName("求购");
              marketTabListVo.getMachineTradeType().add(machineTradeTypeVo);

              //机械类型
              MachineTypeChildVo machineTypeChildVo = new MachineTypeChildVo();
              marketTabListVo.getMachineType().addAll(this.jxcMarketMachineTypeMapper.queryChildMachineType(machineTypeChildVo));
              //机械交易新机/二手机
              MachineNewOldVo machineNewOldVo = new MachineNewOldVo();
              machineNewOldVo.setId(0);
              machineNewOldVo.setName("全部");
              machineNewOldVo.setId(1);
              machineNewOldVo.setName("新机");
              marketTabListVo.getMachineNewOld().add(machineNewOldVo);
              machineNewOldVo = new MachineNewOldVo();
              machineNewOldVo.setId(2);
              machineNewOldVo.setName("二手机");
              marketTabListVo.getMachineNewOld().add(machineNewOldVo);
              //机械搜索价格
              MachinePriceVo machinePriceVo = new MachinePriceVo();
              machinePriceVo.setId(1);
              machinePriceVo.setName("由低到高");
              marketTabListVo.getMachinePrice().add(machinePriceVo);
              machinePriceVo = new MachinePriceVo();
              machinePriceVo.setId(2);
              machinePriceVo.setName("由高到低");
              marketTabListVo.getMachinePrice().add(machinePriceVo);

              //租赁交易类型:3--机械求租 4--机械出租
              RentTradeTypeVo rentTradeTypeVo = new RentTradeTypeVo();
              rentTradeTypeVo.setId(3);
              rentTradeTypeVo.setName("求租");
              marketTabListVo.getRentTradeType().add(rentTradeTypeVo);
              rentTradeTypeVo = new RentTradeTypeVo();
              rentTradeTypeVo.setId(4);
              rentTradeTypeVo.setName("出租");
              marketTabListVo.getRentTradeType().add(rentTradeTypeVo);
              //租赁交易结算:出租(求租)方式(1--包月  2--包天 3--小时)
              RentLeaseTypeVo rentLeaseTypeVo = new RentLeaseTypeVo();
              rentLeaseTypeVo.setId(1);
              rentLeaseTypeVo.setName("月结");
              marketTabListVo.getRentLeaseType().add(rentLeaseTypeVo);
              rentLeaseTypeVo = new RentLeaseTypeVo();
              rentLeaseTypeVo.setId(2);
              rentLeaseTypeVo.setName("日结");
              marketTabListVo.getRentLeaseType().add(rentLeaseTypeVo);
              rentLeaseTypeVo = new RentLeaseTypeVo();
              rentLeaseTypeVo.setId(3);
              rentLeaseTypeVo.setName("小时");
              marketTabListVo.getRentLeaseType().add(rentLeaseTypeVo);
              //品牌
              JxcMarketBrand jxcMarketBrand = new JxcMarketBrand();
              jxcMarketBrand.setCode("0");
              marketTabListVo.getBrand().addAll(this.jxcMarketBrandMapper.selectListByConditions(jxcMarketBrand));
              //资源类型
              marketTabListVo.getSourceType().addAll(this.jxcMarketResourceTypeMapper.queryChildSourceTypeList());
              //交易类型:5--资源求购 6--资源出售
              SourceTradeTypeVo sourceTradeTypeVo = new SourceTradeTypeVo();
              sourceTradeTypeVo.setId(5);
              sourceTradeTypeVo.setName("求购");
              marketTabListVo.getSourceTradeType().add(sourceTradeTypeVo);
              sourceTradeTypeVo = new SourceTradeTypeVo();
              sourceTradeTypeVo.setId(6);
              sourceTradeTypeVo.setName("出售");
              marketTabListVo.getSourceTradeType().add(sourceTradeTypeVo);
              result.setData(marketTabListVo);
              return result;
       }
}