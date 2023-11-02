package com.github.nanoyou.akariyumetabackend.entity;

import lombok.Data;

import java.lang.reflect.Array;

@Data
public class FileItem {
    /**
     *文件ID(哈希值)
     */
    private String id;
    /**
     * 文件的MIME类型，如image/png image/jpeg
     */
    private String mimeType;
    /**
     * 文件数据(二进制格式)
     */
    private Array data;
}
