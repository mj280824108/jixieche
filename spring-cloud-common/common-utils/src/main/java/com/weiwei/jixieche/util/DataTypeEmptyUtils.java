package com.weiwei.jixieche.util;

import java.math.BigDecimal;

/**
 * @ClassName DataTypeEmptyUtils
 * @Description 判断各种数据类型是否为空
 * @Author houji
 * @Date 2019/6/13 17:21
 * @Version 1.0.1
 **/
public class DataTypeEmptyUtils {

    //判断BigDecimal是否为空,金额不为空则除以100
    public static BigDecimal emptyBigDecimalMoney(BigDecimal bigDecimal){
        BigDecimal result = new BigDecimal(0.00);
        if(bigDecimal == null){
            return  result;
        }else{
            result = bigDecimal.divide(new BigDecimal(100));
            return result;
        }
    }

    //判断Integer是否为空,金额不为空则除以100
    public static Integer emptyInteger(Integer integer){
        Integer result = 0;
        if(integer == null){
            return result;
        }else{
            result = integer;
            return result;
        }
    }

    public static Integer emptyMoneyInteger(Integer integer){
        Integer result = 0;
        if(integer == null){
            return result;
        }else{
            result = integer/100;
            return result;
        }
    }

    public static Double doubleMax3(Double a, Double b, Double c) {
        if(a.compareTo(b) > 0 && a.compareTo(c) > 0){
            return a;
        }else if(b.compareTo(a) > 0 && b.compareTo(c) > 0){
            return b;
        }else {
            return c;
        }
    }

    public static Integer integerMax3(Integer a, Integer b, Integer c) {
        if(a.compareTo(b) > 0 && a.compareTo(c) > 0){
            return a;
        }else if(b.compareTo(a) > 0 && b.compareTo(c) > 0){
            return b;
        }else {
            return c;
        }
    }

    public static BigDecimal bigDecimalMax3(BigDecimal a, BigDecimal b, BigDecimal c) {
        if(a.compareTo(b) > 0 && a.compareTo(c) > 0){
            return a;
        }else if(b.compareTo(a) > 0 && b.compareTo(c) > 0){
            return b;
        }else {
            return c;
        }
    }
}
