package com.allinpay.service;

import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.TEtcSysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
public interface ITEtcSysUserService extends IService<TEtcSysUser> {


    ResponseData queryPage(Integer pageNo, Integer pageSize, HashMap map);
}
