package com.github.nanoyou.akariyumetabackend.dto.user;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.Gender;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RegisterDTO {
    /**
     * 头像链接
     */
    private String avatarURL;
    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Gender gender;
    /**
     * 个人介绍
     */
    private String introduction;
    /**
     * 昵称
     */
    @NotNull(message = "昵称不能为空")
    private String nickname;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 角色，管理员不支持注册
     */
    @NotNull(message = "用户角色不能为空")
    private Role role;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    private List<String> tags;
}
