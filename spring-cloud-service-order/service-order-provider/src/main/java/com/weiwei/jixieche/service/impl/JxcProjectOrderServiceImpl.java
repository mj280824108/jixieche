package com.weiwei.jixieche.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiwei.jixieche.JpushMsgFeign;
import com.weiwei.jixieche.JxcCreditScoreScoredFeign;
import com.weiwei.jixieche.JxcOpenCityFeign;
import com.weiwei.jixieche.ShortMsgFeign;
import com.weiwei.jixieche.bean.*;
import com.weiwei.jixieche.constant.*;
import com.weiwei.jixieche.generate.IDGenerator;
import com.weiwei.jixieche.jwt.AuthorizationUser;
import com.weiwei.jixieche.mapper.*;
import com.weiwei.jixieche.page.PageUtils;
import com.weiwei.jixieche.rabbit.JpushMessageVo;
import com.weiwei.jixieche.rabbit.JpushTemplateVo;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.service.JxcClockInOutRecordService;
import com.weiwei.jixieche.service.JxcProjectOrderService;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.verify.AssertUtil;
import com.weiwei.jixieche.verify.VerifyStr;
import com.weiwei.jixieche.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author 钟焕
 * @Description
 * @Date 19:29 2019-08-14
 **/
@Service("jxcProjectOrderService")
public class JxcProjectOrderServiceImpl implements JxcProjectOrderService {
    @Resource
    private JxcProjectOrderMapper jxcProjectOrderMapper;

    @Autowired
    private JxcOwnerOrderMapper jxcOwnerOrderMapper;

    @Autowired
    private JxcTransCostsMapper jxcTransCostsMapper;

    @Autowired
    private JxcMachineRouteMapper jxcMachineRouteMapper;

    @Autowired
    private JxcWaitHandleItemsMapper jxcWaitHandleItemsMapper;

    @Autowired
    private JxcOwnerApplyQuitMapper jxcOwnerApplyQuitMapper;

    @Autowired
    private JxcProjectUserMapper jxcProjectUserMapper;

    @Autowired
    private JxcCreditScoreScoredFeign jxcCreditScoreScoredFeign;

    @Autowired
    private JpushMsgFeign jpushMsgFeign;

    @Autowired
    private ShortMsgFeign shortMsgFeign;
    
    @Autowired
    private JxcOpenCityFeign jxcOpenCityFeign;

    @Autowired
    private JxcMachineMapper jxcMachineMapper;

    @Autowired
    private JxcClockInOutPairMapper jxcClockInOutPairMapper;

    @Autowired
    private JxcClockInOutRecordService jxcClockInOutRecordService;

    /**
     * 前端分页查询承租方订单表
     *
     * @param pageNo          分页索引
     * @param pageSize        每页显示数量
     * @param jxcProjectOrder 查询条件
     * @return
     */
    @Override
    public ResponseMessage<JxcProjectOrder> findByPageForFront(Integer pageNo, Integer pageSize, JxcProjectOrder jxcProjectOrder) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(pageNo, pageSize);
        List<JxcProjectOrder> list = this.jxcProjectOrderMapper.selectListByConditions(jxcProjectOrder);
        PageInfo<JxcProjectOrder> page = new PageInfo<>(list);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 根据预计公里数计算预计价格
     *
     * @param jxcSiteVo
     * @return
     */
    @Override
    public ResponseMessage estimatePrice(JxcSiteVo jxcSiteVo) {
        if (jxcSiteVo.getCityCode() == null || jxcSiteVo.getCityCode() == 0) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "城市编码不能为空或为0");
        }
        if (jxcSiteVo.getEarthType() == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "土方类型不能为空");
        }

        if (VerifyStr.isEmpty(jxcSiteVo.getEstimateMiles())) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "预计公里数不能为空");
        }
        BigDecimal estimateMiles = new BigDecimal(jxcSiteVo.getEstimateMiles());
        int price = 0;
        //计算价格
        Map<String, Object> params = new HashMap<String, Object>(2) {{
            put("cityCode", jxcSiteVo.getCityCode());
            put("earthType", jxcSiteVo.getEarthType());
        }};
        JxcTransCostsVo transCosts = jxcTransCostsMapper.getTransCosts(params);
        if (transCosts == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "该项目所在城市还未制定计费规则，请联系平台管理员！");
        }
        //价格计算
        //获取限定里程
        BigDecimal startPriceMileage = transCosts.getStartPriceMileage();
        if (startPriceMileage == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "起步里程不能为空");
        }
        Integer outMileage = transCosts.getOutMileage();
        if ((estimateMiles.compareTo(BigDecimal.ZERO) > 0) && estimateMiles.compareTo(startPriceMileage) <= 0) {
            if (transCosts.getStartPrice() != null) {
                price = transCosts.getStartPrice();
            }
        } else if (estimateMiles.compareTo(startPriceMileage) > 0 && estimateMiles.compareTo(new BigDecimal(outMileage)) <= 0) {
            int price1 = (estimateMiles.subtract(startPriceMileage)).multiply(new BigDecimal(transCosts.getFollowPrice())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
            price = price1 + transCosts.getStartPrice();
        } else if (estimateMiles.compareTo(startPriceMileage) > 0) {
            price = estimateMiles.multiply(new BigDecimal(transCosts.getUnifiedPrice())).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        //添加额外费用（洗车费）
        if (price > 0) {
            price = price + transCosts.getAdditionalPrice();
        }
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("price", VerifyStr.strToStr(price + ""));
        return new ResponseMessage(map);
    }

    /**
     * 添加承租方订单表
     *
     * @param t
     * @return
     */
    @Override
    public ResponseMessage<JxcProjectOrder> addObj(JxcProjectOrder t) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcProjectOrderMapper.insertSelective(t);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.ADD_ERORR.getDescript(), ErrorCodeConstants.ADD_ERORR.getId());
        return result;
    }


    /**
     * 修改承租方订单表
     *
     * @param t
     * @return
     */
    @Override
    public ResponseMessage<JxcProjectOrder> modifyObj(JxcProjectOrder t) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        int res = this.jxcProjectOrderMapper.updateByPrimaryKeySelective(t);
        AssertUtil.numberGtZero(res, ErrorCodeConstants.EDIT_ERORR.getDescript(), ErrorCodeConstants.EDIT_ERORR.getId());
        return result;
    }


    /**
     * 根据ID查询承租方订单表
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage<JxcProjectOrder> queryObjById(int id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcProjectOrder model = this.jxcProjectOrderMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 承租方发布订单
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage addJxcProjectOrder(JxcProjectOrderVo jxcProjectOrderVo) {
        if (jxcProjectOrderVo.getUserId() == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "当前用户ID不能为空");
        }

        Integer projectId = jxcProjectOrderVo.getProjectId();
        if (projectId == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "项目id不能为空");
        }

        //根据项目ID查询cityCode


        Integer earthType = jxcProjectOrderVo.getEarthType();
        if (earthType == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "土方类型不能为空");
        }

        Integer estimateTransportTimes = jxcProjectOrderVo.getEstimateTransportTimes();
        if (estimateTransportTimes == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "预计总工程量不能为空");
        }

        Integer estimateMachineCount = jxcProjectOrderVo.getEstimateMachineCount();
        if (estimateMachineCount == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "预计要车数不能为空");
        }

        Integer accountMethod = jxcProjectOrderVo.getAccountMethod();
        if (accountMethod == null) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "结算类型不能为空");
        }

        String startDate = jxcProjectOrderVo.getStartDate();
        if (startDate == null || startDate.isEmpty()) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "开始日期不能为空");
        } else {
            if (!VerifyStr.isYYYYMMDD(startDate)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "开始日期格式不正确");
            }
        }

        String endDate = jxcProjectOrderVo.getEndDate();
        if (VerifyStr.isEmpty(endDate)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "结束日期不能为空");
        } else {
            if (!VerifyStr.isYYYYMMDD(endDate)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "结束日期格式不正确");
            }
        }

        String workStartClock = jxcProjectOrderVo.getWorkStartClock();
        if (VerifyStr.isEmpty(workStartClock)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "上班时间不能为空");
        }

        if (!VerifyStr.isClock_hourAndMinute(workStartClock)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "上班时间格式不正确");
        }

        String workEndClock = jxcProjectOrderVo.getWorkEndClock();
        if (VerifyStr.isEmpty(workEndClock)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "下班时间不能为空");
        }

        if (!VerifyStr.isClock_hourAndMinute(workEndClock)) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "下班时间格式不正确");
        }

        //给订单ID赋值
        long orderId = IDGenerator.getInstance().next();
        jxcProjectOrderVo.setId(orderId);

        //校验消纳场个数
        List<JxcSiteVo> siteList = jxcProjectOrderVo.getSiteList();
        if (siteList.size() == 0) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "消纳场不能为空");
        }

        if (siteList.size() > 1) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "不允许添加多个消纳场");
        }

        //订单信息插入订单表
        try {
            //订单关联消纳场插入关联表
            ResponseMessage responseMessage = addSite(jxcProjectOrderVo);
            if (responseMessage.getCode() != 200) {
                return responseMessage;
            }
            jxcProjectOrderMapper.insertJxcProjectOrder(jxcProjectOrderVo);
        } catch (Exception e) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "订单发布失败！");
        }
        return new ResponseMessage();
    }

    private ResponseMessage addSite(JxcProjectOrderVo jxcProjectOrderVo) throws Exception {
        List<JxcOrderRefSite> list = new ArrayList<>();
        Set<Integer> lsIdSet = new HashSet<>();
        List<JxcSiteVo> siteList = jxcProjectOrderVo.getSiteList();
        for (JxcSiteVo site : siteList) {
            if (site.getId() == null || site.getId().equals(0)) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "消纳场不能为空");
            }
            if (site.getPricingMode() == null) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "计价方式不能为空");
            }
            if (site.getEstimateMiles() == null || site.getEstimateMiles().equals("")) {
                return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "预计公里数不能为空");
            }

            //根据预计公里数计算价格
            JxcSiteVo vo = new JxcSiteVo();
            Integer cityCode = jxcTransCostsMapper.getCityCodeByProjectId(jxcProjectOrderVo.getProjectId());
            vo.setEarthType(jxcProjectOrderVo.getEarthType());
            vo.setEstimateMiles(site.getEstimateMiles());
            vo.setCityCode(cityCode);
            //这里是为了给更换消纳场准备的  更换消纳场时不会传projectId需要用orderId去查询
            if (jxcProjectOrderVo.getProjectId() == null) {
                Map<String, Object> map = jxcTransCostsMapper.getCityCodeByOrderId(jxcProjectOrderVo.getId());
                if (map != null && map.size() > 0) {
                    vo.setEarthType(Integer.valueOf(String.valueOf(map.get("earthType"))));
                    vo.setCityCode(Integer.valueOf(String.valueOf(map.get("cityCode"))));
                }
            }
            ResponseMessage r = estimatePrice(vo);
            Integer price = 0;
            if (r.getData() != null) {
                Map<String, Object> data = (Map<String, Object>) r.getData();
                if (data.size() > 0) {
                    price = VerifyStr.strToInteger(String.valueOf(data.get("price")));
                }
            }
            JxcOrderRefSite jxcOrderRefSite = new JxcOrderRefSite();
            //设置承租方用户ID
            jxcOrderRefSite.setUserId(jxcProjectOrderVo.getUserId());
            //设置消纳场ID
            jxcOrderRefSite.setSiteId(site.getId());
            //设置消纳场关联表的订单ID
            jxcOrderRefSite.setTenantryOrderId(jxcProjectOrderVo.getId());
            jxcOrderRefSite.setEstimateMiles(new BigDecimal(site.getEstimateMiles()));
            jxcOrderRefSite.setEstimatePrice(price);
            jxcOrderRefSite.setFixedPrice(price);
            jxcOrderRefSite.setPricingMode(site.getPricingMode());

            list.add(jxcOrderRefSite);
            //去重
            lsIdSet.add(site.getId());
        }
        if (lsIdSet.size() < siteList.size()) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_FORMAT_ERROR.getId(), "消纳场不能重复选择");
        }
        //订单关联消纳场插入关联表

        jxcProjectOrderMapper.insertOrderRefSite(list);

        return new ResponseMessage();
    }

    /**
     * 承租方查询订单列表
     *
     * @param user
     * @param projectOrderListVo
     * @return
     */
    @Override
    public ResponseMessage selectProjectOrderList(AuthorizationUser user, ProjectOrderListVo projectOrderListVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(projectOrderListVo.getPageNo(), projectOrderListVo.getPageSize());
        List<ProjectOrderListVo> projectOrderList = new ArrayList<>();
        List<JxcProjectOrder> jxcProjectOrders = new ArrayList<>();
        if (user.getRoleId().equals(UserRoleContants.TEN_MAN.getRoleId())) {
            Integer projectId = jxcProjectOrderMapper.getProjectIdByTenantryManId(user.getUserId());
            if (projectId != null) {
                jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(null, projectOrderListVo.getTabFlag(), null, projectId);
            }

        } else {
            jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(user.getUserId(), projectOrderListVo.getTabFlag(), null, projectOrderListVo.getProjectId());
        }
        if (jxcProjectOrders != null && jxcProjectOrders.size() > 0) {
            for (JxcProjectOrder jxcProjectOrder : jxcProjectOrders) {
                ProjectOrderListVo pv = getProjectOrderListVo(jxcProjectOrder);
                //放入list
                projectOrderList.add(pv);
            }
        }
        PageInfo<JxcProjectOrder> page1 = new PageInfo<>(jxcProjectOrders);
        PageInfo<ProjectOrderListVo> page = new PageInfo<>(projectOrderList);
        page.setPageNum(page1.getPageNum());
        page.setPages(page1.getPages());
        page.setPageSize(page1.getPageSize());
        page.setTotal(page1.getTotal());
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    private ProjectOrderListVo getProjectOrderListVo(JxcProjectOrder jxcProjectOrder) {
        ProjectOrderListVo pv = new ProjectOrderListVo();
        Long orderId = jxcProjectOrder.getId();
        pv.setOrderId(orderId);
        //项目名称
        pv.setProjectName(jxcProjectOrder.getProjectName());
        //订单开始以及结束时间
        pv.setStartDate(DateUtils.format(jxcProjectOrder.getStartDate()));
        pv.setEndDate(DateUtils.format(jxcProjectOrder.getEndDate()));
        //预计工程量
        pv.setEstimateTransportTimes(jxcProjectOrder.getEstimateTransportTimes());
        //预计要车数量
        pv.setEstimateMachineCount(jxcProjectOrder.getEstimateMachineCount());
        //结算方式
        Integer accountMethod = jxcProjectOrder.getAccountMethod();
        pv.setAccountMethod(accountMethod);
        //停工标记
        pv.setStopWorkState(jxcProjectOrder.getStopWorkState());
        //订单状态
        Integer orderState = jxcProjectOrder.getOrderState();
        pv.setOrderState(orderState);
        //已完成车数
        Integer completeTransportTimes = jxcProjectOrderMapper.countCompleteTransportTimes(orderId);
        //今日已完成车数
        Integer todayCompleteTransportTimes = jxcProjectOrderMapper.countTodayCompleteTransportTimes(orderId);
        pv.setCompleteTransportTimes(completeTransportTimes);
        //待接单以及进行中时需要显示的已接单车辆
        if (orderState.equals(ProjectOrderListVo.OrderState.WAIT) || orderState.equals(ProjectOrderListVo.OrderState.ONGING)) {
            //统计已接单车辆数
            Integer receivedMachineCount = jxcProjectOrderMapper.countReceivedMachineCount(orderId);
            pv.setReceivedMachineCount(receivedMachineCount);
            if (orderState.equals(ProjectOrderListVo.OrderState.ONGING)) {
                //统计今日参与车辆数
                pv.setTodayMachineCount(jxcProjectOrderMapper.countTodayMachineCount(orderId));
                //今日已完成车数
                pv.setTodayCompleteTransportTimes(todayCompleteTransportTimes);
            }
        } else if (orderState.equals(ProjectOrderListVo.OrderState.END)) {
            //参与车辆数
            pv.setTotalMachineCount(jxcProjectOrderMapper.countTotalMachineCount(orderId));
        }else if(orderState.equals(ProjectOrderListVo.OrderState.CANCEL)){
            //接单车辆
            List<MachineListVo> machineListVos = jxcProjectOrderMapper.selectMachineListInOrder3(orderId);
            if(machineListVos != null) {
                pv.setReceivedMachineCount(machineListVos.size());
            }else{
                pv.setReceivedMachineCount(0);
            }
        }
        //处理待支付问题 排除掉待接单以及已取消两种状态的订单
        if (orderState.equals(ProjectOrderListVo.OrderState.ONGING) || orderState.equals(ProjectOrderListVo.OrderState.END)) {
            ProjectOrderListVo projectOrderListVo1 = getPayFlagAndPayAmount(jxcProjectOrder);
            pv.setPayFlag(projectOrderListVo1.getPayFlag());
            pv.setPayAmount(projectOrderListVo1.getPayAmount());
            pv.setNoPayRouteCount(projectOrderListVo1.getNoPayRouteCount());

        }

        return pv;
    }

    private ProjectOrderListVo getPayFlagAndPayAmount(JxcProjectOrder jxcProjectOrder) {
        ProjectOrderListVo pv = new ProjectOrderListVo();
        Long orderId = jxcProjectOrder.getId();
        Integer accountMethod = jxcProjectOrder.getAccountMethod();
        Integer orderState = jxcProjectOrder.getOrderState();
        JxcProjectOrderVo vo = new JxcProjectOrderVo();
        vo.setId(jxcProjectOrder.getId());
        //日结订单
        if (accountMethod.equals(1)) {
            //查询当前日期之前是否有未支付的行程
            String endDate = DateUtils.getYesterday(new Date());
            int payAmount = jxcProjectOrderMapper.sumPayAmount(orderId, endDate);
            vo.setSearchStartDate(null);
            vo.setSearchEndDate(endDate);
            int i = jxcProjectOrderMapper.countRouteCount(vo, 1);
            if (payAmount != 0) {
                //有需要待支付的行程
                pv.setPayFlag(1);
                //设置待支付金额
                pv.setPayAmount(VerifyStr.strToStr(payAmount + ""));
                //设置待支付趟数
                pv.setNoPayRouteCount(i);
            }

        } else if (accountMethod.equals(2)) {
            //周结订单
            String startTime = DateUtils.format(jxcProjectOrder.getStartDate()) + " 00:00:00";
            String endTime = DateUtils.format(jxcProjectOrder.getEndDate()) + " 23:59:59";
            //判断该订单是进行中还是已完工
            //进行中
            if (orderState.equals(ProjectOrderListVo.OrderState.ONGING)) {
                //查询当前日期所在的周
                Map<String, Object> week = DateUtils.getWeek(startTime, endTime, DateUtils.formatHMS(new Date()));
                //看看所在周的开始日期是否跟订单开始日期相等
                if (week != null) {
                    String startDate = String.valueOf(week.get("startDate"));
                    if (!startDate.equals(startTime)) {
                        //不等才需要去查询，相等则表明是第一周，不需要处理
                        //如果不等，则用本周开始的时间作为结束时间点  查询之前的行程是否有未支付的
                        Date date = DateUtils.parseYMD(startDate.split(" ")[0]);
                        //获取前一天
                        endTime = DateUtils.getYesterday(date);
                        int payAmount = jxcProjectOrderMapper.sumPayAmount(orderId, endTime);
                        vo.setSearchStartDate(null);
                        vo.setSearchEndDate(endTime);
                        int i = jxcProjectOrderMapper.countRouteCount(vo, 1);
                        if (payAmount != 0) {
                            //有需要待支付的行程
                            pv.setPayFlag(1);
                            //设置待支付金额
                            pv.setPayAmount(VerifyStr.strToStr(payAmount + ""));
                            //设置待支付趟数
                            pv.setNoPayRouteCount(i);
                        }
                    }
                }
            } else if (orderState.equals(ProjectOrderListVo.OrderState.END)) {
                //已完工
                //查询之前所有的行程
                int payAmount = jxcProjectOrderMapper.sumPayAmount(orderId, DateUtils.format(jxcProjectOrder.getEndDate()));
                vo.setSearchStartDate(null);
                vo.setSearchEndDate(DateUtils.format(jxcProjectOrder.getEndDate()));
                int i = jxcProjectOrderMapper.countRouteCount(vo, 1);
                if (payAmount != 0) {
                    //有需要待支付的行程
                    pv.setPayFlag(1);
                    //设置待支付金额
                    pv.setPayAmount(VerifyStr.strToStr(payAmount + ""));
                    //设置待支付趟数
                    pv.setNoPayRouteCount(i);
                }
            }
        }

        return pv;
    }

    /**
     * 根据ID查询承租方订单详情
     *
     * @param id
     * @return
     */
    @Override
    public ResponseMessage queryJxcProjectOrderById(Long id) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        JxcProjectOrderVo model = this.jxcProjectOrderMapper.selectJxcProjectOrderById(id);
        List<JxcProjectOrder> jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(null, null, id, null);
        if (jxcProjectOrders != null && jxcProjectOrders.size() == 1) {
            ProjectOrderListVo projectOrderListVo = getProjectOrderListVo(jxcProjectOrders.get(0));
            model.setProjectOrderListVo(projectOrderListVo);
            //设置待支付趟数
            model.setNoPayRouteCount(projectOrderListVo.getNoPayRouteCount());
            model.setCityCode(jxcProjectOrders.get(0).getCityCode());
        }
        //查询已接单车辆数
        Integer acceptedCarCount = jxcProjectOrderMapper.queryAcceptedCarCount(id);
        model.setAcceptedCarCount(acceptedCarCount);
        //评价标记
        Integer count = jxcProjectOrderMapper.countJxcScore(String.valueOf(id), model.getUserId());
        if(count < acceptedCarCount){
            model.setScoreFlag(1);
        }
        //将消纳场设置进去
        PageHelper.clearPage();
        List<JxcOrderRefSite> jxcOrderRefSites = jxcProjectOrderMapper.selectSiteListByOrderId(id);
        List<JxcSiteVo> siteList = new ArrayList<>();
        if (jxcOrderRefSites != null && jxcOrderRefSites.size() > 0) {
            for (JxcOrderRefSite jxcOrderRefSite : jxcOrderRefSites) {
                JxcSiteVo jxcSiteVo = new JxcSiteVo();
                jxcSiteVo.setId(jxcOrderRefSite.getSiteId());
                jxcSiteVo.setSiteName(jxcOrderRefSite.getSiteName());
                jxcSiteVo.setEstimateMiles(jxcOrderRefSite.getEstimateMiles().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                jxcSiteVo.setEstimatePrice(VerifyStr.strToStr(jxcOrderRefSite.getEstimatePrice().toString()));
                jxcSiteVo.setFixedPrice(VerifyStr.strToStr(jxcOrderRefSite.getFixedPrice().toString()));
                jxcSiteVo.setPricingMode(jxcOrderRefSite.getPricingMode());
                siteList.add(jxcSiteVo);
            }
        }
        JxcProjectOrderVo projectOrderVo = new JxcProjectOrderVo();
        projectOrderVo.setId(id);
        String endDate = DateUtils.format(new Date());
        projectOrderVo.setSearchStartDate(null);
        projectOrderVo.setSearchEndDate(endDate);
        //设置正常趟数
        int normalRouteCount = jxcProjectOrderMapper.countRouteCount(projectOrderVo, 2);
        model.setNormalRouteCount(normalRouteCount);
        //设置异常趟数
        int abnormalRouteCount = jxcProjectOrderMapper.countRouteCount(projectOrderVo, 3);
        model.setAbnormalRouteCount(abnormalRouteCount);
        model.setSiteList(siteList);
        AssertUtil.notNull(model, ErrorCodeConstants.QUERY_EMPTY_DATA.getDescript(), ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
        result.setData(model);
        return result;
    }

    /**
     * 根据订单ID查询订单中的接单车辆
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    public ResponseMessage selectMachineListInOrder(JxcProjectOrderVo jxcProjectOrderVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        PageHelper.startPage(jxcProjectOrderVo.getPageNo(), jxcProjectOrderVo.getPageSize());
        List<JxcProjectOrder> jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(null, null, jxcProjectOrderVo.getId(), null);
        if (jxcProjectOrders != null && jxcProjectOrders.size() == 1) {
            JxcProjectOrder jxcProjectOrder = jxcProjectOrders.get(0);
            jxcProjectOrderVo.setOrderState(jxcProjectOrder.getOrderState());
        }
        List<MachineListVo> machineListVos = new ArrayList<>();
        if (jxcProjectOrderVo.getOrderState().equals(0) || jxcProjectOrderVo.getOrderState().equals(2)) {
            machineListVos = jxcProjectOrderMapper.selectMachineListInOrder1(jxcProjectOrderVo.getId(), jxcProjectOrderVo.getOrderState());
        } else if (jxcProjectOrderVo.getOrderState().equals(3)) {
            machineListVos = jxcProjectOrderMapper.selectMachineListInOrder2(jxcProjectOrderVo.getId());
        }else if(jxcProjectOrderVo.getOrderState().equals(1)){
            machineListVos = jxcProjectOrderMapper.selectMachineListInOrder3(jxcProjectOrderVo.getId());
        }

        for (MachineListVo machineListVo : machineListVos) {
            List<MachineListVo> machineListVos1 = jxcProjectOrderMapper.selectDriverVoList(machineListVo.getMachineId());
            if (machineListVos1 != null && machineListVos1.size() > 0) {
                machineListVo.setDriverName(machineListVos1.get(0).getDriverName());
                machineListVo.setDriverPhone(machineListVos1.get(0).getDriverPhone());
                for (MachineListVo machineListVo1 : machineListVos1) {
                    if (machineListVo1.getWorkState().equals(1)) {
                        machineListVo.setDriverName(machineListVo1.getDriverName());
                        machineListVo.setDriverPhone(machineListVo1.getDriverPhone());
                    }
                }
            }
        }
        PageInfo<MachineListVo> page = new PageInfo<>(machineListVos);
        result.setData(new PageUtils<>(page).getPageViewDatatable());
        return result;
    }

    /**
     * 解雇机械
     *
     * @param orderId
     * @param machineId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage fireMachine(Long orderId, Integer machineId, String reason) {
        //先查询此时机主订单的状态
        PageHelper.clearPage();
        JxcOwnerOrder jxcOwnerOrder = jxcProjectOrderMapper.queryOwnerOrderState(orderId, machineId);
        if (jxcOwnerOrder.getOrderState().equals(0)) {
            try {
                jxcProjectOrderMapper.updateOwnerOrderState(orderId, machineId, ProjectOrderListVo.OrderState.CANCEL, reason);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (jxcOwnerOrder.getOrderState().equals(2)) {
            //校验机械是否正在跑趟中
            JxcMachineRouteVo vo = new JxcMachineRouteVo();
            vo.setMachineId(machineId);
            JxcMachineRoute machineRoute = jxcMachineRouteMapper.queryJxcMachineRoute(vo);
            if(machineRoute != null){
                return new ResponseMessage(ErrorCodeConstants.ORDER_DRIVER_RUN.getId(),"该机械正在跑趟中，不能解雇！");
            }
            try {
                jxcProjectOrderMapper.updateOwnerOrderState(orderId, machineId, 4, reason);
                //更新机械状态
                jxcProjectOrderMapper.updateMachineState(machineId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo = jxcProjectOrderMapper.getOwnerPushInfoByMachineId(jxcOwnerOrder);
        //站内信
        JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
        jpushCustomMsgVo.setUserId(jxcOwnerOrderDetailVo.getUserId());
        jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_FIRE_MACHINE_TELL_OWNER.getId());
        JSONObject js = new JSONObject();
        js.put("machineCardNo",jxcOwnerOrderDetailVo.getMachineCarNo());
        js.put("fireReason",reason);
        js.put("ownerOrderId",jxcOwnerOrderDetailVo.getOwnerOrderId());
        jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
        //推送
        JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
        jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
        jpushTemplateVo.setAliases(jxcOwnerOrderDetailVo.getThirdId());
        jpushTemplateVo.setParams(js);
        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_FIRE_MACHINE_TELL_OWNER.getId());
        jpushMsgFeign.jpushNotice(jpushTemplateVo);
        //刷新工作台
        JpushMessageVo jpushTemplateVo3 = new JpushMessageVo();
        jpushTemplateVo3.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
        jpushTemplateVo3.setAliases(jxcOwnerOrderDetailVo.getThirdId());
        jpushTemplateVo3.setMessageContent("刷新工作台");
        jpushTemplateVo3.setMessageTitle("刷新工作台");
        JSONObject js2 = new JSONObject();
        js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
        jpushTemplateVo3.setMessageExtraParams(js2);
        jpushMsgFeign.jpushMessage(jpushTemplateVo3);

        //推给司机
        List<Map<String, Object>> driverPushInfoList = jxcProjectOrderMapper.getDriverPushInfoList(jxcOwnerOrderDetailVo.getMachineId(), jxcOwnerOrderDetailVo.getUserId());
        if (driverPushInfoList != null && driverPushInfoList.size() > 0){
            for (Map<String,Object> map : driverPushInfoList){
                jpushCustomMsgVo.setUserId(Integer.valueOf(String.valueOf(map.get("driverId"))));
                jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_FIRE_MACHINE_TELL_DRIVER.getId());
                jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                jpushTemplateVo.setAliases(String.valueOf(map.get("thirdId")));
                jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_FIRE_MACHINE_TELL_DRIVER.getId());
                jpushMsgFeign.jpushNotice(jpushTemplateVo);
                //刷新工作台
                JpushMessageVo jpushTemplateVo2 = new JpushMessageVo();
                jpushTemplateVo2.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                jpushTemplateVo2.setAliases(String.valueOf(map.get("thirdId")));
                jpushTemplateVo2.setMessageContent("刷新工作台");
                jpushTemplateVo2.setMessageTitle("刷新工作台");
                js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                jpushTemplateVo2.setMessageExtraParams(js2);
                jpushMsgFeign.jpushMessage(jpushTemplateVo2);
            }
        }

        //短信推送
        AliShortMsgVo aliShortMsgVo = new AliShortMsgVo();
        aliShortMsgVo.setClientType(AliShortMsgVo.ClientType.BACK);
        aliShortMsgVo.setPhone(jxcOwnerOrderDetailVo.getOwnerPhone());
        aliShortMsgVo.setTemplateId(ShortMsgConstants.ALISMS_TEN_FIRE_OWNER.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("machineCardNo",jxcOwnerOrderDetailVo.getMachineCarNo());
        aliShortMsgVo.setTemplateParam(jsonObject.toJSONString());
        shortMsgFeign.aliSendShortMsg(aliShortMsgVo);

        return new ResponseMessage();
    }

    /**
     * 停止继续要车
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage stopContinueCar(Long orderId) {
        try {
            jxcProjectOrderMapper.updateContinueCarFlag(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseMessage();
    }

    /**
     * 申请停工
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage applyStopWork(JxcProjectOrderVo jxcProjectOrderVo) {
        try {
            jxcProjectOrderMapper.applyStopWork(jxcProjectOrderVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //推送给机主
        List<JxcOwnerOrderDetailVo> pushInfoList = jxcProjectOrderMapper.getOwnerPushInfoList(jxcProjectOrderVo.getId(),2);
        if(pushInfoList != null && pushInfoList.size() > 0){
            for (JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo : pushInfoList){
                //站内信
                JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
                jpushCustomMsgVo.setUserId(jxcOwnerOrderDetailVo.getUserId());
                jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_STOP_TELL_OWNER.getId());
                JSONObject js = new JSONObject();
                js.put("machineCardNo",jxcOwnerOrderDetailVo.getMachineCarNo());
                js.put("startDate",jxcProjectOrderVo.getStopWorkStart());
                js.put("endDate",jxcProjectOrderVo.getStopWorkEnd());
                js.put("ownerOrderId",jxcOwnerOrderDetailVo.getOwnerOrderId());
                jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                //推送
                JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                jpushTemplateVo.setAliases(jxcOwnerOrderDetailVo.getThirdId());
                jpushTemplateVo.setParams(js);
                jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_STOP_TELL_OWNER.getId());
                jpushMsgFeign.jpushNotice(jpushTemplateVo);

                //推给司机
                List<Map<String, Object>> driverPushInfoList = jxcProjectOrderMapper.getDriverPushInfoList(jxcOwnerOrderDetailVo.getMachineId(), jxcOwnerOrderDetailVo.getUserId());
                if (driverPushInfoList != null && driverPushInfoList.size() > 0){
                    for (Map<String,Object> map : driverPushInfoList){
                        jpushCustomMsgVo.setUserId(Integer.valueOf(String.valueOf(map.get("driverId"))));
                        jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_STOP_TELL_DRIVER.getId());
                        jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                        jpushTemplateVo.setAliases(String.valueOf(map.get("thirdId")));
                        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_STOP_TELL_DRIVER.getId());
                        jpushMsgFeign.jpushNotice(jpushTemplateVo);

                        //短信推送(停工提醒司机)
                        if(map.get("driverPhone") != null) {
                            AliShortMsgVo aliShortMsgVo = new AliShortMsgVo();
                            aliShortMsgVo.setClientType(AliShortMsgVo.ClientType.BACK);
                            aliShortMsgVo.setPhone(String.valueOf(map.get("driverPhone")));
                            aliShortMsgVo.setTemplateId(ShortMsgConstants.ALISMS_STOP_DIRVER.getId());
                            shortMsgFeign.aliSendShortMsg(aliShortMsgVo);
                        }
                    }
                }

                //短信推送(承租方申请停工提醒机主)
                AliShortMsgVo aliShortMsgVo = new AliShortMsgVo();
                aliShortMsgVo.setClientType(AliShortMsgVo.ClientType.BACK);
                aliShortMsgVo.setPhone(jxcOwnerOrderDetailVo.getOwnerPhone());
                aliShortMsgVo.setTemplateId(ShortMsgConstants.ALISMS_TEN_STOP_OWNER.getId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("startTime", jxcProjectOrderVo.getStopWorkStart());
                jsonObject.put("endTime", jxcProjectOrderVo.getStopWorkEnd());
                aliShortMsgVo.setTemplateParam(jsonObject.toJSONString());
                shortMsgFeign.aliSendShortMsg(aliShortMsgVo);
            }
        }
        return new ResponseMessage();
    }

    /**
     * 申请提前完工
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage applyEndWork(JxcProjectOrderVo jxcProjectOrderVo) {
        try {
            //先发出推送再结束订单
            //推送给机主
            List<JxcOwnerOrderDetailVo> pushInfoList = jxcProjectOrderMapper.getOwnerPushInfoList(jxcProjectOrderVo.getId(),2);
            if(pushInfoList != null && pushInfoList.size() > 0){
                for (JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo : pushInfoList){
                    //站内信
                    JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
                    jpushCustomMsgVo.setUserId(jxcOwnerOrderDetailVo.getUserId());
                    jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_OWNER.getId());
                    JSONObject js = new JSONObject();
                    js.put("machineCardNo",jxcOwnerOrderDetailVo.getMachineCarNo());
                    js.put("ownerOrderId",jxcOwnerOrderDetailVo.getOwnerOrderId());
                    jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                    //推送
                    JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                    jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                    jpushTemplateVo.setAliases(jxcOwnerOrderDetailVo.getThirdId());
                    jpushTemplateVo.setParams(js);
                    jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_OWNER.getId());
                    jpushMsgFeign.jpushNotice(jpushTemplateVo);
                    //刷新工作台
                    JpushMessageVo jpushTemplateVo3 = new JpushMessageVo();
                    jpushTemplateVo3.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                    jpushTemplateVo3.setAliases(jxcOwnerOrderDetailVo.getThirdId());
                    jpushTemplateVo3.setMessageContent("刷新工作台");
                    jpushTemplateVo3.setMessageTitle("刷新工作台");
                    JSONObject js2 = new JSONObject();
                    js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                    jpushTemplateVo3.setMessageExtraParams(js2);
                    jpushMsgFeign.jpushMessage(jpushTemplateVo3);

                    //推给司机
                    List<Map<String, Object>> driverPushInfoList = jxcProjectOrderMapper.getDriverPushInfoList(jxcOwnerOrderDetailVo.getMachineId(), jxcOwnerOrderDetailVo.getUserId());
                    if (driverPushInfoList != null && driverPushInfoList.size() > 0){
                        for (Map<String,Object> map : driverPushInfoList){
                            jpushCustomMsgVo.setUserId(Integer.valueOf(String.valueOf(map.get("driverId"))));
                            jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_DRIVER.getId());
                            jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                            jpushTemplateVo.setAliases(String.valueOf(map.get("thirdId")));
                            jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_DONE_BEFORE_TELL_DRIVER.getId());
                            jpushMsgFeign.jpushNotice(jpushTemplateVo);
                            //刷新工作台
                            JpushMessageVo jpushTemplateVo2 = new JpushMessageVo();
                            jpushTemplateVo2.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                            jpushTemplateVo2.setAliases(String.valueOf(map.get("thirdId")));
                            jpushTemplateVo2.setMessageContent("刷新工作台");
                            jpushTemplateVo2.setMessageTitle("刷新工作台");
                            js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                            jpushTemplateVo2.setMessageExtraParams(js2);
                            jpushMsgFeign.jpushMessage(jpushTemplateVo2);
                        }
                    }
                }
            }
            //结束订单
            jxcProjectOrderMapper.applyEndWork(jxcProjectOrderVo);
        } catch (Exception e) {
            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "申请失败！");
        }
        return new ResponseMessage();
    }

    /**
     * 取消订单之前的验证
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    public ResponseMessage tenCancelOrderConfirm(JxcProjectOrderVo jxcProjectOrderVo){
        //取消之前先做好验证
        //首先判断订单状态 非待接单状态下不能取消
        JxcProjectOrderVo jxcProjectOrderVo1 = jxcProjectOrderMapper.selectJxcProjectOrderById(jxcProjectOrderVo.getId());
        if(jxcProjectOrderVo1 != null){
            if(jxcProjectOrderVo1.getOrderState().equals(0)){
                //判断是否有人接单
                Integer acceptedCarCount = jxcProjectOrderMapper.queryAcceptedCarCount(jxcProjectOrderVo1.getId());
                if(acceptedCarCount == null || acceptedCarCount.equals(0)){
                    //没有人接单
                    return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(),"确定取消订单吗？");
                }else {
                    JxcCreditScoreTemplate effectiveById = jxcProjectOrderMapper.getEffectiveById(CreditScoreTemplateConstants.SCORE_TEN_CANCEL_DRIVER_ORDER.getId());
                    if(effectiveById != null){
                        //拿到规则时间
                        Integer condition = effectiveById.getCondition();
                        Integer score = Math.abs(effectiveById.getScore());
                        String startDate = jxcProjectOrderVo1.getStartDate();
                        if((DateUtils.parseYMD(startDate).getTime() - System.currentTimeMillis()) > condition*60*60*1000){
                            //大于规则时间提示
                            return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_3.getId(),"已有车辆接单，现在取消订单可能会扣除信用分，确定取消么？");
                        }else {
                            return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_2.getId(),"订单将于"+condition+"小时后开始，现在取消订单会扣除"+score+"分信用分，确定取消么？");
                        }
                    }else {
                        return new ResponseMessage(ErrorCodeConstants.TEN_CANCEL_1.getId(),"确定取消订单吗？");
                    }
                }
            }else {
                return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"非待接单状态下的订单不能取消");
            }
        }else {
            return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"订单不存在");
        }

    }

    /**
     * 取消订单
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage cancelProjectOrder(Integer userId, JxcProjectOrderVo jxcProjectOrderVo) {
        JxcProjectOrderVo jxcProjectOrderVo1 = jxcProjectOrderMapper.selectJxcProjectOrderById(jxcProjectOrderVo.getId());
        if(jxcProjectOrderVo1 != null){
            if(jxcProjectOrderVo1.getOrderState().equals(0)){
                //判断是否有人接单
                Integer acceptedCarCount = jxcProjectOrderMapper.queryAcceptedCarCount(jxcProjectOrderVo1.getId());
                if(acceptedCarCount == null || acceptedCarCount.equals(0)){
                    try {
                        jxcProjectOrderMapper.cancelProjectOrder(jxcProjectOrderVo);
                        //推送消息
                        cancelOrderPush(jxcProjectOrderVo);
                        jxcProjectOrderMapper.cancelOwnerOrder(jxcProjectOrderVo);
                    } catch (Exception e) {
                        return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "申请失败！");
                    }
                }else {
                    JxcCreditScoreTemplate effectiveById = jxcProjectOrderMapper.getEffectiveById(CreditScoreTemplateConstants.SCORE_TEN_CANCEL_DRIVER_ORDER.getId());
                    if(effectiveById != null){
                        //拿到规则时间
                        Integer condition = effectiveById.getCondition();
                        String startDate = jxcProjectOrderVo1.getStartDate();
                        if((DateUtils.parseYMD(startDate).getTime() - System.currentTimeMillis()) > condition*60*60*1000){
                            //大于规则时间提示
                            try {
                                jxcProjectOrderMapper.cancelProjectOrder(jxcProjectOrderVo);
                                //推送消息
                                cancelOrderPush(jxcProjectOrderVo);
                                jxcProjectOrderMapper.cancelOwnerOrder(jxcProjectOrderVo);
                            } catch (Exception e) {
                                return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "申请失败！");
                            }
                        }else {
                            try {
                                jxcProjectOrderMapper.cancelProjectOrder(jxcProjectOrderVo);
                                //推送消息
                                cancelOrderPush(jxcProjectOrderVo);
                                jxcProjectOrderMapper.cancelOwnerOrder(jxcProjectOrderVo);
                                //扣除信用分
                                UserCreditScoreVo userCreditScoreVo = new UserCreditScoreVo();
                                userCreditScoreVo.setUserId(userId);
                                userCreditScoreVo.setTemplateId(CreditScoreTemplateConstants.SCORE_TEN_CANCEL_DRIVER_ORDER.getId());
                                jxcCreditScoreScoredFeign.insertCreditScoreScored(userCreditScoreVo);
                            } catch (Exception e) {
                                return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "申请失败！");
                            }
                        }
                    }else {
                        try {
                            jxcProjectOrderMapper.cancelProjectOrder(jxcProjectOrderVo);
                            //推送消息
                            cancelOrderPush(jxcProjectOrderVo);
                            jxcProjectOrderMapper.cancelOwnerOrder(jxcProjectOrderVo);
                        } catch (Exception e) {
                            return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "申请失败！");
                        }
                    }
                }
            }else {
                return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"非待接单状态下的订单不能取消");
            }

        }else {
            return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"订单不存在");
        }

        return new ResponseMessage();
    }
    private void cancelOrderPush(JxcProjectOrderVo jxcProjectOrderVo){
        //推送给机主
        List<JxcOwnerOrderDetailVo> pushInfoList = jxcProjectOrderMapper.getOwnerPushInfoList(jxcProjectOrderVo.getId(),0);
        if(pushInfoList != null && pushInfoList.size() > 0){
            for (JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo : pushInfoList){
                //站内信
                JpushCustomMsgVo jpushCustomMsgVo = new JpushCustomMsgVo();
                jpushCustomMsgVo.setUserId(jxcOwnerOrderDetailVo.getUserId());
                jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_CANCEL_ORDER_TELL_OWNER.getId());
                JSONObject js = new JSONObject();
                js.put("machineCardNo",jxcOwnerOrderDetailVo.getMachineCarNo());
                js.put("ownerOrderId",jxcOwnerOrderDetailVo.getOwnerOrderId());
                jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                //推送
                JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                jpushTemplateVo.setAliases(jxcOwnerOrderDetailVo.getThirdId());
                jpushTemplateVo.setParams(js);
                jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_CANCEL_ORDER_TELL_OWNER.getId());
                jpushMsgFeign.jpushNotice(jpushTemplateVo);

                //推给司机
                List<Map<String, Object>> driverPushInfoList = jxcProjectOrderMapper.getDriverPushInfoList(jxcOwnerOrderDetailVo.getMachineId(), jxcOwnerOrderDetailVo.getUserId());
                if (driverPushInfoList != null && driverPushInfoList.size() > 0){
                    for (Map<String,Object> map : driverPushInfoList){
                        jpushCustomMsgVo.setUserId(Integer.valueOf(String.valueOf(map.get("driverId"))));
                        jpushCustomMsgVo.setServiceCode(PushTemplateConstants.JPUSH_TEN_CANCEL_ORDER_TELL_DRIVER.getId());
                        jpushMsgFeign.jpushCustomMsg(jpushCustomMsgVo);
                        jpushTemplateVo.setAliases(String.valueOf(map.get("thirdId")));
                        jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_TEN_CANCEL_ORDER_TELL_DRIVER.getId());
                        jpushMsgFeign.jpushNotice(jpushTemplateVo);
                    }
                }
            }
        }
    }
    /**
     * 验证消纳场是否可以更换
     *
     * @param user
     * @param orderId
     * @param siteId
     * @return
     */
    @Override
    public ResponseMessage confirmSiteCoupon(AuthorizationUser user, Long orderId, Integer siteId) {
        //获取该订单的土方类型以及方量
        JxcProjectOrderVo jxcProjectOrderVo = jxcProjectOrderMapper.selectJxcProjectOrderById(orderId);
        if (jxcProjectOrderVo != null) {
            //查询该用户在该消纳场的券的张数
            Integer siteCouponCount = jxcProjectOrderMapper.countSiteCouponCount(user.getUserId(), siteId, jxcProjectOrderVo.getEarthType(), jxcProjectOrderVo.getCapacity());
            //查询目前车辆的在途数量
            List<Integer> machineIdList = jxcProjectOrderMapper.countMachineOngoing(orderId);
            if (machineIdList != null) {
                if (machineIdList.size() > siteCouponCount) {
                    return new ResponseMessage(ErrorCodeConstants.CHANGE_SITE_NOTICE.getId(), "弹出提示：发实体卡或者购买消纳券");
                }
            }
        }
        return new ResponseMessage();
    }

    /**
     * 更换消纳场
     *
     * @param jxcProjectOrderVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage changeSite(AuthorizationUser user, JxcProjectOrderVo jxcProjectOrderVo) {
        //更换消纳场不能更换原有的计价方式，所以先查出原有的计价方式
        JxcOrderRefSite jxcOrderRefSite = jxcProjectOrderMapper.getJxcOrderRefSite(jxcProjectOrderVo.getId());
        jxcProjectOrderVo.setUserId(user.getUserId());
        List<JxcSiteVo> siteList = jxcProjectOrderVo.getSiteList();
        if (siteList != null && siteList.size() > 0) {
            for (JxcSiteVo site : siteList) {
                site.setPricingMode(jxcOrderRefSite.getPricingMode());
                if (site.getId().equals(jxcOrderRefSite.getSiteId())) {
                    return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "不能选择现有的消纳场！");
                }
            }
            //更换消纳场
            //先将该订单的原消纳场给变为停用
            try {
                jxcProjectOrderMapper.updateJxcOrderRefSite(jxcOrderRefSite.getId());
                //再将新消纳场添加进去
                ResponseMessage responseMessage = addSite(jxcProjectOrderVo);
                if (responseMessage.getCode() != 200) {
                    return responseMessage;
                }
                //然后给所有在途的车辆发一张新卡
                List<Integer> machineIdList = jxcProjectOrderMapper.countMachineOngoing(jxcProjectOrderVo.getId());
                if (machineIdList != null && machineIdList.size() > 0) {
                    JxcProjectOrderVo jxcProjectOrderVo1 = jxcProjectOrderMapper.selectJxcProjectOrderById(jxcProjectOrderVo.getId());
                    JxcProjectUserVo jxcProjectUserVo = new JxcProjectUserVo();
                    jxcProjectUserVo.setProjectUserId(user.getUserId());
                    jxcProjectUserVo.setCapacity(jxcProjectOrderVo1.getCapacity());
                    jxcProjectUserVo.setCouponType(jxcProjectOrderVo1.getEarthType());
                    List<JxcSiteVo> siteList1 = jxcProjectOrderVo.getSiteList();
                    if (siteList1 != null && siteList1.size() == 1) {
                        jxcProjectUserVo.setSiteId(siteList1.get(0).getId());
                    } else {
                        return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "只能选择一个消纳场");
                    }
                    for (Integer machineId : machineIdList) {
                        //获取消纳劵电子券号
                        Long couponId = jxcProjectUserMapper.getCouponNumById(jxcProjectUserVo);
                        //更新消纳劵电子券
                        jxcProjectUserMapper.updateCouponById(couponId, machineId, jxcProjectOrderVo1.getProjectId());
                    }
                }

                //给正在上班中的司机推送通知以及自定义消息
                List<String> driverThirdId = jxcProjectOrderMapper.getDriverThirdId(jxcProjectOrderVo.getId());
                if(driverThirdId != null && driverThirdId.size() > 0){
                    JxcSite site = jxcProjectOrderMapper.getSiteNameBySiteId(jxcProjectOrderVo.getSiteList().get(0).getId());
                    if(site != null) {
                        for (String thirdId : driverThirdId) {
                            //弹框（自已定义消息）
                            JpushMessageVo jpushMessageVo = new JpushMessageVo();
                            jpushMessageVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                            jpushMessageVo.setMessageTitle("更换消纳场通知");
                            jpushMessageVo.setMessageContent("雇主已将您正在运输中的订单所关联的消纳场更换为" + site.getSiteName() + "，请注意！");
                            jpushMessageVo.setAliases(thirdId);
                            JSONObject js = new JSONObject();
                            js.put("type",PushTemplateConstants.JPUSH_CHANGE_SITE_NOTICE.getId());
                            js.put("siteName",site.getSiteName());
                            jpushMessageVo.setMessageExtraParams(js);
                            jpushMessageVo.setSendToAll(false);
                            jpushMsgFeign.jpushMessage(jpushMessageVo);
                            //推送通知
                            JpushTemplateVo jpushTemplateVo = new JpushTemplateVo();
                            jpushTemplateVo.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                            jpushTemplateVo.setAliases(thirdId);
                            jpushTemplateVo.setTemplateCode(PushTemplateConstants.JPUSH_CHANGE_SITE_NOTICE.getId());
                            js.remove("type");
                            jpushTemplateVo.setParams(js);
                            jpushMsgFeign.jpushNotice(jpushTemplateVo);
                        }
                    }
                }

            } catch (Exception e) {
                return new ResponseMessage(ErrorCodeConstants.EDIT_ERORR.getId(), "更换消纳场失败！");
            }

        }
        return new ResponseMessage();
    }

    /**
     * 查询装车记录
     *
     * @param projectOrderVo
     * @return
     */
    @Override
    public ResponseMessage selectRouteRecord(JxcProjectOrderVo projectOrderVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();

        List<JxcProjectOrder> jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(null, null, projectOrderVo.getId(), null);
        if (jxcProjectOrders == null || jxcProjectOrders.size() == 0) {
            return new ResponseMessage(ErrorCodeConstants.PARAM_EMPTY.getId(), "该订单不存在!");
        }
        JxcProjectOrder jxcProjectOrder = jxcProjectOrders.get(0);
        OrderRouteRecord orr = new OrderRouteRecord();
        //设置orderId
        orr.setOrderId(projectOrderVo.getId());
        orr.setProjectId(jxcProjectOrder.getProjectId());
        projectOrderVo.setAccountMethod(jxcProjectOrder.getAccountMethod());
        //停工标记
        orr.setStopWorkState(jxcProjectOrder.getStopWorkState());
        //订单状态
        Integer orderState = jxcProjectOrder.getOrderState();
        orr.setOrderState(orderState);
        projectOrderVo.setAccountMethod(jxcProjectOrder.getAccountMethod());
        orr.setAccountMethod(jxcProjectOrder.getAccountMethod());
        //设置项目名
        orr.setProjectName(jxcProjectOrder.getProjectName());
        //设置总计趟数
        int totalCount = jxcProjectOrderMapper.getTotalCount(projectOrderVo.getId());
        orr.setTotalRouteCount(totalCount);
        //设置已支付金额
        int payAmount = jxcProjectOrderMapper.getPayAmount(projectOrderVo.getId());
        orr.setTotalPay(VerifyStr.strToStr(payAmount + ""));
        //设置待支付标签以及待支付金额
        ProjectOrderListVo projectOrderListVo = getPayFlagAndPayAmount(jxcProjectOrder);
        orr.setPayFlag(projectOrderListVo.getPayFlag());
        if (projectOrderListVo.getPayAmount() != null && !projectOrderListVo.getPayAmount().equals("")) {
            orr.setTotalNoPay(projectOrderListVo.getPayAmount());
        }
        //设置待支付趟数
        orr.setNoPayRouteCount(projectOrderListVo.getNoPayRouteCount());
        if (jxcProjectOrder.getAccountMethod().equals(1)) {
            String searchStartDate = projectOrderVo.getSearchStartDate();
            //查询正常趟数列表 没传时间查所有截止到目前  传了时间查传的日期的那一天
            if (searchStartDate == null || searchStartDate.equals("")) {
                projectOrderVo.setSearchStartDate(null);
                projectOrderVo.setSearchEndDate(DateUtils.format(new Date()));
            } else {
                projectOrderVo.setSearchStartDate(searchStartDate);
                projectOrderVo.setSearchEndDate(searchStartDate);
            }
        }else {
            //周结不传日期，查所有
            if(projectOrderVo.getSearchStartDate() == null || projectOrderVo.getSearchStartDate().equals("")) {
                projectOrderVo.setSearchStartDate(DateUtils.format(jxcProjectOrder.getStartDate()));
                projectOrderVo.setSearchEndDate(DateUtils.format(jxcProjectOrder.getEndDate()));
            }
        }
        //设置正常趟数
        int normalRouteCount = jxcProjectOrderMapper.countRouteCount(projectOrderVo, 2);
        orr.setNormalRouteCount(normalRouteCount);
        //设置异常趟数
        int abnormalRouteCount = jxcProjectOrderMapper.countRouteCount(projectOrderVo, 3);
        orr.setAbnormalRouteCount(abnormalRouteCount);

        //日结
        if (jxcProjectOrder.getAccountMethod().equals(1)) {
            //查询待支付趟数列表 只有查询当天的时候才有可能出现待支付列表  判断传的日期是否为当天
            PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"mr.start_time DESC , mr.owner_order_id ASC");
            List<MachineRouteRecord> machineRouteRecords = jxcProjectOrderMapper.selectNoPayList(projectOrderVo);
            if (machineRouteRecords != null && machineRouteRecords.size() > 0) {
                for (MachineRouteRecord mrr : machineRouteRecords) {
                    mrr.setState(3);
                    mrr.setAmount(VerifyStr.strToStr(mrr.getAmount()));
                    //设置行程支付ID集合
                    List<Long> routeIdListB = jxcProjectOrderMapper.getRouteIdListB(mrr);
                    mrr.setRouteIdList(routeIdListB);
                }
            }
            orr.setNoPayList(machineRouteRecords);
            //查询行程集合用于支付
            List<Long> routeIdListA = jxcProjectOrderMapper.getRouteIdListA(projectOrderVo);
            orr.setRouteIdList(routeIdListA);

            //查询正常列表
            List<MachineRouteRecord> normalList = new ArrayList<>();
            normalList = jxcProjectOrderMapper.selectNormalList(projectOrderVo);
            if (normalList != null && normalList.size() > 0) {
                for (MachineRouteRecord mrr : normalList) {
                    mrr.setAmount(VerifyStr.strToStr(mrr.getAmount()));
                }
            }
            PageInfo<MachineRouteRecord> page1 = new PageInfo<>(normalList);
            orr.setNormalList(new PageUtils<>(page1).getPageViewDatatable());
            //查询异常列表
            List<MachineRouteRecord> abnormalList = new ArrayList<>();
            PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"mr.start_time DESC , mr.owner_order_id ASC");
            abnormalList = jxcProjectOrderMapper.selectAbnormalList(projectOrderVo);
            //计算金额及确定显示状态
            if (abnormalList != null && abnormalList.size() > 0) {
                for (MachineRouteRecord record : abnormalList) {
                    //查询该机械的异常行程列表
                    projectOrderVo.setSearchStartDate(record.getStartDate());
                    projectOrderVo.setSearchEndDate(record.getStartDate());
                    List<AbnormalRouteVo> abnormalRouteVos = jxcProjectOrderMapper.selectAbnormalRouteVoList(projectOrderVo, record.getMachineId());
                    int amount = 0;
                    for (AbnormalRouteVo routeVo : abnormalRouteVos) {
                        Integer payState = routeVo.getPayState();
                        Integer abnormalStatus = routeVo.getAbnormalStatus();
                        //先判断是否已支付状态 判断顺序 已支付<已申报<待支付<待处理
                        if (payState.equals(2)) {
                            record.setState(2);
                            amount = amount + Integer.parseInt(routeVo.getAmount());
                        } else if (payState.equals(0) && abnormalStatus.equals(0)) {
                            record.setState(5);
                        } else if (payState.equals(1)) {
                            record.setState(1);
                            amount = amount + Integer.parseInt(routeVo.getAmount());
                        } else if (payState.equals(0) && abnormalStatus.equals(-1)) {
                            record.setState(4);
                        } else {
                            //剩下的没判断到的统统显示待处理
                            record.setState(4);
                        }
                    }
                    //设置金额
                    record.setAmount(VerifyStr.strToStr(amount + ""));
                }
            }
            PageInfo<MachineRouteRecord> page = new PageInfo<>(abnormalList);
            orr.setAbnormalList(new PageUtils<>(page).getPageViewDatatable());
        }
        String s = DateUtils.format(jxcProjectOrder.getStartDate());
        String e = DateUtils.format(jxcProjectOrder.getEndDate());
        String startTime = s + " 00:00:00";
        String endTime = e + " 23:59:59";
        //周结
        if (jxcProjectOrder.getAccountMethod().equals(2)) {
            String searchEndDate = projectOrderVo.getSearchEndDate();
            String searchStartDate = projectOrderVo.getSearchStartDate();
            //查询待支付列表
            //先判断当前订单是进行中还是已完结
            if (jxcProjectOrder.getOrderState().equals(3)) {
                List<Map<String, String>> weekList = DateUtils.getWeek(s, e);
                List<MachineRouteRecord> machineRouteRecords = new ArrayList<>();
                if (weekList != null && weekList.size() > 0) {
                    //传的时间在订单结束时间之前 那就按照传的时间来进行查询
                    for (Map<String, String> week : weekList) {
                        projectOrderVo.setSearchStartDate(String.valueOf(week.get("startDate")));
                        projectOrderVo.setSearchEndDate(String.valueOf(week.get("endDate")));
                        PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"ifnull(mr.start_time,mr.end_time) DESC , mr.owner_order_id ASC");
                        List<MachineRouteRecord> childList = jxcProjectOrderMapper.selectNoPayList(projectOrderVo);
                        if (childList != null && childList.size() > 0) {
                            for (MachineRouteRecord mrr : childList) {
                                mrr.setState(3);
                                mrr.setAmount(VerifyStr.strToStr(mrr.getAmount()));
                                //设置行程支付ID集合
                                List<Long> routeIdListB = jxcProjectOrderMapper.getRouteIdListB(mrr);
                                mrr.setRouteIdList(routeIdListB);
                                mrr.setStartDate(String.valueOf(week.get("startDate")).split(" ")[0]);
                                mrr.setEndDate(String.valueOf(week.get("endDate")).split(" ")[0]);
                            }
                        }
                        machineRouteRecords.addAll(childList);
                    }
                }
                orr.setNoPayList(machineRouteRecords);
                //查询行程集合用于支付
                projectOrderVo.setSearchStartDate(s);
                projectOrderVo.setSearchEndDate(e);
                List<Long> routeIdListA = jxcProjectOrderMapper.getRouteIdListA(projectOrderVo);
                orr.setRouteIdList(routeIdListA);
            } else if (jxcProjectOrder.getOrderState().equals(2)) {
                //进行中的订单
                //查询当前日期所在的周
                Map<String, Object> week = DateUtils.getWeek(startTime, endTime, DateUtils.formatHMS(new Date()));
                //看看所在周的开始日期是否跟订单开始日期相等
                if (week != null) {
                    String startDate = String.valueOf(week.get("startDate"));
                    if (!startDate.equals(startTime)) {
                        //如果不等，则用本周开始的时间作为结束时间点  查询之前的行程是否有未支付的 相等则表明第一周  不需要查询待支付
                        //待支付是需要查询上一周的  计算上一周的时间区间
                        Map<String, Object> week1 = DateUtils.getWeek(startTime, endTime, DateUtils.getYesterday(DateUtils.parseYMD(startDate.split(" ")[0])) + " 23:59:59");

                        String s1 = String.valueOf(week1.get("startDate")).split(" ")[0];
                        String e1 = String.valueOf(week1.get("endDate")).split(" ")[0];
                        projectOrderVo.setSearchStartDate(s1);
                        projectOrderVo.setSearchEndDate(e1);
                        PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"ifnull(mr.start_time,mr.end_time) DESC , mr.owner_order_id ASC");
                        List<MachineRouteRecord> machineRouteRecords = jxcProjectOrderMapper.selectNoPayList(projectOrderVo);
                        if (machineRouteRecords != null && machineRouteRecords.size() > 0) {
                            for (MachineRouteRecord mrr : machineRouteRecords) {
                                mrr.setState(3);
                                mrr.setAmount(VerifyStr.strToStr(mrr.getAmount()));
                                //设置行程支付ID集合
                                List<Long> routeIdListB = jxcProjectOrderMapper.getRouteIdListB(mrr);
                                mrr.setRouteIdList(routeIdListB);
                                mrr.setStartDate(s1);
                                mrr.setEndDate(e1);
                            }
                        }
                        orr.setNoPayList(machineRouteRecords);
                        //查询行程集合用于支付
                        List<Long> routeIdListA = jxcProjectOrderMapper.getRouteIdListA(projectOrderVo);
                        orr.setRouteIdList(routeIdListA);
                    }
                }
            }
            //如果前端不传时间
            List<Map<String, String>> weekList = DateUtils.getWeek(searchStartDate, searchEndDate);
            /*if (searchStartDate == null || searchStartDate.equals("")) {
                //传空的时候，查所有周
                weekList = DateUtils.getWeek(s, e);
            } else {
                Map<String, String> map = new HashMap<>(2);
                map.put("startDate", searchStartDate);
                map.put("endDate", searchEndDate);
                weekList.add(map);
            }*/
            List<MachineRouteRecord> normalList = new ArrayList<>();
            for (int i=weekList.size()-1;i>=0;i--) {
                Map<String, String> week = weekList.get(i);
                projectOrderVo.setSearchStartDate(String.valueOf(week.get("startDate")).split(" ")[0]);
                projectOrderVo.setSearchEndDate(String.valueOf(week.get("endDate")).split(" ")[0]);
                //查询正常列表
                PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"ifnull(mr.start_time,mr.end_time) DESC , mr.owner_order_id ASC");
                List<MachineRouteRecord> childNormalList = jxcProjectOrderMapper.selectNormalList(projectOrderVo);
                if (childNormalList != null && childNormalList.size() > 0) {
                    for (MachineRouteRecord mrr : childNormalList) {
                        //在这里对周结的订单行程状态显示单独处理一下
                        if (jxcProjectOrder.getAccountMethod().equals(2)) {
                            //如果当前时间所在的周与传过来的时间所在周相等  那就行程记录就标识为进行中
                            Map<String, Object> week1 = DateUtils.getWeek(startTime, endTime, DateUtils.formatHMS(new Date()));
                            if (String.valueOf(week1.get("startDate")).compareTo(projectOrderVo.getSearchStartDate() + " 00:00:00") == 0) {
                                mrr.setState(0);
                            }else {
                                //设置状态
                                if(mrr.getPayState().equals(1)){
                                    mrr.setState(1);
                                }else if(mrr.getPayState().equals(2)){
                                    mrr.setState(2);
                                }
                            }
                        }
                        mrr.setAmount(VerifyStr.strToStr(mrr.getAmount()));
                        mrr.setStartDate(String.valueOf(week.get("startDate")).split(" ")[0]);
                        mrr.setEndDate(String.valueOf(week.get("endDate")).split(" ")[0]);

                    }
                }
                normalList.addAll(childNormalList);
            }
            PageInfo<MachineRouteRecord> page1 = new PageInfo<>(normalList);
            orr.setNormalList(new PageUtils<>(page1).getPageViewDatatable());
            //查询异常列表
            List<MachineRouteRecord> abnormalList = new ArrayList<>();
            for (int i=weekList.size()-1;i>=0;i--) {
                Map<String, String> week = weekList.get(i);
                projectOrderVo.setSearchStartDate(String.valueOf(week.get("startDate")).split(" ")[0]);
                projectOrderVo.setSearchEndDate(String.valueOf(week.get("endDate")).split(" ")[0]);
                PageHelper.startPage(projectOrderVo.getPageNo(),projectOrderVo.getPageSize(),"ifnull(mr.start_time,mr.end_time) DESC , mr.owner_order_id ASC");
                List<MachineRouteRecord> childAbnormalList = jxcProjectOrderMapper.selectAbnormalList(projectOrderVo);
                //计算金额及确定显示状态
                if (childAbnormalList != null && childAbnormalList.size() > 0) {
                    for (MachineRouteRecord record : childAbnormalList) {
                        //查询该机械的异常行程列表
                        projectOrderVo.setSearchStartDate(record.getStartDate());
                        projectOrderVo.setSearchEndDate(record.getStartDate());
                        List<AbnormalRouteVo> abnormalRouteVos = jxcProjectOrderMapper.selectAbnormalRouteVoList(projectOrderVo, record.getMachineId());
                        int amount = 0;
                        for (AbnormalRouteVo routeVo : abnormalRouteVos) {
                            Integer payState = routeVo.getPayState();
                            Integer abnormalStatus = routeVo.getAbnormalStatus();
                            //先判断是否已支付状态 判断顺序 已支付<已申报<待支付<待处理
                            if (payState.equals(2)) {
                                record.setState(2);
                                amount = amount + Integer.parseInt(routeVo.getAmount());
                            } else if (payState.equals(0) && abnormalStatus.equals(0)) {
                                record.setState(5);
                            } else if (payState.equals(1)) {
                                record.setState(1);
                                amount = amount + Integer.parseInt(routeVo.getAmount());
                            } else if (payState.equals(0) && abnormalStatus.equals(-1)) {
                                record.setState(4);
                            } else {
                                //剩下的没判断到的统统显示待处理
                                record.setState(4);
                            }
                        }
                        //设置金额
                        record.setAmount(VerifyStr.strToStr(amount + ""));
                        record.setStartDate(String.valueOf(week.get("startDate")).split(" ")[0]);
                        record.setEndDate(String.valueOf(week.get("endDate")).split(" ")[0]);
                    }
                }
                abnormalList.addAll(childAbnormalList);
            }
            PageInfo<MachineRouteRecord> page = new PageInfo<>(abnormalList);
            orr.setAbnormalList(new PageUtils<>(page).getPageViewDatatable());
        }
        //把查询时间给前端返回
        orr.setSearchStartDate(projectOrderVo.getSearchStartDate());
        orr.setSearchEndDate(projectOrderVo.getSearchEndDate());
        result.setData(orr);
        return result;
    }

    /**
     * 查询装车记录详情列表
     *
     * @param machineRouteRecord
     * @return
     */
    @Override
    public ResponseMessage selectRouteRecordList(MachineRouteRecord machineRouteRecord) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        //查询一些订单的消息后面要用
        List<JxcProjectOrder> jxcProjectOrders = jxcProjectOrderMapper.selectProjectOrderList(null, null, machineRouteRecord.getOrderId(), null);
        if(jxcProjectOrders == null || jxcProjectOrders.size() == 0){
            return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(),"未查询到该数据！");
        }
        JxcProjectOrder jxcProjectOrder = jxcProjectOrders.get(0);
        //结算方式
        Integer accountMethod = jxcProjectOrder.getAccountMethod();
        //异常类型
        Integer abnormalType = machineRouteRecord.getAbnormalType();
        //订单开始结束时间
        Date startDate = jxcProjectOrder.getStartDate();
        Date endDate = jxcProjectOrder.getEndDate();
        String startDate1 = machineRouteRecord.getStartDate();
        //日结
        if (jxcProjectOrder.getAccountMethod().equals(1)) {
            //日结 肯定是查询某一天的
            machineRouteRecord.setEndDate(startDate1);
        }
        String s = DateUtils.format(startDate);
        String e = DateUtils.format(endDate);
        String startTime = s + " 00:00:00";
        String endTime = e + " 23:59:59";

        List<RouteRecordDetailVo> routeRecordDetailList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        JxcProjectOrderVo projectOrderVo = new JxcProjectOrderVo();
        if (accountMethod.equals(1)) {
            projectOrderVo.setSearchStartDate(machineRouteRecord.getStartDate());
            projectOrderVo.setSearchEndDate(machineRouteRecord.getStartDate());
            machineRouteRecord.setEndDate(machineRouteRecord.getStartDate());
        } else {
            projectOrderVo.setSearchStartDate(machineRouteRecord.getStartDate());
            projectOrderVo.setSearchEndDate(machineRouteRecord.getEndDate());
        }
        projectOrderVo.setId(machineRouteRecord.getOrderId());

        if (abnormalType.equals(0)) {
            //查询正常列表
            routeRecordDetailList = jxcProjectOrderMapper.selectRouteRecordDetailList(machineRouteRecord, accountMethod, 1);
            if (routeRecordDetailList != null && routeRecordDetailList.size() > 0) {
                for (RouteRecordDetailVo mrr : routeRecordDetailList) {
                    //在这里对周结的订单行程状态显示单独处理一下
                    if (jxcProjectOrder.getAccountMethod().equals(2) && jxcProjectOrder.getOrderState().equals(2)) {
                        //如果当前时间所在的周与传过来的时间所在周相等  那就行程记录就标识为进行中
                        Map<String, Object> week = DateUtils.getWeek(startTime, endTime, DateUtils.formatHMS(new Date()));
                        if (String.valueOf(week.get("startDate")).compareTo(machineRouteRecord.getStartDate() + " 00:00:00") == 0) {
                            mrr.setState(0);
                        }else {
                            //设置状态
                            if(mrr.getPayState().equals(1)){
                                mrr.setState(1);
                            }else if(mrr.getPayState().equals(2)){
                                mrr.setState(2);
                            }
                        }

                    }
                    /*//待支付都转成去支付
                    if(mrr.getState() != null && mrr.getState().equals(1)){
                        mrr.setState(3);
                    }*/
                    //实际金额
                    mrr.setFactAmount(VerifyStr.strToStr(mrr.getFactAmount()));
                    //预计金额
                    mrr.setEstimatePrice(VerifyStr.strToStr(mrr.getEstimatePrice()));
                    //拼接行程
                    RouteRecordDetailVo handoverList = getHandoverList(mrr);
                    if(handoverList != null){
                        mrr.setHandoverFlag(handoverList.getHandoverFlag());
                        mrr.setHandoverList(handoverList.getHandoverList());
                    }
                }
            }
            //设置趟数
            map.put("totalRouteCount", routeRecordDetailList.size());
            //设置待支付ID集合
            List<Long> list = jxcProjectOrderMapper.selectRouteIdListByMachineId(machineRouteRecord, 1);
            map.put("routeIdList", list);
            //设置待支付总金额
            Integer integer = jxcProjectOrderMapper.sumPayAmountByMachineId(machineRouteRecord, 1);
            map.put("totalAmount", VerifyStr.strToStr(integer.toString()));
        }
        if (abnormalType.equals(1) || abnormalType.equals(2) || abnormalType.equals(3)) {
            routeRecordDetailList = jxcProjectOrderMapper.selectRouteRecordDetailList(machineRouteRecord, accountMethod, 2);
            if (routeRecordDetailList != null && routeRecordDetailList.size() > 0) {
                for (RouteRecordDetailVo mrr : routeRecordDetailList) {
                    //还未申报异常
                    if (mrr.getAbnormalStatus().equals(-1) && mrr.getPayState().equals(0)) {
                        mrr.setState(4);
                    }
                    //已申报了但还未处理
                    if (mrr.getAbnormalStatus().equals(0) && mrr.getPayState().equals(0)) {
                        mrr.setState(5);
                    }
                    //去支付
                    if (mrr.getAbnormalStatus().equals(1) && mrr.getPayState().equals(1)) {
                        mrr.setState(3);
                    }
                    //已支付
                    if (mrr.getPayState().equals(2)) {
                        mrr.setState(2);
                    }
                    //实际金额
                    mrr.setFactAmount(VerifyStr.strToStr(mrr.getFactAmount()));
                    //预计金额
                    mrr.setEstimatePrice(VerifyStr.strToStr(mrr.getEstimatePrice()));

                    //拼接行程
                    RouteRecordDetailVo handoverList = getHandoverList(mrr);
                    if(handoverList != null){
                        mrr.setHandoverFlag(handoverList.getHandoverFlag());
                        mrr.setHandoverList(handoverList.getHandoverList());
                    }
                }
            }

            //设置趟数
            map.put("totalRouteCount", routeRecordDetailList.size());
            //设置待支付ID集合
            List<Long> list = jxcProjectOrderMapper.selectRouteIdListByMachineId(machineRouteRecord, 2);
            map.put("routeIdList", list);
            //设置待支付总金额
            Integer integer = jxcProjectOrderMapper.sumPayAmountByMachineId(machineRouteRecord, 2);
            map.put("totalAmount", VerifyStr.strToStr(integer.toString()));
        }
        map.put("list", routeRecordDetailList);
        result.setData(map);
        return result;
    }

    private RouteRecordDetailVo getHandoverList(RouteRecordDetailVo vo){
        if(vo.getAbnormalType().equals(0) || vo.getAbnormalType().equals(3) ) {
            JxcDriverHandoverVo driverHandover = jxcMachineRouteMapper.getDriverHandover(vo.getRouteId());
            if (driverHandover != null) {
                vo.setHandoverFlag(1);
                List<Map<String, Object>> handoverList = new ArrayList<>();
                //塞入两个司机的信息
                Map<String, Object> map1 = new HashMap<>(3);
                map1.put("thirdId", driverHandover.getThirdIdDown());
                map1.put("startTime", vo.getStartTime());
                map1.put("endTime", driverHandover.getCreateTimeStr());
                handoverList.add(map1);
                Map<String, Object> map2 = new HashMap<>(3);
                map2.put("thirdId", driverHandover.getThirdIdUp());
                map2.put("startTime", driverHandover.getCreateTimeStr());
                map2.put("endTime", vo.getEndTime());
                handoverList.add(map2);
                vo.setHandoverList(handoverList);
            }
        }
        return vo;
    }

    /**
     * 查询异常待支付列表
     *
     * @param orderId
     * @return
     */
    @Override
    public ResponseMessage selectAbnormalRouteRecordList(Long orderId) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        List<AbnormalRouteVo> routeRecordDetailVos = jxcProjectOrderMapper.selectAbnormalRouteRecordList(orderId);
        List<Long> routeIdList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(1);
        String totalAmount = "0.00";
        if (routeRecordDetailVos != null && routeRecordDetailVos.size() > 0) {
            for (AbnormalRouteVo routeVo : routeRecordDetailVos) {
                //转换金额
                routeVo.setAmount(VerifyStr.strToStr(routeVo.getAmount()));
                routeIdList.add(routeVo.getRouteId());
            }
            //查询总金额
            Integer i = jxcProjectOrderMapper.sumAbnormalPayAmount(orderId);
            totalAmount = VerifyStr.strToStr(i.toString());
        }
        JxcProjectOrderVo jxcProjectOrderVo = jxcProjectOrderMapper.selectJxcProjectOrderById(orderId);
        map.put("projectId", jxcProjectOrderVo.getProjectId());
        map.put("routeIdList", routeIdList);
        map.put("totalAmount", totalAmount);
        map.put("routeList", routeRecordDetailVos);
        result.setData(map);
        return result;
    }

    /**
     * 确认异常行程
     *
     * @param routeId
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage confirmAbnormalRoute(AuthorizationUser user, Long routeId) {
        //判断该行程是否已经申诉了防止重复提交
        JxcAbnormalOrderAppeal jxcAbnormalOrderAppealByRouteId = jxcMachineRouteMapper.getJxcAbnormalOrderAppealByRouteId(routeId);
        if (jxcAbnormalOrderAppealByRouteId != null) {
            return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "该异常行程已经被申诉不能确认正常！");
        }
        //查询异常行程的信息
        JxcMachineRoute routeInfoById = jxcMachineRouteMapper.getRouteInfoById(routeId);
        try {
            jxcProjectOrderMapper.insertAbnormalOrderAppealBySelf(routeInfoById);
            //修改支付状态
            JxcMachineRoute machineRoute = new JxcMachineRoute();
            machineRoute.setId(routeId);
            machineRoute.setPayState(1);
            jxcMachineRouteMapper.updateByPrimaryKeySelective(machineRoute);
            //消除异常行程待办事项
            jxcWaitHandleItemsMapper.clearAbnormalRoute(routeId);
            //金额大于0时，才做如下操作
            if (routeInfoById.getAmount() != null && routeInfoById.getAmount() > 0) {
                //用于插入机主财务流水表的bean
                JxcTradeOwner jxcTradeOwner = new JxcTradeOwner();
                jxcTradeOwner.setId(IDGenerator.getInstance().next());
                jxcTradeOwner.setTradeType(1);
                jxcTradeOwner.setPayerUserId(0);
                jxcTradeOwner.setPayeeUserId(routeInfoById.getOwnerId());
                jxcTradeOwner.setTenantryOrderId(routeInfoById.getTenantryOrderId());
                jxcTradeOwner.setRouteId(routeId);
                jxcTradeOwner.setTradeStatus(1);
                jxcTradeOwner.setPaymentMethod(0);
                jxcTradeOwner.setLockStatus(0);
                jxcTradeOwner.setTradeAmount(routeInfoById.getToOwnerAmount());
                //插入流水表
                jxcMachineRouteMapper.insertJxcTradeOwner(jxcTradeOwner);
                //插入异常待支付待办事项
                JxcWaitHandleItems jxcWaitHandleItems = new JxcWaitHandleItems();
                jxcWaitHandleItems.setItemType(JxcWaitHandleItems.itemType.ITEMTYPE4);
                jxcWaitHandleItems.setProjectOrderId(routeInfoById.getTenantryOrderId());
                jxcWaitHandleItems.setOwnerOrderId(routeInfoById.getOwnerOrderId());
                jxcWaitHandleItems.setMachineId(routeInfoById.getMachineId());
                jxcWaitHandleItems.setDriverId(routeInfoById.getDriverId());
                jxcWaitHandleItems.setProjectName(routeInfoById.getProjectName());
                jxcWaitHandleItems.setUserId(routeInfoById.getTenantryId());
                jxcWaitHandleItems.setRouteId(routeId);
                jxcWaitHandleItems.setPayAmount(routeInfoById.getAmount());
                //插入待办事项
                jxcWaitHandleItemsMapper.insertSelective(jxcWaitHandleItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseMessage();
    }

    /**
     * 根据异常行程ID查询异常详情
     *
     * @param user
     * @param routeId
     * @return
     */
    @Override
    public ResponseMessage getAbnormalRouteDetail(AuthorizationUser user, Long routeId) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        AbnormalRouteVo abnormalRouteDetail = jxcMachineRouteMapper.getAbnormalRouteDetail(routeId);
        if (abnormalRouteDetail != null) {
            //转化一下金额
            if(user.getRoleId().equals(UserRoleContants.OWNER.getRoleId()) || user.getRoleId().equals(UserRoleContants.DRIVER.getRoleId()) || user.getRoleId().equals(UserRoleContants.CHILD.getRoleId())) {
                abnormalRouteDetail.setAmount(VerifyStr.strToStr(abnormalRouteDetail.getToOwnerAmount()));
                Integer toOwnerAmount = jxcOpenCityFeign.getToOwnerAmount(abnormalRouteDetail.getTenantryOrderId(), Integer.parseInt(abnormalRouteDetail.getEstimatePrice()));
                abnormalRouteDetail.setEstimatePrice(VerifyStr.strToStr(toOwnerAmount.toString()));
            }else{
                abnormalRouteDetail.setAmount(VerifyStr.strToStr(abnormalRouteDetail.getAmount()));
                abnormalRouteDetail.setEstimatePrice(VerifyStr.strToStr(abnormalRouteDetail.getEstimatePrice()));
            }
            //拼接行程的数据
            if(abnormalRouteDetail.getAbnormalType().equals(0) || abnormalRouteDetail.getAbnormalType().equals(3)) {
                RouteRecordDetailVo vo = new RouteRecordDetailVo();
                vo.setRouteId(routeId);
                vo.setAbnormalType(abnormalRouteDetail.getAbnormalType());
                vo.setStartTime(abnormalRouteDetail.getStartTime());
                vo.setEndTime(abnormalRouteDetail.getEndTime());
                RouteRecordDetailVo handoverList = getHandoverList(vo);
                if(handoverList != null){
                    abnormalRouteDetail.setHandoverFlag(handoverList.getHandoverFlag());
                    abnormalRouteDetail.setHandoverList(handoverList.getHandoverList());
                }
            }
            result.setData(abnormalRouteDetail);
        } else {
            return new ResponseMessage(ErrorCodeConstants.QUERY_EMPTY_DATA.getId(), "未查询到该异常记录！");
        }
        return result;
    }

    /**
     * 申诉异常行程
     *
     * @param jxcAbnormalOrderAppeal
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage applyAbnormalRoute(JxcAbnormalOrderAppeal jxcAbnormalOrderAppeal) {
        //判断该行程是否已经申诉了防止重复提交
        JxcAbnormalOrderAppeal jxcAbnormalOrderAppealByRouteId = jxcMachineRouteMapper.getJxcAbnormalOrderAppealByRouteId(jxcAbnormalOrderAppeal.getRouteId());
        if (jxcAbnormalOrderAppealByRouteId != null) {
            return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "该异常行程已经被申诉！");
        }
        JxcMachineRoute routeInfoById = jxcMachineRouteMapper.getRouteInfoById(jxcAbnormalOrderAppeal.getRouteId());
        try {
            jxcProjectOrderMapper.insertAbnormalOrderAppeal(routeInfoById, jxcAbnormalOrderAppeal.getAbnormalDescription());
            //消除待办事项
            jxcWaitHandleItemsMapper.clearAbnormalRoute(jxcAbnormalOrderAppeal.getRouteId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseMessage();
    }

    /**
     * 获取机主退场申请记录
     *
     * @param user
     * @param jxcOwnerApplyQuitVo
     * @return
     */
    @Override
    public ResponseMessage selectOwnerQuitRecordList(AuthorizationUser user, JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        if (jxcOwnerApplyQuitVo.getOrderId() != null && jxcOwnerApplyQuitVo.getOwnerOrderId() == null) {
            PageHelper.startPage(jxcOwnerApplyQuitVo.getPageNo(), jxcOwnerApplyQuitVo.getPageSize(), "oq.create_time DESC");
            List<JxcOwnerApplyQuitVo> jxcOwnerApplyQuitVos = jxcOwnerApplyQuitMapper.selectQuitList(user.getUserId(), jxcOwnerApplyQuitVo.getOrderId(), null);
            PageInfo<JxcOwnerApplyQuitVo> page = new PageInfo<>(jxcOwnerApplyQuitVos);
            result.setData(new PageUtils<>(page).getPageViewDatatable());
        } else {
            List<JxcOwnerApplyQuitVo> jxcOwnerApplyQuitVos = jxcOwnerApplyQuitMapper.selectQuitList(user.getUserId(), null, jxcOwnerApplyQuitVo.getOwnerOrderId());
            if (jxcOwnerApplyQuitVos != null && jxcOwnerApplyQuitVos.size() > 0) {
                JxcOwnerApplyQuitVo jxcOwnerApplyQuitVo1 = jxcOwnerApplyQuitVos.get(0);
                Map<String, Object> map = new HashMap<>(1);
                map.put("jxcOwnerApplyQuit", jxcOwnerApplyQuitVo1);
                result.setData(map);
            }else {
                result.setData(ErrorCodeConstants.QUERY_EMPTY_DATA.getId());
                result.setMessage("该待办事项已被处理或者撤回，请再次下拉刷新！");
            }
        }
        return result;
    }

    /**
     * 同意或者驳回机主退场申请
     *
     * @param jxcOwnerApplyQuit
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage dealOwnerQuitApply(JxcOwnerApplyQuit jxcOwnerApplyQuit) {
        try {
            jxcOwnerApplyQuit.setUpdateTime(new Date());
            jxcOwnerApplyQuitMapper.updateByPrimaryKeySelective(jxcOwnerApplyQuit);
            //同意，同时更改机主订单
            JxcOwnerApplyQuit jxcOwnerApplyQuit1 = jxcOwnerApplyQuitMapper.selectByPrimaryKey(jxcOwnerApplyQuit.getId());
            //清除待办事项
            jxcWaitHandleItemsMapper.clearOwnerQuitApply(jxcOwnerApplyQuit1.getOwnerOrderId());

            if (jxcOwnerApplyQuit.getApplyState().equals(1)) {
                JxcOwnerOrder jxcOwnerOrder = new JxcOwnerOrder();
                jxcOwnerOrder.setId(jxcOwnerApplyQuit1.getOwnerOrderId());
                jxcOwnerOrder.setOrderState(3);
                jxcOwnerOrderMapper.updateByPrimaryKeySelective(jxcOwnerOrder);

                //更新车辆状态为空闲中
                JxcOwnerOrder jxcOwnerOrder1 = jxcOwnerOrderMapper.getById(jxcOwnerApplyQuit1.getOwnerOrderId());
                if(jxcOwnerOrder1 != null){
                    JxcMachine jxcMachine = new JxcMachine();
                    jxcMachine.setId(jxcOwnerOrder1.getMachineId());
                    jxcMachine.setStatus(JxcMachine.carStatus.IN_IDLE);
                    jxcMachineMapper.updateByPrimaryKeySelective(jxcMachine);

                    //检查机械是否正在上班中的司机强制打下班卡
                    JxcClockInOutPair jxcClockInOutPair = jxcClockInOutPairMapper.getMachineWorkById(jxcOwnerOrder1.getMachineId());
                    if(jxcClockInOutPair != null){
                        JxcClockInOutRecord jxcClockInOutRecord = new JxcClockInOutRecord();
                        jxcClockInOutRecord.setShortJobId(jxcClockInOutPair.getShortJobId());
                        jxcClockInOutRecord.setDriverId(jxcClockInOutPair.getDriverId());
                        jxcClockInOutRecord.setClockAddress("机主强制补下班卡");
                        jxcClockInOutRecord.setMachineId(jxcClockInOutPair.getMachineId());
                        //下班打卡
                        jxcClockInOutRecordService.clockOut(jxcClockInOutRecord);
                    }
                }

                //刷新机主工作台
                JxcOwnerOrderDetailVo jxcOwnerOrderDetailVo = jxcProjectOrderMapper.getOwnerPushInfoByMachineId(jxcOwnerOrder);
                JpushMessageVo jpushTemplateVo3 = new JpushMessageVo();
                jpushTemplateVo3.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                jpushTemplateVo3.setAliases(jxcOwnerOrderDetailVo.getThirdId());
                jpushTemplateVo3.setMessageContent("刷新工作台");
                jpushTemplateVo3.setMessageTitle("刷新工作台");
                JSONObject js2 = new JSONObject();
                js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                jpushTemplateVo3.setMessageExtraParams(js2);
                jpushMsgFeign.jpushMessage(jpushTemplateVo3);
                //刷新司机工作台
                List<Map<String, Object>> driverPushInfoList = jxcProjectOrderMapper.getDriverPushInfoList(jxcOwnerOrderDetailVo.getMachineId(), jxcOwnerOrderDetailVo.getUserId());
                if (driverPushInfoList != null && driverPushInfoList.size() > 0){
                    for (Map<String,Object> map : driverPushInfoList){
                        //刷新工作台
                        JpushMessageVo jpushTemplateVo2 = new JpushMessageVo();
                        jpushTemplateVo2.setAppClient(ClientTypeConstants.CLIENT_OWNER.getId());
                        jpushTemplateVo2.setAliases(String.valueOf(map.get("thirdId")));
                        jpushTemplateVo2.setMessageContent("刷新工作台");
                        jpushTemplateVo2.setMessageTitle("刷新工作台");
                        js2.put("type",PushTemplateConstants.JPUSH_REFRESH.getId());
                        jpushTemplateVo2.setMessageExtraParams(js2);
                        jpushMsgFeign.jpushMessage(jpushTemplateVo2);
                    }
                }
            }

        } catch (Exception e) {
            return new ResponseMessage(ErrorCodeConstants.ADD_ERORR.getId(), "网络开小差了，请稍后重试！");
        }

        return new ResponseMessage();

    }

}