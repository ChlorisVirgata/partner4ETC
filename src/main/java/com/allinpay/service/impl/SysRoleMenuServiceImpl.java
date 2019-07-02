package com.allinpay.service.impl;

import com.allinpay.entity.SysRoleMenu;
import com.allinpay.mapper.SysRoleMenuMapper;
import com.allinpay.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author wuchao
 * @since 2019-04-16
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Integer roleId, List<Long> menuIdList) {
        //先删除角色与菜单关系
        deleteBatch(new Integer[]{roleId});

        if (menuIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        for (Long menuId : menuIdList) {
            SysRoleMenu SysRoleMenu = new SysRoleMenu();
            SysRoleMenu.setMenuId(menuId);
            SysRoleMenu.setRoleId(roleId);

            this.save(SysRoleMenu);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Integer roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Integer[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
