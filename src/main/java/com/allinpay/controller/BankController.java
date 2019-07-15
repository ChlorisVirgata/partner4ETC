package com.allinpay.controller;

import com.allinpay.controller.query.BankQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.BankInfo;
import com.allinpay.service.IBankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 银行基础信息维护
 * @author: tanguang
 * @date: 2019-07-10
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private IBankInfoService bankInfoService;

    /**
     * @Description: 查询银行基础信息列表
     * @Param: [bankQuery]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @GetMapping("/getList")
    public ResponseData getList(BankQuery bankQuery) {
        PageVO<BankInfo> pageVO = bankInfoService.selectByCondition(bankQuery);
        return ResponseData.success().setData(pageVO);
    }

    /**
     * @Description: 新增银行基础信息
     * @Param: [bankId, bankName]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/add")
    public ResponseData add(@RequestParam("bankId") String bankId,
                            @RequestParam("bankName") String bankName) {
        bankInfoService.addBank(bankId, bankName);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 编辑银行基础信息
     * @Param: [bankId, bankName]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/edit")
    public ResponseData edit(@RequestParam("bankId") String bankId,
                             @RequestParam("bankName") String bankName) {
        bankInfoService.editBank(bankId, bankName);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 变更记录有效状态
     * @Param: [status, bankId]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @RequestMapping("/status")
    public ResponseData status(@RequestParam("status") String status,
                               @RequestParam("bankId") String bankId) {
        bankInfoService.updateStatus(bankId, status);
        return ResponseData.success().setData(null);
    }
}
