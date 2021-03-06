package com.fishdemon.sbt.security.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("allen").password("123456").authorities("USER").build());
        return manager;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 认证中心的关于授权/token 获取/用户登录/登出 都是由 Security 配置来拦截处理的
         * 这些 API 请不要让 ResourceServer 拦截 ， 否则会出现各种诡异的问题
         */
        http
                .authorizeRequests()
                .antMatchers("/hello", "/oauth/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()

                .and()
                .csrf().disable();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
