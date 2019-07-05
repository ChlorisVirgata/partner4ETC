package com.allinpay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

/**
 * 用户发卡信息查询参数实体类
 *
 * @author xuming
 * @date 2019-07-02
 */
public class UserhairpinVo extends OrgQueryVo {

    /**
     * 用户标识
     */
    private String authId;

    /**
     * 用户名称
     */
    private String authName;

    /**
     * 流水号
     */
    private String orderNo;

    /**
     * 签约结果
     */
    private String signsStatus;
}
