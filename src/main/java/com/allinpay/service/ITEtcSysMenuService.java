package com.allinpay.service;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.entity.TEtcSysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
public interface ITEtcSysMenuService extends IService<TEtcSysMenu> {
    ResponseBean queryPage(Integer pageNo, Integer pageSize, HashMap map);

    Boolean saveOrUpdateData(Integer roleId, List<Integer> menuIdList);
    Boolean removeMenuById(Integer roleId);

    List<TEtcSysMenu> menuList();
    Boolean updateRole(TEtcSysRole etcSysRole);
    List<Integer> checkMenuIdByRole(Integer roleId);
}
