package com.allinpay.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码的枚举类
 */
@Getter
@AllArgsConstructor
public enum BizEnums {
    /**
     * 参数缺失异常
     */
    MISSING_PARAM("50001", "参数缺失"),

    /**
     * 参数不合法异常
     */
    ILLEGAL_ARGUMENT("50002", "参数不合法"),

    /**
     * 异常Exception
     */
    EXCEPTION("50000", "系统异常");

    /**
     * 机构管理模块业务异常
     */

    private String code;
    private String msg;
}
