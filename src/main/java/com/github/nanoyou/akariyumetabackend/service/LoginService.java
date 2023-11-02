package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserDao userDao;

    public Result login(@NotNull User loginUser) {
        assert loginUser.getRole() != null;

        User user = null;

        switch (loginUser.getRole()){
            case ADMIN:
                user = userDao.admin(loginUser);
            case VOLUNTEER:
                user = userDao.volunteer(loginUser);
            case SPONSOR:
                user = userDao.sponsor(loginUser);
            case CHILD:
                user = userDao.child(loginUser);
        }

        // 登录成功data
        var u = RegisterService._User.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole().value)
                .gender(user.getGender().value)
                .introduction(user.getIntroduction())
                .avatarURL(user.getAvatarURL())
                .usageDuration(user.getUsageDuration())
                .build();

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .data(u)
                .message("登录成功")
                .build();
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
