package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.bean.JxcSite;
import com.weiwei.jixieche.bean.JxcSiteBankAccount;
import com.weiwei.jixieche.bean.JxcSiteCouponType;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.generate.MapDistance;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.JxcProjectMapper;
import com.weiwei.jixieche.mapper.JxcSiteMapper;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcSiteService;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 钟焕 
 * @Description
 * @Date 18:18 2019-08-19
 **/
@Service("jxcSiteService")
public class JxcSiteServiceImpl implements JxcSiteService {
       @Resource
       private JxcSiteMapper jxcSiteMapper;

       @Autowired
       private JxcProjectMapper jxcProjectMapper;

       /**
        * 前端分页查询消纳场表
        * @param pageNo  分页索引
        * @param pageSize  每页显示数量
        * @param jxcSite 查询条件
        * @return
        */
       @Override
       public ResponseMessage<JxcSite> findByPageForFront(Integer pageNo, Integer pageSize, JxcSite jxcSite) {
               ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(pageNo,pageSize);
              List<JxcSite> list = this.jxcSiteMapper.selectListByConditions(jxcSite);
              PageInfo<JxcSite> page = new PageInfo<>(list);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }


       /**
        * 添加消纳场表
        * @param t
        * @return
        */
       @Override
       public ResponseMessage<JxcSite> addObj(JxcSite t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcSiteMapper.insertSelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.ADD_ERORR.getDescript(),ErrorCodeConstants.ADD_ERORR.getId());
              return result;
       }


       /**
        * 修改消纳场表
        * @param t
        * @return
        */
       @Override
       public ResponseMessage<JxcSite> modifyObj(JxcSite t) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              int res=this.jxcSiteMapper.updateByPrimaryKeySelective(t);
              AssertUtil.numberGtZero(res,ErrorCodeConstants.EDIT_ERORR.getDescript(),ErrorCodeConstants.EDIT_ERORR.getId());
              return result;
       }


       /**
        * 根据ID查询消纳场表
        * @param id
        * @return
        */
       @Override
       public ResponseMessage<JxcSite> queryObjById(int id) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              JxcSite model=this.jxcSiteMapper.selectByPrimaryKey(id);
              AssertUtil.notNull(model,ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(),ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
              result.setData(model);
              return result;
       }

       /**
        * 承租方查询我的消纳场列表
        * @param user
        * @return
        */
       @Override
       public ResponseMessage selectMySiteList(AuthorizationUser user){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              Map<String,Object> map = new HashMap<>(1);
              List<MySiteListVo> mySiteList = jxcSiteMapper.selectMySiteListByUserId(user.getUserId());
              List<Integer> siteIdList = jxcSiteMapper.selectSiteIdList(user.getUserId());
              if(mySiteList != null && mySiteList.size() > 0){
                     for (MySiteListVo vo : mySiteList){
                            vo.setState(0);
                            if(siteIdList != null && siteIdList.size() > 0){
                                   for(Integer siteId : siteIdList) {
                                          if (vo.getSiteId().equals(siteId)){
                                                 vo.setState(1);
                                          }

                                   }
                            }
                            //好土券张数
                            Integer goodSoil = jxcSiteMapper.countSoilCoupon(user.getUserId(), vo.getSiteId(), JxcSiteCouponType.CouponTypeSite.GOOD_SOIL);
                            vo.setGoodSoilTicket(goodSoil);
                            //坏土券张数
                            Integer badSoil = jxcSiteMapper.countSoilCoupon(user.getUserId(), vo.getSiteId(), JxcSiteCouponType.CouponTypeSite.BAD_SOIL);
                            vo.setBadSoilTicket(badSoil);
                     }
              }
              map.put("mySiteList",mySiteList);
              result.setData(map);
              return result;
       }

       /**
        * 查询更多消纳场
        * @param user
        * @param jxcSite
        * @return
        */
       @Override
       public ResponseMessage selectMoreSiteList(AuthorizationUser user,JxcSite jxcSite){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(jxcSite.getPageNo(),jxcSite.getPageSize());
              String latitude = jxcSite.getLatitude();
              String longitude = jxcSite.getLongitude();
              jxcSite.setLatitude(null);
              jxcSite.setLongitude(null);
              //开放城市校验
              Map<String, Object> districtCheck = jxcProjectMapper.checkDistrictCode(jxcSite.getCityCode());
              if (districtCheck == null) {
                     return new ResponseMessage(ErrorCodeConstants.PARAM_TYPE_UNKNOW.getId(), "无此地区");
              }
              Integer cityCode = jxcProjectMapper.checkCityCode(jxcSite.getCityCode());
              String str = "0";
              if (str.equals(String.valueOf(districtCheck.get("isOpened")))) {
                     //在验证一下是否传过来的是市ID
                     if(cityCode == null && cityCode.equals(0)) {
                            return new ResponseMessage(ErrorCodeConstants.PARAM_TYPE_UNKNOW.getId(), "该地区所属城市尚未开放");
                     }
              }else {
                     cityCode = Integer.valueOf(String.valueOf(districtCheck.get("pid")));
              }

              //设置城市编码
              jxcSite.setCityCode(cityCode);
              List<JxcSite> list = this.jxcSiteMapper.selectListByConditions(jxcSite);
              List<MoreSiteListVo> moreSiteList = new ArrayList<>();
              if(list != null && list.size() > 0){
                     for (JxcSite site : list){
                            MoreSiteListVo moreSiteListVo = getMoreSiteListVo(user,site,latitude,longitude);
                            //查询消纳券起价
                            BigDecimal lowerPrice = jxcSiteMapper.getLowerPrice(site.getId());
                            moreSiteListVo.setPriceStart(lowerPrice.toString());
                            moreSiteList.add(moreSiteListVo);
                     }
              }
              PageInfo<MoreSiteListVo> page = new PageInfo<>(moreSiteList);
              result.setData(new PageUtils<>(page).getPageViewDatatable());
              return result;
       }

       /**
        * 查询消纳场详情
        * @param user
        * @param jxcSite
        * @return
        */
       @Override
       public ResponseMessage querySiteDetailById(AuthorizationUser user,JxcSite jxcSite){
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              //查询消纳场基本信息
              String latitude = jxcSite.getLatitude();
              String longitude = jxcSite.getLongitude();
              jxcSite.setLatitude(null);
              jxcSite.setLongitude(null);
              JxcSite site = this.jxcSiteMapper.selectByPrimaryKey(jxcSite.getId());
              MoreSiteListVo moreSiteListVo = new MoreSiteListVo();
              if(site != null ){
                     moreSiteListVo.setSiteName(site.getSiteName());
                     moreSiteListVo.setSiteAddress(site.getSiteAddress());
                     moreSiteListVo.setPhone(site.getPhone());
                     moreSiteListVo.setShoulder(site.getShoulder());
                     moreSiteListVo = getMoreSiteListVo(user,site,latitude,longitude);
                     //购买说明
                     moreSiteListVo.setBuyDescription(site.getBuyDescription());
                     if(site.getOpenFlag() == 0){
                            //停业公告
                            moreSiteListVo.setClosingNotice(site.getClosingNotice());
                            //停业时间
                            if(site.getClosingStartTime() != null){
                                   moreSiteListVo.setClosingStartTime(sdf.format(site.getClosingStartTime()));
                            }
                            if(site.getClosingEndTime() != null){
                                   moreSiteListVo.setClosingEndTime(sdf.format(site.getClosingEndTime()));
                            }
                     }
                     //好土券张数
                     Integer goodSoil = jxcSiteMapper.countSoilCoupon(user.getUserId(), site.getId(), JxcSiteCouponType.CouponTypeSite.GOOD_SOIL);
                     moreSiteListVo.setGoodSoilNum(goodSoil);
                     //坏土券张数
                     Integer badSoil = jxcSiteMapper.countSoilCoupon(user.getUserId(), site.getId(), JxcSiteCouponType.CouponTypeSite.BAD_SOIL);
                     moreSiteListVo.setBadSoilNum(badSoil);
                     //订单数量
                     Integer integer = jxcSiteMapper.countCouponOrder(user.getUserId(), site.getId());
                     moreSiteListVo.setSiteOrderNum(integer);
                     //消纳券类型
                     List<JxcSiteCouponType> jxcSiteCouponTypes = jxcSiteMapper.selectJxcSiteCouponTypeList(jxcSite.getId());
                     List<JxcSiteCouponType> list0 = new ArrayList<>();
                     List<JxcSiteCouponType> list1 = new ArrayList<>();
                     if(jxcSiteCouponTypes.size() > 0) {
                            for (JxcSiteCouponType siteCoupon : jxcSiteCouponTypes) {
                                   //坏土券
                                   if(siteCoupon.getCouponType().equals(0)){
                                          list0.add(siteCoupon);
                                   }
                                   //好土券
                                   if(siteCoupon.getCouponType().equals(1)){
                                          list1.add(siteCoupon);
                                   }
                            }
                     }
                     Map<String,Object> map = new HashMap<>(2);
                     map.put("badSoil",list0);
                     map.put("goodSoil",list1);
                     moreSiteListVo.setSiteCouponTypeList(map);

                     //账户名称
                     JxcSiteBankAccount jxcSiteBankAccount = jxcSiteMapper.queryJxcSiteBankAccountBySiteId(site.getId());
                     if(jxcSiteBankAccount != null){
                            moreSiteListVo.setBankAccountName(jxcSiteBankAccount.getCardRealName());
                            moreSiteListVo.setBankAccountCode(jxcSiteBankAccount.getBankCardNumber());
                     }

              }
              Map<String,Object> map = new HashMap<>(1);
              map.put("siteDetail",moreSiteListVo);
              return new ResponseMessage(map);
       }

       private MoreSiteListVo getMoreSiteListVo(AuthorizationUser user,JxcSite site,String latitude,String longitude){
              MoreSiteListVo moreSiteListVo = new MoreSiteListVo();
              moreSiteListVo.setSiteId(site.getId());
              moreSiteListVo.setSiteName(site.getSiteName());
              moreSiteListVo.setLatitude(site.getLatitude());
              moreSiteListVo.setLongitude(site.getLongitude());
              moreSiteListVo.setSiteAddress(site.getSiteAddress());
              moreSiteListVo.setIntoFlag(site.getIntoFlag());
              moreSiteListVo.setOpenFlag(site.getOpenFlag());
              moreSiteListVo.setShoulder(site.getShoulder());
              moreSiteListVo.setPhone(site.getPhone());
              JxcSiteBankAccount jxcSiteBankAccount = jxcSiteMapper.queryJxcSiteBankAccountBySiteId(site.getId());
              if(jxcSiteBankAccount != null){
                     moreSiteListVo.setBankAccountName(jxcSiteBankAccount.getCardRealName());
                     moreSiteListVo.setBankAccountCode(jxcSiteBankAccount.getBankCardNumber());
              }
              //查询是否正在使用或者使用过
              List<MySiteListVo> mySiteListVos = jxcSiteMapper.checkSiteState(user.getUserId(), site.getId());
              if(mySiteListVos != null && mySiteListVos.size() >0){
                     if (mySiteListVos.size() == 1){
                            moreSiteListVo.setState(mySiteListVos.get(0).getState());
                     }else {
                            moreSiteListVo.setState(1);
                     }
              }else{
                     moreSiteListVo.setState(-1);
              }
              //计算距离
              String distance = MapDistance.getDistance(latitude, longitude, site.getLatitude(), site.getLongitude());
              moreSiteListVo.setDistance(distance);
              return moreSiteListVo;
       }

       /**
        * 消纳场券下单
        * @param user
        * @param siteOrderVo
        * @return
        */
       @Override
       @Transactional(rollbackFor = Exception.class)
       public ResponseMessage addSiteOrder(AuthorizationUser user,JxcSiteOrderVo siteOrderVo){
              //生成订单ID
              SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
              String format = sdf.format(new Date());
              Long id = Long.valueOf(format);
              siteOrderVo.setId(id);
              siteOrderVo.setTenantryId(user.getUserId());
              try {
                     jxcSiteMapper.insertSiteOrder(siteOrderVo);
              } catch (Exception e) {
                     e.printStackTrace();
              }
              //插入消纳券订单详情表
              List<JxcSiteCouponOrderVo> siteCouponOrderList = siteOrderVo.getSiteCouponOrderList();
              if(siteCouponOrderList != null && siteCouponOrderList.size() > 0){
                     for (JxcSiteCouponOrderVo siteCouponOrder : siteCouponOrderList){
                            JxcSiteCouponType siteCouponType = jxcSiteMapper.getSiteCouponType(siteCouponOrder.getCouponTypeId());
                            siteCouponOrder.setPrice(siteCouponType.getPrice().toString());
                            siteCouponOrder.setOrderId(id);
                            try {
                                   jxcSiteMapper.insertSiteOrderDetail(siteCouponOrder);
                            } catch (Exception e) {
                                   e.printStackTrace();
                            }
                     }
              }
              Map<String,Object> map = new HashMap<>();
              //返回订单号
              map.put("orderId",siteOrderVo.getId());
              return new ResponseMessage(map);
       }

       /**
        * 查询某个消纳场的消纳券订单列表
        * @param siteOrderVo
        * @return
        */
       @Override
       public ResponseMessage selectSiteOrderListBySiteId(JxcSiteOrderVo siteOrderVo){
              ResponseMessage result = ResponseMessageFactory.newInstance();
              Map<String,Object> map = new HashMap<>(3);
              //统计总计购买张数
              Integer totalCouponCount = jxcSiteMapper.countSoilCoupon(siteOrderVo.getTenantryId(), siteOrderVo.getSiteId(), null);
              //统计支出总金额
              BigDecimal bigDecimal = jxcSiteMapper.sumRealAmount(siteOrderVo.getTenantryId(), siteOrderVo.getSiteId()).setScale(1,BigDecimal.ROUND_HALF_UP);
              map.put("totalCouponCount",totalCouponCount);
              map.put("totalPayMoney",bigDecimal.toString());
              //查询订单列表
              PageHelper.startPage(siteOrderVo.getPageNo(),siteOrderVo.getPageSize());
              List<JxcSiteOrderVo> siteOrderList = jxcSiteMapper.selectSiteOrderList(siteOrderVo);
              for (JxcSiteOrderVo siteOrderVo1 : siteOrderList){
                     //填充订单其他信息
                     List<JxcSiteCouponOrderVo> jxcSiteCouponOrderVos = jxcSiteMapper.selectSiteCouponOrderList(siteOrderVo1.getId());
                     siteOrderVo1.setSiteCouponOrderList(jxcSiteCouponOrderVos);
                     //对金额进行转换
                     if(siteOrderVo1.getRealAmount() != null) {
                            siteOrderVo1.setRealAmount(siteOrderVo1.getRealAmount().setScale(1, BigDecimal.ROUND_HALF_UP));
                     }
                     if(siteOrderVo1.getTotalAmount() != null){
                            siteOrderVo1.setTotalAmount((new BigDecimal(siteOrderVo1.getTotalAmount()).setScale(1,BigDecimal.ROUND_HALF_UP)).toString());
                     }
              }
              PageInfo<JxcSiteOrderVo> page = new PageInfo<>(siteOrderList);
              map.put("siteOrderList",new PageUtils<>(page).getPageViewDatatable());
              result.setData(map);
              return result;
       }


       /**
        * 根据消纳场券订单号查询订单详情
        * @param siteOrderVo
        * @return
        */
       @Override
       public ResponseMessage querySiteOrderByOrderId(JxcSiteOrderVo siteOrderVo) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              SiteOrderDetailVo siteOrderDetailVo = jxcSiteMapper.querySiteOrderDetailVoByOrderId(siteOrderVo.getId());
              if(siteOrderDetailVo != null){
                     List<JxcSiteCouponOrderVo> jxcSiteCouponOrderVos = jxcSiteMapper.selectSiteCouponOrderList(siteOrderVo.getId());
                     if(jxcSiteCouponOrderVos != null && jxcSiteCouponOrderVos.size() >0){
                            List<JxcSiteCouponOrderVo> list0 = new ArrayList<>();
                            List<JxcSiteCouponOrderVo> list1 = new ArrayList<>();
                            for(JxcSiteCouponOrderVo couponOrderVo : jxcSiteCouponOrderVos){
                                   //坏土券
                                   if(couponOrderVo.getCouponType().equals(0)){
                                          list0.add(couponOrderVo);
                                   }
                                   //好土券
                                   if(couponOrderVo.getCouponType().equals(1)){
                                          list1.add(couponOrderVo);
                                   }
                            }
                            Map<String,Object> map = new HashMap<>(2);
                            map.put("badSoil",list0);
                            map.put("goodSoil",list1);
                            siteOrderDetailVo.setSiteCouponOrderList(map);
                     }
                     if(siteOrderDetailVo.getOrderFlag().equals(SiteOrderDetailVo.orderFlag.YES_DEAL)){
                            siteOrderDetailVo.setGiveOutTime(jxcSiteMapper.getGiveOutTime(siteOrderVo.getId()));
                     }
                     //对金额进行转换
                     if(siteOrderDetailVo.getRealAmount() != null) {
                            siteOrderDetailVo.setRealAmount(siteOrderDetailVo.getRealAmount().setScale(1, BigDecimal.ROUND_HALF_UP));
                     }
                     if(siteOrderDetailVo.getTotalAmount() != null){
                            siteOrderDetailVo.setTotalAmount(siteOrderDetailVo.getTotalAmount().setScale(1,BigDecimal.ROUND_HALF_UP));
                     }
                     //账户名称
                     JxcSiteBankAccount jxcSiteBankAccount = jxcSiteMapper.queryJxcSiteBankAccountBySiteId(siteOrderDetailVo.getSiteId());
                     if(jxcSiteBankAccount != null){
                            siteOrderDetailVo.setBankAccountName(jxcSiteBankAccount.getCardRealName());
                            siteOrderDetailVo.setBankAccountCode(jxcSiteBankAccount.getBankCardNumber());
                     }

              }
              Map<String,Object> map = new HashMap<>(1);
              map.put("siteOrderDetail",siteOrderDetailVo);
              result.setData(map);
              return result;
       }

       /**
        * 取消消纳场券订单
        * @param siteOrderVo
        * @return
        */
       @Override
       @Transactional(rollbackFor = Exception.class)
       public ResponseMessage cancelSiteOrderById(JxcSiteOrderVo siteOrderVo){
              //检查订单是否是未发券（未处理）状态
              SiteOrderDetailVo siteOrderDetailVo = jxcSiteMapper.querySiteOrderDetailVoByOrderId(siteOrderVo.getId());
              if(siteOrderDetailVo.getOrderFlag().equals( SiteOrderDetailVo.orderFlag.NO_DEAL)){
                     jxcSiteMapper.cancelSiteOrder(siteOrderVo.getId());
                     return new ResponseMessage();
              }else if(siteOrderDetailVo.getOrderFlag().equals( SiteOrderDetailVo.orderFlag.YES_DEAL)) {
                     return new ResponseMessage(ErrorCodeConstants.CANCEL_FAIL.getId(),"订单取消失败，该订单已发券不能取消！");
              }
              return new ResponseMessage();
       }

       /**
        * 查询我的消纳券列表
        * @param siteOrderVo
        * @param flag 1:我的消纳券列表 2：历史消纳券列表
        * @return
        */
       @Override
       public ResponseMessage mySiteCoupon(JxcSiteOrderVo siteOrderVo,Integer flag) {
              ResponseMessage result = ResponseMessageFactory.newInstance();
              PageHelper.startPage(siteOrderVo.getPageNo(),siteOrderVo.getPageSize(),"sc.id ASC, st.capacity ASC");
              List<JxcSiteCouponVo> jxcSiteCouponVos = jxcSiteMapper.selectSiteCouponList(siteOrderVo, flag);
              Map<String,Object> map = new HashMap<>(3);
              //券的总张数
              Integer totalNum;
              totalNum = jxcSiteMapper.countSoilCouponNum(siteOrderVo, flag, null);
              map.put("totalNum",totalNum);
              //不同方量
              List<Integer> integers = jxcSiteMapper.queryCouponCapacity(siteOrderVo, flag);
              List<Map<String,Object>> capacityList = new ArrayList<>();
              if(integers != null && integers.size() >0){
                     for (Integer capacity : integers){
                            Map<String,Object> maps = new HashMap<>(2);
                            //统计不同方量的张数
                            Integer num = jxcSiteMapper.countSoilCouponNum(siteOrderVo, flag, capacity);
                            maps.put("capacity",capacity);
                            maps.put("num",num);
                            capacityList.add(maps);
                     }
              }
              map.put("capacityList",capacityList);
              PageInfo<JxcSiteCouponVo> page = new PageInfo<>(jxcSiteCouponVos);
              map.put("siteCouponList",new PageUtils<>(page).getPageViewDatatable());
              result.setData(map);
              return result;
       }

       /**
        * 获取消纳场名称
        * @author  liuy
       * @param userId
        * @return
        * @date    2019/10/7 18:57
        */
	@Override
	public ResponseMessage getSiteByUserId(Integer userId) {
	       JxcSite jxcSite = jxcSiteMapper.getSiteByUserId(userId);
           return new ResponseMessage(jxcSite);
	}
}