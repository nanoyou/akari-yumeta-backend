package com.github.nanoyou.akariyumetabackend.websocket;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.exception.OnlineError;
import com.github.nanoyou.akariyumetabackend.dto.SendMessageDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.MessageType;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.ChatService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/ws/chat")
public class JavaClientController {

    private final JavaClient javaClient;

    private final UserService userService;

    private final ChatService chatService;

    private Map<String, WebSocketClient> onlineMap = new HashMap<>();

    @Autowired
    public JavaClientController(JavaClient javaClient, UserService userService, ChatService chatService) {
        this.javaClient = javaClient;
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping(value = "/hello/{receiverID}")
    public Result hello(@RequestAttribute("user") User loginUser,
                        @PathVariable String receiverID,
                        @RequestBody SendMessageDTO sendMessageDTO) {
        val client = javaClient.getClient("ws://localhost:8080/ws/server")
                .orElseThrow(() -> new OnlineError(ResponseCode.INNER_SERVER_ERROR, "登不上了。滚"));
        onlineMap.put(loginUser.getId(), client);
        val receiverClient = onlineMap.get(receiverID);

        val result = addMessage(sendMessageDTO.getType(), sendMessageDTO.getContent(), loginUser.getId(), receiverID);
        if (receiverClient != null) {
            receiverClient.send(sendMessageDTO.getContent());
        }
        return result;

    }

    private Result addMessage(@Nonnull MessageType type, @Nonnull String content, @Nonnull String senderID, @Nonnull String receiverID) {

        if (!StringUtils.hasText(content)) {
            return Result.failed("发送消息不能为空", ResponseCode.EMPTY_MESSAGE_CONTENT);
        }

        if (!userService.userExists(senderID)) {
            return Result.failed("发信人用户不存在", ResponseCode.NO_SUCH_USER);
        }

        if (!userService.userExists(receiverID)) {
            return Result.failed("收信人用户不存在", ResponseCode.NO_SUCH_USER);
        }

        var message = Message.builder()
                .senderID(senderID)
                .receiverID(receiverID)
                .content(content)
                .isRead(false)
                .sendTime(LocalDateTime.now())
                .type(type)
                .build();

        message = chatService.addMessage(message);

        return Result.success("消息发送成功", message);
    }
}