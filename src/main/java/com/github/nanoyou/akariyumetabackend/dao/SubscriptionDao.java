package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import jakarta.annotation.Nonnull;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends JpaRepository<Subscription, Subscription._CombinedPrimaryKey> {
    List<Subscription> findByCombinedPrimaryKeyFollowerID(@Nonnull String followerID);

    Optional<Subscription> findByCombinedPrimaryKey(@Nonnull Subscription._CombinedPrimaryKey combinedPrimaryKey);

    Boolean deleteByCombinedPrimaryKey(@Nonnull Subscription._CombinedPrimaryKey combinedPrimaryKey);
}
