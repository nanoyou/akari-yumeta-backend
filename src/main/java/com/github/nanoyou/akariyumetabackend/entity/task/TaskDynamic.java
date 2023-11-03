package com.github.nanoyou.akariyumetabackend.entity.task;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * TaskDynamic 任务动态对
 * 属于：学习任务
 * 一任务对多动态
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskDynamic {

    @Id
    private _TaskDynamicCombinedPrimaryKey taskDynamic;

    @Embeddable
    @Data
    public static class _TaskDynamicCombinedPrimaryKey implements Serializable {
        /**
         * 任务ID
         */
        private String taskID;
        /**
         * 动态ID
         * 学习任务下方的评论，需要为Comment根节点
         */
        private String dynamicID;
    }


}
