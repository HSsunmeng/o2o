package com.o2o.dto;
/**
 * 成功或错误的函数
 * */
public class Result<T> {
    //是否成功
        private boolean success;
        //成功时返回的数据类型
        private T data;
        //返回的错误信息
    private String errorMsg;
    private Integer errorCode;

    public Result() {
    }
    //成功的构造函数

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    //错误的构造器

    public Result(boolean success, String errorMsg, Integer errorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
