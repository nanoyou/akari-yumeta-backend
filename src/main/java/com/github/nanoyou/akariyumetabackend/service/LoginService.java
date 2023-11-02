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

        var user = userDao.login(loginUser.getUsername(), loginUser.getPassword());

        assert user.getRole() != null;

        switch (user.getRole()){
            case ADMIN:
                userDao.admin(user);
                break;
            case VOLUNTEER:
                userDao.volunteer(user);
                break;
            case SPONSOR:
                userDao.sponsor(user);
                break;
            case CHILD:
                userDao.child(user);
                break;
        }

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .data(user)
                .message("登录成功")
                .build();
    }

}
