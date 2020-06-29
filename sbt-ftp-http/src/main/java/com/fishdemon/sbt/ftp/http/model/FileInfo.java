package com.fishdemon.sbt.ftp.http.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Anjin.Ma
 * @description FileInfo
 * @date 2020/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = false)
public class FileInfo {

    private String name;

    private String url;

    private Date updateTime;

    private Long size;

    private Boolean isDir;

}
