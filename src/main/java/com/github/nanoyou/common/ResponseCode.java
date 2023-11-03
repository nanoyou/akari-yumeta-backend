package com.github.nanoyou.common;

// 枚举类
public enum ResponseCode {
    SUCCESS(0),
    PARAM_ERR(1),
    LOGIN_REQUIRE(2),
    UNAUTHORIZED(3);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static void main(String[] args) {
        // 写一个上面的例子
        System.out.println(ResponseCode.SUCCESS.getCode());

    }
}