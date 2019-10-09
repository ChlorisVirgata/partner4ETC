package com.allinpay.mapper;

import com.allinpay.entity.MenuInfo;
import com.allinpay.entity.TEtcSysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@Repository
public interface TEtcMenuMapper extends BaseMapper<TEtcSysMenu> {
    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<MenuInfo> queryListParentId(Integer parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<TEtcSysMenu> queryNotButtonList();


    Boolean saveRoleMenu(Integer roleMenuId, Integer roleId, Integer menuId);

    Boolean removeMenuById(Integer roleId);

    List<Integer> queryAllMenuId(Integer roleId);


    List<Integer> queryMenuByRoleId(Integer roleId);


}
