package com.allinpay.controller;

import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerStorage;
import com.allinpay.service.IOrgEnterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @description: 机构信息录入
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/org/enter")
@Slf4j
public class OrgEnterController {
    @Autowired
    private IOrgEnterService enterService;

    /**
     * @Description: 新增机构记录提交审核
     * @Param: [request, audit]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/sendAudit")
    public ResponseData sendAudit(MultipartHttpServletRequest request, PartnerAudit audit) {
        enterService.sendOrgAudit(request, audit);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 新增机构记录仅保存，未提交审核
     * @Param: [request, audit]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/add")
    public ResponseData add(MultipartHttpServletRequest request, PartnerStorage storage) {
        enterService.addOrg(request, storage);
        return ResponseData.success().setData(null);
    }
}
