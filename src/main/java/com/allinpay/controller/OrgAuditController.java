package com.allinpay.controller;

import com.allinpay.controller.query.OrgAuditQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.service.IOrgAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 机构信息审核
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/org/audit")
public class OrgAuditController {
    @Autowired
    private IOrgAuditService auditService;

    /**
     * @Description: 查询待审核列表
     * @Param: [name]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @GetMapping("/getList")
    public ResponseData getList(OrgAuditQuery query) {
        PageVO<PartnerAudit> pageVO = auditService.selectByCondition(query);
        return ResponseData.success().setData(pageVO);
    }

    @GetMapping("/getOne")
    public ResponseData getOne(@RequestParam String partnerId) {
        PartnerAudit partnerAudit = auditService.selectByPartnerId(partnerId);
        return ResponseData.success().setData(partnerAudit);
    }

    @PostMapping("/approve")
    public ResponseData approve(PartnerAudit audit) {
        auditService.auditApprove(audit);
        return ResponseData.success().setData(null);
    }

    @PostMapping("/refuse")
    public ResponseData refuse(@RequestParam String partnerId,
                               @RequestParam String failReason) {
        auditService.auditRefuse(partnerId, failReason);
        return ResponseData.success().setData(null);
    }
}
