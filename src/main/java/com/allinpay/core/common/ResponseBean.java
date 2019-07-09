package com.allinpay.core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : wuchao
 * Date   : 2019/7/3
 * Desc   :
 */
public class ResponseBean extends HashMap<String, Object> {
    private static final Long serialVersionUID = 1L;

    public ResponseBean() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseBean error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ResponseBean error(String msg) {
        return error(500, msg);
    }

    public static ResponseBean error(int code, String msg) {
        ResponseBean r = new ResponseBean();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResponseBean ok(String msg) {
        ResponseBean r = new ResponseBean();
        r.put("msg", msg);
        return r;
    }

    public static ResponseBean ok(Map<String, Object> map) {
        ResponseBean r = new ResponseBean();
        r.putAll(map);
        return r;
    }

    public static ResponseBean ok() {
        return new ResponseBean();
    }

    @Override
    public ResponseBean put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
