package com.allinpay.core.base;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置文件
 *
 * @author 吴超
 */
@Configuration
public class ShiroConfig {

    /**
     * 单机环境，session交给shiro管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager(@Value("${globalSessionTimeout:3600}") long globalSessionTimeout) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationInterval(globalSessionTimeout * 1000);
        sessionManager.setGlobalSessionTimeout(globalSessionTimeout * 1000);
        return sessionManager;
    }


    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/web/login");
        //  shiroFilter.setUnauthorizedUrl("/");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/templates/**", "anon");
        filterMap.put("/public/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");

        // 配置不会被拦截的链接 顺序判断
        filterMap.put("/web/login", "anon");
        filterMap.put("/etc/login", "anon");
        filterMap.put("/etc/captcha", "anon");
        filterMap.put("/etc/logout", "anon");
        filterMap.put("/*.ico", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/static/lib/**", "anon");
        //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilter.setLoginUrl("/web/login");
        //shiroFilter.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilter.setSuccessUrl("/web/index");
        //未授权界面;
        shiroFilter.setUnauthorizedUrl("/403");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
