package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.CommentDao;
import com.github.nanoyou.akariyumetabackend.dao.TaskDynamicDao;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.DynamicDTO;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.DynamicTreeDTO;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicService {

    private final CommentDao commentDao;
    private final SubscriptionService subscriptionService;
    private final LikeService likeService;
    private final TaskDynamicDao taskDynamicDao;

    @Autowired
    private DynamicService(CommentDao commentDao, SubscriptionService subscriptionService, LikeService likeService, TaskDynamicDao taskDynamicDao) {
        this.commentDao = commentDao;
        this.subscriptionService = subscriptionService;
        this.likeService = likeService;
        this.taskDynamicDao = taskDynamicDao;
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

    @Deprecated
    public Optional<DynamicTreeDTO> getDynamicWithoutChildrenByID(@Nonnull String dynamicID) {
        val dynamic = commentDao.findById(dynamicID);
        val likeCount = likeService.getLikeCountByCommentID(dynamicID);

        return dynamic.map(
                d -> DynamicTreeDTO.builder()
                        .id(d.getId())
                        .commenterID(d.getCommenterID())
                        .content(d.getContent())
                        .replyTo(null)
                        .createTime(d.getCreateTime())
                        .likes(likeCount)
                        .children(new ArrayList<>())
                        .build()
        );
    }

    public DynamicTreeDTO getDynamicTree(@Nonnull String commentID) {
        DynamicTreeDTO node = null;

        val commentOpt = commentDao.findById(commentID);
        if (commentOpt.isEmpty()) {
            return node;
        }
        val comment = commentOpt.get();
        val likeCount = likeService.getLikeCountByCommentID(comment.getId());
        node = DynamicTreeDTO.builder()
                .id(comment.getId())
                .commenterID(comment.getCommenterID())
                .content(comment.getContent())
                .replyTo(comment.getReplyTo())
                .createTime(comment.getCreateTime())
                .likes(likeCount)
                .children(new ArrayList<>())
                .build();
        val byReplyTo = commentDao.findByReplyTo(comment.getId());
        for (Comment c : byReplyTo) {
            node.getChildren().add(this.getDynamicTree(c.getId()));
        }
        return node;
    }

    public Optional<DynamicDTO> reply(@Nonnull String content, @Nonnull String replyTo, @Nonnull String whoReply) {
        val comment = this.addComment(Comment.builder()
                .commenterID(whoReply)
                .replyTo(replyTo)
                .content(content)
                .createTime(LocalDateTime.now())
                .build());
        return comment.map(c -> getDynamicDTOByID(c.getId()));
    }

    public boolean existByID(@Nonnull String id) {
        return commentDao.existsById(id);
    }

    public DynamicDTO getDynamicDTOByID(@Nonnull String id) {
        val dynamic = commentDao.findById(id).orElseThrow(NullPointerException::new);
        int likes = likeService.getLikeCountByCommentID(dynamic.getId());
        val commentList = commentDao.findByReplyTo(dynamic.getId());
        val children = commentList.stream().map(
                comment -> {
                    int commentLikes = likeService.getLikeCountByCommentID(comment.getId());
                    return DynamicDTO.builder()
                            .id(comment.getId())
                            .commenterID(comment.getCommenterID())
                            .content(comment.getContent())
                            .createTime(comment.getCreateTime())
                            .likes(commentLikes)
                            .replyTo(comment.getReplyTo())
                            .children(null)
                            .likeUsers(null)
                            .build();
                }
        ).toList();

        val taskId = taskDynamicDao.findByTaskDynamicDynamicID(dynamic.getId()).map(
                td -> td.getTaskDynamic().getTaskID()
        ).orElse(null);

        val likeUsers = likeService.getLikerIdListByCommentId(dynamic.getId());

        return DynamicDTO.builder()
                .id(dynamic.getId())
                .commenterID(dynamic.getCommenterID())
                .content(dynamic.getContent())
                .createTime(dynamic.getCreateTime())
                .likes(likes)
                .replyTo(dynamic.getReplyTo())
                .children(children)
                .likeUsers(likeUsers)
                .taskID(taskId)
                .build();

    }


}
