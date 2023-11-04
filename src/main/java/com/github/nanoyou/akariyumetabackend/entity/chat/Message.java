package com.github.nanoyou.akariyumetabackend.entity.chat;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.MessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息 Message
 * 所属: 聊天
 */
@Data
public class Message {
    /**
     * Message ID
     */
    private String id;
    /**
     * 发送者 ID
     */
    private String senderID;
    /**
     * 接收者 ID
     */
    private String receiverID;
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
    @Enumerated(EnumType.STRING)
    private MessageType type;
}
