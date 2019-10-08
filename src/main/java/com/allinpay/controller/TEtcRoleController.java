package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.service.ITEtcSysMenuService;
import com.allinpay.service.ITEtcSysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
@RequestMapping("/manage/role")
public class TEtcRoleController extends BaseController {

    @Autowired
    private ITEtcSysRoleService itEtcSysRoleService;

    @Autowired
    private ITEtcSysMenuService itEtcSysMenuService;

    @RequestMapping("/list")
    @RequiresPermissions("role:list")
    public ResponseBean roleList(Integer pageNo, Integer pageSize, String rolename) {
        HashMap map = new HashMap<>();
        ResponseBean data = itEtcSysRoleService.queryPage(pageNo, pageSize, rolename);
        return data;
    }

    @RequestMapping("/allList")
    public List<TEtcSysRole> allList() {
        List<TEtcSysRole> tEtcSysRoleList = itEtcSysRoleService.list();
        return tEtcSysRoleList;
    }


    @RequestMapping(value = "/operate", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("role:operate")
    public ResponseBean add(TEtcSysRole etcSysRole, String opreate) {
        if (opreate.equals("edit")) {
            etcSysRole.setUpdateTime(new Date());
            Boolean val = itEtcSysMenuService.updateRole(etcSysRole);
            return ResponseBean.result(val && itEtcSysRoleService.updateById(etcSysRole));
        }
        Integer roleId = map.get("roleId") + 1;
        map.put("roleId", roleId);
        etcSysRole.setRoleId(roleId);
        etcSysRole.setCreateTime(new Date());
        Boolean val = itEtcSysMenuService.saveOrUpdateData(roleId, etcSysRole.getMenuIdList());
        return ResponseBean.result(val && itEtcSysRoleService.save(etcSysRole));
    }

    @RequestMapping("/del")
    @RequiresPermissions("role:del")
    public ResponseBean del(Integer id) {
        itEtcSysMenuService.removeMenuById(id);
        return ResponseBean.result(itEtcSysRoleService.removeById(id));
    }


}
