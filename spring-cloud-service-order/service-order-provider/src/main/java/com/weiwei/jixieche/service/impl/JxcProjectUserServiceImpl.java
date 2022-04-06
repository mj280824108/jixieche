package com.weiwei.jixieche.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.EagleEyesFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.constant.UserRoleContants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.generate.MapDistance;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcProjectUserService;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 承租方管理员
 *
 * @author liuy
 * @date 2019-08-26 11:03
 */
@Service
public class JxcProjectUserServiceImpl implements JxcProjectUserService {

    @Resource
    private JxcProjectUserMapper jxcProjectUserMapper;

    @Resource
    private JxcMachineRouteMapper jxcMachineRouteMapper;

    @Resource
    private DriverMapper driverMapper;

    @Autowired
    private JxcTransCostsMapper jxcTransCostsMapper;

    @Autowired
    private JxcSiteMapper jxcSiteMapper;

    @Autowired
    private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

    @Autowired
    private EagleEyesFeign eagleEyesFeign;

    /**
     * 承方管理员扫一扫
     *
     * @param machineCarNo
     * @return
     * @author liuy
     * @date 2019/8/26 11:02
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseMessage projectUserScan(AuthorizationUser user,String machineCarNo, Integer flag) {
        if (StringUtils.isBlank(machineCarNo)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "车牌号码不能为空");
        }
        if (null == flag) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "参数flag不能为空");
        }
        //打卡状态：1:打卡成功(已入驻); 2:打卡成功(已入驻),消纳券余额不足10张;
        // 3:打卡成功(未入驻) 4:消纳券余额不足 5：消纳场停业中; 6:打卡失败; 7:上一趟车辆有未核销消纳劵
        //返回结果
        JxcProjectUserScanVo jxcProjectUserScanVo = new JxcProjectUserScanVo();
        jxcProjectUserScanVo.setMachineCarNo(machineCarNo);
        PageHelper.clearPage();
        Long orderIdByMachineNo = jxcProjectUserMapper.getOrderIdByMachineNo(machineCarNo);
        if(orderIdByMachineNo == null){
            return new ResponseMessage(ErrorCodeConstants.QUERY_PARAM_EMPTY.getId(),"该车辆没有进行中的订单");
        }
        //验证扫码的项目ID或者承租方ID是否与车辆所接订单的项目ID一致
        JxcProjectOrderVo jxcProjectOrderVo = jxcProjectUserMapper.getJxcProjectOrderVoById(orderIdByMachineNo);
        //如果是管理员
        if(user.getRoleId().equals(UserRoleContants.TEN_MAN.getRoleId())){
            //比对项目ID
            Integer projectId = jxcProjectUserMapper.getProjectIdByTenAdmin(user.getUserId());
            if(!projectId.equals(jxcProjectOrderVo.getProjectId())){
                return new ResponseMessage(ErrorCodeConstants.QUERY_ERORR.getId(),"该车辆没有接您的订单！");
            }
        }
        //如果是承租方
        if(user.getRoleId().equals(UserRoleContants.TEN.getRoleId())){
            if(!user.getUserId().equals(jxcProjectOrderVo.getUserId())){
                return new ResponseMessage(ErrorCodeConstants.QUERY_ERORR.getId(),"该车辆没有接您的订单！");
            }
        }
        //承租方订单ID
        jxcProjectUserScanVo.setTenantryOrderId(orderIdByMachineNo);

        //查询是否有未核销消纳劵
        List<Long> couponCodeList = jxcProjectUserMapper.getCouponByMachineCarNo(machineCarNo);

        //查询施工场地的打卡记录
        JxcMachineRouteVo jxcMachineRouteVo = new JxcMachineRouteVo();
        jxcMachineRouteVo.setMachineCarNo(machineCarNo);
        PageHelper.clearPage();
        JxcMachineRoute machineRoute = jxcMachineRouteMapper.queryJxcMachineRoute(jxcMachineRouteVo);

        //flag 1:正常; 2:有未核销消纳劵(已入驻);上一趟未结束(未入驻)
        if (flag == 1) {
            if ((couponCodeList != null && couponCodeList.size() >0) || machineRoute != null) {
                //有未核销消纳劵
                jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS7);
                return new ResponseMessage(jxcProjectUserScanVo);
            }
        }
        JxcProjectUserVo jxcProjectUserVo = jxcProjectUserMapper.getInfoByMachineCarNo(machineCarNo);
        if (jxcProjectUserVo != null) {
            //消纳场名称
            jxcProjectUserScanVo.setSiteName(jxcProjectUserVo.getSiteName());

            //是否停业（1：营业 0：停业）
            if (jxcProjectUserVo.getOpenFlag() == 0) {
                jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS5);
                //停业公告
                jxcProjectUserScanVo.setClosingNotice(jxcProjectUserVo.getClosingNotice());
                jxcProjectUserScanVo.setMachineCarNo(machineCarNo);
                return new ResponseMessage(jxcProjectUserScanVo);
            }

            //判断该车辆是否有司机上班中
            Integer machineId = jxcProjectUserMapper.getMachineIdByMachineCardNo(machineCarNo);
            if(machineId == null){
                return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"该机械不存在");
            }
            Integer driverId = jxcProjectUserMapper.getDriverIdByMachineId(machineId);
            if (null == driverId) {
                //未上班中
                jxcProjectUserScanVo.setWorkStatus(JxcProjectUserScanVo.workStatus.WORKOUTSTATUS);
                //正在跑趟的司机没有打上班卡时写入待办事项中
                JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
                jxcWaitHandleItems.setMachineId(jxcProjectUserVo.getMachineId());
                jxcWaitHandleItems.setOwnerOrderId(jxcProjectUserVo.getOwnerOrderId());
                jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE11);
                int res = jxcWaitHandleItemsMapper.checkWaitHandleItemById(jxcWaitHandleItems);
                if(res == 0) {
                    jxcWaitHandleItems.setUserId(jxcProjectUserVo.getOwnerUserId());
                    jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
                }
            } else {
                jxcProjectUserScanVo.setWorkStatus(JxcProjectUserScanVo.workStatus.WORKINSTATUS);
            }

            //消纳场是否入驻平台（1：是 0：否）
            if (jxcProjectUserVo.getIntoFlag() == 1) {
                //获取消纳劵电子券剩余数量
                Integer count = jxcProjectUserMapper.getTotalCountCoupon(jxcProjectUserVo);
                if (count > 0) {
                    if (count > 10) {
                        jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS1);
                    } else {
                        //剩余数量不足10张
                        jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS2);
                    }
                    JxcMachineRoute jxcMachineRoute = new JxcMachineRoute();
                    long routeId = IDGenerator.getInstance().next();
                    jxcMachineRoute.setId(routeId);
                    jxcMachineRoute.setOwnerOrderId(jxcProjectUserVo.getOwnerOrderId());
                    jxcMachineRoute.setMachineId(jxcProjectUserVo.getMachineId());
                    jxcMachineRoute.setDriverId(driverId);
                    jxcMachineRoute.setTenantryOrderId(jxcProjectUserVo.getTenantryOrderId());
                    jxcMachineRoute.setStartTime(new Date());
                    jxcMachineRoute.setSiteId(jxcProjectUserVo.getSiteId());
                    //发卡类型: 1:实体卡; 2:消纳券
                    jxcMachineRoute.setCardType(2);
                    int res = jxcMachineRouteMapper.insertSelective(jxcMachineRoute);
                    if (res > 0) {
                        //获取消纳劵电子券号
                        Long couponId = jxcProjectUserMapper.getCouponNumById(jxcProjectUserVo);
                        jxcProjectUserScanVo.setCouponId(couponId);
                        //消纳场地址
                        jxcProjectUserScanVo.setSiteAddress(jxcProjectUserVo.getSiteAddress());
                        jxcProjectUserScanVo.setMachineCarNo(machineCarNo);
                        //消纳券类型 (0:坏土 1:好土)
                        jxcProjectUserScanVo.setCouponType(jxcProjectUserVo.getCouponType());
                        jxcProjectUserScanVo.setCapacity(jxcProjectUserVo.getCapacity());
                        //剩余张数
                        jxcProjectUserScanVo.setCount(count);
                        //更新消纳劵电子券
                        jxcProjectUserMapper.updateCouponById(couponId, jxcProjectUserVo.getMachineId(), jxcProjectUserVo.getProjectId());
                        return new ResponseMessage(jxcProjectUserScanVo);
                    }
                } else {
                    //消纳券余额不足
                    jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS4);
                    return new ResponseMessage(jxcProjectUserScanVo);
                }
            }
            //未入驻
            else {
                JxcMachineRoute jxcMachineRoute = new JxcMachineRoute();
                long routeId = IDGenerator.getInstance().next();
                jxcMachineRoute.setId(routeId);
                jxcMachineRoute.setOwnerOrderId(jxcProjectUserVo.getOwnerOrderId());
                jxcMachineRoute.setMachineId(jxcProjectUserVo.getMachineId());
                jxcMachineRoute.setDriverId(driverId);
                jxcMachineRoute.setTenantryOrderId(jxcProjectUserVo.getTenantryOrderId());
                jxcMachineRoute.setStartTime(new Date());
                jxcMachineRoute.setSiteId(jxcProjectUserVo.getSiteId());
                //发卡类型: 1:实体卡; 2:消纳券
                jxcMachineRoute.setCardType(1);
                jxcMachineRouteMapper.insertSelective(jxcMachineRoute);
                //未入驻平台
                jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS3);
                jxcProjectUserScanVo.setMachineCarNo(machineCarNo);
                return new ResponseMessage(jxcProjectUserScanVo);
            }
        } else {
            //打卡失败
            jxcProjectUserScanVo.setClockStatus(JxcProjectUserScanVo.clockStatus.CLOCKSTATUS6);
            return new ResponseMessage(jxcProjectUserScanVo);
        }
        return new ResponseMessage(jxcProjectUserScanVo);
    }

    /**
     * 确认补上一趟的消纳场卡
     * @param machineCarNo
     * @return
     */
    @Override
    public ResponseMessage confirmClock(String machineCarNo){
        if(VerifyStr.isEmpty(machineCarNo)){
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(),"车牌号不能为空");
        }
        JxcMachineRouteVo jxcMachineRouteVo = new JxcMachineRouteVo();
        jxcMachineRouteVo.setMachineCarNo(machineCarNo);
        PageHelper.clearPage();
        JxcMachineRoute machineRoute = jxcMachineRouteMapper.queryJxcMachineRoute(jxcMachineRouteVo);
        //确定结束上一趟记录
        if (machineRoute != null) {
            //更新上一趟记录为异常(消纳场地漏打卡)
            JxcMachineRoute jxcMachineRoute = new JxcMachineRoute();
            jxcMachineRoute.setId(machineRoute.getId());
            jxcMachineRoute.setAbnormalType(2);
            jxcMachineRouteMapper.updateByPrimaryKeySelective(jxcMachineRoute);
            //加入待办事项
            JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
            jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE2);
            jxcWaitHandleItems.setProjectOrderId(machineRoute.getTenantryOrderId());
            jxcWaitHandleItems.setOwnerOrderId(machineRoute.getOwnerOrderId());
            jxcWaitHandleItems.setMachineId(machineRoute.getMachineId());
            jxcWaitHandleItems.setProjectName(machineRoute.getProjectName());
            jxcWaitHandleItems.setUserId(machineRoute.getTenantryId());
            jxcWaitHandleItems.setDriverId(machineRoute.getDriverId());
            jxcWaitHandleItems.setRouteId(machineRoute.getId());

            jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
        }
        //查询是否有未核销消纳劵
        List<Long> couponCodeList = jxcProjectUserMapper.getCouponByMachineCarNo(machineCarNo);
        if (couponCodeList != null && couponCodeList.size() >0) {
            for (Long couponId : couponCodeList) {
                //更新消纳劵为未使用
                jxcProjectUserMapper.updateSiteCouponById(couponId);
            }
        }
        return new ResponseMessage();
    }

    /**
     * 实体卡
     *
     * @param machineCarNo
     * @return
     * @author liuy
     * @date 2019/8/26 14:47
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage entityCardClock(String machineCarNo) {
        JxcProjectUserVo jxcProjectUserVo = jxcProjectUserMapper.getInfoByMachineCarNo(machineCarNo);
        if (jxcProjectUserVo != null) {
            JxcMachineRoute jxcMachineRoute = new JxcMachineRoute();
            long routeId = IDGenerator.getInstance().next();
            jxcMachineRoute.setId(routeId);
            jxcMachineRoute.setOwnerOrderId(jxcProjectUserVo.getOwnerOrderId());
            jxcMachineRoute.setMachineId(jxcProjectUserVo.getMachineId());
            jxcMachineRoute.setDriverId(jxcProjectUserVo.getDriverId());
            jxcMachineRoute.setTenantryOrderId(jxcProjectUserVo.getTenantryOrderId());
            jxcMachineRoute.setStartTime(new Date());
            jxcMachineRoute.setSiteId(jxcProjectUserVo.getSiteId());
            //发卡类型: 1:实体卡; 2:消纳券
            jxcMachineRoute.setCardType(1);
            try {
                jxcMachineRouteMapper.insertSelective(jxcMachineRoute);
                //如有电子券  也一并退回
                //查询是否有未核销消纳劵
                List<Long> couponCodeList = jxcProjectUserMapper.getCouponByMachineCarNo(machineCarNo);
                if (couponCodeList != null && couponCodeList.size() >0) {
                    for (Long couponId : couponCodeList) {
                        //更新消纳劵为未使用
                        jxcProjectUserMapper.updateSiteCouponById(couponId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseMessage(200, machineCarNo + "打卡成功");
        } else {
            return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "数据不存在");
        }
    }

    /**
     * 手动发券机械列表与手动发券当日记录列表
     * @param user
     * @param vo
     * @return
     */
    @Override
    public ResponseMessage waitSendCouponMachineList(AuthorizationUser user, JxcSendSiteCouponVo vo){
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //先判断是管理员还是承租方自己
        if(user.getRoleId().equals(UserRoleContants.TEN.getRoleId())){
            Integer userId = user.getUserId();
            vo.setTenantryId(userId);
        }
        if(user.getRoleId().equals(UserRoleContants.TEN_MAN.getRoleId())){
            Map<String, Object> projectIdAndTenId = jxcProjectUserMapper.getProjectIdAndTenId(user.getUserId());
            if(projectIdAndTenId != null && projectIdAndTenId.size() >0){
                Integer userId = Integer.valueOf(String.valueOf(projectIdAndTenId.get("tenId")));
                Integer projectId = Integer.valueOf(String.valueOf(projectIdAndTenId.get("projectId")));
                vo.setTenantryId(userId);
                vo.setProjectId(projectId);
            }
        }
        Map<String,Object> map = new HashMap<>(3);
        //消纳券剩余张数
        Integer surplusCount = jxcProjectUserMapper.countTotalSiteCoupon(vo);
        //今日已使用张数
        Integer todayUsedCount = jxcProjectUserMapper.countUsedSiteCoupon(vo);
        map.put("surplusCount",surplusCount);
        map.put("todayUsedCount",todayUsedCount);
        //待发券的机械列表
        if(vo.getTabFlag().equals(1)){
            PageHelper.startPage(vo.getPageNo(),vo.getPageSize());
            List<JxcSendSiteCouponVo> jxcSendSiteCouponVos = jxcProjectUserMapper.selectWaitSendCouponMachineList(vo);
            PageInfo<JxcSendSiteCouponVo> page = new PageInfo<>(jxcSendSiteCouponVos);
            map.put("machineList",new PageUtils<>(page).getPageViewDatatable());
        }
        //发券记录列表
        if(vo.getTabFlag().equals(2)){
            PageHelper.startPage(vo.getPageNo(),vo.getPageSize(),"jsc.pair_time DESC");
            List<JxcSendSiteCouponVo> jxcSendSiteCouponVos = jxcProjectUserMapper.selectUsedCouponMachineList(vo);
            PageInfo<JxcSendSiteCouponVo> page = new PageInfo<>(jxcSendSiteCouponVos);
            map.put("machineList",new PageUtils<>(page).getPageViewDatatable());
        }
        result.setData(map);
        return result;
    }

    /**
     * 已入驻的消纳场的消纳场打卡 传消纳场管理员的token
     * 未入驻的消纳场，传当前司机的token 以及扫码以后获取的消纳场ID
     *
     * @param user
     * @param siteClockVo
     * @return
     */
    @Override
    public ResponseMessage siteAdminScan(AuthorizationUser user, SiteClockVo siteClockVo) {
        String machineCardNo = siteClockVo.getMachineCardNo();
        Integer siteId = siteClockVo.getSiteId();
        if (siteId == null) {
            if (machineCardNo == null || machineCardNo.equals("")) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "未获取到车牌号！");
            } else {
                //检查该消纳场管理员是否存在以及查询其所在消纳场ID
                Integer siteId2 = jxcMachineRouteMapper.getSiteIdByUserId(user.getUserId());
                if (siteId2 == null || siteId2.equals(0)) {
                    return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "您的账号被禁用或者没有给您该消纳场的权限！");
                }
                //检查该消纳场是否入驻，非入驻消纳场不能通过管理员扫码实现打卡
                Integer integer = jxcMachineRouteMapper.checkSiteIntoFlag(siteId2);
                if (integer == null || integer.equals(0)) {
                    return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "该消纳场未入驻，需司机扫消纳场二维码进行打卡！");
                }
            }
        }
        if (machineCardNo == null || machineCardNo.equals("")) {
            if (siteId == null) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "未获取到消纳场编码！");
            }
        }

        if (machineCardNo == null || machineCardNo.equals("")) {
            //未入驻的消纳场  通过司机的ID查询目前所绑定的车牌号
            machineCardNo = jxcMachineRouteMapper.getMachineCardNoByDriverId(user.getUserId());
        }
        JxcMachineRouteVo routeVo = new JxcMachineRouteVo();
        routeVo.setMachineCarNo(machineCardNo);
        //获取订单相关信息
        JxcMachineRoute jxcMachineRoute = jxcMachineRouteMapper.queryMachineOrderInfo(routeVo);
        if (jxcMachineRoute == null) {
            return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "该机械没有进行中的订单，不能打卡！");
        }
        //开始打卡操作
        //获取计费模式，预计公里数，预计行程信息
        JxcOrderRefSite jxcOrderRefSite1 = new JxcOrderRefSite();
        if (siteId == null) {
            siteId = jxcMachineRouteMapper.getSiteIdByUserId(user.getUserId());
        }

        //校验订单绑定消纳场是否与当前打卡消纳场一致
        //先查询该车辆携带的消纳券是否有与当前消纳场ID一致的
        List<Integer> siteIdByMachineId = jxcProjectUserMapper.getSiteIdByMachineId(jxcMachineRoute.getMachineId());
        if(siteIdByMachineId != null && siteIdByMachineId.size() > 0){
            if(!siteIdByMachineId.contains(siteId)){
                Integer siteIdByOrderId = jxcProjectUserMapper.getSiteIdByOrderId(jxcMachineRoute.getTenantryOrderId());
                if(!siteId.equals(siteIdByOrderId)){
                    return new ResponseMessage(ErrorCodeConstants.QUERY_ERORR.getId(),"当前消纳场与订单绑定的消纳场不一致");
                }
            }
        }else {
            Integer siteIdByOrderId = jxcProjectUserMapper.getSiteIdByOrderId(jxcMachineRoute.getTenantryOrderId());
            if(!siteId.equals(siteIdByOrderId)){
                return new ResponseMessage(ErrorCodeConstants.QUERY_ERORR.getId(),"当前消纳场与订单绑定的消纳场不一致");
            }
        }

        //校验打卡距离
        JxcSite jxcSite = jxcMachineRouteMapper.getJxcSiteById(siteId);
        /*if (jxcSite != null) {
            String distance = MapDistance.getDistance(siteClockVo.getLatitude(), siteClockVo.getLongitude(), jxcSite.getLatitude(), jxcSite.getLongitude());
            if (new BigDecimal(distance).compareTo(new BigDecimal("3.00")) > 0) {
                return new ResponseMessage(ErrorCodeConstants.CLOCK_TOO_FARTHER.getId(), "打卡距离超过3公里，不能打卡！");
            }
        }*/

        if (jxcMachineRoute.getTenantryOrderId() != null) {
            JxcOrderRefSite jxcOrderRefSite = new JxcOrderRefSite();
            jxcOrderRefSite.setTenantryOrderId(jxcMachineRoute.getTenantryOrderId());
            jxcOrderRefSite.setSiteId(siteId);
            PageHelper.clearPage();
            jxcOrderRefSite1 = jxcMachineRouteMapper.queryOrderRefSite(jxcOrderRefSite);
            if (jxcOrderRefSite1 == null) {
                return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "未获取到订单与该消纳场相关信息！");
            }
        }
        //获取施工场地的打卡行程
        PageHelper.clearPage();
        JxcMachineRoute route = jxcMachineRouteMapper.queryJxcMachineRoute(routeVo);
        //待办事项
        JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
        jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE2);
        jxcWaitHandleItems.setProjectOrderId(jxcMachineRoute.getTenantryOrderId());
        jxcWaitHandleItems.setOwnerOrderId(jxcMachineRoute.getOwnerOrderId());
        jxcWaitHandleItems.setMachineId(jxcMachineRoute.getMachineId());
        jxcWaitHandleItems.setProjectName(jxcMachineRoute.getProjectName());
        jxcWaitHandleItems.setUserId(jxcOrderRefSite1.getUserId());

        routeVo.setMachineId(jxcMachineRoute.getMachineId());

        Integer driverId = jxcMachineRouteMapper.getDriverId(routeVo);
        if (route == null) {
            //没有施工场地打卡记录
            long routeId = IDGenerator.getInstance().next();
            jxcMachineRoute.setId(routeId);
            jxcMachineRoute.setDriverId(driverId);
            jxcMachineRoute.setSiteId(siteId);
            if (jxcOrderRefSite1.getPricingMode().equals(0)) {
                jxcMachineRoute.setAmount(jxcOrderRefSite1.getEstimatePrice());
            } else {
                jxcMachineRoute.setAmount(jxcOrderRefSite1.getFixedPrice());
            }
            jxcMachineRoute.setAbnormalType(1);
            jxcMachineRoute.setEndTime(new Date());
            //获取应该给机主的金额
            Integer actualAmount = getActualAmount(jxcMachineRoute.getTenantryOrderId(), jxcMachineRoute.getAmount().toString());
            jxcMachineRoute.setToOwnerAmount(actualAmount);

            //插入待办事项表
            jxcWaitHandleItems.setDriverId(driverId);
            jxcWaitHandleItems.setRouteId(routeId);

            try {
                jxcMachineRouteMapper.insertSelective(jxcMachineRoute);
                jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
            } catch (Exception e) {
                return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(),"打卡失败！");
            }
        } else {
            //有施工场地打卡记录
            //获取时间
            Long tenantryOrderId = jxcMachineRoute.getTenantryOrderId();
            Long routeId = route.getId();
            Date startTime = route.getStartTime();
            Integer ownerId = route.getOwnerId();
            if(route.getDriverId() != null){
                driverId = route.getDriverId();
            }
            String driverThirdId = jxcMachineRouteMapper.getDriverThirdId(driverId);
            double miles = 0d;
            //查询是否有交接班的情况
            JxcDriverHandoverVo driverHandover = jxcMachineRouteMapper.getDriverHandover(routeId);
            if (driverHandover != null) {
                //前一个司机的实际里程
                double factMiles1 = getFactMiles(driverHandover.getThirdIdDown(), startTime, driverHandover.getCreateTime());
                //第二个司机的实际里程
                double factMiles2 = getFactMiles(driverHandover.getThirdIdUp(), driverHandover.getCreateTime(), new Date());
                miles = new BigDecimal(factMiles1).add(new BigDecimal(factMiles2).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
            } else {
                miles = getFactMiles(driverThirdId, startTime, new Date());
            }
            //预计公里数
            BigDecimal estimateMiles = jxcOrderRefSite1.getEstimateMiles();

            if (estimateMiles.compareTo(BigDecimal.ZERO) <= 0) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "预计公里数为不能为0");
            }

            //用于更新行程表的bean
            JxcMachineRoute machineRoute = new JxcMachineRoute();
            machineRoute.setId(routeId);
            machineRoute.setSiteId(siteId);
            machineRoute.setEndTime(new Date());
            machineRoute.setFactMileage(new BigDecimal(miles));
            BigDecimal divideResult = new BigDecimal(miles).divide(estimateMiles,2,BigDecimal.ROUND_HALF_UP);

            //用于插入财务流水表的bean
            JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
            jxcTradeOwner.setId(IDGenerator.getInstance().next());
            jxcTradeOwner.setTradeType(1);
            jxcTradeOwner.setPayerUserId(0);
            jxcTradeOwner.setPayeeUserId(ownerId);
            jxcTradeOwner.setTenantryOrderId(tenantryOrderId);
            jxcTradeOwner.setRouteId(routeId);
            jxcTradeOwner.setTradeStatus(1);
            jxcTradeOwner.setPaymentMethod(0);
            jxcTradeOwner.setLockStatus(0);

            jxcWaitHandleItems.setDriverId(route.getDriverId());
            jxcWaitHandleItems.setRouteId(routeId);

            //按预计公里数计价
            if (jxcOrderRefSite1.getPricingMode().equals(0)) {
                Integer estimatePrice = jxcOrderRefSite1.getEstimatePrice();
                //假如实际公里数大于预计公里数25%
                if (divideResult.compareTo(new BigDecimal("1.25")) > 0) {
                    //公里数异常
                    Integer price = getPrice(tenantryOrderId, new BigDecimal(miles));
                    machineRoute.setAbnormalType(3);
                    machineRoute.setAmount(price);
                    machineRoute.setToOwnerAmount(getActualAmount(tenantryOrderId, price.toString()));
                    try {
                        //修改行程表记录
                        jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
                        //插入待办事项
                        jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (divideResult.compareTo(new BigDecimal("1.25")) <= 0 && divideResult.compareTo(new BigDecimal("1.00")) >= 0) {
                    //正常行程  按照实际里程付费
                    //计算金额
                    Integer price = getPrice(tenantryOrderId, new BigDecimal(miles));
                    machineRoute.setAmount(price);
                    machineRoute.setPayState(1);
                    Integer toOwnerAmount = getActualAmount(tenantryOrderId, price.toString());
                    machineRoute.setToOwnerAmount(toOwnerAmount);
                    //修改行程表记录
                    jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
                    //插入流水表
                    jxcTradeOwner.setTradeAmount(toOwnerAmount);
                    jxcMachineRouteMapper.insertJxcTradeOwner(jxcTradeOwner);
                } else if (divideResult.compareTo(new BigDecimal("1.00")) < 0 && divideResult.compareTo(new BigDecimal("0.75")) >= 0) {
                    machineRoute.setAmount(estimatePrice);
                    machineRoute.setPayState(1);
                    Integer toOwnerAmount = getActualAmount(tenantryOrderId, estimatePrice.toString());
                    machineRoute.setToOwnerAmount(toOwnerAmount);
                    //修改行程表记录
                    jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
                    //插入流水表
                    jxcTradeOwner.setTradeAmount(toOwnerAmount);
                    jxcMachineRouteMapper.insertJxcTradeOwner(jxcTradeOwner);
                } else {
                    machineRoute.setAmount(estimatePrice);
                    Integer toOwnerAmount = getActualAmount(tenantryOrderId, estimatePrice.toString());
                    machineRoute.setToOwnerAmount(toOwnerAmount);
                    machineRoute.setAbnormalType(3);
                    try {
                        //修改行程表记录
                        jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
                        //插入待办事项
                        jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //一口价 不验距离 直接垫付
                machineRoute.setAmount(jxcOrderRefSite1.getFixedPrice());
                Integer toOwnerAmount = getActualAmount(tenantryOrderId, jxcOrderRefSite1.getFixedPrice().toString());
                machineRoute.setPayState(1);
                machineRoute.setToOwnerAmount(toOwnerAmount);
                //修改行程表记录
                jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
                //插入流水表
                jxcTradeOwner.setTradeAmount(toOwnerAmount);
                jxcMachineRouteMapper.insertJxcTradeOwner(jxcTradeOwner);
            }

        }
        Map<String, Object> map = new HashMap<>(1);
        //获取此车消纳券
        PageHelper.clearPage();
        JxcSiteCouponVo siteCouponVo = jxcMachineRouteMapper.getSiteCouponByMachineId(siteClockVo.getMachineCardNo(), siteId);
        if(siteCouponVo != null) {
            //此处无需判断是否为空  让前端判断 并且显示相应的文字以及按钮
            map.put("siteCoupon", siteCouponVo);
            //对消纳券进行核销
            siteClockVo.setSiteAdminId(user.getUserId());
            siteClockVo.setSiteCouponId(siteCouponVo.getId());
            jxcMachineRouteMapper.updateSiteCoupon1(siteClockVo);
        }else {
            //给了实体卡，将其携带的其他电子卡进行退回
            Integer machineId = jxcMachineRoute.getMachineId();
            if(machineId != null) {
                //将该车携带的其他消纳券进行退回处理
                jxcMachineRouteMapper.updateCouponFlag(machineId);
            }
        }
        return new ResponseMessage(map);
    }

    /**
     * 核销消纳券
     *
     * @param user
     * @param siteClockVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage siteClockIn(AuthorizationUser user, SiteClockVo siteClockVo) {
        //修改消纳券状态以及异常类型
        if (siteClockVo.getSiteCouponId() != null) {
            siteClockVo.setSiteAdminId(user.getUserId());
            try {
                jxcMachineRouteMapper.updateSiteCoupon(siteClockVo);
                Integer machineId = jxcMachineRouteMapper.getMachineIdByCouponId(siteClockVo.getSiteCouponId());
                if(machineId != null) {
                    //将该车携带的其他消纳券进行退回处理
                    jxcMachineRouteMapper.updateCouponFlag(machineId);
                }
            } catch (Exception e) {
                return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(),"消纳券核销失败！");
            }
        }else {
            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(),"消纳券不能为空！");
        }
        return new ResponseMessage();
    }

    /**
     * 调用百度鹰眼获取实际里程
     *
     * @param thirdId
     * @param start
     * @param end
     * @return
     */
    private double getFactMiles(String thirdId, Date start, Date end) {
        double miles = 0d;
        if ((end.getTime() - start.getTime()) < 24 * 3600 * 1000) {
            EagleEyesVo eagleEyesVo = new EagleEyesVo();
            eagleEyesVo.setClient("2");
            eagleEyesVo.setEntityName(thirdId);
            eagleEyesVo.setStartTime((start.getTime() / 1000));
            eagleEyesVo.setEndTime((end.getTime() / 1000));
            eagleEyesVo.setActionCode(EagleEyesVo.actionCode.GETDISTANCE);
            ResponseMessage responseMessage = eagleEyesFeign.baiduEntity(eagleEyesVo);

            Map<String, Object> data = (Map<String, Object>) responseMessage.getData();
            if(data != null) {
                System.out.println("鹰眼返回json:" + data.toString());
                if (data != null && Integer.parseInt(String.valueOf(data.get("status"))) == 0) {
                    double distance_d = new BigDecimal(String.valueOf(data.get("distance"))).doubleValue();
                    int dis = (int) distance_d;
                    if (dis > 0) {
                        miles = Math.round(dis / 100d) / 10d;
                    }
                }
            }else {
                //临时措施  后面要取消
                miles = 0;
            }
        }
        return miles;
    }

    /**
     * 计算承租方应付金额
     *
     * @param orderId
     * @param miles
     * @return
     */
    private Integer getPrice(Long orderId, BigDecimal miles) {
        int price = 0;
        //计算价格
        Map<String, Object> cityCodeByOrderId = jxcTransCostsMapper.getCityCodeByOrderId(orderId);
        if (cityCodeByOrderId == null || cityCodeByOrderId.size() == 0) {
            return price;
        }
        JxcTransCostsVo transCosts = jxcTransCostsMapper.getTransCosts(cityCodeByOrderId);
        if (transCosts == null) {
            return price;
        }
        //价格计算
        //1、获取限定里程
        BigDecimal startPriceMileage = transCosts.getStartPriceMileage();
        if (startPriceMileage == null) {
            startPriceMileage = BigDecimal.ZERO;
        }
        Integer outMileage = transCosts.getOutMileage();

        if (miles.compareTo(BigDecimal.ZERO) > 0 && miles.compareTo(startPriceMileage) <= 0) {
            if (transCosts.getStartPrice() != null) {
                price = transCosts.getStartPrice();
            }
        } else if (miles.compareTo(startPriceMileage) > 0 && miles.compareTo(new BigDecimal(outMileage)) <= 0) {
            int price1 = ((miles.subtract(startPriceMileage)).multiply(new BigDecimal(transCosts.getFollowPrice()))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
            price = price1 + transCosts.getStartPrice();
        } else if (miles.compareTo(new BigDecimal(outMileage)) > 0) {
            price = miles.multiply(new BigDecimal(transCosts.getUnifiedPrice())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        //添加额外费用
        if (price > 0) {
            price = price + transCosts.getAdditionalPrice();
        }
        System.out.println(price);
        return price;

    }

    /**
     * 计算应该支付给机主的金额
     *
     * @param orderId
     * @param factAmount
     * @return
     */
    private Integer getActualAmount(Long orderId, String factAmount) {
        int i = 0;
        //获取当前订单所在城市费率
        Map<String, Object> cityCodeAndEarthType = jxcTransCostsMapper.getCityCodeByOrderId(orderId);
        JxcTransCostsVo transCosts = new JxcTransCostsVo();
        if (cityCodeAndEarthType != null || cityCodeAndEarthType.size() > 0) {
            transCosts = jxcTransCostsMapper.getTransCosts(cityCodeAndEarthType);
        }
        BigDecimal divide1 = BigDecimal.ZERO;
        if (transCosts != null) {
            Integer platCommission = transCosts.getPlatCommission();
            if (platCommission != 0) {
                divide1 = new BigDecimal(platCommission).divide(new BigDecimal(100));
            }
        }
        if (new BigDecimal(factAmount).compareTo(BigDecimal.ZERO) > 0) {
            i = (new BigDecimal(factAmount).multiply(new BigDecimal(1).subtract(divide1))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * 消纳场管理员查看消纳券记录
     *
     * @param user
     * @param tabPageListVo flag 1-正常记录 2-异常记录
     * @return
     */
    @Override
    public ResponseMessage selectSiteCouponList(AuthorizationUser user, TabPageListVo tabPageListVo) {
        //检查该消纳场管理员是否存在以及查询其所在消纳场ID
        Integer siteId = jxcMachineRouteMapper.getSiteIdByUserId(user.getUserId());
        if (siteId == null || siteId.equals(0)) {
            return new ResponseMessage(ErrorCodeConstants.NO_SUCH_DATA.getId(), "您的账号被禁用或者没有给您该消纳场的权限！");
        }
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(tabPageListVo.getPageNo(), tabPageListVo.getPageSize(), "sc.check_time DESC");
        List<JxcSiteCouponVo> list = this.jxcSiteMapper.selectSiteCouponUsedList(siteId, tabPageListVo.getFlag());
        PageInfo<JxcSiteCouponVo> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

}
