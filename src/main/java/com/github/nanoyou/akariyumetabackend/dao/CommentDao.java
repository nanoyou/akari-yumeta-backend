package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentDao extends JpaRepository<Comment, String> {

    @Override
    Comment saveAndFlush(Comment comment);

    List<Comment> findByCommenterID(@Nonnull String commenterID);

    Optional<Comment> findById(@Nonnull String id);

    List<Comment> findByReplyTo(@Nonnull String replyTo);

    Optional<CommenterID> findCommenterIdProjById(@Nonnull String id);

    interface CommenterID {
        String getCommenterID();
    }
}
