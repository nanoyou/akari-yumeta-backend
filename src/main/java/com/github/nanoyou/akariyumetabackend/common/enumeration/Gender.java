package com.github.nanoyou.akariyumetabackend.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    /**
     * 男
     */
    MALE("男"),
    /**
     * 女
     */
    FEMALE("女"),
    /**
     * 秘密
     */
    SECRET("秘密");

    public final String value;
}
