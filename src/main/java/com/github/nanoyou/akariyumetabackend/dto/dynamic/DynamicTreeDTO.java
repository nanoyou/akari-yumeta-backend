package com.github.nanoyou.akariyumetabackend.dto.dynamic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DynamicTreeDTO {
    /**
     * 发评论的人的ID
     */
    private String commenterID;
    /**
     * 以Markdown格式存储的评论
     */
    private String content;
    /**
     * 评论发表时间
     */
    private LocalDateTime createTime;
    /**
     * 动态 ID
     */
    private String id;
    /**
     * 点赞数
     */
    private int likes;
    /**
     * 父评论ID，被回复评论的ID，为空则为动态节点（根节点）
     */
    private String replyTo;
    /**
     * 子评论
     */
    private List<DynamicTreeDTO> children;
}
