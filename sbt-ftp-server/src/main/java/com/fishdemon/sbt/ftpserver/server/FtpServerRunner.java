package com.fishdemon.sbt.ftpserver.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FtpServerRunner implements CommandLineRunner {

    @Autowired
    private FtpServer ftpServer;

    @Override
    public void run(String... args) throws Exception {
        log.info("starting ftp server...");
        ftpServer.start();
        log.info("ftp server started successfully!");
    }

}
