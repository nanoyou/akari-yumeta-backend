package com.github.nanoyou.akariyumetabackend.entity.friend;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Subscription 好友关注
 * 属于: 好友
 */
@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Subscription {

    @Id
    private _CombinedPrimaryKey combinedPrimaryKey;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class _CombinedPrimaryKey implements Serializable {
        /**
         * 粉丝ID
         */
        private String followerID;
        /**
         * 被关注者ID
         */
        private String followeeID;
    }
}
