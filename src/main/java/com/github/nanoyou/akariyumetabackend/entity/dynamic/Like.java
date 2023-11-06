package com.github.nanoyou.akariyumetabackend.entity.dynamic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

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
    @UUID
    private String commentID;
    /**
     * 被点赞的人的ID
     */
    @UUID
    @NotNull
    private String likedID;
    /**
     * 发起点赞的人的ID
     */
    @UUID
    @NotNull
    private String likerID;
}
