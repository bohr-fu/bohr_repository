package com.bohr.blogmvn.config.shiro;

import com.bohr.blogmvn.common.cache.RedisManager;
import com.bohr.blogmvn.common.util.PasswordHelper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * shiro配置类
 * @author Bohr Fu
 * @date 2019/12/11 11:02
 * @Version 1.0
 */
@Configuration
public class ShiroConfig {


    /**
     * ShiroFilter是整个Shiro的入口点，用于拦截需要安全控制的请求进行处理
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**", "anon")来配置匿名访问，必须配置到每个静态目录
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/html/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    /**
     * 开启shiro aop注解支持.
     * 匹配所有加了 Shiro 认证注解的方法
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 安全管理器
     * @param realm
     * @param sessionManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(OAuthRealm realm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 域对象
     * @return
     */
    @Bean
    public OAuthRealm oAuthRealm() {
        OAuthRealm myShiroRealm = new OAuthRealm();
        // 设定凭证匹配器用于比对权限信息
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法及次数(与密码解密工具类对应)
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.algorithmName);
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.hashIterations);
        return hashedCredentialsMatcher;
    }

    /**
     * 自定义的session管理器（session缓存在Redis）
     * @param oAuthSessionDao
     * @return
     */
    @Bean
    public SessionManager sessionManager(OAuthSessionDao oAuthSessionDao){
        OAuthSessionManager sessionManager = new OAuthSessionManager();
        sessionManager.setSessionDAO(oAuthSessionDao);
        return sessionManager;
    }

    @Bean
    public OAuthSessionDao authSessionDAO(RedisManager redisManager) {
        OAuthSessionDao authSessionDAO = new OAuthSessionDao();
        authSessionDAO.setRedisManager(redisManager);
        return authSessionDAO;
    }
}
