package com.allinpay.core.common;


import java.util.HashMap;

/**
 * 返回数据
 *
 * @author 吴超
 */
public class ResponseBean<T>  {
    private static final Long serialVersionUID = 1L;
    private Integer code;
    private String msg;
    private Long count;
    private T data;


    public static ResponseBean result(Boolean result) {
        return result ? success() : failure();
    }

    public static String resultStr(Boolean result) {
        return result ? "操作成功" : "操作失败";
    }

    public static HashMap ok() {
        return new HashMap<String, Object>();
    }

    public static HashMap okMap() {
        HashMap map =  new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "success");
        return map;
    }

    public static ResponseBean okData() {
        return new ResponseBean(0, "操作成功");
    }
    public ResponseBean setData(T data) {
        this.data = data;
        return this;
    }

    public static ResponseBean ok(Object data) {
        return new ResponseBean(0, "操作成功", data);
    }

    public static ResponseBean ok(Object data, Long count) {
        return new ResponseBean(0, "操作成功", data, count);
    }

    private ResponseBean(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseBean(Integer code, String msg, T data, Long count) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    private ResponseBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseBean success() {
        return new ResponseBean(0, "操作成功");
    }
    public static ResponseBean error(String msg) {
        return new ResponseBean(1, msg);
    }

    public static ResponseBean success(String message) {
        return new ResponseBean(0, message);
    }

    public static ResponseBean failure() {
        return new ResponseBean(1, "操作失败");
    }

    public static ResponseBean failure(String message) {
        return new ResponseBean(1, message);
    }

    public static ResponseBean failure(Integer code, String message) {
        return new ResponseBean(code, message);
    }

    public static ResponseBean failure(String code, String message) {
        return new ResponseBean(Integer.valueOf(code), message);
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


    public ResponseBean(Integer code, String msg, Long count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
