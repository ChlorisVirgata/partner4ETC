package com.allinpay.service.impl;

import com.allinpay.mapper.TEtcRoleMapper;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.service.ITEtcSysRoleService;
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
 * @since 2019-07-05
 */
@Service
public class TEtcSysRoleServiceImpl extends ServiceImpl<TEtcRoleMapper, TEtcSysRole> implements ITEtcSysRoleService {

    @Autowired
    private TEtcRoleMapper tEtcRoleMapper;

    @Override
    public ResponseData queryPage(Integer pageNo, Integer pageSize, HashMap map) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        List<TEtcSysRole> roleList = (List) tEtcRoleMapper.selectByMap(map);
        PageInfo<TEtcSysRole> pageInfo = new PageInfo<TEtcSysRole>(roleList);
        return  ResponseData.ok(roleList,pageInfo.getTotal());
    }
}
