package com.github.nanoyou.akariyumetabackend.entity.task;

import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskRecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TaskRecord 任务记录
 * 属于：学习任务
 * 受困儿童与任务的关系。当儿童点击了任务，这个任务就被开启了。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TaskRecord {

    @Id
    private _TaskRecordCombinedPrimaryKey taskRecordCombinedPrimaryKey;
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
    @Enumerated(EnumType.STRING)
    private TaskRecordStatus status;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class _TaskRecordCombinedPrimaryKey implements Serializable {
        /**
         * 关联的Task的ID
         */
        private String taskID;
        /**
         * 受困儿童的ID
         */
        private String childID;
    }
}
