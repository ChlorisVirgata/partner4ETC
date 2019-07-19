package com.allinpay.entity;

import lombok.Data;

@Data
public class PartnerDeposit {
    /**
     * 主键
     */
    private String kid;
    /**
     * 机构编号
     */
    private String partnerId;
    /**
     * 签约保证金
     */
    private Integer deposit;
    /**
     * 最低保证金, 少于此值将被拉入黑名单
     */
    private Integer minDeposit;
}
