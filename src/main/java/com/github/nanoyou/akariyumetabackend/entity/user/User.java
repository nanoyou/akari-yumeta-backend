package com.github.nanoyou.akariyumetabackend.entity.user;

import com.github.nanoyou.akariyumetabackend.common.enumeration.Gender;
import com.github.nanoyou.akariyumetabackend.common.enumeration.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 User
 * 属于: 用户
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /**
     * 用户名
     */
    @Column(unique = true)
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 角色
     */
    private Role role;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Gender gender;
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
