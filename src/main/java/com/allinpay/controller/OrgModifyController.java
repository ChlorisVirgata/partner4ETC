package com.allinpay.controller;

import com.allinpay.core.common.ResponseData;
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
    /**
     * @Description:
     * @Param: [name]
     * @Return: com.allinpay.core.common.ResponseData
     */
    @GetMapping("/getList")
    public ResponseData getList(String name) {
        return null;
    }

    @GetMapping("/getOne")
    public ResponseData getOne() {
        return null;
    }

    @GetMapping("/save")
    public ResponseData approve() {
        return null;
    }

    @GetMapping("/audit")
    public ResponseData refuse() {
        return null;
    }
}
