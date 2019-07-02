package com.allinpay.entity;

import com.allinpay.core.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wuchao
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("T_ETC_PARTNER_INFO")
public class TEtcPartnerInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 机构编号
     */
    @TableId(value = "PARTNER_ID" , type = IdType.AUTO)
    private String partnerId;

    /**
     * 机构名称
     */
    @TableField("PARTNER_NAME")
    private String partnerName;

    /**
     * 机构类型
     */
    @TableField("PARTNER_TYPE")
    private String partnerType;

    /**
     * 营业执照编号
     */
    @TableField("BUSINESS_LICENCE_NO")
    private String businessLicenceNo;

    /**
     * 等级
     */
    @TableField("RANK")
    private Double rank;

    /**
     * 推广人
     */
    @TableField("SALER")
    private String saler;

    /**
     * 法人姓名
     */
    @TableField("LEGAL_NAME")
    private String legalName;

    /**
     * 法人身份证
     */
    @TableField("LEGAL_ID")
    private String legalId;

    /**
     * 法人联系方式
     */
    @TableField("LEGAL_PHONE")
    private String legalPhone;

    /**
     * 机构联系人
     */
    @TableField("CONTACTOR")
    private String contactor;

    /**
     * 联系人电话
     */
    @TableField("CONTACT_PHONE")
    private String contactPhone;

    /**
     * 父级机构
     */
    @TableField("PARENT_ID")
    private String parentId;

    /**
     * 状态
     */
    @TableField("STATUS")
    private Double status;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("MODIFY_TIME")
    private LocalDateTime modifyTime;

    /**
     * 最后操作人
     */
    @TableField("SYS_USER")
    private String sysUser;


}
