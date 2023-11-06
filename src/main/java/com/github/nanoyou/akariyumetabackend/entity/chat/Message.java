package com.github.nanoyou.akariyumetabackend.entity.chat;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.MessageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

/**
 * 消息 Message
 * 所属: 聊天
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * Message ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /**
     * 发送者 ID
     */
    @UUID
    @NotNull
    private String senderID;
    /**
     * 接收者 ID
     */
    @UUID
    @NotNull
    private String receiverID;
    /**
     * 消息内容
     */
    @NotBlank
    private String content;
    /**
     * 是否已读
     */
    @NotNull
    private boolean isRead;
    /**
     * 发送时间
     */
    @NotNull
    private LocalDateTime sendTime;
    /**
     * 消息类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageType type;
}
