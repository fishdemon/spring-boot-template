package com.fishdemon.sbt.ftp.http.controller;

import com.fishdemon.sbt.ftp.http.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Anjin.Ma
 * @description IndexController
 * @date 2020/6/28
 */
@Slf4j
@Controller
@RequestMapping
public class IndexController {
    @Value("${ftp.home-directory}")
    private String homeDirectory;
    @Value("${ftp.download-context-path}")
    private String fileDownloadContextPath;

    @RequestMapping("/ftp/**")
    public ModelAndView index(HttpServletRequest request, Model model) {
        // request path
        String servletPath = request.getServletPath();

        String currentDirPath = "";
        if (servletPath.length() > 4) {
            currentDirPath = servletPath.substring(4);

            String parentDirPath = servletPath.substring(0, servletPath.lastIndexOf("/"));
            model.addAttribute("parentDirPath", parentDirPath);
        }
        model.addAttribute("path", currentDirPath.isEmpty()?"/":currentDirPath);
        // real path of directory
        String dirRealPath = homeDirectory + currentDirPath;

        File file = new File(dirRealPath);
        if (!file.exists()) {
            log.warn("the path is not exist: {}", dirRealPath);
        }

        // get all files
        List<FileInfo> fileInfos = new ArrayList<>();

        if (file.isDirectory()) {
            String[] files = file.list();
            String finalDirPath = currentDirPath;
            Arrays.stream(files).forEach(subPath -> {
                File subFile = new File(dirRealPath + "/" + subPath);
                if(subFile.exists()) {
                    FileInfo fileInfo = FileInfo.builder()
                            .name(subFile.getName())
                            .isDir(subFile.isDirectory())
                            .size(subFile.getFreeSpace())
                            .updateTime(new Date(subFile.lastModified()))
                            .build();

                    if (fileInfo.getIsDir()) {
                        fileInfo.setUrl(servletPath + "/" + subFile.getName());
                    } else {
                        fileInfo.setUrl(fileDownloadContextPath + finalDirPath + "/" + subFile.getName());
                    }

                    fileInfos.add(fileInfo);
                }
            });
        } else {

        }

        model.addAttribute("subFiles", fileInfos);

        return new ModelAndView("index", "indexModel", model);
    }

}
