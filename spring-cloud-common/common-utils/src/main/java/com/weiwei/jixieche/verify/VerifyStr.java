package com.weiwei.jixieche.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName S
 * @Description TODO
 * @Author houji
 * @Date 2019/5/14 9:21
 * @Version 1.0.1
 **/
public class VerifyStr {
    private static DateFormat dateFormat_yyyyMMdd;

    static {
        dateFormat_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat_yyyyMMdd.setLenient (false);
    }


    //是否为空（null或者去除头尾空格后为""）
    public static boolean isEmpty(String str) {
        return (str == null || str.trim ().equals ("")) ? true : false;
    }

    //是否长整形
    public static boolean isLong(String str) {
        if (str == null) {
            return false;
        }
        if (!str.matches ("([+-]?[1-9][0-9]*)|0")) {
            return false;
        }

        BigInteger bi = new BigInteger (str);
        BigInteger minValue = new BigInteger (String.valueOf (Long.MIN_VALUE));
        BigInteger maxValue = new BigInteger (String.valueOf (Long.MAX_VALUE));
        if (bi.compareTo (minValue) < 0 || bi.compareTo (maxValue) > 0) {
            return false;
        }
        return true;
    }

    //是否整形
    public static boolean isInt(String str) {
        if (str == null) {
            return false;
        }
        if (!str.matches ("([+-]?[1-9][0-9]*)|0")) {
            return false;
        }

        BigInteger bi = new BigInteger (str);
        BigInteger minValue = new BigInteger (String.valueOf (Integer.MIN_VALUE));

        BigInteger maxValue = new BigInteger (String.valueOf (Integer.MAX_VALUE));
        if (bi.compareTo (minValue) < 0 || bi.compareTo (maxValue) > 0) {
            return false;
        }
        return true;
    }

    //最多100个字符
    public static boolean isOneHundred(String str){
        if (!str.matches ("[^*]{0,101}$")){
            return false;
        }
        return true;
    }

    //校验只能输入中文
    public static boolean isChinese(String str){

        String pattern = "[\u4e00-\u9fa5]+";

        boolean isMatch =  Pattern.matches(pattern, str);

        return isMatch;
    }



    //拼接
    public static String join(String... strArr) {
        StringBuffer sb = new StringBuffer ();
        for (String s : strArr) {
            sb.append (s);
        }
        return sb.toString ();
    }

    public static boolean isRegionCode(String str) {
        return str != null && str.matches ("[0-9]{2,4}");
    }

    public static boolean isPhone(String str) {
        return str != null && str.matches ("1[3-9][0-9]{9}");
    }

    public static boolean isClock_hourAndMinute(String str) {
        return str != null && str.matches ("((0[0-9]|1[0-9]|2[0-3]):[0-5][0-9])|(24:00)");
    }

    public static boolean isYYYYMMDD(String str) {
        return str != null && str.matches ("((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)");
    }

    public static boolean isYYYYMM(String str) {
        return str != null && str.matches ("(?!0000)[0-9]{4}-(0[1-9]|1[0-2])");
    }

    //验证输入数字金额是否合法 必须是两位小数
    public static boolean isDouble(String s) {

        Pattern pattern = Pattern.compile ("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式

        Matcher match = pattern.matcher (s);
        boolean bo = match.matches ();

        return bo;

    }

    // string 转double 乘以100 再转为Integer
    public static Integer strToInteger(String s) {
        Double d = new Double (s) * 100;
        int i = d.intValue ();
        return i;
    }
    //
    public static Integer strToInteger2(String s) {

        if(s.equals("")){
            s = "0";
        }
        BigDecimal big = new BigDecimal(s);
        if(big.compareTo(BigDecimal.ZERO) == 0){
            return 0;
        }
        BigDecimal divide = big.divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);

        int i = divide.intValue ();
        return i;
    }

    public static String strToStr(String s) {

        if(s.equals("")){
            s = "0";
        }
        BigDecimal big = new BigDecimal(s);
        if(big.compareTo(BigDecimal.ZERO) == 0){
            return "0.00";
        }
        BigDecimal divide = big.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

        String i = divide.toString();
        return i;
    }

    public static String integerToStrHand(Integer s) {
        String res = "0.00";
        if(s ==  null){
            return res;
        }
        res = new BigDecimal(s).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return res;
    }

    public static BigDecimal strToDecimal(String str) {
        BigDecimal bigDecimal = null;
        if(str == null || str.equals(null)){
            bigDecimal = new BigDecimal(0.00).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            bigDecimal = new BigDecimal(str).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        return bigDecimal;
    }

    public static Integer strToIntegerHander(String str) {
        Integer amount = 0;
        if(str == null){
            return amount;
        }else{
            return Integer.parseInt(str);
        }
    }

    public static String IntegerToStr(Integer str) {
        String resStr = "0";
        if(str == null){
            return resStr;
        }else{
            return String.valueOf(str);
        }
    }


    /**
     * 处理bigDecimal类型数据为空的判断
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimal(BigDecimal bigDecimal) {
        if(bigDecimal == null){
            return new BigDecimal(0.00);
        }else{
            return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }
}
