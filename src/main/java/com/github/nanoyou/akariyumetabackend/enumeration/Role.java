package com.github.nanoyou.akariyumetabackend.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    /**
     * 志愿者
     */
    VOLUNTEER("志愿者"),
    /**
     * 管理者
     */
    ADMIN("管理者"),
    /**
     * 儿童
     */
    CHILD("儿童"),
    /**
     * 捐助者
     */
    SPONSOR("捐助者");

    public final String value;
}
