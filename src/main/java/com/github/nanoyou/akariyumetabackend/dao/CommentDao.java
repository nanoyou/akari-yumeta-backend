package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentDao extends JpaRepository<Comment, String> {

    @Override
    Comment saveAndFlush(Comment comment);

    List<Comment> findByCommenterIDOrderByCreateTimeDesc(@Nonnull String commenterID);

    Optional<Comment> findByIdOrderByCreateTimeDesc(@Nonnull String id);

    List<Comment> findByReplyToOrderByCreateTimeDesc(@Nonnull String replyTo);

    Optional<CommenterID> findCommenterIdProjById(@Nonnull String id);

    List<CommentID> findCommentIDByCommenterIDAndReplyToOrderByCreateTimeDesc(@Nonnull String commenterId, @Nullable String replyTo);

    interface CommentID {
        String getId();
    }
    interface CommenterID {
        String getCommenterID();
    }
}
