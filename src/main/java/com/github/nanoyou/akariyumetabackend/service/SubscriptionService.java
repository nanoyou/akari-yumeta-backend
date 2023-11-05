package com.github.nanoyou.akariyumetabackend.service;


import com.github.nanoyou.akariyumetabackend.dao.SubscriptionDao;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
