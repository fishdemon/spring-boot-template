package com.fishdemon.sbt.ftp.http.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Anjin.Ma
 * @description LayUploadResult
 * @date 2020/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = false)
public class LayUploadResult {

    private Integer code;

    private String msg;

    private Object data;

}
