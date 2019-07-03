package com.allinpay.controller;

import com.allinpay.core.common.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 机构信息录入
 * @author: tanguang
 * @date: 2019-07-02 16:58
 */
@RestController
@RequestMapping("/org/enter")
public class OrgEnterController {
    @GetMapping("/audit")
    public ResponseData approve() {
        return null;
    }

    @GetMapping("/save")
    public ResponseData refuse() {
        return null;
    }
}
