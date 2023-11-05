package com.github.nanoyou.akariyumetabackend.entity.dynamic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Like 点赞
 * 属于：捐助
 * 点赞关系
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`like`")
public class Like {
    /**
     * 被点赞的评论的ID
     */
    @Id
    private String commentID;
    /**
     * 被点赞的人的ID
     */
    private String likedID;
    /**
     * 发起点赞的人的ID
     */
    private String likerID;
}
