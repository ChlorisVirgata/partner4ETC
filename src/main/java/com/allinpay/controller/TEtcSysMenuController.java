package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.core.common.ResponseData;
import com.allinpay.core.util.DateUtils;
import com.allinpay.entity.TEtcSysMenu;
import com.allinpay.entity.TEtcSysRole;
import com.allinpay.service.ITEtcSysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuchao
 * @since 2019-07-05
 */
@RestController
@RequestMapping("/menu")
public class TEtcSysMenuController extends BaseController {

    @Autowired
    private ITEtcSysMenuService etcSysMenuService;

    @RequestMapping("/list")
    public ResponseData roleList(Integer pageNo, Integer pageSize, String name) {
        HashMap map = new HashMap<>();
        if (!StringUtils.isBlank(name)) {
            map.put("name", name);
        }
        ResponseData data = etcSysMenuService.queryPage(pageNo, pageSize, map);
        return data;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(TEtcSysMenu sysMenu) {
        String dateStr = DateUtils.getNowTime();
        sysMenu.setCreateTime(dateStr);
        return ResponseData.resultStr(etcSysMenuService.save(sysMenu));
    }

    @RequestMapping("/del")
    public ResponseData del(Integer id){
        return ResponseData.result(etcSysMenuService.removeById(id));
    }

}
