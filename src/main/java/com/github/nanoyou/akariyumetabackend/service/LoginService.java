package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.dto.LoginDTO;
import com.github.nanoyou.akariyumetabackend.dto.UserDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.enumeration.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final UserDao userDao;

    private LoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<UserDTO> login(@NotNull LoginDTO loginUserDTO) {

        var loginUser = userDao.findByUsernameAndPassword(loginUserDTO.getUsername(), loginUserDTO.getPassword());

        return loginUser.map(
                user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .introduction(user.getIntroduction())
                        .usageDuration(user.getUsageDuration())
                        .role(user.getRole().value)
                        .gender(user.getGender().value)
                        .avatarURL(user.getAvatarURL())
                        .build()

        );

    }

//    @Builder
//    @Data
//    static class _User {
//        String id;
//        String username;
//        String nickname;
//        String role;
//        String gender;
//        String introduction;
//        String avatarURL;
//        Integer usageDuration;
//    }
}
