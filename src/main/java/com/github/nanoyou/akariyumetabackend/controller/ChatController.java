package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.SendMessageDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.service.ChatService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @RequestMapping(path = "/chat/message/{userID}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result sendMessage(@PathVariable String userID,
                              @RequestBody SendMessageDTO sendMessageDTO,
                              @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID
    ) {
        // receiver = userID
        val content = sendMessageDTO.getContent();

        if (!StringUtils.hasText(content)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.EMPTY_MESSAGE_CONTENT.value)
                    .message("发送消息不能为空")
                    .data(null)
                    .build();
        }

        if (!userService.userExists(userID)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NO_SUCH_USER.value)
                    .message("用户不存在")
                    .data(null)
                    .build();
        }

        var message = Message.builder()
                .senderID(loginUserID)
                .receiverID(userID)
                .content(content)
                .isRead(false)
                .sendTime(LocalDateTime.now())
                .type(sendMessageDTO.getType())
                .build();


        message = chatService.addMessage(message);

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("消息发送成功")
                .data(message)
                .build();
    }

}
