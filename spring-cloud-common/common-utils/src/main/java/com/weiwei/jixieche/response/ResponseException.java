package com.weiwei.jixieche.response;

/**
 * @author houji
 * @create 2017-09-06 15:27
 * @desc 返回异常统一处理
 **/
public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code = 200;

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, int code) {
        super(message);
        this.code = code;
    }
    public ResponseException(int code, String message) {
        super(message);
        this.code = code;
    }
    public static ResponseException create(int code, String message){
        return new ResponseException(code, message);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
