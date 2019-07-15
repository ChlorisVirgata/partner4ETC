package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.util.DateUtils;
import com.allinpay.core.util.ShiroUtils;
import com.allinpay.entity.TEtcSysUser;
import com.allinpay.service.ITEtcSysUserService;
import com.allinpay.service.ITEtcUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuchao
 * @since 2019-07-03
 */
@RestController
@RequestMapping("/sys/user")
public class TEtcUserController extends BaseController {

    @Autowired
    private ITEtcSysUserService etcSysUserService;

    @Autowired
    private ITEtcUserRoleService etcUserRoleService;

    @RequestMapping("/list")
    public ResponseBean list(Integer pageNo, Integer pageSize, String username) {
        HashMap map = new HashMap<>();
        if (!StringUtils.isBlank(username)) {
            map.put("username", username);
        }
        ResponseBean data = etcSysUserService.queryPage(pageNo, pageSize, map);
        return data;
    }


    @RequestMapping("/add")
    @ResponseBody
    @Transactional
    public String add(TEtcSysUser etcSysUser, String opreate) {
        if (opreate != null && opreate.equals("edit")) {
            TEtcSysUser user = etcSysUserService.getOne(new QueryWrapper<TEtcSysUser>().eq("USER_ID", etcSysUser.getUserId()));
            user.setStatus(etcSysUser.getStatus().equals("on") ? "1" : "0");
            user.setRoleName(etcSysUser.getRoleId() == 0 ? "超级管理员" : etcSysUser.getRoleId() == 1 ? "普通管理员" : "");
            user.setPassword(ShiroUtils.sha256(etcSysUser.getPassword(), user.getSalt()));
            String dateStr = DateUtils.getNowTime();
            user.setUpdateTime(dateStr);
            return ResponseBean.resultStr(etcSysUserService.updateById(user));
        }
        TEtcSysUser user = etcSysUserService.getOne(new QueryWrapper<TEtcSysUser>().eq("username", etcSysUser.getUsername()));
        if (user != null) {
            return "用户名重复";
        }
        etcSysUser.setStatus(etcSysUser.getStatus().equals("on") ? "1" : "0");
        String dateStr = DateUtils.getNowTime();
        etcSysUser.setCreateTime(dateStr);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        etcSysUser.setSalt(salt);
        etcSysUser.setRoleName(etcSysUser.getRoleId() == 0 ? "超级管理员" : etcSysUser.getRoleId() == 1 ? "普通管理员" : "");
        etcSysUser.setPassword(ShiroUtils.sha256(etcSysUser.getPassword(), etcSysUser.getSalt()));
        return ResponseBean.resultStr(etcSysUserService.save(etcSysUser));
    }


    @RequestMapping("/del")
    public ResponseBean del(Integer id) {
        return ResponseBean.result(etcSysUserService.removeById(id));
    }

    @GetMapping("/queryUserById")
    public ResponseBean queryUserById(Integer userId, String opreate) {
        TEtcSysUser user = etcSysUserService.getById(userId);
        return ResponseBean.ok(user);
    }


    @GetMapping("/queryUserByUserName")
    public ResponseBean queryUserByUserName(String username) {
        TEtcSysUser user = etcSysUserService.getOne(new QueryWrapper<TEtcSysUser>().eq("username", username));
        return ResponseBean.ok(user);
    }
}
