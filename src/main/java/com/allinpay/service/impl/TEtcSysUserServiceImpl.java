package com.allinpay.service.impl;

import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.TEtcSysUser;
import com.allinpay.mapper.TEtcUserMapper;
import com.allinpay.service.ITEtcSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
@Service
public class TEtcSysUserServiceImpl extends ServiceImpl<TEtcUserMapper, TEtcSysUser> implements ITEtcSysUserService {

    @Autowired
    private TEtcUserMapper TEtcUserMapper;

    @Override
    public ResponseData queryPage(Integer pageNo, Integer pageSize,HashMap map) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        List<TEtcSysUser> sysRoleList = (List) TEtcUserMapper.selectByMap(map);
        PageInfo<TEtcSysUser> pageInfo = new PageInfo<TEtcSysUser>(sysRoleList);
        return  ResponseData.ok(sysRoleList,pageInfo.getTotal());
    }
}
