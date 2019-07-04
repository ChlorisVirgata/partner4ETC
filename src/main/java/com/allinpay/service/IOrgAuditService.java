package com.allinpay.service;

import com.allinpay.controller.query.OrgAuditQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerAudit;


/**
 * @description: 机构审核接口
 * @author: tanguang
 * @date: 2019-07-02
 */
public interface IOrgAuditService {
    /**
     * @Description: 查询待审核列表
     * @Param: query
     * @Return: PageVO<PartnerAudit>
     */
    PageVO<PartnerAudit> selectByCondition(OrgAuditQuery query);

    /**
     * @Description: 根据机构编号查询待审核记录
     * @Param: partnerId
     * @Return: PartnerAudit
     */
    PartnerAudit selectByPartnerId(String partnerId);

    /**
     * @Description: 审核未通过
     * @Param: partnerId, failReason
     * @Return: void
     */
    void auditRefuse(String partnerId, String failReason);

    /**
     * @Description: 审核通过
     * @Param: audit
     * @Return: void
     */
    void auditApprove(PartnerAudit audit);
}
