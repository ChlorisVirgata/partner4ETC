package com.allinpay.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class OrgQueryBack extends PartnerAudit{


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 状态（汉子）
     */
    private String parstatus;

    /**
     * 机构地址
     */
    private String partner_address;
    private String license;
    private String id_front;
    private String id_back;
    private String agreement;

}
