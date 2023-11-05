package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.TagDTO;
import com.github.nanoyou.akariyumetabackend.dto.subscription.SubscriptionDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserUpdateDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import com.github.nanoyou.akariyumetabackend.entity.user.Tag;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.TagService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.nanoyou.akariyumetabackend.service.SubscriptionService;

import java.util.ArrayList;

@RestController
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final TagService tagService;

    @Autowired
    public UserController(UserService userService, SubscriptionService subscriptionService, TagService tagService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.tagService = tagService;
    }


    @RequestMapping(path = "/user", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result user() {
        val allUsers = userService.getAllUsers();

        val userDTOBuilderStream = allUsers.stream().map(
                user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .nickname(user.getNickname())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .introduction(user.getIntroduction())
                        .avatarURL(user.getAvatarURL())
                        .usageDuration(user.getUsageDuration())
                        // TODO: tags 尚未实现
                        .tags(new ArrayList<>())
                        .build()
        ).toList();
        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .data(userDTOBuilderStream)
                .message("查看用户列表成功")
                .build();
    }

    @RequestMapping(path = "/my/info", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myInfo(@ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        val userDTO = userService.getUserDTO(loginUserID);
        return userDTO.map(
                u -> Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("查看登录用户个人信息成功")
                        .data(u)
                        .build()
        ).orElse(Result.builder()
                .ok(false)
                .code(ResponseCode.NO_SUCH_USER.value)
                .message("查看登录用户个人信息失败：用户不存在")
                .data(null)
                .build());
    }

    @RequestMapping(path = "/user/{userID}/info", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result userInfo(@PathVariable String userID) {
        val userDTO = userService.getUserDTO(userID);
        return userDTO.map(
                u -> Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("查看他人个人信息成功")
                        .data(u)
                        .build()
        ).orElse(Result.builder()
                .ok(false)
                .code(ResponseCode.NO_SUCH_USER.value)
                .message("查看他人个人信息失败：用户不存在")
                .data(null)
                .build());
    }

    /**
     * 关注
     *
     * @param followeeID
     * @param loginUserID
     * @return
     */
    @RequestMapping(path = "/my/follow/{followeeID}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result follow(@PathVariable String followeeID, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            val friend = Subscription._CombinedPrimaryKey.builder()
                    .followerID(loginUserID)
                    .followeeID(followeeID)
                    .build();

            if (subscriptionService.validateFollow(friend)) {
                return subscriptionService.unfollow(friend) ?
                        Result.builder()
                                .ok(true)
                                .code(ResponseCode.SUCCESS.value)
                                .message("取消关注成功")
                                .build() :
                        Result.builder()
                                .ok(false)
                                .code(ResponseCode.UNFOLLOW_FAIL.value)
                                .message("取消关注失败")
                                .build();
            } else {
                var subscription = Subscription.builder()
                        .combinedPrimaryKey(friend)
                        .build();

                var subscriptionDTO = subscriptionService.follow(subscription).map(
                        subs -> SubscriptionDTO.builder()
                                .followerID(subs.getCombinedPrimaryKey().getFollowerID())
                                .followeeID(subs.getCombinedPrimaryKey().getFolloweeID())
                                .build()
                ).orElseThrow(NullPointerException::new);

                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("关注成功")
                        .data(subscriptionDTO)
                        .build();
            }

        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.FOLLOW_FAIL.value)
                    .message("内部服务器错误")
                    .build();
        }

    }

    /**
     * 查看是否关注某人
     *
     * @param userID
     * @param loginUserID
     * @return
     */
    @RequestMapping(path = "/my/follow/{userID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result isFollowed(@PathVariable String userID, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            val friend = Subscription._CombinedPrimaryKey.builder()
                    .followerID(loginUserID)
                    .followeeID(userID)
                    .build();

            var followed = subscriptionService.validateFollow(friend) ? true : false;

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("您已关注" + userID)
                    .data(followed)
                    .build();
        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NOT_FOLLOW.value)
                    .message("您未关注" + userID)
                    .build();
        }
    }

    /**
     * 修改个人信息
     * @param userUpdateDTO
     * @return
     */
    @RequestMapping(path = "/my/info", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public Result info(@RequestBody UserUpdateDTO userUpdateDTO, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            var userUpdate = User.builder()
                    .id(loginUserID)
                    .nickname(userUpdateDTO.getNickname())
                    .gender(userUpdateDTO.getGender())
                    .introduction(userUpdateDTO.getIntroduction())
                    .avatarURL(userUpdateDTO.getAvatarURL())
                    .build();
            var tagsUpdate = TagDTO.builder()
                    .userID(loginUserID)
                    .tagContentList(userUpdateDTO.getTags())
                    .build();

            val userDTO = userService.info(userUpdate, tagsUpdate).map(
                    user -> UserDTO.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .nickname(user.getNickname())
                            .role(user.getRole())
                            .gender(user.getGender())
                            .introduction(user.getIntroduction())
                            .avatarURL(user.getAvatarURL())
                            .usageDuration(user.getUsageDuration())
                            .tags(user.getTags())
                            .build()
            ).orElseThrow(NullPointerException::new);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("个人信息修改成功")
                    .data(userDTO)
                    .build();
        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.PERSONAL_INFO_MODIFY_FAIL.value)
                    .message("个人信息修改失败")
                    .build();
        }


    }

}
