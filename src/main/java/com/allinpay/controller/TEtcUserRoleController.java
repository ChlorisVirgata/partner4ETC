package com.allinpay.controller;


import com.allinpay.core.common.BaseController;
import com.allinpay.service.ITEtcSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wuchao
 * @since 2019-07-09
 */
@Component
public class TEtcUserRoleController extends BaseController implements CommandLineRunner {

    @Autowired
    private ITEtcSysUserService etcSysUserService;

    @Override
    public void run(String... args) {
        Integer maxId = etcSysUserService.selectMaxId();
        Integer maxRoleId = etcSysUserService.selectMaxRoleId();
        map.put("roleId", maxId);
        map.put("roleMenuId", maxRoleId);
    }

    @RequestMapping("/manage/index")
    public String index() {
        return "common/index";
    }
}
