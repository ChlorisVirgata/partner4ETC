package com.allinpay.service.impl;

import com.allinpay.entity.SysUserEntity;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SysUserEntity entity = new SysUserEntity();
        entity.setUserId(11);
        entity.setUsername("test");
        entity.setPassword("A1234567");
        entity.setSalt("ASWFER1234567");
        List<Integer> roleIdList = new ArrayList<>();
        //1： 表示查询角色
//        roleIdList.add(1);
//        //2： 表示录入角色
//        roleIdList.add(1);
//        roleIdList.add(2);
//        //3： 表示审核角色
        roleIdList.add(1);
        roleIdList.add(2);
        roleIdList.add(3);
        roleIdList.add(4);
        roleIdList.add(5);
        roleIdList.add(6);
        roleIdList.add(7);
        roleIdList.add(8);
        roleIdList.add(9);
        roleIdList.add(10);
        roleIdList.add(11);
//        roleIdList.add(12);
        entity.setRoleIdList(roleIdList);
        //通过用户名查询用户信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                entity, //用户名
                entity.getPassword(), //密码
                ByteSource.Util.bytes(entity.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
