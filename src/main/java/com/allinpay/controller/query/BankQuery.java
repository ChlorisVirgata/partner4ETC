package com.allinpay.controller.query;

import com.allinpay.core.common.BaseEntity;
import lombok.Data;

@Data
public class BankQuery extends BaseEntity {
    private String bankId;
    private String partnerId;
    private String bankName;
    private Integer status;
}
