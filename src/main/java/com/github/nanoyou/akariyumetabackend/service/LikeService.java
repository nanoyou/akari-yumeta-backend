package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.LikeDao;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeDao likeDao;

    private LikeService(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    public int getLikeCountByCommentID(@Nonnull String commentID) {
        return likeDao.findByCommentID(commentID).size();
    }
}
