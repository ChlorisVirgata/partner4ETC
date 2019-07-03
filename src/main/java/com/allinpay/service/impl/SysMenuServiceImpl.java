package com.allinpay.service.impl;

import com.allinpay.entity.SysMenuEntity;
import com.allinpay.entity.SysRoleMenu;
import com.allinpay.mapper.SysMenuMapper;
import com.allinpay.service.ISysMenuService;
import com.allinpay.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {
//    @Autowired
//    private SysUserService sysUserService;
    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(Integer parentId, List<Integer> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(Integer parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Integer userId) {
//        //系统管理员，拥有最高权限
//        if (userId == Constant.SUPER_ADMIN) {
//            return getAllMenuList(null);
//        }
//
//        //用户菜单列表
//        List<Integer> menuIdList = sysUserService.queryAllMenuId(userId);
//        return getAllMenuList(menuIdList);
        return null;
    }

    @Override
    public void delete(Integer menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", menuId));
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<Integer> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Integer> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

        for (SysMenuEntity entity : menuList) {
            //目录
//            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
//                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
//            }
//            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
