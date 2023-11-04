package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentDao extends JpaRepository<Comment, String> {

    @Override
    Optional<Comment> saveAndFlush(Comment comment);
}
