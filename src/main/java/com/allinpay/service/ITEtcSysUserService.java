package com.allinpay.service;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
public interface ITEtcSysUserService extends IService<TEtcSysUser> {


    ResponseBean queryPage(Integer pageNo, Integer pageSize, String username);

    String addUser(TEtcSysUser etcSysUser, String opreate);
    Integer selectMaxId();
    Integer selectMaxRoleId();
}
