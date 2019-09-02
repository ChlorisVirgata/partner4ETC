package com.allinpay.controller;

import com.allinpay.controller.query.OrgModifyQuery;
import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerAudit;
import com.allinpay.entity.PartnerStorage;
import com.allinpay.service.IOrgModifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @description: 机构信息变更
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/manage/org/modify")
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

    /**
     * @Description: 编辑机构信息，未提交审核
     * @Param: [request, storage]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/edit")
    public ResponseData edit(MultipartHttpServletRequest request, PartnerStorage storage) {
        modifyService.editOrg(request, storage);
        return ResponseData.success().setData(null);
    }

    /**
     * @Description: 编辑信息，机构信息提交审核
     * @Param: request， audit
     * @Return: com.allinpay.core.common.ResponseData
     */
    @PostMapping("/sendAudit")
    public ResponseData sendAudit(MultipartHttpServletRequest request, PartnerAudit audit) {
        modifyService.sendOrgAudit(request, audit);
        return ResponseData.success().setData(null);
    }
}
