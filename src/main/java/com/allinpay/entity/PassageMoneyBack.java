package com.allinpay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通行费记录查询返回信息实体类
 *
 * @author xuming
 * @date 2019-07-02
 */
@Setter
@Getter
@ToString
public class PassageMoneyBack {

    /**
     * 机构编号
     */
    private String partnerId;

    /**
     * 用户编号
     */
    private String authId;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 用户名称
     */
    private String authName;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 交易结果
     */
    private String status;

    /**
     * 扣款时间
     */
    private String transeTime;

    /**
     * 金额
     */
    private String amount;

    /**
     * 出入口
     */
    private String passageway;

    /**
     * 插入时间
     */
    private String insertTime;

    /**
     * 通行时间
     */
    private String passTime;


}
