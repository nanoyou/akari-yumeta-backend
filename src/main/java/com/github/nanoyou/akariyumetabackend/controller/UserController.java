package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.exception.NoSuchUserError;
import com.github.nanoyou.akariyumetabackend.dto.TagDTO;
import com.github.nanoyou.akariyumetabackend.dto.subscription.SubscriptionDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserUpdateDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.SubscriptionService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public UserController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }


    @RequestMapping(path = "/user", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result user() {
        val allUsers = userService.getAllUsers();
        val userDTOs = allUsers.stream().map(
                user -> userService.getUserDTO(user.getId())
                        .orElseThrow(() -> new NoSuchUserError(ResponseCode.NO_SUCH_USER, "用户不存在"))
        ).toList();
        return Result.success("查看用户列表成功", userDTOs);
    }

    @RequestMapping(path = "/my/info", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myInfo(@RequestAttribute("user") User user) {
        val userDTO = userService.getUserDTO(user.getId());
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
     * @param user
     */
    @RequestMapping(path = "/my/follow/{followeeID}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result follow(@PathVariable String followeeID, @RequestAttribute("user") User user) {
        try {
            val friend = Subscription._CombinedPrimaryKey.builder()
                    .followerID(user.getId())
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
     * @param user
     */
    @RequestMapping(path = "/my/follow/{userID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result isFollowed(@PathVariable String userID, @RequestAttribute("user") User user) {
        try {
            val friend = Subscription._CombinedPrimaryKey.builder()
                    .followerID(user.getId())
                    .followeeID(userID)
                    .build();

            var followed = subscriptionService.validateFollow(friend);

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
     *
     * @param userUpdateDTO
     */
    @RequestMapping(path = "/my/info", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public Result info(@RequestBody UserUpdateDTO userUpdateDTO, @RequestAttribute("user") User user) {
        try {
            val loginUserID = user.getId();
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
                    u -> UserDTO.builder()
                            .id(u.getId())
                            .username(u.getUsername())
                            .nickname(u.getNickname())
                            .role(u.getRole())
                            .gender(u.getGender())
                            .introduction(u.getIntroduction())
                            .avatarURL(u.getAvatarURL())
                            .usageDuration(u.getUsageDuration())
                            .tags(u.getTags())
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


    @RequestMapping(path = "/my/follow", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myFollow(@RequestAttribute("user") User user) {
        try {
            val loginUserID = user.getId();
            val followeeIDs = subscriptionService.getFolloweeIDs(loginUserID);
            val followees = userService.getFollowee(followeeIDs);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("查看关注列表成功")
                    .data(followees)
                    .build();
        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NOT_FOLLOW_ANYONE.value)
                    .message("您还没有关注任何人")
                    .data(null)
                    .build();
        }
    }

}
