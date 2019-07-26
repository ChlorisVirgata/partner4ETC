package com.allinpay.service.impl;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.util.DateUtils;
import com.allinpay.core.util.ShiroUtils;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.entity.TEtcSysUser;
import com.allinpay.mapper.TEtcRoleMapper;
import com.allinpay.mapper.TEtcUserMapper;
import com.allinpay.service.ITEtcSysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
@Service
public class TEtcSysUserServiceImpl extends ServiceImpl<TEtcUserMapper, TEtcSysUser> implements ITEtcSysUserService {

    @Autowired
    private TEtcUserMapper tEtcUserMapper;
    @Autowired
    private TEtcRoleMapper tEtcRoleMapper;

    @Override
    public ResponseBean queryPage(Integer pageNo, Integer pageSize, String username) {
        Page page = PageHelper.startPage(pageNo, pageSize);
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(username)) {
            map.put("username", username);
        }
        List<TEtcSysUser> sysRoleList = (List) tEtcUserMapper.selectByMap(map);
        PageInfo<TEtcSysUser> pageInfo = new PageInfo<TEtcSysUser>(sysRoleList);
        return ResponseBean.ok(sysRoleList, pageInfo.getTotal());
    }

    public String addUser(TEtcSysUser etcSysUser, String opreate) {
        if (opreate != null && opreate.equals("edit")) {
            TEtcSysUser user = tEtcUserMapper.selectById(etcSysUser.getUserId());
            String dateStr = getString(etcSysUser, user);
            user.setUsername(etcSysUser.getUsername());
            user.setUpdateTime(dateStr);
            return ResponseBean.resultStr(tEtcUserMapper.updateById(user) > 0);
        }
        TEtcSysUser user = tEtcUserMapper.selectOne(new QueryWrapper<TEtcSysUser>().eq("username", etcSysUser.getUsername()));
        if (user != null) {
            return "用户名重复";
        }
        String salt = RandomStringUtils.randomAlphanumeric(20);
        etcSysUser.setSalt(salt);
        String dateStr = getString(etcSysUser, etcSysUser);
        etcSysUser.setCreateTime(dateStr);
        etcSysUser.setPassword(ShiroUtils.sha256(etcSysUser.getPassword(), etcSysUser.getSalt()));
        return ResponseBean.resultStr(tEtcUserMapper.insert(etcSysUser) > 0);
    }

    @Override
    public Integer selectMaxId() {
        return tEtcUserMapper.selectMaxId();
    }

    @Override
    public Integer selectMaxRoleId() {
        return tEtcUserMapper.selectMaxRoleId();
    }

    private String getString(TEtcSysUser etcSysUser, TEtcSysUser user) {
        String dateStr = getString(user, etcSysUser.getStatus());
        if (etcSysUser.getRoleId() != null) {
            TEtcSysRole tEtcSysRole = tEtcRoleMapper.selectById(etcSysUser.getRoleId());
            user.setRoleName(tEtcSysRole.getRoleName());
            user.setRoleId(etcSysUser.getRoleId());
        }
        return dateStr;
    }

    private String getString(TEtcSysUser etcSysUser, String status) {
        etcSysUser.setStatus(StringUtils.isEmpty(status) ? "0" : "1");
        return DateUtils.getNowTime();
    }
}
