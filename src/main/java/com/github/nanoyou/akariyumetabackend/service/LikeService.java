package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.exception.NoSuchUserError;
import com.github.nanoyou.akariyumetabackend.dao.CommentDao;
import com.github.nanoyou.akariyumetabackend.dao.LikeDao;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Like;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeDao likeDao;

    private final CommentDao commentDao;

    private LikeService(LikeDao likeDao, CommentDao commentDao) {
        this.likeDao = likeDao;
        this.commentDao = commentDao;
    }

    public int getLikeCountByCommentID(@Nonnull String commentID) {
        return likeDao.findByCombinedPrimaryKeyCommentID(commentID).size();
    }

    public String findCommenterIdByCommentID(@Nonnull String commentID) {
        return commentDao.findCommenterIdProjById(commentID).map(
                CommentDao.CommenterID::getCommenterID
        ).orElseThrow(() -> new NoSuchUserError(ResponseCode.NO_SUCH_USER, "这条评论没有作者，这不应该发生"));
    }

    public Like addLike(@Nonnull Like like) {
        return likeDao.saveAndFlush(like);
    }

    public List<String> getLikerIdListByCommentId(@Nonnull String commentID) {
        val byCommentID = likeDao.findByCombinedPrimaryKeyCommentID(commentID);
        return byCommentID.stream().map(
                l -> l.getCombinedPrimaryKey().getLikerID()
        ).toList();
    }

    public boolean existLike(@Nonnull String commentID, @Nonnull String likerID) {
        val key = Like.CombinedPrimaryKey.builder()
                .commentID(commentID)
                .likerID(likerID)
                .build();
        return likeDao.existsById(key);
    }

    public void unlike(@Nonnull String commentID, @Nonnull String likerID) {
        val key = Like.CombinedPrimaryKey.builder()
                .commentID(commentID)
                .likerID(likerID)
                .build();
        likeDao.deleteById(key);
    }
}
