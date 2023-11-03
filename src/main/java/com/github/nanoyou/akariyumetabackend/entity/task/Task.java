package com.github.nanoyou.akariyumetabackend.entity.task;

import com.github.nanoyou.akariyumetabackend.enumeration.TaskCategory;
import com.github.nanoyou.akariyumetabackend.enumeration.TaskStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Task 任务
 * 属于：学习任务
 * 受困儿童需要完成的任务。
 */
@Data
@Builder
public class Task {
    /**
     * Task ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务发布人ID
     */
    private String taskUploaderID;
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
