package com.allinpay.core.common;

import com.allinpay.entity.TEtcSysUser;
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

    protected TEtcSysUser getUser() {
        return (TEtcSysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Integer getUserId() {
        return getUser().getUserId();
    }

    protected String getUserName() {
        TEtcSysUser userEntity = getUser();
        String operatorName = userEntity.getUsername();
        return operatorName;
    }


}
