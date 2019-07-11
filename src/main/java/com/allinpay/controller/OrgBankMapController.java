package com.allinpay.controller;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerBank;
import com.allinpay.service.IPartnerBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 机构发卡银行列表controller
 * @author: tanguang
 * @date: 2019-07-10
 */
@RestController
@RequestMapping("/org/bank")
public class OrgBankMapController {
    @Autowired
    private IPartnerBankService partnerBankService;

    /**
     * @Description: 查询机构银行映射列表
     * @Param: [bankQuery]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @RequestMapping("/getList")
    public ResponseData getList(BankQuery bankQuery) {
        PageVO<PartnerBank> pageVo = partnerBankService.selectByCondition(bankQuery);
        return ResponseData.success().setData(pageVo);
    }

    /**
     * @Description: 新增（变更）机构银行映射记录
     * @Param: [partnerBank]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/add")
    public ResponseData add(PartnerBank partnerBank) {
        partnerBankService.addOrgBank(partnerBank);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 变更机构银行映射记录有效状态
     * @Param: [partnerBank]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @RequestMapping("/status")
    public ResponseData status(PartnerBank partnerBank) {
        partnerBankService.updateStatus(partnerBank);
        return ResponseData.success().setData(null);
    }
}
