package com.allinpay.mapper;

import com.allinpay.entity.TEtcSysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 系统用户
 *
 * @author 吴超
 */
@Repository
@Mapper
public interface TEtcUserMapper extends BaseMapper<TEtcSysUser> {

    /**
     * 查询用户的所有权限
     */
    Set<String> selectAllPermsByRoleId(Integer roleId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(Integer userId);

    List<TEtcSysUser> selectByUserName(String username);

    Integer selectMaxId();

    Integer selectMaxRoleId();

    Set<String> selectAllPerms();

    TEtcSysUser saveUserInfo(TEtcSysUser etcSysUser);
}
