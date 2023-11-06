package com.github.nanoyou.akariyumetabackend.entity.friend;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.io.Serializable;

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
        @NotNull
        @UUID
        private String followerID;
        /**
         * 被关注者ID
         */
        @NotNull
        @UUID
        private String followeeID;
    }
}
