package com.fishdemon.sbt.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * fromLogin(): 表单认证
         * httpBasic(): 弹出框认证
         * authorizeRequests() 身份认证请求
         * anyRequest(): 所有请求
         * authenticated(): 身份认证
         */

//        http.formLogin()
        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

}
