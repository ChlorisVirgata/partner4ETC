package com.allinpay.entity;

import lombok.Data;

@Data
public class BankInfo {
    /**
     * 银行编号
     */
    private String bankId;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 状态 0 禁用 1 有效
     */
    private String status;

    private String insertTime;
    private String modifyTime;

}
