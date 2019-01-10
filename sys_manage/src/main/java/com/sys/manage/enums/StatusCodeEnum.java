package com.sys.manage.enums;


/**
 * @Title: StatusCodeEnum
 * @Description: 通用枚举
 * @author： sww
 * @date 2019/1/10
 **/
public enum StatusCodeEnum {
    SUCCESS(200, "请求成功接收并处理"),
    CREATED(201, "请求完成，一个或多个资源被创建"),
    ACCEPTED(202, "表示一个请求已经进入后台排队（异步任务）"),
    NO_CONTENT(204, "请求处理成功，但是没有返回数据，可以代表删除数据成功"),
    INVALID_REQUEST(400, "请求有错误（请求语法错误、body 数据格式有误、或者body缺少必须的字段等），导致服务端无法处理"),
    UNAUTHORIZED(401, "请求的资源需要认证，客户端没有提供认证信息或者认证信息不正确（令牌、用户名、密码错误）"),
    FORBIDDEN(403, "用户得到授权（与401错误相对），但是权限不足，访问是被禁止的"),
    NOT_FOUND(404, "请求的资源未找到"),
    GONE(410, "请求的资源被永久删除，且不会再得到"),
    UNSUPPORTED_MEDIA_TYPE(415, "请求的格式不可得"),
    TOO_MANY_REQUEST(429, "请求过多"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误，导致无法完成请求的内容");

    private int index;

    private String message;

    private StatusCodeEnum(int index, String message) {
        this.setIndex(index);
        this.setMessage(message);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
