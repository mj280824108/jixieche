package com.weiwei.jixieche.generate;

import com.alibaba.fastjson.JSONObject;

import java.util.Random;

/**
 * @ClassName ConfirmCodeUtil
 * @Description TODO
 * @Author houji
 * @Date 2019/8/27 11:10
 * @Version 1.0.1
 **/
public class ConfirmCodeUtil {

    //生成n位验证码
    public static String getNumberCode(int bit) {

        //int numCode = 123456;
        int numCode = new Random().nextInt((int)Math.pow(10,bit));
        return String.format("%0" + bit + "d", numCode);
    }



    public static void main(String args[]){

        System.out.println(getNumberCode(6));

    }
}
