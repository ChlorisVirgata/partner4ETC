package com.allinpay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
@Data
@Accessors(chain = true)
@TableName("T_ETC_SYS_USER")
public class TEtcSysUser{

    private static final long serialVersionUID = 1L;

    @TableId(value = "USER_ID",type = IdType.AUTO)
    private Integer userId;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField("SALT")
    private String salt;


    @TableField("STATUS")
    private String status;

    @TableField("ROLE_ID")
    private Integer roleId;

    @TableField("ROLE_NAME")
    private String roleName;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("LAST_LOGIN_TIME")
    private String lastLoginTime;

    //机构编号
    @TableField("PARTNER_ID")
    private String partnerId;

    //机构名称
    @TableField("PARTNER_NAME")
    private String partnerName;


}
