package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.RegisterDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<UserDTO> getUserDTO(@Nonnull String userID) {
        val user = userDao.findById(userID);

        val tagContentList = user.map(u -> {
            val tags = tagService.getTags(u.getId());
            return tags.getTagContentList();
        }).orElse(new ArrayList<>());

        return user.map(
                u -> UserDTO.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .nickname(u.getNickname())
                        .role(u.getRole())
                        .gender(u.getGender())
                        .introduction(u.getIntroduction())
                        .avatarURL(u.getAvatarURL())
                        .usageDuration(u.getUsageDuration())
                        .tags(tagContentList)
                        .build()
        );

    }

    public boolean userExists(@Nonnull String userID) {
        return userDao.existsById(userID);
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

    public Optional<UserDTO> info(@Nonnull User user, @Nonnull TagDTO tagDTO) {
        userDao.saveAndFlush(user);

        //tags更新
        val tagList = tagDTO.getTagContentList();
        TagDTO tagDTO1 = null;
        if ((tagList != null) && (!(tagList.isEmpty()))) {
            tagDTO1 = tagService.addTags(user.getId(), tagDTO.getTagContentList());
        }

        return Optional.of(UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .introduction(user.getIntroduction())
                        .avatarURL(user.getAvatarURL())
                        .usageDuration(user.getUsageDuration())
                        .tags(tagDTO1 == null ? new ArrayList<>() : tagDTO1.getTagContentList())
                .build());
    }

    public  List<UserDTO> getFollowee(@Nonnull List<String> followeeIDs){
        val followees =  followeeIDs.stream().map(
                followeeID -> userDao.findById(followeeID).orElseThrow(NullPointerException::new)
        ).toList();

        val tags = followeeIDs.stream().map(
                tagService::getTags
        ).toList();


        Map<String, List<String>> tagMap = tags.stream()
                .collect(Collectors.toMap(TagDTO::getUserID, TagDTO::getTagContentList));

        return followees.stream().map(
                user -> {
                    UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .gender(user.getGender())
                    .introduction(user.getIntroduction())
                    .avatarURL(user.getAvatarURL())
                    .usageDuration(user.getUsageDuration())
                    .build();

                    List<String> userTags = tagMap.get(user.getId());
                    if (userTags != null) {
                        userDTO.setTags(userTags);
                    }
                    return userDTO;
        }).toList();

    }


}
