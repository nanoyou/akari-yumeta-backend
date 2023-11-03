package com.github.nanoyou.akariyumetabackend.entity.friend;

import lombok.Data;

import java.util.UUID;

/**
 * Subscription 好友关注
 * 属于: 好友
 */
@Data
public class Subscription {
    /**
     * 粉丝ID
     */
    private UUID followerID;
    /**
     * 被关注者ID
     */
    private UUID followeeID;
}
