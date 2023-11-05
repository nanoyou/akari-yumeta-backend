package com.github.nanoyou.akariyumetabackend.dto.subscription;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDTO {
    private String followerID;
    private String followeeID;
}
