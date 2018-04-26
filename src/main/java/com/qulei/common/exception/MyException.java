package com.qulei.common.exception;


import com.qulei.bean.app.ResultCode;
import com.qulei.common.enums.ExceptionEnum;

public class MyException extends RuntimeException {

    //错误码
    private int code = ResultCode.NORMAL_ERROR;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MyException(int code,String message){
        super(message);
        this.code = code;
    }

    public MyException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
    }

}
