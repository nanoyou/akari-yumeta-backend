package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.dynamic.Like;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeDao extends JpaRepository<Like, Like.CombinedPrimaryKey> {
    List<Like> findByCombinedPrimaryKeyCommentID(@Nonnull String commentID);

}
