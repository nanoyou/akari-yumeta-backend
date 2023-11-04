package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.CommentDao;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicService {

    private final CommentDao commentDao;
    private final SubscriptionService subscriptionService;
    private final LikeService likeService;

    @Autowired
    private DynamicService(CommentDao commentDao, SubscriptionService subscriptionService, LikeService likeService) {
        this.commentDao = commentDao;
        this.subscriptionService = subscriptionService;
        this.likeService = likeService;
    }

    public Optional<Comment> addComment(@Nonnull Comment comment) {
        return Optional.ofNullable(commentDao.saveAndFlush(comment));
    }

    public List<Comment> getDynamicsByCommenterID(@Nonnull String commenterID) {
        return commentDao.findByCommenterID(commenterID);
    }

    public List<Comment> getDynamicsByFollowerID(@Nonnull String commenterID) {
        return subscriptionService.getFolloweeIDs(commenterID).stream()
                .flatMap(followeeID -> commentDao.findByCommenterID(followeeID).stream())
                .collect(Collectors.toList());
    }

}
