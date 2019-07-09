package com.allinpay.controller;

import com.allinpay.core.common.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/etc")
public class LoginController {

    //登录
    @RequestMapping("login")
    public ResponseData login(String username, String password, String randomCode) {
        //todo 短信验证码校验
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        //如何设置remember me ?
        //设置认证时间的时效,即超过该时间需要重新登录。 设置为10分钟，单位毫秒
        subject.getSession().setTimeout(60000);
        return ResponseData.success().setData(subject.getPrincipal());
    }

    //退出
    @RequestMapping("logout")
    public ResponseData logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseData.success().setData(null);
    }

    //获取验证码
    @GetMapping("randomCode")
    public ResponseData getRandomCode() {
        return null;
    }
}
