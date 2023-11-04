package com.github.nanoyou.akariyumetabackend.dto.task;

import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskCategory;
import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @apiNote GET: /task 学习任务列表
 */
@Data
@Builder
public class TaskCourseDTO {
    /**
     * Task 的 ID
     */
    private String id;
    /**
     * 奖励积分，任务完成给予的积分数
     */
    private Integer bonus;
    /**
     * 任务类别
     */
    private TaskCategory category;
    /**
     * 任务创建时间，课程任务一旦被确认上传的时间
     */
    private LocalDateTime createdTime;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务结束时间
     */
    private LocalDateTime endTime;

    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;
    /**
     * 任务状态，详见枚举
     */
    private TaskStatus status;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务发布人ID
     */
    private String taskUploaderID;
    /**
     * 视频时长，单位秒，注意视频时长不等于任务时长。
     */
    private Integer videoDuration;
    /**
     * 视频地址，要给受困儿童显示的视频的地址
     */
    private String videoURL;
    /**
     * 被观看的次数
     */
    private Integer watchedCount;
}
