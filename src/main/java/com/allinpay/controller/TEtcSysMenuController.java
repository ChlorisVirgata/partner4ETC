package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.util.DateUtils;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.service.ITEtcSysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
    public ResponseBean roleList(Integer pageNo, Integer pageSize, String name) {
        HashMap map = new HashMap<>();
        if (!StringUtils.isBlank(name)) {
            map.put("name", name);
        }
        ResponseBean data = etcSysMenuService.queryPage(pageNo, pageSize, map);
        return data;
    }

    @RequestMapping("/all")
    public List<TEtcSysMenu> roleallList() {
        List<TEtcSysMenu> list = etcSysMenuService.list();
        return list;
    }

    @RequestMapping("/addmenu")
    @ResponseBody
    public String add(TEtcSysMenu sysMenu,String operate) {
        if(operate.equals("edit")){
            String dateStr = getString(sysMenu);
            sysMenu.setUpdateTime(dateStr);
            return ResponseBean.resultStr(etcSysMenuService.updateById(sysMenu));
        }
        String dateStr = getString(sysMenu);
        sysMenu.setCreateTime(dateStr);
        return ResponseBean.resultStr(etcSysMenuService.save(sysMenu));
    }

    private String getString(TEtcSysMenu sysMenu) {
        String dateStr = DateUtils.getNowTime();
        String[] content = sysMenu.getType().split("\\|");
        sysMenu.setType(content[0]);
        sysMenu.setTypeName(content[1]);
        return dateStr;
    }

    @RequestMapping("/del")
    public ResponseBean del(Integer id) {
        return ResponseBean.result(etcSysMenuService.removeById(id));
    }

    @RequestMapping("/queryRoleMenuIds")
    public List<Integer> queryRoleMenuIds(Integer roleId) {
        List<Integer> menuIds = etcSysMenuService.checkMenuIdByRole(roleId);
        return menuIds;
    }

}
