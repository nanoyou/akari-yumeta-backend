package com.github.nanoyou.akariyumetabackend.entity.task;

import lombok.Data;

import java.util.UUID;

/**
 * TaskDynamic 任务动态对
 * 属于：学习任务
 * 一任务对多动态
 */
@Data
public class TaskDynamic {
    /**
     * 任务ID
     */
    private UUID taskID;
    /**
     * 动态ID
     * 学习任务下方的评论，需要为Comment根节点
     */
    private UUID dynamicID;
}
