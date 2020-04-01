package com.fishdemon.sbt.security.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * fromLogin(): 表单认证
         * httpBasic(): 弹出框认证
         * requestMatchers(): 匹配的请求会进入过滤器链，否则直接开放，一般放在最前面
         * authorizeRequests() 身份认证请求
         * anyRequest(): 匹配所有请求,一般放在最后面
         * authenticated(): 身份认证
         */
        http
                .formLogin()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/auth/index")
//                .httpBasic()
                .and()
                .requestMatchers().antMatchers("/auth/**", "/login")
                .and()
                .authorizeRequests()
//                .antMatchers("/noauth/**")
//                .permitAll()
                .anyRequest()
                .authenticated();
    }


}
