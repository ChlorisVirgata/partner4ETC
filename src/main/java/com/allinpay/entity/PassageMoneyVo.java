package com.allinpay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
/**
 * 通行费记录查询参数实体类
 *
 * @author xuming
 * @date 2019-07-02
 */
public class PassageMoneyVo extends OrgQueryVo {

    /**
     * 车牌号
     */
    private String license;
}
