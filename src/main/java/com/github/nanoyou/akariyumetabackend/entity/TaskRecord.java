package com.github.nanoyou.akariyumetabackend.entity;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskRecordStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TaskRecord 任务记录
 * 属于：学习任务
 * 受困儿童与任务的关系。当儿童点击了任务，这个任务就被开启了。
 */
@Data
public class TaskRecord {
    /**
     * 关联的Task的ID
     */
    private UUID taskID;
    /**
     * 受困儿童的ID
     */
    private UUID childID;
    /**
     * 任务结束时间
     * 受困儿童看完这个视频的时间
     */
    private LocalDateTime endTime;
    /**
     * 任务开启时间
     * 受困儿童点开这个任务的时间
     */
    private LocalDateTime startTime;
    /**
     * 任务状态，详见枚举
     */
    private TaskRecordStatus status;
}
