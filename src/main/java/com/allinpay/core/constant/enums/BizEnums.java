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
    EXCEPTION("50000", "系统异常"),

    /**
     * 文件上传异常
     */
    FILE_UPLOAD_EXCEPTION("40000", "文件上传失败"),
    FILE_COPY_EXCEPTION("40001", "文件拷贝失败"),
    FILE_DELETE_EXCEPTION("40002", "文件删除失败"),
    FILE_OPERATE_EXCEPTION("40003", "文件操作失败"),

    CAPTCHA_GET_EXCEPTION("60000", "获取验证码失败"),
    CAPTCHA_INVALIDATE("60001", "验证码有误"),

    USER_AUTHENTICATION_FAIL("70001", "用户名或密码不正确"),
    /**
     * 机构管理模块业务异常
     */
    ORG_MODIFY_AUDITED("10001", "机构状态有误，机构已在审核中"),
    ORG_MODIFY_FAIL("10002", "机构信息编辑失败"),
    ORG_SENDAUDIT_FAIL("10003", "机构信息提交审核失败"),
    ORG_NOT_EXIST("10004", "待审核机构不存在"),
    ORG_AUDIT_FAIL("10005", "机构信息审核失败");


    private String code;
    private String msg;
}
