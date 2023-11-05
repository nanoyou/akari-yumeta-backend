package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.SendMessageDTO;
import com.github.nanoyou.akariyumetabackend.dto.chat.ChatDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.ChatService;
import com.github.nanoyou.akariyumetabackend.service.LikeService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                              @RequestAttribute("user") User user
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
                .senderID(user.getId())
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

    @RequestMapping(path = "/chat/message/{userID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result sendMessage(@PathVariable String userID,
                              @RequestAttribute("user") User user) {
        val list = chatService.getMessageListByPair(userID, user.getId());
        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("查看消息列表成功")
                .data(list)
                .build();
    }

    @RequestMapping(path = "/my/chat", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myChat(@RequestAttribute("user") User user) {
        val myChat = chatService.getMyChat(user.getId());
        List<ChatDTO> chatDTOList = new ArrayList<>();
        myChat.forEach(pair -> {
            val userDTO = userService.getUserDTO(pair.getFirst());
            if (userDTO.isPresent()) {
                val chatDTO = ChatDTO.builder()
                        .firstMessage(pair.getSecond())
                        .user(userDTO.get())
                        .build();
                chatDTOList.add(chatDTO);
            }
        });

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("聊天列表查询成功")
                .data(chatDTOList)
                .build();

    }

}
