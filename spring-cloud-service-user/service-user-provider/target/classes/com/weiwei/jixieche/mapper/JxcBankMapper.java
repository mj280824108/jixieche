package com.weiwei.jixieche.mapper;

import com.weiwei.jixieche.bean.JxcBank;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 21:32
 * @Version 2.0
 **/
public interface JxcBankMapper extends BaseMapper<JxcBank> {

    JxcBank selectByPrimaryKey(String id);

}