package com.allinpay.service.impl;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.mapper.TEtcRoleMapper;
import com.allinpay.service.ITEtcSysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ResponseBean queryPage(Integer pageNo, Integer pageSize, String  roleName) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        List<TEtcSysRole> roleList = (List) tEtcRoleMapper.selectList(new QueryWrapper<TEtcSysRole>().like("role_name",roleName).orderByDesc("ROLE_ID"));
        PageInfo<TEtcSysRole> pageInfo = new PageInfo<TEtcSysRole>(roleList);
        return  ResponseBean.ok(roleList,pageInfo.getTotal());
    }
}
