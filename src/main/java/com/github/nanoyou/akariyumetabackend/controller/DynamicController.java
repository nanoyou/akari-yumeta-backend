package com.github.nanoyou.akariyumetabackend.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.CommentDTO;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.DynamicDTO;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.ReplyDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import com.github.nanoyou.akariyumetabackend.entity.task.TaskDynamic;
import com.github.nanoyou.akariyumetabackend.service.DynamicService;
import com.github.nanoyou.akariyumetabackend.service.LikeService;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class DynamicController {

    private final DynamicService dynamicService;
    private final TaskService taskService;
    private final LikeService likeService;

    @Autowired
    private DynamicController(DynamicService dynamicService, TaskService taskService, LikeService likeService) {
        this.dynamicService = dynamicService;
        this.taskService = taskService;
        this.likeService = likeService;
    }

    @RequestMapping(path = "/dynamic", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result dynamic(@RequestBody CommentDTO commentDTO,
                          @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {

        // 动态内容是否为空
        if (!StringUtils.hasText(commentDTO.getContent())) {
            return Result.builder()
                    .ok(false)
                    .message("评论内容不能为空")
                    .code(ResponseCode.EMPTY_COMMENT_CONTENT.value)
                    .build();
        }

        // 任务是否存在
        if (!taskService.existTask(commentDTO.getTaskID())) {
            return Result.builder()
                    .ok(false)
                    .message("本动态要关联的课程任务不存在")
                    .code(ResponseCode.NO_SUCH_TASK_COURSE.value)
                    .build();
        }

        val postComment = Comment.builder()
                .commenterID(loginUserID)
                .content(commentDTO.getContent())
                .createTime(LocalDateTimeUtil.now())
                .replyTo(null)
                .build();

        // 提交动态
        val comment = dynamicService.addComment(postComment).orElseThrow(NullPointerException::new);

        // 将动态与任务绑定
        val taskDynamic = taskService.addTaskDynamic(TaskDynamic.builder()
                .taskDynamic(TaskDynamic._TaskDynamicCombinedPrimaryKey.builder()
                        .taskID(commentDTO.getTaskID())
                        .dynamicID(comment.getId())
                        .build())
                .build());

        taskDynamic.orElseThrow(NullPointerException::new);

        return Result.builder()
                .ok(true)
                .message("成功评论")
                .code(ResponseCode.SUCCESS.value)
                .data(comment)
                .build();

    }

    @RequestMapping(path = "/my/dynamic", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result myDynamic(@ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {

        // 关注人的动态
        val dynamics = dynamicService.getDynamicsByFollowerID(loginUserID);
        // 我的动态
        val myDynamics = dynamicService.getDynamicsByCommenterID(loginUserID);
        // 将自己和他人的动态列表合并
        dynamics.addAll(myDynamics);

        val likes = dynamics.stream().map(
                dynamic -> likeService.getLikeCountByCommentID(dynamic.getCommenterID())
        ).toList();

        val dynamicDTOs = concat(dynamics, likes);

        return Result.builder()
                .ok(true)
                .message("查询到 " + dynamics.size() + " 条动态")
                .code(ResponseCode.SUCCESS.value)
                .data(dynamicDTOs)
                .build();

    }

    @RequestMapping(path = "/dynamic/{dynamicID}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result dynamic(@PathVariable String dynamicID) {

        val dynamicTree = dynamicService.getDynamicTree(dynamicID);
        if (dynamicTree == null) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NO_SUCH_COMMENT_OR_DYNAMIC.value)
                    .message("动态的树不存在")
                    .data(null)
                    .build();
        }

        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("评论区加载完成")
                .data(dynamicTree)
                .build();
    }

    @RequestMapping(path = "/comment/{commentID}/reply", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result reply(@PathVariable String commentID,
                        @RequestBody ReplyDTO replyDTO,
                        @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {

        val content = replyDTO.getContent();
        if (!StringUtils.hasText(content)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.EMPTY_COMMENT_CONTENT.value)
                    .message("回复评论不能为空")
                    .data(null)
                    .build();
        }

        if (!dynamicService.existByID(commentID)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NO_SUCH_COMMENT_OR_DYNAMIC.value)
                    .message("动态或评论不存在")
                    .data(null)
                    .build();
        }

        val reply = dynamicService.reply(content, commentID, loginUserID);

        return reply.map(
                r -> Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("评论回复成功")
                        .data(r)
                        .build()
        ).orElse(
                Result.builder()
                        .ok(false)
                        .code(ResponseCode.CREATE_COMMENT_OR_DYNAMIC_FAILED.value)
                        .message("评论回复失败")
                        .data(null)
                        .build()
        );

    }

    @RequestMapping(path = "/task/{taskID}/dynamic", method = RequestMethod.GET, headers = "Accept=application/json")
    public Result taskDynamic(@PathVariable String taskID) {
        if (!taskService.existTask(taskID)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.NO_SUCH_TASK_COURSE.value)
                    .data(null)
                    .message("课程不存在")
                    .build();
        }

        try {
            val taskDynamicIdList = taskService.getTaskDynamicIdList(taskID);

            val dynamics = taskDynamicIdList.stream().map(
                    id -> dynamicService.getDynamicWithoutChildrenByID(id).orElseThrow(NullPointerException::new)
            ).toList();

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("获取任务动态列表成功")
                    .data(dynamics)
                    .build();

        } catch (NullPointerException e) {
            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("获取任务动态列表失败：获取动态时遇到空指针")
                    .data(null)
                    .build();
        }
    }

    private List<DynamicDTO> concat(List<Comment> dynamics, List<Integer> likes) {
        return IntStream.range(0, dynamics.size())
                .mapToObj(i -> {
                            val dynamic = dynamics.get(i);
                            int like = likes.get(i);
                            return DynamicDTO.builder()
                                    .id(dynamic.getId())
                                    .commenterID(dynamic.getCommenterID())
                                    .content(dynamic.getContent())
                                    .replyTo(dynamic.getReplyTo())
                                    .createTime(dynamic.getCreateTime())
                                    .likes(like)
                                    .build();
                        }
                ).toList();
    }


}
