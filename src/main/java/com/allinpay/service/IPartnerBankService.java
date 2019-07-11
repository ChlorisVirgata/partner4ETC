package com.allinpay.service;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerBank;

public interface IPartnerBankService {
    /**
     * @Description: 条件查询机构管理银行列表
     * @Param: bankQuery
     * @Return: PageVO<PartnerBank>
     */
    PageVO<PartnerBank> selectByCondition(BankQuery bankQuery);

    /**
     * @Description: 添加机构银行映射信息
     * @Param: partnerBank
     * @Return: void
     */
    void addOrgBank(PartnerBank partnerBank);

    /**
     * @Description: 禁用或恢复 机构银行信息
     * @Param: partnerBank
     * @Return: void
     */
    void updateStatus(PartnerBank partnerBank);
}
