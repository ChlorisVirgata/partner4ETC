package com.allinpay.controller;

import com.allinpay.core.common.PageVO;
import com.allinpay.core.common.ResponseData;
import com.allinpay.entity.PartnerSecretInfo;
import com.allinpay.service.ISignNotifyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/sign")
public class SignNotifyConfigController {
    @Autowired
    private ISignNotifyConfigService signNotifyService;

    @GetMapping("/getOne")
    public ResponseData getOne(@RequestParam String partnerId) {
        PartnerSecretInfo secretInfo = signNotifyService.getByPartnerId(partnerId);
        return ResponseData.success().setData(secretInfo);
    }

    @GetMapping("/getList")
    public ResponseData getList(String partnerId, @RequestParam int pageNum,
                                @RequestParam int pageSize) {
        PageVO pageVO = signNotifyService.getByPartnerId(partnerId, pageNum, pageSize);
        return ResponseData.success().setData(pageVO);
    }

    @PostMapping("/add")
    public ResponseData add(PartnerSecretInfo secretInfo) {
        signNotifyService.addNotifyResultConfigInfo(secretInfo);
        return ResponseData.success();
    }

    @PostMapping("/edit")
    public ResponseData edit(PartnerSecretInfo secretInfo) {
        signNotifyService.editNotifyResultConfigInfo(secretInfo);
        return ResponseData.success();
    }
}
