package com.fishdemon.sbt.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务配置
 * 开放的 Url 不仅要在 @EnableWebSecurity 类中配置，还要在 此类 中配置，否则还是会被拦截
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/hello")
            .permitAll()
            .anyRequest()
            .authenticated()

            .and()
            .csrf().disable();
    }
}
