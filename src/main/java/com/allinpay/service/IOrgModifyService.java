package com.allinpay.service;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerAudit;

public interface IOrgModifyService {
    /**
     * @Description: 查询可做信息变更的机构列表
     * @Param: query
     * @Return: PageVO<PartnerAudit>
     */
    PageVO<PartnerAudit> selectByCondition(OrgModifyQuery query);
}
