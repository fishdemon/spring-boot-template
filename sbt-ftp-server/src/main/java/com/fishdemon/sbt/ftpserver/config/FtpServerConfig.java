package com.fishdemon.sbt.ftpserver.config;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FtpServerConfig {
    @Value("${ftp.server.port:21}")
    private int port;
    @Value("${ftp.server.allow-anonymous:true}")
    private boolean allowAnonymous;
    @Value("${ftp.server.username}")
    private String username;
    @Value("${ftp.server.password}")
    private String password;
    @Value("${ftp.server.home-directory}")
    private String homeDirectory;
    @Value("${ftp.server.allow-write:false}")
    private boolean allowWrite;

    @Bean
    public FtpServer createFtpServer() throws FtpException {
        FtpServerFactory serverFactory = new FtpServerFactory();

        BaseUser user = new BaseUser();
        if (allowAnonymous) {
            user.setName("anonymous");
        } else {
            user.setName(username);
            user.setPassword(password);
        }
        user.setHomeDirectory(homeDirectory);

        List<Authority> authority = new ArrayList<Authority>();
        if (allowWrite) {
            authority.add(new WritePermission());
        }
//            //最大同时登录人数+最大同时登录ip
//            authority.add(new ConcurrentLoginPermission(2,3));
//            //最大下载速度+最大上传速度
//           authority.add(new TransferRatePermission(10,30));
        user.setAuthorities(authority);

        serverFactory.getUserManager().save(user);
        return serverFactory.createServer();
    }

}
