package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerDeposit;
import com.allinpay.service.IOrgDepositService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/manage/org/deposit")
@RestController
public class OrgDepositController {
    @Autowired
    private IOrgDepositService depositService;

    @RequestMapping("getList")
    @RequiresPermissions("deposit:getList")
    public ResponseData getList(String partnerId, int pageNum, int pageSize) {
        PageVO<PartnerDeposit> pageVO = depositService.selectByCondition(partnerId, pageNum, pageSize);
        return ResponseData.success().setData(pageVO);
    }

    @RequestMapping("add")
    @RequiresPermissions("deposit:add")
    public ResponseData add(PartnerDeposit deposit) {
        depositService.addOrgDeposit(deposit);
        return ResponseData.success().setData(null);
    }

    @RequestMapping("edit")
    @RequiresPermissions("deposit:edit")
    public ResponseData edit(PartnerDeposit deposit) {
        depositService.editOrgDeposit(deposit);
        return ResponseData.success().setData(null);
    }
}
