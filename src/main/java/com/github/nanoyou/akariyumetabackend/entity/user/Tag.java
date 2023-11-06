package com.github.nanoyou.akariyumetabackend.entity.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Tag {

    @Id
    private CombinedPrimaryKey combinedPrimaryKey;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CombinedPrimaryKey implements Serializable {

        @NotNull
        private String userId;

        @NotNull
        private String tagContent;
    }
}
