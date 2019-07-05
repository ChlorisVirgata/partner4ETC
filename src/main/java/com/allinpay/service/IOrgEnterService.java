package com.allinpay.service;

import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerStorage;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IOrgEnterService {
    /**
     * @Description: 新增机构信息。未提交审核，用于临时保存
     * @Param: request, storage
     * @Return: void
     */
    void addOrg(MultipartHttpServletRequest request, PartnerStorage storage);

    /**
     * @Description: 新增机构信息。提交审核
     * @Param: request, audit
     * @Return: void
     */
    void sendOrgAudit(MultipartHttpServletRequest request, PartnerAudit audit);
}
