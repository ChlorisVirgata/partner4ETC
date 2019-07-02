package com.allinpay.core.common;

import com.allinpay.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author : wuchao
 * Date   : 2019/6/28
 * Desc   :
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Integer getUserId() {
        return getUser().getUserId();
    }

    protected String getUserName() {
        SysUserEntity userEntity = getUser();
        String operatorName = userEntity.getUsername();
        return operatorName;
    }

    protected Integer getDeptId() {
        return getUser().getDeptId();
    }
}