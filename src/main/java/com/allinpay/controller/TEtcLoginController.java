package com.allinpay.controller;

import com.allinpay.core.common.ResponseBean;
import com.allinpay.core.util.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author : wuchao
 * Date   : 2019/7/3
 * Desc   :
 */
@RestController
public class TEtcLoginController {

    @RequestMapping(value = "/web/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean login(String username, String password, String captcha) {

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (
                UnknownAccountException e) {
            return ResponseBean.error(e.getMessage());
        } catch (
                IncorrectCredentialsException e) {
            return ResponseBean.error("账号或密码不正确");
        } catch (
                LockedAccountException e) {
            return ResponseBean.error("账号已被锁定,请联系管理员");
        } catch (
                AuthenticationException e) {
            return ResponseBean.error("账户验证失败");
        }
        return ResponseBean.okData();
    }



    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        ShiroUtils.logout();
        ModelAndView view = new ModelAndView("login");
        return view;
    }


}
