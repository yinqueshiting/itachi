package com.example.itachi.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private Integer timeout;



    //将自己的Realm放到SecurityManager中
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //自定义Session管理
        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现
        securityManager.setCacheManager(redisCacheManager());
        //自定义Realm验证
        securityManager.setRealm(ShiroRealm());
        return securityManager;
    }

  //以下是设置密码匹配器 start----------------------------------------------------------------------------
    @Bean
    public ShiroRealm ShiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /*
     * 凭证匹配器
     * Shiro 提供了用于加密密码和验证密码服务的 CredentialsMatcher 接口，而 HashedCredentialsMatcher 正是 CredentialsMatcher 的一个实现类。
     * 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理,在这里做匹配配置
     * @Author Sans
     * @CreateTime 2019/6/12 10:48
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用SHA256算法;
        shaCredentialsMatcher.setHashAlgorithmName("SHA-256");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        shaCredentialsMatcher.setHashIterations(1);
        //是否存储为16进制
        shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return shaCredentialsMatcher;
    }
// 密码匹配器结束 end--------------------------------------------------------------------------------

    //* 开启Shiro-aop注解支持 ,否则    @RequiresRoles()    @RequiresPermissions()不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

// 存储进redis ，避免每次验证权限时查询数据库-----------------------------------------------------

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //存放一个id标识
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setExpire(timeout);
        return redisSessionDAO;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        defaultWebSessionManager.setDeleteInvalidSessions(true);//自动删除过期的session
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);//是否定时检查session
        return defaultWebSessionManager;
    }



// 存储进redis ，避免每次验证权限时查询数据库-----------------------------------------------------

    /*
     * 配置拦截规则
     * @param securityManager
     * @return
     */

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        log.info("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器. LinkedHashMap保证添加时的顺序
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 可以理解为任何人都可以访问
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterChainDefinitionMap.put("/logout", "logout");
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        /*前者(authc)是认证过，后者(user)是登录过，如果开启了rememberMe功能的话，后者(user)也是可以通过的，而前者(authc)通过不了。
        故我们用authc来校验一些关键操作，比如购买，我们可以采用user校验即可。而支付的时候，我们需要认证的用户，那就需要authc了*/
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
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
