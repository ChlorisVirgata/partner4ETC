package com.allinpay.service;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
public interface ITEtcSysRoleService extends IService<TEtcSysRole> {
    ResponseBean queryPage(Integer pageNo, Integer pageSize, String roleName);
}
