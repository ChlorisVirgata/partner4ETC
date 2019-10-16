package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.exception.AllinpayException;
import com.allinpay.core.util.ShiroUtils;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.entity.TEtcSysUser;
import com.allinpay.service.ITEtcSysRoleService;
import com.allinpay.service.ITEtcSysUserService;
import com.allinpay.service.ITEtcUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/manage/user")
public class TEtcUserController extends BaseController {

    @Autowired
    private ITEtcSysUserService etcSysUserService;

    @Autowired
    private ITEtcSysRoleService sysRoleService;

    @Autowired
    private ITEtcUserRoleService etcUserRoleService;

    @RequestMapping("/list")
    public ResponseBean list(Integer pageNo, Integer pageSize, String username) {
        ResponseBean data = etcSysUserService.queryPage(pageNo, pageSize, username);
        return data;
    }


    @RequestMapping("/add")
    @ResponseBody
    @Transactional
    public ResponseBean add(TEtcSysUser etcSysUser, String opreate, Integer[] roleId) {
        return etcSysUserService.addUser(etcSysUser, opreate);
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

    @RequestMapping("/info")
    public HashMap info() {
        return (HashMap) ResponseBean.okMap().put("user", getUser());
    }

    @GetMapping("/queryUserByUserName")
    public ResponseBean queryUserByUserName(String username) {
        TEtcSysUser user = etcSysUserService.getOne(new QueryWrapper<TEtcSysUser>().eq("username", username));
        return ResponseBean.ok(user);
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        ShiroUtils.logout();
        ModelAndView view = new ModelAndView("login");
        return view;
    }

    @RequestMapping("/password")
    public ResponseBean password(String password, String newPassword) {
        TEtcSysUser user = etcSysUserService.getById(getUserId());
        if (StringUtils.isBlank(newPassword)) {
            throw new AllinpayException("新密码不为能空");
        }
        password = ShiroUtils.sha256(password, user.getSalt());
        newPassword = ShiroUtils.sha256(newPassword, user.getSalt());
        boolean flag = etcSysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return ResponseBean.error("原密码不正确");
        }
        return ResponseBean.okData();
    }

    @GetMapping("/queryRoleById")
    public ResponseBean queryRoleById(Integer roleId) {
        TEtcSysRole role = sysRoleService.getById(roleId);
        return ResponseBean.ok(role);
    }


}
