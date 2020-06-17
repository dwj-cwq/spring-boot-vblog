package com.dwj.vblogold.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Configuration
public class shiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        filterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("loginFilter", new MyRequestFilter());
        filterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/**", "loginFilter");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return filterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(realm());

        return securityManager;
    }

    @Bean
    public Realm realm() {
        Realm realm = new MyRealm();

        return realm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();

        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());

        return authorizationAttributeSourceAdvisor;
    }

}
