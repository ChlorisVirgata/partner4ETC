package com.allinpay.entity;

import com.allinpay.core.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuchao
 * @since 2019-06-28
 */
@Data
@Accessors(chain = true)
@TableName("T_ETC_PARTNER_STATUS")
public class TEtcPartnerStatus   {

    private static final long serialVersionUID = 1L;

    @TableField("DICT_ID")
    private Double dictId;

    @TableField("STATUS")
    private String status;


}
