package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    private final UserDao userDao;

    @Autowired
    private RegisterService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDTO register(@NonNull User registerUser) {
        // 注册的用户被添加进数据库
        userDao.save(registerUser);
        // TODO: 存储tags

        return UserDTO.builder()
                .id(registerUser.getId())
                .username(registerUser.getUsername())
                .nickname(registerUser.getNickname())
                .role(registerUser.getRole())
                .gender(registerUser.getGender())
                .introduction(registerUser.getIntroduction())
                .avatarURL(registerUser.getAvatarURL())
                .usageDuration(registerUser.getUsageDuration())
                .tags(List.of())
                .build();
    }



}
