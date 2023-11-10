package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends JpaRepository<Subscription, Subscription._CombinedPrimaryKey> {
    List<Subscription> findByCombinedPrimaryKeyFollowerID(@Nonnull String followerID);

    Optional<Subscription> findByCombinedPrimaryKey(@Nonnull Subscription._CombinedPrimaryKey combinedPrimaryKey);

    List<FolloweeIDProj> findDistinctByCombinedPrimaryKey_FollowerID(@Nonnull String followerID);

    interface FolloweeIDProj {
        String getCombinedPrimaryKeyFolloweeID();
    }

}
