package com.github.nanoyou.akariyumetabackend.dto;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageDTO {
    String content;
    MessageType type;
}
