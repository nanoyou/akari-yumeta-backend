package com.github.nanoyou.akariyumetabackend.entity.chat;

import com.github.nanoyou.akariyumetabackend.common.enumeration.MessageType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 消息 Message
 * 所属: 聊天
 */
@Data
public class Message {
    /**
     * Message ID
     */
    private UUID id;
    /**
     * 发送者 ID
     */
    private UUID senderID;
    /**
     * 接收者 ID
     */
    private UUID receiverID;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已读
     */
    private boolean isRead;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 消息类型
     */
    private MessageType type;
}
