package com.xy.honest_record.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应体
 * @param <T> 具体数据对象类型
 */

@Data
public class ResponseResult<T> implements Serializable {
    /**响应码*/
    private Integer code;
    /**响应信息*/
    private String message;
    /**具体数据*/
    private T data;

    public ResponseResult() {}

    public ResponseResult(Code resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public ResponseResult(Code resultCode, T data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public static ResponseResult success() {
        ResponseResult result = new ResponseResult(Code.SUCCESS);
        return result;
    }

    public static ResponseResult success(Object data) {
        ResponseResult result = new ResponseResult(Code.SUCCESS,data);
        return result;
    }

    public static ResponseResult failure(Code resultCode) {
        ResponseResult result = new ResponseResult(resultCode);
        return result;
    }

    public static ResponseResult failure(Code resultCode, Object data) {
        ResponseResult result = new ResponseResult(resultCode,data);
        result.setData(data);
        return result;
    }
}
