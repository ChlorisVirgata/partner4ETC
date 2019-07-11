package com.allinpay.service;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.entity.BankInfo;

public interface IBankInfoService {
    /**
     * @Description: 根据银行编码、银行名称查询银行基础信息
     * @Param: bankId, bankName
     * @Return: PageVO<BankInfo>
     */
    PageVO<BankInfo> selectByCondition(BankQuery bankQuery);

    /**
     * @Description: 银行信息添加
     * @Param: bankId, bankName
     * @Return: void
     */
    void addBank(String bankId, String bankName);

    /**
     * @Description: 银行信息编辑
     * @Param: bankId, bankName
     * @Return: void
     */
    void editBank(String bankId, String bankName);

    /**
     * @Description: 更新银行信息状态，禁用或有效
     * @Param: bankId, status
     * @Return: void
     */
    void updateStatus(String bankId, String status);
}
