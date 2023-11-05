package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.subscription.SubscriptionDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import com.github.nanoyou.akariyumetabackend.service.SubscriptionService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public UserController(UserService userService, SubscriptionService subscriptionService){
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(path = "/my/follow/{followeeID}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result follow(@PathVariable String followeeID, @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            val friend = Subscription._CombinedPrimaryKey.builder()
                    .followerID(loginUserID)
                    .followeeID(followeeID)
                    .build();

            if(subscriptionService.validateFollow(friend)){
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
            }
            else{
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

}
