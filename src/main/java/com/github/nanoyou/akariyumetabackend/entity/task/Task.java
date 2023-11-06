package com.github.nanoyou.akariyumetabackend.entity.task;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskCategory;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

/**
 * Task 任务
 * 属于：学习任务
 * 受困儿童需要完成的任务。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    @NotBlank
    @Column(unique = true)
    private String taskName;
    /**
     * 任务发布人ID
     */
    @NotNull
    @UUID
    private String taskUploaderID;
    /**
     * 任务创建时间
     */
    @NotNull
    private LocalDateTime createdTime;
    /**
     * 任务开始时间
     */
    @NotNull
    private LocalDateTime startTime;
    /**
     * 任务结束时间
     */
    @NotNull
    private LocalDateTime endTime;
    /**
     * 任务状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务类别
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskCategory category;
    /**
     * 奖励积分
     */
    @Min(0)
    @NotNull
    private Integer bonus;

}
