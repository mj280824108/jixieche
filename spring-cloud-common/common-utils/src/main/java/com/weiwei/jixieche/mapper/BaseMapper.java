package com.weiwei.jixieche.mapper;

import java.util.List;

public interface BaseMapper<T> {

    int insertSelective(T t);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T t);

    List<T> selectListByConditions(T t);

}