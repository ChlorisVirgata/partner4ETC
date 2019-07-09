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
     * 机构名称
     */
    private String partnerName;

    /**
     * 车牌号
     */
    private String license;

    /**
     * 流水号
     */
    private String orderNo;

    /**
     * 创建时间
     */
    private String tradedate;

    /**
     * 金额
     */
    private String amount;

    /**
     * 入口
     */
    private String entrance;

    /**
     * 出口
     */
    private String exitway;


}
