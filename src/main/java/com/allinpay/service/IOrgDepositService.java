package com.allinpay.service;

import com.allinpay.core.common.PageVO;
import com.allinpay.entity.PartnerDeposit;

public interface IOrgDepositService {
    /**
     * 条件查询机构保证金信息列表
     *
     * @param partnerId, pageNum, pageSize
     * @return PageVO<PartnerDeposit>
     */
    PageVO<PartnerDeposit> selectByCondition(String partnerId, int pageNum, int pageSize);

    /**
     * 新增机构保证金信息
     *
     * @param deposit
     */
    void addOrgDeposit(PartnerDeposit deposit);

    /**
     * 编辑机构保证金信息
     *
     * @param deposit
     */
    void editOrgDeposit(PartnerDeposit deposit);
}
