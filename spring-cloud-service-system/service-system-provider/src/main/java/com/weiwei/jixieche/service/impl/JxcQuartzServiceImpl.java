package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcWaitHandleItemsFeign;
import com.weiwei.jixieche.ShortMsgFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.ClientTypeConstants;
import com.weiwei.jixieche.constant.PushTemplateConstants;
import com.weiwei.jixieche.constant.ShortMsgConstants;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.mapper.JxcQuartzMapper;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.rabbit.JxcShortMsgVo;
import com.weiwei.jixieche.service.JxcQuartzService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.CollectionUtils;
import com.weiwei.jixieche.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author zhong huan
 * @Date 2019-09-02 14:25
 * @Description
 */
@Service("jxcQuartzService")
public class JxcQuartzServiceImpl implements JxcQuartzService {

    @Autowired
    private JxcQuartzMapper jxcQuartzMapper;

    @Autowired
    private JxcWaitHandleItemsFeign jxcWaitHandleItemsFeign;

    @Autowired
    private ShortMsgFeign shortMsgFeign;

    @Autowired
    private JpushMsgFeign jpushMsgFeign;

    /**
     * 每晚12点更新承租方订单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderState() {
        //将到工期的订单更改为已完工状态
        List<Map<String,Object>> tenantryList2 = jxcQuartzMapper.queryTenantryOrderIdList(2);
        List<JxcOwnerOrder> ownerList = new ArrayList<>();
        if (tenantryList2 != null && tenantryList2.size() > 0) {
            for (Map<String,Object> tenantryOrderId : tenantryList2) {
                Long id = Long.valueOf(String.valueOf(tenantryOrderId.get("id")));
                try {
                    //查出机主订单
                    List<JxcOwnerOrder> ownerList1 = jxcQuartzMapper.queryOwnerOrderIdList(id);
                    ownerList.addAll(ownerList1);
                    //更新承租方订单状态
                    jxcQuartzMapper.updateTenantryOrderState(id, 3);
                    //更新机主订单状态
                    jxcQuartzMapper.updateOwnerOrderState(id, 3, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        //更新停工标记
        jxcQuartzMapper.updateStopWorkState();

        //先查询没有人接单订单，没有人接单的订单直接取消该订单
        List<Map<String,Object>> tenantryList1 = jxcQuartzMapper.queryTenantryOrderIdList(0);
        if (tenantryList1 != null && tenantryList1.size() > 0) {
            for (Map<String,Object> tenantryOrderId : tenantryList1) {
                Long id = Long.valueOf(String.valueOf(tenantryOrderId.get("id")));
                String thirdId = String.valueOf(tenantryOrderId.get("thirdId"));
                //查询是否有人接单 没有则取消该订单
                List<JxcOwnerOrder> ownerList2 = jxcQuartzMapper.queryOwnerOrderIdList(id);
                ownerList.addAll(ownerList2);
                if (ownerList == null || ownerList.size() == 0) {
                    //没人接单，取消订单
                    try {
                        jxcQuartzMapper.updateTenantryOrderState(id, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        //有人接单，将订单状态更改为进行中
                        jxcQuartzMapper.updateTenantryOrderState(id, 2);
                        //同时将机主订单以及机械状态更改为进行中
                        jxcQuartzMapper.updateOwnerOrderState(id, 2, 2);
                        //发送推送通知给承租方
                        JpushMessageVo jpushTemplateVo = new JpushMessageVo();
                        jpushTemplateVo.setMessageContent("承租方订单开始");
                        jpushTemplateVo.setMessageTitle("承租方订单开始");
                        jpushTemplateVo.setAliases(thirdId);
                        jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
                        JSONObject js = new JSONObject();
                        js.put("orderId",id);
                        js.put("type",PushTemplateConstants.JPUSH_TEN_ORDER_START.getId());
                        jpushTemplateVo.setMessageExtraParams(js);
                        jpushMsgFeign.jpushMessage(jpushTemplateVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //刷新司机工作台
        for (JxcOwnerOrder jxcOwnerOrder : ownerList) {
            List<Map<String, Object>> driverPushInfoList = jxcQuartzMapper.getDriverPushInfoList2(jxcOwnerOrder.getMachineId());
            if (driverPushInfoList != null && driverPushInfoList.size() > 0) {
                for (Map<String, Object> map1 : driverPushInfoList) {
                    JpushMessageVo jpushTemplateVo = new JpushMessageVo();
                    jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                    jpushTemplateVo.setAliases(String.valueOf(map1.get("thirdId")));
                    jpushTemplateVo.setMessageTitle("刷新工作台");
                    jpushTemplateVo.setMessageContent("刷新工作台");
                    JSONObject js = new JSONObject();
                    js.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                    jpushTemplateVo.setMessageExtraParams(js);
                    jpushMsgFeign.jpushMessage(jpushTemplateVo);
                }
            }
        }


    }

    /**
     * 更新机械行驶证、营运证状态
     *
     * @return
     * @author liuy
     * @date 2019/9/2 15:39
     */
    @Override
    public void updateMachine() {
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = sdf.format(date);

        //先把即将过期的状态改为2
        jxcQuartzMapper.updateMachineOpState(currentTime);
        jxcQuartzMapper.updateMachineDlState(currentTime);

        //再把过期的设置为1
        jxcQuartzMapper.updateMachineOpState1(currentTime);
        jxcQuartzMapper.updateMachineDlState1(currentTime);
    }

    /**
     * 正在跑趟中的司机未打上班卡
     *
     * @return
     * @author liuy
     * @date 2019/9/2 15:41
     */
    @Override
    public void getDriverNoClock() {
        //获取正在跑趟中的机械和司机
        List<JxcWaitHandleItems> waitHandleItemsList = jxcQuartzMapper.getMachineRunList();
        if (!CollectionUtils.isEmpty(waitHandleItemsList)) {
            waitHandleItemsList.stream().forEach(jxcWaitHandleItems -> {
                //查询是否正在上班中
                int count = jxcQuartzMapper.getDriverWorkStateById(jxcWaitHandleItems.getDriverId());
                if (count == 0) {
                    //正在跑趟的司机没有打上班卡时写入待办事项中
                    jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE11);
                    int res = jxcQuartzMapper.checkWaitHandleItemById(jxcWaitHandleItems);
                    if(res == 0) {
                        jxcWaitHandleItemsFeign.insert(jxcWaitHandleItems);
                    }
                }
            });
        }
    }

    /**
     * 正在进行中的订单所对应的机械没有绑定司机
     *
     * @return
     * @author liuy
     * @date 2019/9/2 15:41
     */
    @Override
    public void getOrderRunList() {
        List<JxcWaitHandleItems> waitHandleItemsList = this.jxcQuartzMapper.getOrderRunList();
        if (!CollectionUtils.isEmpty(waitHandleItemsList)) {
            waitHandleItemsList.stream().forEach(jxcWaitHandleItems -> {
                //该机械没有绑定司机
	            Integer count = jxcQuartzMapper.getMachineBindDriver(jxcWaitHandleItems.getMachineId());
                if (count == 0) {
                    //正在进行中的订单所对应的机械没有绑定司机
                    jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE12);
                    int res = jxcQuartzMapper.checkWaitHandleItemById(jxcWaitHandleItems);
                    if(res == 0) {
                        jxcWaitHandleItemsFeign.insert(jxcWaitHandleItems);
                    }
                }
            });
        }
    }

    /**
     * 定时将超过有效期的职位的状态改为失效
     *
     * @return
     * @author liuy
     * @date 2019/9/3 10:37
     */
    @Override
    public void updateJobStatus() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //短期职位失效
        List<Map<String, Object>> joblist = jxcQuartzMapper.queryShortJObList();
        long currentTime = System.currentTimeMillis();
        if (joblist != null && joblist.size() > 0) {
            for (Map<String, Object> jobMap : joblist) {
                //职位ID
                Integer jobId = Integer.valueOf(jobMap.get("jobId").toString());
                if (jobMap.get("effectiveDateEnd") != null) {
                    String effectiveDateEnd = String.valueOf(jobMap.get("effectiveDateEnd"));
                    Date effeDate = null;
                    try {
                        effeDate = df.parse(effectiveDateEnd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (currentTime > effeDate.getTime()) {
                        //查询已招聘人数
                        int count = jxcQuartzMapper.getShortJobCount(jobId);
                        if(count == 0) {
                            //更新职位状态为失效
                            jxcQuartzMapper.updateShortJobStatus(1, jobId);
                        }else{
                            //更新职位状态为机主关闭职位
                            jxcQuartzMapper.updateShortJobStatus(3, jobId);
                        }
                    }
                }
            }
        }

        //长期职位失效
        List<Map<String, Object>> longJobList = jxcQuartzMapper.queryLongJobList();
        for (Map<String, Object> longJobMap : longJobList) {
            String endTime = String.valueOf(longJobMap.get("endTime"));
            Integer jobId = (Integer) longJobMap.get("jobId");
            if (StringUtils.isNotBlank(endTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date effeDate = null;
                try {
                    effeDate = sdf.parse(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date nextZero = DateUtils.getNextZero(effeDate);
                if (currentTime > nextZero.getTime()) {
                    jxcQuartzMapper.updateLongJobStatus(jobId);
                }
            }
        }
    }

    /**
     * 强制打下班卡
     *
     * @return
     * @author liuy
     * @date 2019/9/3 13:47
     */
    @Override
    public void forceClockOut() {
        //查询所有16小时之前未打下班卡的配对表中的记录
        List<ClockInOutPair> list = jxcQuartzMapper.getNotClockOutPairClockInIdBefore16Hour();
        list.stream().forEach(dto -> {
            final long recordId = IDGenerator.getInstance().next();
            Map<String, Object> params = new HashMap<String, Object>() {{
                put("recordId", recordId);
                put("recordType", 2);
                put("driverId", dto.getDriverId());
                put("machineId",dto.getMachineId());
                put("jobId", jxcQuartzMapper.getJobId(dto.getClockInId()));
                put("clockAddress", "系统自动补卡");
                put("clockInTime", dto.getClockInTime());
            }};
            //强制插入下班卡 并且更新配对表
            jxcQuartzMapper.addClockRecord(params);
            //更新配对表
            jxcQuartzMapper.updateClockPair(dto.getClockInId(), recordId);
        });

        //对于每天最后一趟 打了施工场地卡超了8小时没有打消纳场卡的趟数  变为异常
        //查询没有打消纳场卡的所有趟数记录 并且将其变为异常
        List<Map<String, Object>> machineRouteList = jxcQuartzMapper.getMachineRouteList();
        if (machineRouteList != null && machineRouteList.size() > 0) {
            machineRouteList.stream().forEach(map -> {
                if (map != null && map.size() > 0) {
                    Long orderId = (Long) map.get("orderId");
                    if (orderId != null) {
                        Integer estimatePrice = jxcQuartzMapper.getEstimatePrice(orderId);
                        Long routeId = (Long) map.get("routeId");
                        if (routeId != null) {
                            Integer actualAmount = getActualAmount(orderId, estimatePrice.toString());
                            jxcQuartzMapper.updateMachineRoute(estimatePrice, routeId, actualAmount);
                            //加入待办事项
                            JxcMachineRoute machineRoute = jxcQuartzMapper.getJxcMachineRouteById(routeId);
                            JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
                            jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE2);
                            jxcWaitHandleItems.setProjectOrderId(machineRoute.getTenantryOrderId());
                            jxcWaitHandleItems.setOwnerOrderId(machineRoute.getOwnerOrderId());
                            jxcWaitHandleItems.setMachineId(machineRoute.getMachineId());
                            jxcWaitHandleItems.setProjectName(machineRoute.getProjectName());
                            jxcWaitHandleItems.setUserId(machineRoute.getTenantryId());
                            jxcWaitHandleItems.setDriverId(machineRoute.getDriverId());
                            jxcWaitHandleItems.setRouteId(machineRoute.getId());
                            jxcWaitHandleItemsFeign.insert(jxcWaitHandleItems);
                        }
                    }
                }
            });
        }
    }

    /**
     * 超过12小时机主申请退出,承租方未同意时强制更新为同意
     * @author  liuy
     * @return
     * @date    2019/9/4 11:41
     */
    @Override
    public void updateOwnerApplyQuitStatus() {
        List<Map<String,Object>> list = jxcQuartzMapper.getOwnerApplyQuitList();
        if(!CollectionUtils.isEmpty(list)){
            List<Integer> ids = new ArrayList<>();
            List<Long> ownerOrderIds = new ArrayList<>();
            for(Map map : list){
                Integer id = Integer.parseInt(map.get("id").toString());
                ids.add(id);
                Long orderId = Long.valueOf(String.valueOf(map.get("ownerOrderId")));
                ownerOrderIds.add(orderId);
            }
            //更新机主申请退出状态为同意
            jxcQuartzMapper.updateOwnerApplyQuit(ids);
            //清除承租方待办事项
            jxcQuartzMapper.updateJxcWaitHandle(ownerOrderIds);
            //更新机主订单状态为已完结
            jxcQuartzMapper.updateOwnerOrderById(ownerOrderIds);
        }
    }

    /**
     * 按照费率计算实付金额
     * @param orderId
     * @param factAmount
     * @return
     */
    private Integer getActualAmount(Long orderId, String factAmount) {
        int i = 0;
        //获取当前订单所在城市费率
        Map<String, Object> cityCodeAndEarthType = jxcQuartzMapper.getCityCodeByOrderId(orderId);
        TransCosts transCosts = new TransCosts();
        if (cityCodeAndEarthType != null || cityCodeAndEarthType.size() > 0) {
            transCosts = jxcQuartzMapper.getTransCosts(cityCodeAndEarthType);
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

    @Override
    public void payNotice(){
        //TODO
        //日结
        //取出前一天未支付的订单以及对应的承租方ID
        List<Map<String, Object>> list = jxcQuartzMapper.getTenantryIdAndOrderIdDay();
        if(list != null && list.size() >0){
            for(Map<String,Object> map : list){
                if(map != null && map.size() > 0){
                    Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    if(userId != null && orderId != null) {
                        //站内信
                        JpushCustomMsgVo socketPushMsgVo = new JpushCustomMsgVo();
                        JSONObject msg2 = new JSONObject();
                        msg2.put("orderId", orderId);
                        socketPushMsgVo.setParam(msg2);
                        socketPushMsgVo.setUserId(userId);
                        socketPushMsgVo.setServiceCode(PushTemplateConstants.JPUSH_DAY_ORDER_PRO_PAY.getId());
                        jpushMsgFeign.jpushCustomMsg(socketPushMsgVo);
                    }
                }
            }
        }
        List<Map<String, Object>> list1 = jxcQuartzMapper.getTenantryIdAndOrderIdDay1();
        if(list1 != null && list1.size() >0){
            for(Map<String,Object> map : list1){
                if(map != null && map.size() > 0){
                    Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    if(userId != null && orderId != null) {
                        //站内信
                        JSONObject msg2 = new JSONObject();
                        msg2.put("orderId", orderId);
                        //推送
                        String thirdIdByUserId = jxcQuartzMapper.getThirdIdByUserId(userId);
                        JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                        jpushTemplateVo.setAliases(thirdIdByUserId);
                        jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
                        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_DAY_ORDER_PRO_PAY.getId());
                        jpushTemplateVo.setParams(msg2);
                        jpushMsgFeign.jpushNotice(jpushTemplateVo);
                        //短信推送(承租方日结订单提醒)
                        String phone = jxcQuartzMapper.getPhoneByUserId(Integer.valueOf(userId.toString()));
                        AliShortMsgVo aliShortMsgVo = new AliShortMsgVo();
                        aliShortMsgVo.setClientType(AliShortMsgVo.ClientType.BACK);
                        aliShortMsgVo.setPhone(phone);
                        aliShortMsgVo.setTemplateId(ShortMsgConstants.ALISMS_TEN_DAY_ORDER_PAY.getId());
                        shortMsgFeign.aliSendShortMsg(aliShortMsgVo);
                    }
                }
            }
        }

        //周结
        List<Map<String, Object>> list2 = jxcQuartzMapper.getTenantryIdAndOrderIdWeek();
        if(list2 != null && list2.size() >0){
            for(Map<String,Object> map : list2){
                if(map != null && map.size() > 0){
                    Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    String startDate = String.valueOf(map.get("startDate"));
                    String endDate = String.valueOf(map.get("endDate"));
                    //如果当前日期大于结束日期  则表明该订单已经完结
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date end = null;
                    try {
                        end = sdf.parse(endDate+" 23:59:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(System.currentTimeMillis() > end.getTime()){
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, endDate);
                        //统计上周的情况
                        List<Long> list3 = jxcQuartzMapper.getList(orderId, String.valueOf(week.get("startDate")), String.valueOf(week.get("endDate")));
                        if(list3 == null || list3.size() ==0){
                            continue;
                        }
                    }else {
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, sdf2.format(new Date()));
                        //如果当前日期没有大于结束日期，则说明订单还在进行中，那就比较本周开始时间与订单开始时间
                        Date start = null;
                        Date weekStart = null;
                        try {
                            start = sdf.parse(startDate + " 00:00:00");
                            weekStart = sdf.parse(String.valueOf(week.get("startDate")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (weekStart.getTime() == start.getTime()) {
                            //当前时间是该订单的第一周 不用支付
                            continue;
                        } else {
                            //取上一周的开始时间 以及结束时间
                            Calendar c = Calendar.getInstance();
                            //过去七天
                            c.setTime(weekStart);
                            c.add(Calendar.DATE, -7);
                            Date d = c.getTime();
                            String day = sdf.format(d);
                            List<Long> list3 = jxcQuartzMapper.getList(orderId, day, String.valueOf(week.get("startDate")));
                            if (list3 == null || list3.size() == 0) {
                                continue;
                            }
                        }
                    }
                    if(userId != null && orderId != null) {
                        //站内信
                        JpushCustomMsgVo socketPushMsgVo = new JpushCustomMsgVo();
                        JSONObject msg2 = new JSONObject();
                        msg2.put("orderId", orderId);
                        socketPushMsgVo.setParam(msg2);
                        socketPushMsgVo.setUserId(userId);
                        socketPushMsgVo.setServiceCode(PushTemplateConstants.JPUSH_WEEK_ORDER_PRO_PAY.getId());
                        jpushMsgFeign.jpushCustomMsg(socketPushMsgVo);
                        //推送
                        String thirdIdByUserId = jxcQuartzMapper.getThirdIdByUserId(userId);
                        JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                        jpushTemplateVo.setAliases(thirdIdByUserId);
                        jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
                        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_WEEK_ORDER_PRO_PAY.getId());
                        jpushTemplateVo.setParams(msg2);
                        jpushMsgFeign.jpushNotice(jpushTemplateVo);
                        //短信推送(承租方周结订单提醒)
                        String phone = jxcQuartzMapper.getPhoneByUserId(Integer.valueOf(userId.toString()));
                        AliShortMsgVo aliShortMsgVo = new AliShortMsgVo();
                        aliShortMsgVo.setClientType(AliShortMsgVo.ClientType.BACK);
                        aliShortMsgVo.setPhone(phone);
                        aliShortMsgVo.setTemplateId(ShortMsgConstants.ALISMS_TEN_WEEK_ORDER_PAY.getId());
                        shortMsgFeign.aliSendShortMsg(aliShortMsgVo);

                    }
                }
            }
        }
    }

    /**
     * 每天晚上0点运行一次，将待支付的金额加入待办事项
     */
    @Override
    public void insertWaitHandleItem(){
        //日结
        List<JxcWaitHandleItems> jxcWaitHandleItemsList = jxcQuartzMapper.selectJxcWaitHandleItemsListDay();
        insertJxcWaitHandleItems(jxcWaitHandleItemsList,null,null);
        //周结
        List<Map<String, Object>> list2 = jxcQuartzMapper.selectWeekOrder();
        if(list2 != null && list2.size() >0){
            for(Map<String,Object> map : list2){
                if(map != null && map.size() > 0){
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    String startDate = String.valueOf(map.get("startDate"));
                    String endDate = String.valueOf(map.get("endDate"));
                    //如果当前日期大于结束日期  则表明该订单已经完结
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date end = null;
                    try {
                        end = sdf.parse(endDate+" 23:59:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(System.currentTimeMillis() > end.getTime()){
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, endDate);
                        //统计上周的情况
                        String startDate1 = String.valueOf(week.get("startDate"));
                        String endDate1 = String.valueOf(week.get("endDate"));
                        List<JxcWaitHandleItems> jxcWaitHandleItemsList1 = jxcQuartzMapper.selectJxcWaitHandleItemsListWeek(orderId, startDate1, endDate1);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = sdf2.parse(startDate1.split(" ")[0]);
                            date2 = sdf2.parse(endDate1.split(" ")[0]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        insertJxcWaitHandleItems(jxcWaitHandleItemsList1,date1,date2);
                    }else {
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, sdf2.format(new Date()));
                        //如果当前日期没有大于结束日期，则说明订单还在进行中，那就比较本周开始时间与订单开始时间
                        Date start = null;
                        Date weekStart = null;
                        try {
                            start = sdf.parse(startDate + " 00:00:00");
                            weekStart = sdf.parse(String.valueOf(week.get("startDate")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (weekStart.getTime() == start.getTime()) {
                            //当前时间是该订单的第一周 不用支付
                            continue;
                        } else {
                            //取上一周的开始时间 以及结束时间
                            Calendar c = Calendar.getInstance();
                            //过去七天
                            c.setTime(weekStart);
                            c.add(Calendar.DATE, -7);
                            Date d = c.getTime();
                            String day = sdf.format(d);
                            List<JxcWaitHandleItems> jxcWaitHandleItemsList1 = jxcQuartzMapper.selectJxcWaitHandleItemsListWeek(orderId, day, String.valueOf(week.get("startDate")));
                            String startDate1 = String.valueOf(week.get("startDate"));
                            Date date1 = null;
                            Date date2 = null;
                            try {
                                date1 = sdf2.parse(day.split(" ")[0]);
                                date2 = sdf2.parse(startDate1.split(" ")[0]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            insertJxcWaitHandleItems(jxcWaitHandleItemsList1,date1,DateUtils.getYesterdayDate(date2));
                        }
                    }
                }
            }
        }

    }

    /**
     * 评价：计算平均分
     * @author  liuy
     * @return
     * @date    2019/9/5 15:37
     */
	@Override
	public void calculateAvgScore() {
	    //机械评分列表
        List<Map<String,Object>> machineScoreList = jxcQuartzMapper.getMachineScoreList();
        if(!CollectionUtils.isEmpty(machineScoreList)){
            for(Map<String,Object> map : machineScoreList){
                if(map.get("machineId") != null && map.get("score") != null){
                    Integer machineId = Integer.parseInt(map.get("machineId").toString());
                    double score = Double.parseDouble(map.get("score").toString());
                    //更新机械评分
                    jxcQuartzMapper.updateMachineById(machineId, score);
                }
            }
        }
        //承租方、司机、机主用户评分
        List<Map<String,Object>> projectUserList = jxcQuartzMapper.getUserScoreList();
        if(!CollectionUtils.isEmpty(projectUserList)){
            for(Map<String,Object> map : projectUserList){
                if(map.get("userId") != null && map.get("score") != null){
                    Integer userId = Integer.parseInt(map.get("userId").toString());
                    double score = Double.parseDouble(map.get("score").toString());
                    //更新用户评分
                    jxcQuartzMapper.updateUserById(userId, score);
                }
            }
        }
	}

	/**
	 * 更新兼职职位状态为进行中
	 * @author  liuy
	 * @return
	 * @date    2019/9/17 20:18
	 */
    @Override
    public void updateShortJobStateById() {
        jxcQuartzMapper.updateDriverJob();

        //1.兼职职位到达结束时间, 还有司机的工资未支付时 解雇司机与机主绑定关系
        //2.工作到达结束时间且已支付, 直接删除司机与机主绑定关系
        List<JxcShortJobListVo> shortJobList = jxcQuartzMapper.getShortJobEndList();
        if(!CollectionUtils.isEmpty(shortJobList)){
            for(JxcShortJobListVo jxcShortJobListVo : shortJobList){
                if(jxcShortJobListVo.getId() != null){
                    //更新职位状态为已完结
                    jxcQuartzMapper.updateShortJobDriverById(2, jxcShortJobListVo.getId());
                }
                //查询司机是否跟机主存在绑定关系
                Integer id = jxcQuartzMapper.getDriverRefOwnerById(jxcShortJobListVo.getOwnerId(), jxcShortJobListVo.getDriverId());

                if(id != null){
                    //查询是否有未支付
                    Integer count = jxcQuartzMapper.getCountPayByDriverId(jxcShortJobListVo.getDriverId(), jxcShortJobListVo.getShortJobId());
                    if(count > 0){
                        //有未付时更新司机与机主绑定关系为解雇状态
                        jxcQuartzMapper.updateByOwnerIdAndDriverId(id, 2);
                    }else{
                        //没有时更新删除状态
                        jxcQuartzMapper.updateByOwnerIdAndDriverId(id, 0);
                    }
                }
                //解绑司机绑定的机械关系
                jxcQuartzMapper.updateByDriverId(jxcShortJobListVo.getDriverId());

                //强制打下班卡
                //检查司机是否正在上班中
                List<JxcClockPair> list = jxcQuartzMapper.getJxcClockPairByDriverId(jxcShortJobListVo.getDriverId());
                if(!CollectionUtils.isEmpty(list)){
                    JxcClockPair jxcClockPair = list.get(0);
                    final long recordId = IDGenerator.getInstance().next();
                    Map<String, Object> params = new HashMap<String, Object>() {{
                        put("recordId", recordId);
                        put("recordType", 2);
                        put("driverId", jxcClockPair.getDriverId());
                        put("machineId",jxcClockPair.getMachineId());
                        put("jobId", jxcClockPair.getShortJobId());
                        put("clockAddress", "系统自动补卡");
                        put("clockInTime", new Date());
                    }};

                    //强制插入下班卡 并且更新配对表
                    jxcQuartzMapper.addClockRecord(params);

                    //更新配对表
                    jxcQuartzMapper.updateClockPair(jxcClockPair.getClockInId(), recordId);
                }
            }
        }


        //绑定司机为兼职司机
        List<JxcShortJobListVo> shortJobListVoList = jxcQuartzMapper.getAllShortJobList();
        if(!CollectionUtils.isEmpty(shortJobListVoList)){
            for(JxcShortJobListVo jxcShortJobListVo : shortJobListVoList){
                //查询司机是否跟机主存在绑定关系
                Integer id = jxcQuartzMapper.getDriverRefOwnerById(jxcShortJobListVo.getOwnerId(), jxcShortJobListVo.getDriverId());
                if(null == id){
                    JxcDriverRefOwner jxcDriverRefOwner = new JxcDriverRefOwner();
                    jxcDriverRefOwner.setOwnerId(jxcShortJobListVo.getOwnerId());
                    jxcDriverRefOwner.setPhone(jxcShortJobListVo.getDriverPhone());
                    jxcDriverRefOwner.setRemarkName(jxcShortJobListVo.getDriverName());
                    jxcDriverRefOwner.setDriverId(jxcShortJobListVo.getDriverId());
                    //兼职司机
                    jxcDriverRefOwner.setDriverType(3);
                    jxcDriverRefOwner.setShortJobId(jxcShortJobListVo.getShortJobId());
                    jxcQuartzMapper.insertDriverRefOwner(jxcDriverRefOwner);
                }
            }
        }
    }

    /**
     * 生成司机账单
     * @author  liuy
     * @return
     * @date    2019/9/23 18:56
     */
    @Override
    public void createDriverBill() {
        //查询司机前一天打卡记录
        List<JxcClockInOutPair> pairList = jxcQuartzMapper.getClockInoutPair();
        if(!CollectionUtils.isEmpty(pairList)){
            for(JxcClockInOutPair jxcClockInOutPair : pairList) {
                JxcClockPairVo jxcClockPairVo = new JxcClockPairVo();
                jxcClockPairVo.setDriverId(jxcClockInOutPair.getDriverId());
                jxcClockPairVo.setMachineId(jxcClockInOutPair.getMachineId());
                jxcClockPairVo.setShortJobId(jxcClockInOutPair.getShortJobId());
                //获取昨天的日期
                Map<String,String> map = DateUtils.getTimeYesterday();
                jxcClockPairVo.setClockDate(DateUtils.parseYMD(map.get("yesterday")));

                Integer driverUserId = jxcClockInOutPair.getDriverId();
                //第一次打上班卡时间
                String startTime = DateUtils.formatHMS(jxcClockInOutPair.getClockInTime());
                //最后一次打下班时间
                String endTime = DateUtils.formatHMS(jxcClockInOutPair.getClockOutTime());
                //打卡次数
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("driverUserId", driverUserId);
                paramMap.put("startTime", startTime);
                paramMap.put("endTime", endTime);
                Integer clockCount = jxcQuartzMapper.getClockCount(paramMap);
                jxcClockPairVo.setClockCount(clockCount);

                //总趟数
                Integer totalCountRoute  = jxcQuartzMapper.getTotalCountRoute(paramMap);
                jxcClockPairVo.setTotalRoute(totalCountRoute);

                //根据日期查询一天的打卡记录
                ClockRecordVo clockRecordVo = new ClockRecordVo();
                List<ClockRecord> clockRecordList = this.jxcQuartzMapper.getClockRecordList(paramMap);
                if (!CollectionUtils.isEmpty(clockRecordList)) {
                        //计算加班趟数和总工时
                        clockRecordVo = getClockRecordList(clockRecordVo, clockRecordList, driverUserId, endTime);
                        //总工时
                        jxcClockPairVo.setWorkHours(clockRecordVo.getWorkHours());
                        //加班趟数
                        jxcClockPairVo.setOverTrainNum(clockRecordVo.getOverTrainNum());
                }
                if(totalCountRoute > 0) {
                    //待收工资
                    BigDecimal totalAmount = getTotalAmount(clockRecordVo.getOverTrainNum(), jxcClockInOutPair.getAreaId(), clockRecordVo.getWorkHours());
                    jxcClockPairVo.setFactAmount(totalAmount.multiply(new BigDecimal(100)).intValue());
                }
                //司机账单ID
                Long clockId = IDGenerator.getInstance().next();
                jxcClockPairVo.setClockId(clockId);
                //生成司机账单
                jxcQuartzMapper.insertDriverBill(jxcClockPairVo);

                //更新司机台班打卡配对表
                if (!CollectionUtils.isEmpty(clockRecordList)) {
                    for(ClockRecord clockRecord : clockRecordList){
                        jxcQuartzMapper.updateClockInOutPairById(clockRecord.getClockInId(), clockId);
                    }
                }
            }
        }

    }
    /**
     * 待收工资计算
     * @param overtimeRoute 加班趟数
     * @param areaId 区域ID
     * @param workHours 工时
     * @return
     */
    public BigDecimal getTotalAmount(Integer overtimeRoute, Integer areaId, double workHours){
        Map<String, Object> map = new HashMap<>();

        BigDecimal totalAmount = new BigDecimal(0.00);

        //查出城市台班费和加班趟数费用
        int driverTeamPrice = 0;
        int driverOutPrice = 0;
        JxcDriverTeamCostVo jxcDriverTeamCostVo = jxcQuartzMapper.queryDriverTeamCost(areaId);
        if (jxcDriverTeamCostVo != null) {
            driverTeamPrice = jxcDriverTeamCostVo.getDriverTeamPrice();
            driverOutPrice = jxcDriverTeamCostVo.getDriverOutPrice();
            //1.有加班趟数时 计算方式 8小时台班费用+加班趟数*加班趟数费用
            if(overtimeRoute > 0){
                //计算每小时平均的台班费
                totalAmount = new BigDecimal(driverTeamPrice).add(new BigDecimal(overtimeRoute).multiply(new BigDecimal(driverOutPrice))).setScale(2, BigDecimal.ROUND_HALF_UP);
            }else{
                if(workHours > 0) {
                    //工作时间刚好是一个台班时
                    if (workHours == 8) {
                        totalAmount = new BigDecimal(driverTeamPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    //工作时间小于8小时时
                    else if (workHours < 8) {
                        BigDecimal bigDecimal = new BigDecimal(workHours);
                        //计算每小时平均的台班费
                        BigDecimal price = new BigDecimal(driverTeamPrice).divide(new BigDecimal(8), 2, BigDecimal.ROUND_HALF_UP);
                        //计算费用
                        totalAmount = price.multiply(bigDecimal).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }else if(workHours > 8 && overtimeRoute == 0){
                        totalAmount = new BigDecimal(driverTeamPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
            }
        }
        return totalAmount;
    }

    /**
     * 计算加班趟数和总工时
     * @author  liuy
     * @param clockRecordList
     * @return
     * @date    2019/8/17 18:30
     */
    public ClockRecordVo getClockRecordList(ClockRecordVo clockRecordVo, List<ClockRecord> clockRecordList,
                                            Integer driverUserId, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //总工时
        double totalCountHours = 0;
        //是否有加班
        boolean isOver = false;
        //超过8小时的时间点
        Date outTime = null;

        for (ClockRecord clockRecord : clockRecordList) {
            //上班时间
            String clockInTime = clockRecord.getClockInTime();
            //下班时间
            String clockOutTime = clockRecord.getClockOutTime();

            //工时计算
            Date dateIn = null;
            Date dateOut = null;

            try {
                dateIn = sdf.parse(clockInTime);
                dateOut = sdf.parse(clockOutTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //计算一个台班工时
            double workHours = clockRecord.getWorkHours();
            totalCountHours += workHours;
            if (totalCountHours > 8) {
                double hour = totalCountHours - 8;
                //超过8小时的
                int addMinutes = (int) (hour * 60);
                if(outTime == null) {
                    //计算超过8小时的时间点
                    outTime = DateUtils.addDateMinutes(dateOut, -addMinutes);
                    isOver = true;
                }
            }
        }
        clockRecordVo.setWorkHours(totalCountHours);
        //加班趟数
        if (isOver) {
            Map<String, Object> params1 = new HashMap<>();
            params1.put("driverId", driverUserId);
            params1.put("startTime", outTime);
            params1.put("endTime", endTime);
            int overCount = jxcQuartzMapper.getTotalCountRoute(params1);
            //加班趟数
            clockRecordVo.setOverTrainNum(overCount);
        }
        return clockRecordVo;
    }

    private void insertJxcWaitHandleItems(List<JxcWaitHandleItems> jxcWaitHandleItemsList,Date startDate,Date endDate){
        if(jxcWaitHandleItemsList != null && jxcWaitHandleItemsList.size() > 0){
            for (JxcWaitHandleItems jxcWaitHandleItems : jxcWaitHandleItemsList){
                if(jxcWaitHandleItems.getPayAmount() != null && jxcWaitHandleItems.getPayAmount() > 0){
                    jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE3);
                    if(startDate == null && endDate == null){
                        Date billStartDate = jxcWaitHandleItems.getBillStartDate();
                        String s = DateUtils.formatHMS(billStartDate);
                        jxcWaitHandleItems.setBillStartDate(DateUtils.parseYMD(s.split(" ")[0]));
                        jxcWaitHandleItems.setBillEndDate(DateUtils.parseYMD(s.split(" ")[0]));
                    }else {
                        jxcWaitHandleItems.setBillStartDate(startDate);
                        jxcWaitHandleItems.setBillEndDate(endDate);
                    }
                    //同一个订单  同一个账单日期，只能存在一条待办事项
                    JxcWaitHandleItems jxcWaitHandleItems1 = jxcQuartzMapper.queryByOrderId(jxcWaitHandleItems);
                    if(jxcWaitHandleItems1 == null) {
                        jxcWaitHandleItemsFeign.insert(jxcWaitHandleItems);
                    }
                }
            }
        }
    }

    @Override
    public void unfreeze() {
        jxcQuartzMapper.unfreeze();
    }

    @Override
    public void orderStartNotice(){
        //TODO
        List<Map<String, Object>> list = jxcQuartzMapper.getTenantryId();
        if(list != null && list.size()>0){
            for (Map<String,Object> map : list){
                if(map != null && map.size() >0){
                    Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    String thirdId = String.valueOf(map.get("thirdId"));
                    if(userId != null && orderId != null) {
                        //通知承租方
                        //订单开始提醒
                        //站内信
                        JpushCustomMsgVo socketPushMsgVo = new JpushCustomMsgVo();
                        JSONObject msg2 = new JSONObject();
                        msg2.put("orderId", orderId);
                        socketPushMsgVo.setParam(msg2);
                        socketPushMsgVo.setUserId(userId);
                        socketPushMsgVo.setServiceCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_TEN.getId());
                        jpushMsgFeign.jpushCustomMsg(socketPushMsgVo);
                        //推送
                        JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                        jpushTemplateVo.setAliases(thirdId);
                        jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_TENANTRY.getId());
                        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_TEN.getId());
                        jpushTemplateVo.setParams(msg2);
                        jpushMsgFeign.jpushNotice(jpushTemplateVo);
                        //通知机主
                        List<Map<String,Object>> ownerOrderMapList = jxcQuartzMapper.getOwnerId(orderId);
                        if(ownerOrderMapList != null && ownerOrderMapList.size()>0){
                            ownerOrderMapList.stream().forEach(ownerOrderMap ->{
                                if(ownerOrderMap != null){
                                    Integer ownerId = Integer.valueOf(String.valueOf(ownerOrderMap.get("ownerId")));
                                    Integer machineId = Integer.valueOf(String.valueOf(ownerOrderMap.get("machineId")));
                                    Long ownerOrderId = Long.valueOf(String.valueOf(ownerOrderMap.get("ownerOrderId")));
                                    String ownerThirdId = String.valueOf(ownerOrderMap.get("thirdId"));
                                    //订单开始提醒
                                    JpushCustomMsgVo socketPushMsgVo2 = new JpushCustomMsgVo();
                                    JSONObject msg3 = new JSONObject();
                                    msg3.put("orderId", ownerOrderId);
                                    socketPushMsgVo2.setParam(msg3);
                                    socketPushMsgVo2.setUserId(ownerId);
                                    socketPushMsgVo2.setServiceCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_OWNER.getId());
                                    jpushMsgFeign.jpushCustomMsg(socketPushMsgVo2);
                                    //推送
                                    JpushTemplateVo jpushTemplateVo2 = new JpushTemplateVo();
                                    jpushTemplateVo2.setAliases(ownerThirdId);
                                    jpushTemplateVo2.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                                    jpushTemplateVo2.setTemplateCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_OWNER.getId());
                                    jpushTemplateVo2.setParams(msg3);
                                    jpushMsgFeign.jpushNotice(jpushTemplateVo2);
                                    //通知每个司机
                                    List<Map<String, Object>> driverPushInfoList = jxcQuartzMapper.getDriverPushInfoList(machineId, ownerId);
                                    if(driverPushInfoList != null && driverPushInfoList.size() > 0){
                                        for(Map<String,Object> map1 : driverPushInfoList) {
                                            socketPushMsgVo2.setUserId(Integer.valueOf(String.valueOf(map1.get("driverId"))));
                                            socketPushMsgVo2.setServiceCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_CHILD.getId());
                                            jpushMsgFeign.jpushCustomMsg(socketPushMsgVo2);
                                            jpushTemplateVo2.setAliases(String.valueOf(map1.get("thirdId")));
                                            jpushTemplateVo2.setTemplateCode(PushTemplateConstants.JPUSH_ORDER_START_TWELVE_BEFORE_CHILD.getId());
                                            jpushMsgFeign.jpushNotice(jpushTemplateVo2);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    @Override
    public void stopSendOrder(){
        /**
         * 日结订单
         */
        //对正在正在进行中的日结或者周结订单中的司机发送停工短信
        List<Map<String,Object>> list = jxcQuartzMapper.select24HourNoPayOrder();
        if(list != null && list.size() >0){
            for (Map<String,Object> map : list){
                Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                //做出限制
                noticeChild(orderId,userId);
            }
        }
        /**
         * 周结订单
         */
        List<Map<String, Object>> list2 = jxcQuartzMapper.getTenantryIdAndOrderIdWeek();
        if(list2 != null && list2.size() >0){
            for(Map<String,Object> map : list2){
                if(map != null && map.size() > 0){
                    Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
                    Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
                    String startDate = String.valueOf(map.get("startDate"));
                    String endDate = String.valueOf(map.get("endDate"));
                    //如果当前日期大于结束日期  则表明该订单已经完结
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date end = null;
                    try {
                        end = sdf.parse(endDate+" 23:59:59");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //判断当前时间是不是大于等于订单结束时间的24小时
                    if((System.currentTimeMillis() - end.getTime()) >= 24*3600*1000){
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, endDate);

                        //统计上周的情况
                        List<Long> list3 = jxcQuartzMapper.getList(orderId, String.valueOf(week.get("startDate")), String.valueOf(week.get("endDate")));
                        if(list3 == null || list3.size() ==0){
                            continue;
                        }else {
                            //对账号做出限制
                            jxcQuartzMapper.updateSendOrderState(userId);
                        }
                    }else {
                        Map<String, Object> week = DateUtils.getWeek(startDate, endDate, sdf2.format(new Date()));
                        //如果当前日期没有大于结束日期，则说明订单还在进行中，那就比较本周开始时间与订单开始时间
                        Date start = null;
                        Date weekStart = null;
                        try {
                            start = sdf.parse(startDate + " 00:00:00");
                            weekStart = sdf.parse(String.valueOf(week.get("startDate")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (weekStart.getTime() == start.getTime()) {
                            //当前时间是该订单的第一周 不用支付
                            continue;
                        } else {
                            //比较当前时间是否大于上周结束时间24小时以上
                            if((System.currentTimeMillis() - weekStart.getTime()) > 24*3600*1000) {
                                //取上一周的开始时间 以及结束时间
                                Calendar c = Calendar.getInstance();
                                c.setTime(weekStart);
                                c.add(Calendar.DATE, -7);
                                Date d = c.getTime();
                                String day = sdf.format(d);
                                List<Long> list3 = jxcQuartzMapper.getList(orderId, day, String.valueOf(week.get("startDate")));
                                if (list3 == null || list3.size() == 0) {
                                    continue;
                                } else {
                                    //限制
                                    noticeChild(orderId,userId);

                                }
                            }

                        }
                    }
                }
            }
        }


    }

    private void noticeChild(Long orderId,Integer userId){
        List<Map<String, Object>> maps = jxcQuartzMapper.getDriverThirdId(orderId);
        if(maps != null && maps.size() >0){
            for (Map<String,Object> map : maps){
                Integer driverId = Integer.valueOf(String.valueOf(map.get("userId")));
                String thirdId = String.valueOf(map.get("thirdId"));
                Long ownerOrderId = Long.valueOf(String.valueOf(map.get("ownerOrderId")));
                String machineCardNo = String.valueOf(map.get("machineCardNo"));
                //站内信
                JpushCustomMsgVo socketPushMsgVo = new JpushCustomMsgVo();
                socketPushMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_DRIVER.getId());
                socketPushMsgVo.setUserId(driverId);
                JSONObject msg2 = new JSONObject();
                msg2.put("ownerOrderId", ownerOrderId);
                msg2.put("machineCardNo", machineCardNo);
                socketPushMsgVo.setParam(msg2);
                jpushMsgFeign.jpushCustomMsg(socketPushMsgVo);
                //推送通知给正在上班的司机
                JpushTemplateVo jpushTemplateVo =new JpushTemplateVo();
                jpushTemplateVo.setAliases(thirdId);
                jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_DRIVER.getId());
                jpushTemplateVo.setParams(msg2);
                jpushMsgFeign.jpushNotice(jpushTemplateVo);
            }
        }
        //对账号做出限制
        jxcQuartzMapper.updateSendOrderState(userId);

        //更新承租方订单状态
        jxcQuartzMapper.updateTenantryOrderState1(orderId);
        //更新机主订单状态
        jxcQuartzMapper.updateOwnerOrderState(orderId, 3, 1);
    }

    /**
     * 2小时以后未处理的订单更新消纳券订单状态为已取消
     * @author  liuy
     * @return
     * @date    2019/10/22 9:39
     */
    @Override
    public void updateSiteOrderFlag() {
        jxcQuartzMapper.updateSiteOrderFlag();
    }

}
