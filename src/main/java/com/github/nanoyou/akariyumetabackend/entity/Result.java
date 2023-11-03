package com.github.nanoyou.akariyumetabackend.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    /**
     * 是否成功
     */
    private boolean ok;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;
}
