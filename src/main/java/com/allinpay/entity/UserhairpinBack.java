package com.allinpay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户发卡信息查询返回信息实体类
 *
 * @author xuming
 * @date 2019-07-02
 */
@Setter
@Getter
@ToString
public class UserhairpinBack {

    /**
     * 机构编号
     */
    private String partnerId;

    /**
     * 接口版本号
     */
    private String versionPort;

    /**
     * 流水号
     */
    private String orderNo;

    /**
     * 用户标识
     */
    private String authId;

    /**
     * 用户名称
     */
    private String authName;

    /**
     * 签约结果
     */
    private String signStatus;

    /**
     * 随机字符串
     */
    private String randomsStr;

    /**
     * 签名
     */
    private String signName;

    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String signdate;
}
