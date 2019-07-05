package com.allinpay.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 机构信息查询返回信息实体类
 *
 * @author xuming
 * @date 2019-07-02
 */
@Setter
@Getter
@ToString
public class OrgQueryBack extends PartnerAudit{

    /**
     * 创建时间
     */
    private String createTimeX;

    /**
     * 更新时间
     */
    private String modifyTimeX;

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
