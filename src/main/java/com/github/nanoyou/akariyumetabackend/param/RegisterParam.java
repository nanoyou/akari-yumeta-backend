package com.github.nanoyou.akariyumetabackend.param;

import com.github.nanoyou.akariyumetabackend.enumeration.Gender;
import com.github.nanoyou.akariyumetabackend.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RegisterParam {
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
     * 使用时长，APP使用时长统计，单位为秒
     */
    @NotNull(message = "注册时长不能为空，如果第一次注册，应为0")
    private Integer usageDuration;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;

    private List<String> tags;
}
