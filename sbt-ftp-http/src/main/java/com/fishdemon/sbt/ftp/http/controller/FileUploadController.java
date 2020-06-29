package com.fishdemon.sbt.ftp.http.controller;

import com.fishdemon.sbt.ftp.http.model.LayUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Anjin.Ma
 * @description FileUploadController
 * @date 2020/6/28
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Value("${ftp.home-directory}")
    private String homeDirectory;

    @PostMapping("/file")
    public LayUploadResult uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) throws IOException {
        String fileName = file.getOriginalFilename();
        String realPath = homeDirectory + path + File.separator + fileName;
        File saveFile = new File(realPath);

        if (saveFile.exists()) {
            saveFile.delete();
        } else {
            String dirPath = saveFile.getParent();
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        saveFile.createNewFile();
        try(FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
            InputStream is = file.getInputStream();
            byte[] buf = new byte[2048];
            int len = 0;
            while ((len = is.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            return LayUploadResult.builder().code(-1).build();
        }

        return LayUploadResult.builder().code(0).build();
    }

}
