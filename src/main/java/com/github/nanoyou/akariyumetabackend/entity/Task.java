package com.github.nanoyou.akariyumetabackend.entity;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskCategory;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    /**
     * Task ID
     */
    private UUID id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务发布人ID
     */
    private UUID taskUploaderID;
    /**
     * 任务创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;
    /**
     * 任务结束时间
     */
    private LocalDateTime endTime;
    /**
     * 任务状态
     */
    private TaskStatus status;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务类别
     */
    private TaskCategory category;
    /**
     * 奖励积分
     */
    private Integer bonus;

}
