package com.github.nanoyou.akariyumetabackend.entity.dynamic;

import lombok.Data;

import java.util.UUID;

/**
 * Like 点赞
 * 属于：捐助
 * 点赞关系
 */
@Data
public class Like {
    /**
     * 被点赞的评论的ID
     */
    private UUID commentID;
    /**
     * 被点赞的人的ID
     */
    private UUID likedID;
    /**
     * 发起点赞的人的ID
     */
    private UUID likerID;
}
