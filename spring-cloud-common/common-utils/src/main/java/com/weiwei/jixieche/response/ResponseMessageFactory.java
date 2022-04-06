package com.weiwei.jixieche.response;

/**
 * Created by houji on 2017/9/11.
 */
public class ResponseMessageFactory {
    private static ResponseMessage responseMessage;
    private ResponseMessageFactory(){}
    public static ResponseMessage newInstance(){
        if(responseMessage==null){
            return new ResponseMessage();
        }
        return responseMessage;
    }
}
