package com.allinpay.service;

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
     * @Param: audit
     * @Return: PageVO<PartnerAudit>
     */
    PageVO<PartnerAudit> selectByCondition(PartnerAudit audit);
}
