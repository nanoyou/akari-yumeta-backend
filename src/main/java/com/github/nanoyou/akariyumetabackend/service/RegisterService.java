package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.enumeration.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private UserDao userDao;

    public Result register(@NonNull User registerUser) {
        assert registerUser.getRole() != null;

        // 管理员不可以被注册
        if (Role.ADMIN.equals(registerUser.getRole())) {
            return Result.builder().
                    ok(true).
                    code(ResponseCode.ADMIN_REGISTER_REFUSED.value).
                    message("不能注册管理员账户").
                    build();
        }

        // 注册的用户被添加进数据库
        userDao.addUser(registerUser);

        var retUser = _User.builder()
                .id(registerUser.getId().toString())
                .username(registerUser.getUsername())
                .nickname(registerUser.getNickname())
                .role(registerUser.getRole().value)
                .gender(registerUser.getGender().value)
                .introduction(registerUser.getIntroduction())
                .avatarURL(registerUser.getAvatarURL())
                .usageDuration(registerUser.getUsageDuration())
                .build();

        return Result.builder().
                ok(true).
                code(ResponseCode.SUCCESS.value).
                message("注册成功").
                data(retUser).
                build();

    }

    @Builder
    @Data
    static class _User {
        String id;
        String username;
        String nickname;
        String role;
        String gender;
        String introduction;
        String avatarURL;
        Integer usageDuration;
    }

}
