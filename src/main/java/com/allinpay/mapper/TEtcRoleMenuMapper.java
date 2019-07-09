package com.allinpay.mapper;

import com.allinpay.entity.TEtcSysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author wuchao
 * @since 2019-04-16
 */
@Mapper
public interface TEtcRoleMenuMapper extends BaseMapper<TEtcSysRoleMenu> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Integer roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Integer[] roleIds);
}
