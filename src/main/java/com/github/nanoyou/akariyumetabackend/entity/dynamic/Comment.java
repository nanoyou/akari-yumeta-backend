package com.github.nanoyou.akariyumetabackend.entity.dynamic;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

/**
 * Comment 评论
 * 属于：捐助
 * 支持显示Markdown的评论
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    /**
     * Markdown评论
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /**
     * 发评论的人的ID
     */
    @NotNull
    @UUID
    private String commenterID;
    /**
     * 以Markdown格式存储的评论
     */
    @NotNull
    @NotBlank
    @Length(min = 1, max = 1048576)
    private String content;
    /**
     * 评论发表时间
     */
    @NotNull
    private LocalDateTime createTime;
    /**
     * 父评论ID，被回复评论的ID，为空则为动态节点（根节点）
     */
    private String replyTo;
}
