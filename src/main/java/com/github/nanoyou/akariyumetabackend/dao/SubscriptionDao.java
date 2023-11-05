package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionDao extends JpaRepository<Subscription, Subscription._CombinedPrimaryKey> {
    List<Subscription> findByCombinedPrimaryKeyFollowerID(@Nonnull String followerID);
}
