package com.allinpay.service;


import com.allinpay.entity.SysMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单管理
 *
 * @author 吴超
 */
public interface ISysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Integer parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuEntity> getUserMenuList(Integer userId);

    /**
     * 删除
     */
    void delete(Integer menuId);
}
