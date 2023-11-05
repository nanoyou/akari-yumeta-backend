package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.RegisterDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserUpdateDTO;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.dto.TagDTO;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final TagService tagService;

    @Autowired
    private UserService(UserDao userDao, TagService tagService) {
        this.userDao = userDao;
        this.tagService = tagService;
    }

    public Optional<User> getUser(@Nonnull String userID) {
        return userDao.findById(userID);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public UserDTO register(@NonNull RegisterDTO registerUser) {
        // 注册的用户被添加进数据库

        var user = User.builder()
                .username(registerUser.getUsername())
                .nickname(registerUser.getNickname())
                .role(registerUser.getRole())
                .password(registerUser.getPassword())
                .gender(registerUser.getGender())
                .introduction(registerUser.getIntroduction())
                .avatarURL(registerUser.getAvatarURL())
                .usageDuration(0)
                .build();

        user = userDao.saveAndFlush(user);

        val tagList = registerUser.getTags();
        TagDTO tagDTO = null;
        if ((tagList != null) && (!(tagList.isEmpty()))) {
            tagDTO = tagService.addTags(user.getId(), registerUser.getTags());
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .gender(user.getGender())
                .introduction(user.getIntroduction())
                .avatarURL(user.getAvatarURL())
                .usageDuration(user.getUsageDuration())
                .tags(tagDTO == null ? new ArrayList<>() : tagDTO.getTagContentList())
                .build();
    }

    public Optional<UserDTO> login(@NotNull LoginDTO loginUserDTO) {

        val loginUser = userDao.findByUsernameAndPassword(loginUserDTO.getUsername(), loginUserDTO.getPassword());

        return loginUser.map(
                user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .introduction(user.getIntroduction())
                        .usageDuration(user.getUsageDuration())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .avatarURL(user.getAvatarURL())
                        .tags(tagService.getTags(user.getId()).getTagContentList())
                        .build()
        );
    }

    public Optional<User> info(@Nonnull User user) {
        userDao.saveAndFlush(user);

        return Optional.of(User.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .introduction(user.getIntroduction())
                        .avatarURL(user.getAvatarURL())
                        .usageDuration(user.getUsageDuration())
                        //.tags(tagDTO == null ? new ArrayList<>() : tagDTO.getTagContentList())
                .build());
    }
}
