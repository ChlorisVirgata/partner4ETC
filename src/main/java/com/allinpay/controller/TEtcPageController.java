package com.allinpay.controller;

import com.allinpay.service.ITEtcSysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统页面视图
 *
 * @author 吴超
 */
@Controller
public class TEtcPageController {

    @Autowired
    private ITEtcSysRoleService sysRoleService;


    @RequestMapping(value = {"/", "login.html"})
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/index"})
    public String index() {
        return "common/index";
    }

    @RequestMapping("/userManage")
    public String userManage() {
        return "backstage/userManage";
    }

    @GetMapping("/use/add")
    @RequiresPermissions("system:user:add")
    public ModelAndView toAdd(String userId,String opreate) {
//        List<TEtcSysRole> tEtcSysRoles = sysRoleService.list();
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addUser");
        modelAndView.addObject("userId",userId);
        modelAndView.addObject("opreate",opreate);
        return modelAndView;
    }
    @RequestMapping("/roleManage")
    public String roleManage() {
        return "backstage/roleManage";
    }

    @GetMapping("/role/add")
    public ModelAndView roleAdd(Integer roleId,String data) {
        ModelAndView modelAndView = new ModelAndView("backstage/operation/addRole");
        modelAndView.addObject("roleId",roleId);
        modelAndView.addObject("data",data);
        return modelAndView;
    }

    @GetMapping("/use/selectrole")
    public String selectrole() {
        return "backstage/selectrole";
    }


    @RequestMapping("/menuManage")
    public String menuManage() {
        return "backstage/menuManage";
    }
    @GetMapping("/menu/add")
    //@RequiresPermissions("system:user:add")
    public String menuAdd() {
        return "backstage/operation/addMenu";
    }



    @GetMapping("/role/edit")
    public String editRole(){
        return "backstage/operation/addRole";
    }




}
