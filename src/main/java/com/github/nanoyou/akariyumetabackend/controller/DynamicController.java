package com.github.nanoyou.akariyumetabackend.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.CommentDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import com.github.nanoyou.akariyumetabackend.entity.task.TaskDynamic;
import com.github.nanoyou.akariyumetabackend.service.DynamicService;
import com.github.nanoyou.akariyumetabackend.service.TaskService;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicController {

    private final DynamicService dynamicService;
    private final TaskService taskService;

    @Autowired
    private DynamicController(DynamicService dynamicService, TaskService taskService) {
        this.dynamicService = dynamicService;
        this.taskService = taskService;
    }

    @RequestMapping(path = "/dynamic", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result dynamic(@RequestBody CommentDTO commentDTO, HttpSession httpSession) {

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

        // 获取用户信息
        if (httpSession.getAttribute(SessionAttr.LOGIN_USER_ID.attr) == null) {
            return Result.builder()
                    .ok(false)
                    .message("评论需要用户登录")
                    .code(ResponseCode.LOGIN_REQUIRE.value)
                    .build();
        }
        val loginUserID = ((String) httpSession.getAttribute(SessionAttr.LOGIN_USER_ID.attr));

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
    public Result myDynamic(HttpSession httpSession) {
        // 获取用户信息
        if (httpSession.getAttribute(SessionAttr.LOGIN_USER_ID.attr) == null) {
            return Result.builder()
                    .ok(false)
                    .message("您需要登录后才能查看我的动态")
                    .code(ResponseCode.LOGIN_REQUIRE.value)
                    .build();
        }

        val userID = ((String) httpSession.getAttribute(SessionAttr.LOGIN_USER_ID.attr));

        // 关注人的动态
        val dynamics = dynamicService.getDynamicsByFollowerID(userID);
        // 我的动态
        val myDynamics = dynamicService.getDynamicsByCommenterID(userID);
        // 将自己和他人的动态列表合并
        dynamics.addAll(myDynamics);

        return Result.builder()
                .ok(true)
                .message("查询到 " + dynamics.size() + " 条动态")
                .code(ResponseCode.SUCCESS.value)
                .data(dynamics)
                .build();

    }


}
