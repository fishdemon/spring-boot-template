package com.fishdemon.sbt.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务配置
 * 开放的 Url 不仅要在 @EnableWebSecurity 类中配置，还要在 此类 中配置，否则还是会被拦截
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("user");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        /**
         * Resource Server 配置中请不要拦截认证相关的API，最好的办法就是不要让这个API经过这个过滤器
         */
        http
                .requestMatchers().antMatchers("/user/**")

                .and()
                .authorizeRequests()
                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .csrf().disable();
    }
}
