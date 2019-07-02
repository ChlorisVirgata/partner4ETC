package com.allinpay.core.common;



import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author 吴超
 */
public class ResponseData<T> extends HashMap<String, Object> {
    private static final Long serialVersionUID = 1L;
    private T data;
    private String code;
    private String msg;

    public ResponseData() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseData error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ResponseData error(String msg) {
        return error(500, msg);
    }

    public static ResponseData error(int code, String msg) {
        ResponseData responseData = new ResponseData();
        responseData.put("code", code);
        responseData.put("msg", msg);
        return responseData;
    }

    public static ResponseData ok(String msg) {
        ResponseData responseData = new ResponseData();
        responseData.put("msg", msg);
        return responseData;
    }

    public static ResponseData ok(Map<String, Object> map) {
        ResponseData responseData = new ResponseData();
        responseData.putAll(map);
        return responseData;
    }

    public static ResponseData ok() {
        return new ResponseData();
    }

    @Override
    public ResponseData put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public ResponseData setData(T data) {
        this.data = data;
        return this;
    }

    private ResponseData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseData success() {
        return new ResponseData("00000", "");
    }

    public static ResponseData success(String message) {
        return new ResponseData("00000", message);
    }

    public static ResponseData failure(String code, String message) {
        return new ResponseData(code, message);
    }

}
