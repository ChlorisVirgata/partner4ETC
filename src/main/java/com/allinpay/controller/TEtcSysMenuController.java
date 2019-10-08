package com.allinpay.controller;


import com.alibaba.fastjson.JSONObject;
import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.util.DateUtils;
import com.allinpay.entity.MenuInfo;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.service.ITEtcSysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@RestController
@RequestMapping("/manage/menu")
public class TEtcSysMenuController extends BaseController {

    @Autowired
    private ITEtcSysMenuService etcSysMenuService;

    @RequestMapping("/list")
    @RequiresPermissions("menu:list")
    public ResponseBean roleList(Integer pageNo, Integer pageSize, String name, Integer type) {
        ResponseBean data = etcSysMenuService.queryPage(pageNo, pageSize, name,type);
        return data;
    }

    @RequestMapping("/getPmenuList")
    public List<TEtcSysMenu> menuList() {
        List<TEtcSysMenu> menus = etcSysMenuService.menuList();
        return menus;
    }

    @RequestMapping("/all")
    public List<TEtcSysMenu> roleallList() {
        List<TEtcSysMenu> list = etcSysMenuService.list();
        return list;
    }


    @RequestMapping("/addmenu")
    @ResponseBody
    @RequiresPermissions("menu:add")
    public ResponseBean add(TEtcSysMenu sysMenu, String operate) {
        if (operate != null && operate.equals("edit")) {
            String dateStr = getString(sysMenu);
            sysMenu.setUpdateTime(dateStr);
            return ResponseBean.result(etcSysMenuService.updateById(sysMenu));
        }
        String dateStr = getString(sysMenu);
        sysMenu.setCreateTime(dateStr);
        return ResponseBean.result(etcSysMenuService.save(sysMenu));
    }

    private String getString(TEtcSysMenu sysMenu) {
        String dateStr = DateUtils.getNowTime();
        String[] content = sysMenu.getType().split("\\|");
        sysMenu.setType(content[0]);
        sysMenu.setTypeName(content[1]);
        return dateStr;
    }

    @RequestMapping("/del")
    @RequiresPermissions("menu:del")
    public ResponseBean del(Integer id) {
        return ResponseBean.result(etcSysMenuService.removeById(id));
    }

    @RequestMapping("/queryRoleMenuIds")
    public List<Integer> queryRoleMenuIds(Integer roleId) {
        List<Integer> menuIds = etcSysMenuService.checkMenuIdByRole(roleId);
        return menuIds;
    }

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    @RequiresPermissions("menu:nav")
    public HashMap nav() {
        List<MenuInfo> menuList = etcSysMenuService.getUserMenuList(getUserId());
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("menuList", menuList);
        return map;
    }

}
