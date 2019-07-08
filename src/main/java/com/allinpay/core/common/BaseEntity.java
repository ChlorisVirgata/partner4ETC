package com.allinpay.core.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author : wuchao
 * Date   : 2019/6/28
 * Desc   :
 */
@Setter
@Getter
@ToString
public class BaseEntity {
    private int pageNum = 1;
    private int pageSize = 10;
}
