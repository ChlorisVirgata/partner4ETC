package com.allinpay.controller;

import com.allinpay.controller.query.OrgAuditQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.service.IOrgAuditService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 机构信息审核
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/manage/org/audit")
public class OrgAuditController {
    @Autowired
    private IOrgAuditService auditService;

    /**
     * @Description: 查询待审核列表
     * @Param: [name]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @GetMapping("/getList")
    @RequiresPermissions("org:audit:list")
    public ResponseData getList(OrgAuditQuery query) {
        PageVO<PartnerAudit> pageVO = auditService.selectByCondition(query);
        return ResponseData.success().setData(pageVO);
    }


    /**
     * @Description: 审核通过
     * @Param: [audit]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/approve")
    @RequiresPermissions("org:audit:approve")
    public ResponseData approve(PartnerAudit audit) {
        auditService.auditApprove(audit);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 审核失败
     * @Param: [partnerId，failReason]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/refuse")
    @RequiresPermissions("org:audit:refuse")
    public ResponseData refuse(@RequestParam String partnerId,
                               @RequestParam String failReason) {
        auditService.auditRefuse(partnerId, failReason);
        return ResponseData.success().setData(null);
    }
}
