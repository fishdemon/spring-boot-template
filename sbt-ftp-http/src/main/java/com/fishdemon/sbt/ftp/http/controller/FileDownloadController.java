package com.fishdemon.sbt.ftp.http.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Anjin.Ma
 * @description FileDownloadController
 * @date 2020/6/28
 */
@Controller
@RequestMapping("/file")
public class FileDownloadController {
    @Value("${ftp.home-directory}")
    private String homeDirectory;

    @GetMapping("/**")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // request path
        String servletPath = request.getServletPath();

        String currentfilePath = "";
        if (servletPath.length() > 5) {
            currentfilePath = servletPath.substring(5);
        }

        // real path of directory
        String dirRealPath = homeDirectory + currentfilePath;
        File file = new File(dirRealPath);
        if (!file.exists()) {
            // handle file not exists
            return;
        }

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024];
        int len = 0;
        // 非常重要
        response.reset();
        OutputStream os = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            os.write(buf,0, len);
        }
        br.close();
        os.close();
    }

}
