package com.example.itachi.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 权限过滤规则，Realm
 */

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //将自己的Realm放到SecurityManager中
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
       /* //自定义Session管理
        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现
        securityManager.setCacheManager(cacheManagers());
        //自定义Realm验证
        securityManager.setRealm(ShiroRealm());*/
        return securityManager;
    }

    /**
     * 配置拦截规则
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器. LinkedHashMap保证添加时的顺序
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 可以理解为任何人都可以访问
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果没有登陆会调用这个接口
        shiroFilterFactoryBean.setLoginUrl("/static/toLogin");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/success");
        //没有权限就去调用此接口
        shiroFilterFactoryBean.setUnauthorizedUrl("static/unAuthorized");
        //如果要动态的配置接口的权限 可以将接口需要的权限加到数据库或properties(yml)中读取出来 然后循环放到filterChainDefinitionMap中
        // filterChainDefinitionMap.put(perm.get("url"),perm.get("permission"))
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
