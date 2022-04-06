package com.weiwei.jixieche.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author houji
 * @create 2017-09-06 15:27
 * @desc 统一返回
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
public class ResponseMessage<T> implements Serializable {


    @ApiModelProperty(value = "状态码,200表示成功 其他表示失败", example = "200")
    public int code = 200;

    @ApiModelProperty(value = "错误信息", example = "操作成功")
    public String message = "操作成功";

    @ApiModelProperty(value = "数据", example = "")
    public T data;

    public ResponseMessage() {
    }

    public ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseMessage(T data) {
        this.data = data;
    }

    public ResponseMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
