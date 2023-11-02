package com.github.nanoyou.akariyumetabackend.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginParam {
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
}
