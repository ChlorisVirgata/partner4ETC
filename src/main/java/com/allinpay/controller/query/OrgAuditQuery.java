package com.allinpay.controller.query;

import com.allinpay.core.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 封装机构审核查询条件
 * @author: tanguang
 * @date: 2019-07-03
 */
@Setter
@Getter
@ToString
public class OrgAuditQuery extends BaseEntity {
    private String partnerName;
    private String saler;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyEndTime;
}
