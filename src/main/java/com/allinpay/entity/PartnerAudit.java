package com.allinpay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Setter
@Getter
@ToString
public class PartnerAudit {
    /**
     * 机构编号
     */
    private String partnerId;
    /**
     * 机构名称
     */
    private String partnerName;
    /**
     * 机构类型
     */
    private String partnerType;
    /**
     * 营业执照
     */
    private String businessLicenceNo;
    /**
     * 机构层级
     */
    private Integer rank;
    /**
     * 推广人
     */
    private String saler;
    /**
     * 法人姓名
     */
    private String legalName;
    /**
     * 法人证件号
     */
    private String legalId;
    /**
     * 法人身份证号
     */
    private String legalPhone;
    /**
     * 机构联系人
     */
    private String contactor;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 父机构编号
     */
    private String parentId;
    /**
     * 审核状态 4审核中、5审核通过、6审核未通过
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date modifyTime;
    /**
     * 操作人
     */
    private String sysUser;
    /**
     * 审核失败原因
     */
    private String failReason;

    /**
     * 机构地址
     */
    private String partnerAddress;

    /**
     * 营业执照地址
     */
    private String license;

    /**
     * 身份证正面地址
     */
    private String idFront;

    /**
     * 身份证反面地址
     */
    private String idBack;

    /**
     * 协议图片
     */
    private String agreement;
}
