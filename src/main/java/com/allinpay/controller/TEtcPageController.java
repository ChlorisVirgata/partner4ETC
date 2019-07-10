package com.allinpay.controller;

import com.allinpay.service.ITEtcSysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("modules/{module}/{url}.html")
    public String module(@PathVariable("module") String module, @PathVariable("url") String url) {
        return "modules/" + module + "/" + url;
    }

    @RequestMapping(value = {"/", "index.html"})
    public String index() {
        return "index";
    }

    @RequestMapping("index1.html")
    public String index1() {
        return "index1";
    }

    @RequestMapping("login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("main.html")
    public String main() {
        return "main";
    }

    @RequestMapping("404.html")
    public String notFound() {
        return "404";
    }

    @RequestMapping("/web/userManage")
    public String userManage() {
        return "/backstage/userManage";
    }

    @GetMapping("/web/use/add")
    @RequiresPermissions("system:user:add")
    public ModelAndView toAdd(String userId,String opreate) {
//        List<TEtcSysRole> tEtcSysRoles = sysRoleService.list();
        ModelAndView modelAndView = new ModelAndView("/backstage/operation/addUser");
        modelAndView.addObject("userId",userId);
        modelAndView.addObject("opreate",opreate);
        return modelAndView;
    }
    @RequestMapping("/web/roleManage")
    public String roleManage() {
        return "/backstage/roleManage";
    }

    @GetMapping("/web/role/add")
    public ModelAndView roleAdd(Integer roleId,String data) {
        ModelAndView modelAndView = new ModelAndView("/backstage/operation/addRole");
        modelAndView.addObject("roleId",roleId);
        modelAndView.addObject("data",data);
        return modelAndView;
    }

    @GetMapping("/web/use/selectrole")
    public String selectrole() {
        return "/backstage/selectrole";
    }


    @RequestMapping("/web/menuManage")
    public String menuManage() {
        return "/backstage/menuManage";
    }
    @GetMapping("/web/menu/add")
    //@RequiresPermissions("system:user:add")
    public String menuAdd() {
        return "/backstage/operation/addMenu";
    }


    @GetMapping("/test")
    //@RequiresPermissions("system:user:add")
    public String test() {
        return "/backstage/test";
    }


    @GetMapping("/web/role/edit")
    public String editRole(){
          return "backstage/operation/addRoleInfo";
    }
}
