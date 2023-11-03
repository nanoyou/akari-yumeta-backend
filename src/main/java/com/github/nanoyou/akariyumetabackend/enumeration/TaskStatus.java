package com.github.nanoyou.akariyumetabackend.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskStatus {
    /**
     * 进行中
     */
    IN_PROGRESS("进行中"),
    /**
     * 已结束
     */
    FINISHED("已结束"),
    /**
     * 未开始
     */
    NOT_STARTED("未开始");
    public final String value;
}
