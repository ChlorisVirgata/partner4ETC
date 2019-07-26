package com.allinpay.entity;

import lombok.Data;

@Data
public class PartnerBank {
    /**
     * 机构编号
     */
    private String partnerId;
    /**
     * 银行编号
     */
    private String bankId;
    /**
     * 卡类型 1 借记卡 2 贷记卡
     */
    private String cardType;
    /**
     * 状态 0 禁用 1 有效
     */
    private String status;
    private String insertTime;
    private String modifyTime;
}
