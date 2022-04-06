package com.weiwei.jixieche.util;


import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;

/**
 * 日期处理
 *
 */
public class DateUtils {
    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 时间格式(yyyy-MM-dd HH:mm:ss:SSS) */
    public final static String DATE_TIME_PATTERN_SECOND = "yyyy-MM-dd HH:mm:ss:SSS";


    /**
     * 是否信用雇主
     * @return
     */
    public static boolean isCreDit(Date arg) {
        Predicate<Date> predicate = (basic) -> {
            if (basic == null) {
                return false;
            }

            LocalDateTime now = LocalDateTime.now().withNano(0);
            LocalDateTime startTime = basic.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = startTime.plusHours(40);

            //now,在开始至结束时间范围内
            return startTime.isBefore(now) && endTime.isAfter(now);
        };
        return predicate.test(arg);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 字符串日期反格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static Date parseYMD(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String formatSecond(Date date) {
        return format(date, DATE_TIME_PATTERN_SECOND);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd HH:mm:ss
     * @param date  日期
     * @return  返回yyyy-MM-dd HH:mm:ss格式日期
     */
    public static String formatHMS(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    /**
     * 获取日期的0点
     *
     * @param date 日期
     */
    public static Date getNowZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期后一天的0点
     *
     * @param date 日期
     */
    public static Date getNextZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定年月最后一天的日期
     * @param dateStr
     * @return
     */
    public static String getLastDayOfMonth(String dateStr){
        dateStr.trim();
        String[] split = dateStr.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }


    /** 
     * 根据当前日期获得所在周的日期区间（周一和周日日期） 
     */
    public static Map<String,String> getTimeInterval(Date date) {
        Map<String,String> dateMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        dateMap.put("monday",imptimeBegin);
        System.out.println("所在周星期一的日期："+ imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        dateMap.put("sunday",imptimeEnd);
        System.out.println("所在周星期日的日期："+imptimeEnd);
        return dateMap;
    }

    /** 
     * 获取昨天，前天的时间
     */
    public static Map<String,String> getTimeYesterday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,String> dateMap = new HashMap<>(2);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        dateMap.put("yesterday",sdf.format(calendar.getTime()));
        calendar.add(Calendar.DATE, -1);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
        dateMap.put("beforeYesterday",sdf.format(calendar.getTime()));
        return dateMap;
    }

    public static Date getYesterdayDate(Date date) {
        //取上一周的开始时间 以及结束时间
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取当前月份
     * @return
     */
    public static String getFirstDateByMonth(Integer sum){
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MONTH, sum);
        Date dateFrom = cl.getTime();
        return sdf.format(dateFrom);
    }

    /**
     * 获取当前月份
     * @return
     */
    public static String getMonthStr(Integer sum){
        String result = null;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int resultMonth = month+1+sum;
        switch (resultMonth) {
            case 1: result = "一月";break;case 2: result = "二月";break;case 3: result = "三月";break;case 4: result = "四月";break;
            case 5: result = "五月";break;case 6: result = "六月";break;case 7: result = "七月";break;case 8: result = "八月";break;
            case 9: result = "九月";break;case 10: result = "十月";break;case 11: result = "十一月";break;case 12: result = "十二月";break;
        }
        return result;
    }

    /**
     * 根据年查询月份的天数
     */
    public static String getMonthDays(int sum){
        StringBuffer yearMonthDays = new StringBuffer();
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM");
        String yearMonth = getFirstDateByMonth(sum);
        Calendar rightNow = Calendar.getInstance();
        try{
            rightNow.setTime(sdf.parse(yearMonth));
        }catch(ParseException e){
            e.printStackTrace();
        }
        Integer days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);//根据年月 获取月份天数
        yearMonthDays.append(sdf.format(rightNow.getTime())).append("-").append(days);
        return yearMonthDays.toString();
    }

    /**
     * 获取当前时间,格式(yyyy-MM-dd  HH:mm:ss)
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static Double getMachineCount(Integer num){
        BigDecimal b = new BigDecimal((float)num/30);
        //四舍五入保留2位
        Double result = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {
            //同一年
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++) {
                if(i%4==0 && i%100!=0 || i%400==0) {
                    //闰年
                    timeDistance += 366;
                }
                else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        } else {
            //不同年
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    //计算时间差，以小时为单位。如：2018-08-08 和 2018-08-07 相差24h
    public static double getDistanceTime2(Date startTime, Date endTime) {
        double hour = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        hour = (diff / (60 * 60 * 1000));
        return hour;
    }

//    public static void main(String[] args){
//        String startDate1= "2019-09-01";
//        String endDate1 = "2019-09-08";
//
//        List<Map<String, String>> week = getWeek(startDate1, endDate1);
//        for (Map<String, String> map : week){
//            System.out.println(map.toString());
//        }
//
//    }

    /**
     * 获取指定两个日期之间的周集合
     * @param startDate1
     * @param endDate1
     * @return
     */
    public static List<Map<String,String>> getWeek(String startDate1, String endDate1){
        int o1 = (int) countTwoDayWeek(startDate1, endDate1);
        String startDate3 = startDate1;
        String endDate3 = endDate1;
        List<Map<String,String>> list = new ArrayList<>();
        if (o1 > 1) {
            for (int i = 1; i <= o1; i++) {
                Map<String,String> map = new HashMap<>();
                if (i == o1) {
                    startDate1 = startDate1.split(" ")[0];
                    endDate1 = endDate3.split(" ")[0];
                    if (startDate1.compareTo(endDate1) < 0) {
                        map.put("startDate",startDate1);
                        map.put("endDate",endDate1);
                        list.add(map);
                    } else {
                        map.put("startDate",endDate3);
                        map.put("endDate",endDate3);
                        list.add(map);
                    }

                } else {
                    endDate1 = getSpecifiedDayAfter(startDate1, 6);
                    map.put("startDate",startDate1);
                    map.put("endDate",endDate1);
                    list.add(map);
                }
                startDate1 = getSpecifiedDayAfter(endDate1, 1);
            }
        } else {
            Map<String,String> map = new HashMap<>();
            map.put("startDate",startDate1);
            map.put("endDate",endDate1);
            list.add(map);
        }
        return list;
    }

    /**
     * 对比两个
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public static boolean diff(Date time1,Date time2)
    {
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(time1.before(time2)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 获取昨天的日期
     * @param date
     * @return
     */
    public static String getYesterday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //得到前一天
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String format = df.format(date);
        return format;
    }

    public static Map<String, Object> getWeek(String startDate1, String endDate1, String startDate2) {

        int o1 = (int) countTwoDayWeek(startDate1, endDate1);
        String startDate3 = startDate1;
        String endDate3 = endDate1;
        List<String> list = new ArrayList<>();
        if (o1 > 1) {
            for (int i = 1; i <= o1; i++) {

                if (i == o1) {
                    startDate1 = startDate1.split(" ")[0];
                    endDate1 = endDate3.split(" ")[0];
                    if (startDate1.compareTo(endDate1) < 0) {
                        list.add(startDate1 + "," + endDate1);
                    } else {
                        list.add(endDate3 + "," + endDate3);
                    }

                } else {
                    endDate1 = getSpecifiedDayAfter(startDate1, 6);
                    list.add(startDate1 + "," + endDate1);
                }
                startDate1 = getSpecifiedDayAfter(endDate1, 1);
            }
        } else {
            list.add(startDate1 + "," + endDate1);
        }
        //再查出开始日期到今天的周数

        int o2 = (int) countTwoDayWeek(startDate3, startDate2 + " 23:59:59");
        String s = list.get(o2 - 1);
        String[] split = s.split(",");
        //格式化 开始以及截止的日期
        String[] split1 = split[0].split(" ");
        startDate1 = split1[0] + " 00:00:00";
        endDate1 = (split[1].split(" "))[0] + " 23:59:59";
        Map<String, Object> params2 = new HashMap<>();
        params2.put("startDate", startDate1);
        params2.put("endDate", endDate1);

        return params2;
    }

    public static String getSpecifiedDayAfter(String specifiedDay, int i) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + i);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    public static Object countTwoDayWeek(String startDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        try {
            start = formatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = null;
        try {
            end = formatter.parse(endDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(end);
            cal.add(Calendar.DATE, 1);
            end = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        long time1 = cal.getTimeInMillis();
        cal.setTime(end);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        Double days = Double.parseDouble(String.valueOf(between_days));
        if ((days / 7) > 0 && (days / 7) <= 1) {
            //不满一周的按一周算
            return 1;
        } else if (days / 7 > 1) {
            int day = days.intValue();
            if (day % 7 > 0) {
                return day / 7 + 1;
            } else {
                return day / 7;
            }
        } else if ((days / 7) == 0) {
            return 0;
        } else {
            //负数返还null
            return null;
        }

    }

    /**
     *
     * @param nowTime 可接单开始日期
     * @param beginTime 已接单开始日期
     * @param endTime 已接单结束日期
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param beginTime 将要接单的开始日期
     * @param endTime  将要接单的结束日期
     * @param beginTimeDone 已接单的开始日期
     * @param endTimeDone 已接单的结束日期
     * @return
     */
    public static boolean belongCalendarBru(Date beginTime,Date endTime, Date beginTimeDone, Date endTimeDone) {
        boolean bru = false;
        //当可接单日期的时间等于已接单开始日期或者结束日期，直接返回false
        if(beginTime.compareTo(beginTimeDone) == 0 || beginTime.compareTo(endTimeDone) == 0
                ||  beginTime.compareTo(endTimeDone) == 0 ||  endTime.compareTo(endTimeDone) == 0){
            return bru;
            //当可接单日期小于已接单日期
        }else if(endTime.compareTo(beginTimeDone) == -1){
            bru = true;
        }else if(beginTime.compareTo(endTimeDone) == 1){
            bru = true;
        }
        return bru;
    }

}
