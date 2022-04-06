package com.weiwei.jixieche.utils;

import com.weiwei.jixieche.mapper.OwnerStatisticsMapper;
import com.weiwei.jixieche.util.DataTypeEmptyUtils;
import com.weiwei.jixieche.util.DateUtils;
import com.weiwei.jixieche.vo.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName StatisticsUtils
 * @Description TODO
 * @Author houji
 * @Date 2019/6/4 15:25
 * @Version 1.0.1
 **/
@Component
public class StatisticsUtils {

    @Resource
    private OwnerStatisticsMapper ownerStatisticsMapper;

    //查询时间统计数据
    public OwnerStatisticsVo getDateDataList(Long userId){
        OwnerStatisticsVo ownerStatisticsVo = new OwnerStatisticsVo();
        //根据时间获取昨天，前天
        Map<String,String> yesTime = DateUtils.getTimeYesterday();
        //获取当前时间昨天的时间(yyyy-MM-dd)
        String yesterday =  yesTime.get("yesterday");
        //获取当前时间前天的时间(yyyy-MM-dd)
        String beforeYesterday =  yesTime.get("beforeYesterday");
        //获取上周周一和周日的日期(yyyy-MM-dd)
        Map<String,String> weeekTime = DateUtils.getTimeInterval(new Date());
        String monday = weeekTime.get("monday");
        String sunday = weeekTime.get("sunday");
        //获取当月第一天时间(yyyy-MM)
        String firstDayMonth = DateUtils.getFirstDateByMonth(0);

        //时间统计报表数据展示

        //查询完结趟数集合
        DateItemDataVo itemDataVo = new DateItemDataVo();
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        //昨天完结趟数
        param.put("startTime",yesterday+" 00:00:00");
        param.put("endTime",yesterday+" 23:59:59");
        itemDataVo.getFinishCountList().add(this.ownerStatisticsMapper.queryFinishCountByDate(param));
        itemDataVo.getFinishAmountList().add(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        itemDataVo.getAbnormalCountList().add(this.ownerStatisticsMapper.queryAbnormalCountByDate(param));
        //前天完结趟数
        param.put("startTime",beforeYesterday+" 00:00:00");
        param.put("endTime",beforeYesterday+" 23:59:59");
        itemDataVo.getFinishCountList().add(this.ownerStatisticsMapper.queryFinishCountByDate(param));
        itemDataVo.getFinishAmountList().add(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        itemDataVo.getAbnormalCountList().add(this.ownerStatisticsMapper.queryAbnormalCountByDate(param));
        //本周完结趟数
        param.put("startTime",monday+" 00:00:00");
        param.put("endTime",sunday+" 23:59:59");
        itemDataVo.getFinishCountList().add(this.ownerStatisticsMapper.queryFinishCountByDate(param));
        itemDataVo.getFinishAmountList().add(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        itemDataVo.getAbnormalCountList().add(this.ownerStatisticsMapper.queryAbnormalCountByDate(param));
        //本月完结趟数
        param.put("startTime",firstDayMonth+"-01 00:00:00");
        param.put("endTime", DateUtils.getCurrentTime());
        itemDataVo.getFinishCountList().add(this.ownerStatisticsMapper.queryFinishCountByDate(param));
        itemDataVo.getFinishAmountList().add(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        itemDataVo.getAbnormalCountList().add(this.ownerStatisticsMapper.queryAbnormalCountByDate(param));
        ownerStatisticsVo.getDateDataList().add(itemDataVo);

        //时间统计折线图数据展示
        //上上个月参数
        LineItemDataVo lineItemDataVo = new LineItemDataVo();
        param.put("startTime", DateUtils.getFirstDateByMonth(-2)+"-01"+" 00:00:00");
        param.put("endTime", DateUtils.getMonthDays(-2)+" 23:59:59");
        lineItemDataVo.setMachineNum(DateUtils.getMachineCount(this.ownerStatisticsMapper.queryMonthMachineCount(param)));
        lineItemDataVo.setTotalCount(checkInteger(this.ownerStatisticsMapper.queryMonthRouteCount(param)));
        lineItemDataVo.setTotalAmount(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        lineItemDataVo.setMonthStr(DateUtils.getMonthStr(-2));
        ownerStatisticsVo.getLineDataList().add(lineItemDataVo);
        //上月
        lineItemDataVo = new LineItemDataVo();
        param.put("startTime", DateUtils.getFirstDateByMonth(-1)+"-01"+" 00:00:00");
        param.put("endTime", DateUtils.getMonthDays(-1)+" 23:59:59");
        lineItemDataVo.setMachineNum(DateUtils.getMachineCount(this.ownerStatisticsMapper.queryMonthMachineCount(param)));
        lineItemDataVo.setTotalCount(checkInteger(this.ownerStatisticsMapper.queryMonthRouteCount(param)));
        lineItemDataVo.setTotalAmount(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        lineItemDataVo.setMonthStr(DateUtils.getMonthStr(-1));
        ownerStatisticsVo.getLineDataList().add(lineItemDataVo);
        //本月
        lineItemDataVo = new LineItemDataVo();
        param.put("startTime", DateUtils.getFirstDateByMonth(0)+"-01 00:00:00");
        param.put("endTime", DateUtils.getCurrentTime());
        lineItemDataVo.setMachineNum(DateUtils.getMachineCount(this.ownerStatisticsMapper.queryMonthMachineCount(param)));
        lineItemDataVo.setTotalCount(checkInteger(this.ownerStatisticsMapper.queryMonthRouteCount(param)));
        lineItemDataVo.setTotalAmount(checkBigDecimal(this.ownerStatisticsMapper.queryFinishAmountByDate(param)));
        lineItemDataVo.setMonthStr(DateUtils.getMonthStr(0));
        ownerStatisticsVo.getLineDataList().add(lineItemDataVo);
        //给折线图三个最大值
        ownerStatisticsVo.setMaxMachineNum(DataTypeEmptyUtils.doubleMax3(
                ownerStatisticsVo.getLineDataList().get(0).getMachineNum(),ownerStatisticsVo.getLineDataList().get(1).getMachineNum(),ownerStatisticsVo.getLineDataList().get(2).getMachineNum()));
        ownerStatisticsVo.setMaxTotalCount(DataTypeEmptyUtils.integerMax3(
                ownerStatisticsVo.getLineDataList().get(0).getTotalCount(),ownerStatisticsVo.getLineDataList().get(1).getTotalCount(),ownerStatisticsVo.getLineDataList().get(2).getTotalCount()));
        ownerStatisticsVo.setMaxTotalAmount(DataTypeEmptyUtils.bigDecimalMax3(
                ownerStatisticsVo.getLineDataList().get(0).getTotalAmount(),ownerStatisticsVo.getLineDataList().get(1).getTotalAmount(),ownerStatisticsVo.getLineDataList().get(2).getTotalAmount()));
        return  ownerStatisticsVo;
    }

    //按机械统计数据
    public MachineDataVo getMachineDataList(Long userId, String startDate, String endDate, String monthDate){
        MachineDataVo machineDataVo = new MachineDataVo();
        //if(sameDateTime != null && !StringUtils.isEmpty(sameDateTime)){
            //查询当天(默认)时间的数据(yyyy-MM-dd)
            //1.查询完结趟数
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        /*if(sameDateTime == null){
            param.put("sameDateTime",DateUtils.format(new Date()));
        }else{
            param.put("sameDateTime",sameDateTime);
        }*/
        //默认按当天查询
        if((startDate==null || startDate.equals("")) && (endDate==null || endDate.equals("")) && (monthDate == null || monthDate.equals(""))){
            param.put("startDate", DateUtils.format(new Date()));
            param.put("endDate", DateUtils.format(new Date()));
        }else if((startDate !=null && !(startDate.equals(""))) && (endDate !=null && !(endDate.equals(""))) && (monthDate == null || monthDate.equals(""))){
            param.put("startDate",startDate);
            param.put("endDate",endDate);
        }else if((startDate==null || startDate.equals("")) && (endDate==null || endDate.equals("")) && (monthDate != null && !(monthDate.equals("")))){

            String first = getFirstDayOfMonth1(Integer.parseInt(monthDate.split("-")[0]), Integer.parseInt(monthDate.split("-")[1]));
            String last = getLastDayOfMonth1(Integer.parseInt(monthDate.split("-")[0]), Integer.parseInt(monthDate.split("-")[1]));
            param.put("startDate",first);
            param.put("endDate",last);
        }
        //查询总趟数和总金额
        machineDataVo = this.ownerStatisticsMapper.queryDayFinishTotalCount(param);
        if(monthDate == null || monthDate.equals("")) {
            if (param.get("startDate") != null && !(String.valueOf(param.get("startDate")).equals(""))) {
                machineDataVo.setStartDate(startDate);
            }
            if (param.get("endDate") != null && !(String.valueOf(param.get("endDate")).equals(""))) {
                machineDataVo.setEndDate(endDate);
            }
        }else {
            machineDataVo.setMonthDate(monthDate);
        }
        List<MachineDataListVo> listVos = this.ownerStatisticsMapper.queryMachineDayData(param);
        if(listVos != null && listVos.size() >0){
            listVos.stream().forEach(dto -> {
                /*integer.setTotalAmount(integer.getTotalAmount().divide(new BigDecimal(100)));
                param.put("machineCardNo",integer.getMachineCardNo());
                integer.setAbnormalCount(this.ownerStatisticsMapper.queryMachineDayAbnormalCount(param));*/
                dto.setTotalAmount(dto.getTotalAmount().setScale(2,BigDecimal.ROUND_HALF_UP));
            });
            machineDataVo.setMachineDataList(listVos);
        }
        return machineDataVo;
    }

    //金额除以100
    public static BigDecimal checkBigDecimal(BigDecimal val){
        BigDecimal result = new BigDecimal(0.00);
        if(val == null || val.equals("")){
            return result;
        }else{
            result =  val.divide(new BigDecimal(100));
        }
        return result;
    }

    //金额除以100
    public static Integer checkInteger(Integer val){
        Integer result = new Integer(0);
        if(val == null || val.equals("")){
            return result;
        }else{
            result =  val;
        }
        return result;
    }

    public static String getFirstDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

}
