package com.allinpay.service;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerStorage;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface IOrgModifyService {
    /**
     * @Description: 查询可做信息变更的机构列表
     * @Param: query
     * @Return: PageVO<PartnerAudit>
     */
    PageVO<PartnerAudit> selectByCondition(OrgModifyQuery query);

    /**
     * @Description: 编辑机构信息，未提交审核
     * @Param: request, storage
     * @Return: void
     */
    void editOrg(MultipartHttpServletRequest request, PartnerStorage storage);

    /**
     * @Description: 信息编辑完提交审核
     * @Param: request, audit
     * @Return: void
     */
    void sendOrgAudit(MultipartHttpServletRequest request, PartnerAudit audit);
}
