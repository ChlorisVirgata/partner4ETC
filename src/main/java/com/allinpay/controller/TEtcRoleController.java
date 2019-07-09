package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.common.ResponseData;
import com.allinpay.core.util.DateUtils;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.entity.TEtcSysUser;
import com.allinpay.service.ITEtcSysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/role")
public class TEtcRoleController extends BaseController {

    @Autowired
    private ITEtcSysRoleService itEtcSysRoleService;

    @RequestMapping("/list")
    public ResponseData roleList(Integer pageNo, Integer pageSize, String rolename) {
        HashMap map = new HashMap<>();
        if (!StringUtils.isBlank(rolename)) {
            map.put("role_name", rolename);
        }
        ResponseData data = itEtcSysRoleService.queryPage(pageNo, pageSize, map);
        return data;
    }

    @RequestMapping("/allList")
    public ResponseData allList() {
        List<TEtcSysRole> tEtcSysRoleList = itEtcSysRoleService.list();
        return ResponseData.ok(tEtcSysRoleList);
    }


    @RequestMapping("/add")
    @ResponseBody
    public String add(TEtcSysRole tEtcSysRole) {
        tEtcSysRole.setCreateTime(new Date());
        return ResponseData.resultStr(itEtcSysRoleService.save(tEtcSysRole));
    }

    @RequestMapping("/del")
    public ResponseData del(Integer id){
        return ResponseData.result(itEtcSysRoleService.removeById(id));
    }

}
