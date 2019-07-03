package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.service.IOrgAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseData getList(PartnerAudit audit) {
        PageVO<PartnerAudit> pageVO = auditService.selectByCondition(audit);
        return ResponseData.success().setData(pageVO);
    }

    @GetMapping("/getOne")
    public ResponseData getOne() {
        return null;
    }

    @GetMapping("/approve")
    public ResponseData approve() {
        return null;
    }

    @GetMapping("/refuse")
    public ResponseData refuse() {
        return null;
    }
}
