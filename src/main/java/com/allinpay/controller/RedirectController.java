package com.allinpay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
/**
 * @description: 页面转发controller
 * @author: tanguang
 * @date: 2019-07-02 19:18
 */
public class RedirectController {
    @RequestMapping("/orgEnter")
    public String orgEnter() {
        return "/orgmanage/orgEnter";
    }

    @RequestMapping("/orgModify")
    public String orgModify() {
        return "/orgmanage/orgModify";
    }

    @RequestMapping("/orgAudit")
    public String orgAudit() {
        return "/orgmanage/orgAudit";
    }
}
