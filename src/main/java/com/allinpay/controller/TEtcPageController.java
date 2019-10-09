package com.allinpay.controller;

import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.service.ITEtcSysMenuService;
import com.allinpay.service.ITEtcSysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统页面视图
 *
 * @author 吴超
 */
@Controller
@RequestMapping("/manage")
public class TEtcPageController {

    @Autowired
    private ITEtcSysRoleService sysRoleService;

    @Autowired
    private ITEtcSysMenuService sysMenuService;


    @RequestMapping(value = {"/", "login.html"})
    public String login() {
        return "login";
    }
//
//    @RequestMapping(value = {"/user/index", "/index"})
//    public String index() {
//        return "common/index";
//    }

    @RequestMapping(value = "/main.html")
    public String main() {
        return "common/main";
    }

    @RequestMapping("/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return module + "/" + url;
    }

    @RequestMapping("/userManage")
    public String userManage() {
        return "backstage/userindex";
    }

    @GetMapping("/addUser")
    @RequiresPermissions("user:addpage")
    public ModelAndView userAdd() {
        List<TEtcSysRole> tEtcSysRoles = sysRoleService.list(new QueryWrapper<TEtcSysRole>().eq("status", 1).orderByDesc("ROLE_ID"));
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addUser");
        modelAndView.addObject("roles", tEtcSysRoles);
        return modelAndView;
    }

    @GetMapping("/editUser")
    @RequiresPermissions("user:editpage")
    public ModelAndView userUpdate(String userId, String roleIds, String opreate, String status) {
        List<TEtcSysRole> tEtcSysRoles = sysRoleService.list();
        ModelAndView modelAndView = new ModelAndView("backstage/operation/editUser");
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("roleIds", roleIds);
        modelAndView.addObject("opreate", opreate);
        modelAndView.addObject("status", status);
        modelAndView.addObject("roles", tEtcSysRoles);
        return modelAndView;
    }


    @RequestMapping("/roleManage")
    public String roleManage() {
        return "backstage/roleManage";
    }

    @GetMapping("/addRole")
    @RequiresPermissions("role:addpage")
    public ModelAndView roleAdd() {
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addRole");
        return modelAndView;
    }


    @GetMapping("/editRole")
    @RequiresPermissions("role:editpage")
    public ModelAndView editRole(Integer roleId) {
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addRole");
        TEtcSysRole role = sysRoleService.getById(roleId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("roleName", role.getRoleName());
        modelAndView.addObject("roleId", role.getRoleId());
        modelAndView.addObject("status", role.getStatus());
        modelAndView.addObject("opreate", "edit");
        return modelAndView;
    }


    @RequestMapping("/menuManage")
    public String menuManage() {
        return "backstage/menuManage";
    }

    @GetMapping("/addMenu")
    public ModelAndView menuAdd() {
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addMenu");
        List<TEtcSysMenu> menus = sysMenuService.menuList();
        modelAndView.addObject("menus", menus);
        modelAndView.addObject("menu", new TEtcSysMenu());
        return modelAndView;

    }

    @GetMapping("/editMenu")
    public ModelAndView menuEdit(Integer menuId) {
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addMenu");
        List<TEtcSysMenu> menus = sysMenuService.menuList();
        TEtcSysMenu menu = sysMenuService.getById(menuId);
        modelAndView.addObject("menus", menus);
        modelAndView.addObject("menu", menu);
        modelAndView.addObject("operate", "edit");
        return modelAndView;

    }


}
