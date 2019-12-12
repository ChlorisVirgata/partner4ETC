package com.allinpay.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 合作银行激活obu信息查询返回信息实体类
 *
 * @author xuming
 * @date 2019-12-11
 */
@Setter
@Getter
@ToString
public class ActivationBack extends PartnerAudit {

    /**
     * 合作机构编号
     */
    private String partnerId;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 车牌颜色
     */
    private String licenseColor;

    /**
     * 用户标识
     */
    private String authId;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户名称
     */
    private String authName;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * obu序列号
     */
    private String obuId;

    /**
     * 激活时间
     */
    private String openTime;
    /**
     * cpu序列号
     */
    private String cpuId;
    /**
     * cpu激活时间
     */
    private String cpuOpenTime;

    /**
     * 是否激活
     */
    private String openIs;

    /**
     * 邮寄方式
     */
    private String deliveryMethod;


    /**
     * 合作机构编号用户标识
     */
    private String userPartnerId;

}
