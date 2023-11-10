package com.github.nanoyou.akariyumetabackend.service;


import com.github.nanoyou.akariyumetabackend.dao.SubscriptionDao;
import com.github.nanoyou.akariyumetabackend.entity.friend.Subscription;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionDao subscriptionDao;

    private SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    /**
     * 根据关注者 ID 获取这个关注者对应的被关注者的 ID 列表
     *
     * @param followerID 关注者 ID
     * @return 被关注者的 ID 列表
     */
    public List<String> getFolloweeIDs(@Nonnull String followerID) {
        return subscriptionDao.findByCombinedPrimaryKeyFollowerID(followerID).stream().map(
                subscription ->
                        subscription.getCombinedPrimaryKey().getFolloweeID()

        ).toList();
    }

    public Optional<Subscription> follow(@Nonnull Subscription subscription) {
        subscriptionDao.save(subscription);
        return Optional.of(Subscription.builder()
                .combinedPrimaryKey(subscription.getCombinedPrimaryKey())
                .build());
    }

    public Boolean validateFollow(@Nonnull Subscription._CombinedPrimaryKey combinedPrimaryKey) {
        return subscriptionDao.findByCombinedPrimaryKey(combinedPrimaryKey).isPresent();
    }

    public Boolean unfollow(@Nonnull Subscription._CombinedPrimaryKey combinedPrimaryKey) {
        subscriptionDao.deleteById(combinedPrimaryKey);
        return true;
    }

    public List<String> getFolloweeIDList(@Nonnull String followerID) {
        val followeeIDProjs = subscriptionDao.findDistinctByCombinedPrimaryKey_FollowerID(followerID);
        if (followeeIDProjs == null || followeeIDProjs.isEmpty()) {
            return new ArrayList<>();
        }
        return followeeIDProjs.stream().map(
                SubscriptionDao.FolloweeIDProj::getCombinedPrimaryKeyFolloweeID
        ).toList();
    }
}
