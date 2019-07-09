package com.allinpay.core.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author 吴超
 */
public class ResponseData<T> {
    private static final Long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private Long count;
    private T data;


    public static ResponseData result(Boolean result) {
        return result ? success() : failure();
    }

    public static String resultStr(Boolean result) {
        return result ? "操作成功" : "操作失败";
    }

    public static HashMap ok() {
        return new HashMap<String, Object>();
    }


    public ResponseData setData(T data) {
        this.data = data;
        return this;
    }

    public static ResponseData ok(Object data) {
        return new ResponseData(0, "操作成功", data);
    }

    public static ResponseData ok(Object data, Long count) {
        return new ResponseData(0, "操作成功", data, count);
    }

    private ResponseData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseData(Integer code, String msg, T data, Long count) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    private ResponseData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseData success() {
        return new ResponseData(0, "");
    }

    public static ResponseData success(String message) {
        return new ResponseData(0, message);
    }

    public static ResponseData failure() {
        return new ResponseData(1, "操作失败");
    }

    public static ResponseData failure(String message) {
        return new ResponseData(1, message);
    }

    public static ResponseData failure(Integer code, String message) {
        return new ResponseData(code, message);
    }

    public static ResponseData failure(String code, String message) {
        return new ResponseData(Integer.valueOf(code), message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
