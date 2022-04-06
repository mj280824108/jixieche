package com.weiwei.jixieche.service;

import com.weiwei.jixieche.response.ResponseMessage;

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author houji
 * @Date 2019/5/14 9:53
 * @Version 1.0.1
 **/
public interface BaseService<T> {

    ResponseMessage<T> addObj(T t);

    ResponseMessage<T> modifyObj(T t);

    ResponseMessage<T> queryObjById(int id);

}
