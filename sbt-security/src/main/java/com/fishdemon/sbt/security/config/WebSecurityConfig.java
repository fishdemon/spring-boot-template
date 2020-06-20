package com.fishdemon.sbt.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


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
//                .formLogin()
//                .loginPage("/auth/login")
//                .defaultSuccessUrl("/index")
                .httpBasic()
                .and()
                .rememberMe() // 开启记住我功能
                .tokenValiditySeconds(60)  // 记住我 token 的失效时间
                .userDetailsService(userDetailsService) // 记住我功能验证user的service
                .and()
//                .requestMatchers().antMatchers("/auth/**")
//                .and()
                .authorizeRequests()
                .antMatchers("/noauth/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }


}
