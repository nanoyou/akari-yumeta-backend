package com.github.nanoyou.akariyumetabackend.entity.user;

import com.github.nanoyou.akariyumetabackend.enumeration.UserGender;
import com.github.nanoyou.akariyumetabackend.enumeration.UserRole;
import lombok.Data;

import java.util.UUID;

/**
 * 用户 User
 * 属于: 用户
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private UUID id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 角色
     */
    private UserRole role;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private UserGender gender;
    /**
     * 个人介绍
     */
    private String introduction;
    /**
     * 头像链接
     */
    private String avatarURL;
    /**
     * APP使用时长(单位: s)
     */
    private Integer usageDuration;
}
