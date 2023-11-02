package com.github.nanoyou.akariyumetabackend.entity;

import lombok.Data;

@Data
public class Subscription {
    /**
     * 粉丝ID
     */
    private String followerID;
    /**
     * 被关注者ID
     */
    private String followeeID;
}
