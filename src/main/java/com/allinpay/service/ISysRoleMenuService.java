package com.allinpay.service;

import com.allinpay.entity.TEtcSysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author wuchao
 * @since 2019-04-16
 */
public interface ISysRoleMenuService extends IService<TEtcSysRoleMenu> {
    void saveOrUpdate(Integer roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Integer roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Integer[] roleIds);
}
