package com.xy.honest_record.common.vo;

/**
  全局状态码
 */
public enum Code {
    //全局状态码
    SUCCESS(0,"操作成功"),
    FAIL(1000,"操作失败"),
    INVALID_PARAM(2000,"非法参数"),
    UNAUTHENTICATED(3000,"此操作需要登陆系统"),
    UNAUTHORISE(4000,"权限不足，无权操作"),
    SERVER_ERROR(5000,"抱歉，系统繁忙，请稍后重试");

    //操作代码
    int code;
    //提示信息
    String message;
    /**
     * 构造方法
     * @param code
     * @param message
     */
    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 用于获取code和对应的message

    public int code() {
        return code;
    }
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return this.name();
    }

}

