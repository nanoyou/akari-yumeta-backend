package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.dto.SendMessageDTO;
import com.github.nanoyou.akariyumetabackend.dto.chat.ChatDTO;
import com.github.nanoyou.akariyumetabackend.dto.user.UserDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.ChatService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 发送消息
     *
     * @param userID         信息接收人的 ID
     * @param sendMessageDTO 发送消息的 DTO
     * @param user           自动注入的登录用户, 也是消息发起人
     * @return 请求响应
     */
    @Deprecated
    @RequestMapping(path = "/chat/message/{userID}", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result sendMessage(@PathVariable String userID,
                              @RequestBody SendMessageDTO sendMessageDTO,
                              @RequestAttribute("user") User user
    ) {
        // receiver = userID
        val content = sendMessageDTO.getContent();

        if (!StringUtils.hasText(content)) {
            return Result.failed("发送消息不能为空", ResponseCode.EMPTY_MESSAGE_CONTENT);
        }

        if (!userService.userExists(userID)) {
            return Result.failed("用户不存在", ResponseCode.NO_SUCH_USER);
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

        return Result.success("消息发送成功", message);
    }

    /**
     * 查看指定用户的消息列表
     *
     * @param userID 被查看消息的用户的 ID
     * @param user   自动注入的登录用户
     * @return 请求响应
     */
    @RequestMapping(path = "/chat/message/{userID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result sendMessage(@PathVariable String userID,
                              @RequestAttribute("user") User user) {
        return Result.success("查看消息列表成功", chatService.getMessageListByPair(userID, user.getId()));
    }

    /**
     * 获取聊天列表
     *
     * @param loginUser 自动注入的登录用户
     * @return 请求响应
     */
    @RequestMapping(path = "/my/chat", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myChat(@RequestAttribute("user") User loginUser) {

        val myChat2 = chatService.getMyChat2(loginUser.getId());
        val chat = myChat2.stream().map(
                userMessagePair -> {
                    val user = userMessagePair.getFirst();
                    val message = userMessagePair.getSecond();
                    return userService.getUserDTO(user.getId()).map(
                            u ->
                                    ChatDTO.builder()
                                            .firstMessage(message)
                                            .user(u)
                                            .build()
                    ).orElse(null);
                }
        ).toList();

        return Result.success("聊天列表查询成功", chat);
    }

    /**
     * 标记聊天信息为已读
     *
     * @param messageID 需要被标记已读的消息的 ID
     * @return 请求响应
     */
    @RequestMapping(path = "/chat/message/{messageID}/read", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result read(@PathVariable String messageID) {
        return chatService.read(messageID).map(
                m -> Result.success("标记已读成功", m)
        ).orElse(
                Result.failed("标记已读失败：消息不存在", ResponseCode.NO_SUCH_MESSAGE)
        );
    }

}
