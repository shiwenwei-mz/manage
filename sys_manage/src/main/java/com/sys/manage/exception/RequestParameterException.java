package com.sys.manage.exception;


import com.sys.manage.utils.response.DetailErrorResponse;

import java.util.List;

/**
 * @program:
 * @description: 自定义异常： 请求参数有误
 * @author: ShiWenWei
 * @create: 2018-05-05
 **/
public class RequestParameterException extends RuntimeException {

    private int code;
    private String msg;
    private List<DetailErrorResponse> errors;

    public RequestParameterException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public RequestParameterException(int code, List<DetailErrorResponse> errors) {
        super(errors.toString());
        this.code = code;
        this.errors = errors;
    }
    public RequestParameterException(int code, String msg, List<DetailErrorResponse> errors) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.errors = errors;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DetailErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<DetailErrorResponse> errors) {
        this.errors = errors;
    }
}
