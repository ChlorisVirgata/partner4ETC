package com.allinpay.controller.query;

import com.allinpay.core.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@ToString
/**
 * @description: 机构信息变更查询条件
 * @author: tanguang
 * @date: 2019-07-03
 */
public class OrgModifyQuery extends BaseEntity {
    private String partnerName;
    private String partnerId;
    private String partnerType;
    private String parentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createEndTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyStartTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modifyEndTime;
}
