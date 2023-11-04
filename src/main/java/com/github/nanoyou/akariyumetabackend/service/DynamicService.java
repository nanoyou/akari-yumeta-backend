package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.CommentDao;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DynamicService {

    private final CommentDao commentDao;

    @Autowired
    private DynamicService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public Optional<Comment> addComment(@Nonnull Comment comment) {
        return Optional.ofNullable(commentDao.saveAndFlush(comment));
    }

}
