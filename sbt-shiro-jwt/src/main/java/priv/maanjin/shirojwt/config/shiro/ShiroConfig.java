package priv.maanjin.shirojwt.config.shiro;

import org.springframework.context.annotation.Configuration;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author anjin.ma
 * @date 2019/7/23
 * @description  Shiro 配置类
 */
@Configuration
public class ShiroConfig {
	
    /**
     *  先经过token过滤器，如果检测到请求头存在 token，则用 token 去 login，接着走 Realm 去验证
     */
    @Bean
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        factoryBean.setSecurityManager(securityManager);
        
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        // 设置我们自定义的JWT过滤器
        filterMap.put("jwtFilter", new JwtFilter());
        factoryBean.setFilters(filterMap);
        
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/unauthorized/*");
        
        // 设置拦截器链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        
        // 放行不需要权限认证的接口
        // 放行Swagger接口
        filterChainDefinitionMap.put("/v2/api-docs","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui","anon");
        filterChainDefinitionMap.put("/swagger-resources","anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");
        
        // 放行登录接口和其他不需要权限的接口
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "noSessionCreation,jwtFilter[permissive]"); //做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        filterChainDefinitionMap.put("/getVisitor", "anon");
        filterChainDefinitionMap.put("/unauthorized/*", "anon");
        
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 :这是一个坑呢，一不小心代码就不好使了;
        // ① authc:所有url都必须认证通过才可以访问; 
        // ② anon:所有url都都可以匿名访问
        // 所有请求通过我们自己的JWT Filter
        //filterChainDefinitionMap.put("/b/user/*", "authc");
        filterChainDefinitionMap.put("/**", "jwtFilter");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;

    }

    /**
     *  注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(JwtRealm jwtRealm, DbRealm dbRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm
        securityManager.setRealms(Arrays.asList(jwtRealm, dbRealm));

        //  关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     *  添加注解支持
     *   开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     *  配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    
    /**
     * 
     *  Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
