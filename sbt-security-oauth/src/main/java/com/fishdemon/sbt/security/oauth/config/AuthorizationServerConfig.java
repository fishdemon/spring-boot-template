package com.fishdemon.sbt.security.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * oauth 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 支持允许表单认证登录
        security.allowFormAuthenticationForClients();
        // 允许所有客户端发送请求，避免Spring Security拦截。默认是denyAll()
        security.checkTokenAccess("permitAll()");
    }

    // 配置客户端的信息，这里仍然将客户端数据储存在内存中，实际开发中应该从数据库中读取客户端数据
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("client_1")  // 客户端ID，相当于username
            .resourceIds("info")   // 允许访问的 resource id，这些 resource id 必须在 ResourceServer 配置
            // 客户端访问模式，客户端模式(Client_credentials)、密码模式(password)、授权码模式(authorization_code)
            .authorizedGrantTypes("client_credentials", "password", "refresh_token")
            .scopes("select")      //  客户端域，限制客户端的访问范围
            .authorities("client") //客户端的权限
            .secret("123456")      // 客户端的安全码，相当于password
            .and()
            .withClient("third-part")
            .resourceIds("info")
            .redirectUris("http://example.com")
            .authorizedGrantTypes("authorization_code", "refresh_token")
            .scopes("select")
            .authorities("client")
            .secret("123456");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 将AuthenticationManager设置进去，支持密码模式的授权
        endpoints.authenticationManager(authenticationManager);
    }
}
