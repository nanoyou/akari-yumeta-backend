package com.github.nanoyou.akariyumetabackend.enumeration;

import lombok.AllArgsConstructor;

/**
 * API 专用码从100开始
 */
@AllArgsConstructor
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 参数错误
     */
    PARAM_ERR(1),
    /**
     * 需要登录
     */
    LOGIN_REQUIRE(2),
    /**
     * 无权限访问(角色错误)
     */
    UNAUTHORIZED(3),
    ADMIN_REGISTER_REFUSED(100);

    public final int value;

}
