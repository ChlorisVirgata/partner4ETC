package com.allinpay.service.impl;

import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.constant.Constant;
import com.allinpay.core.util.ShiroUtils;
import com.allinpay.entity.MenuInfo;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.entity.TEtcSysRoleMenu;
import com.allinpay.mapper.TEtcMenuMapper;
import com.allinpay.service.ITEtcSysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@Service
public class TEtcSysMenuServiceImpl extends ServiceImpl<TEtcMenuMapper, TEtcSysMenu> implements ITEtcSysMenuService {

    @Autowired
    private TEtcMenuMapper sysMenuMapper;

    @Override
    public ResponseBean queryPage(Integer pageNo, Integer pageSize, String name,Integer type) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        List<TEtcSysMenu> roleList = (List) sysMenuMapper.selectList(new QueryWrapper<TEtcSysMenu>().like("name",name).like("type",type).orderByDesc("menu_id"));
        PageInfo<TEtcSysMenu> pageInfo = new PageInfo<TEtcSysMenu>(roleList);
        return ResponseBean.ok(roleList, pageInfo.getTotal());
    }

    @Override
    public List<MenuInfo> getUserMenuList(Integer userId) {
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }
        List<Integer> menuIdList = sysMenuMapper.queryAllMenuId(ShiroUtils.getRoleId());
        return getAllMenuList(menuIdList);
    }

    private List<MenuInfo> getAllMenuList(List<Integer> menuIdList) {
        List<MenuInfo> menuList = queryListParentId(0, menuIdList);
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    private List<MenuInfo> getMenuTreeList(List<MenuInfo> menuList, List<Integer> menuIdList) {
        List<MenuInfo> subMenuList = new ArrayList<MenuInfo>();
        for (MenuInfo entity : menuList) {
            if (entity.getType() == 0) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    public List<MenuInfo> queryListParentId(Integer parentId, List<Integer> menuIdList) {
        List<MenuInfo> menuList = queryListByParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }
        List<MenuInfo> userMenuList = new ArrayList<>();
        for (MenuInfo menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    public List<MenuInfo> queryListByParentId(Integer parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public Boolean saveOrUpdateData(Integer roleId, List<Integer> menuIdList) {
        Boolean boo = true;
        for (Integer menuId : menuIdList) {
            Integer roleMenuId = BaseController.map.get("roleMenuId") + 1;
            BaseController.map.put("roleMenuId", roleMenuId);
            boo = boo & sysMenuMapper.saveRoleMenu(roleMenuId, roleId, menuId);
        }
        return boo;
    }

    @Override
    public Boolean removeMenuById(Integer roleId) {
        return sysMenuMapper.removeMenuById(roleId);
    }

    @Override
    public List<TEtcSysMenu> menuList() {
        List<TEtcSysMenu> sysMenus = sysMenuMapper.queryNotButtonList();
        return sysMenus;
    }

    @Override
    public Boolean updateRole(TEtcSysRole etcSysRole) {
        Integer roleId = etcSysRole.getRoleId();
        Boolean boo = sysMenuMapper.removeMenuById(roleId);
        for (Integer menuId : etcSysRole.getMenuIdList()) {
            Integer roleMenuId = BaseController.map.get("roleMenuId") + 1;
            BaseController.map.put("roleMenuId", roleMenuId);
            boo = boo & sysMenuMapper.saveRoleMenu(roleMenuId, roleId, menuId);
        }
        return boo;
    }

    @Override
    public List<Integer> checkMenuIdByRole(Integer roleId) {
        List<Integer> roleIds = sysMenuMapper.queryMenuByRoleId(roleId);
        return roleIds;
    }
}
