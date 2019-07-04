package com.allinpay.controller;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.service.IOrgModifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 机构信息变更
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/org/modify")
public class OrgModifyController {
    @Autowired
    private IOrgModifyService modifyService;

    /**
     * @Description: 查询可做信息变更的机构列表
     * @Param: query
     * @Return: com.allinpay.core.common.ResponseData
     */
    @GetMapping("/getList")
    public ResponseData getList(OrgModifyQuery query) {
        PageVO<PartnerAudit> pageVO = modifyService.selectByCondition(query);
        return ResponseData.success().setData(pageVO);
    }

    @GetMapping("/edit")
    public ResponseData approve() {
        return null;
    }

    @GetMapping("/sendAudit")
    public ResponseData refuse() {
        return null;
    }
}
