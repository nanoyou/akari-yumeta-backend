package com.github.nanoyou.akariyumetabackend.dto.task;

import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskCategory;
import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskDTO {
    private String id;
    private String taskName;
    private String taskUploaderID;
    private LocalDateTime createdTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TaskStatus status;
    private String description;
    private TaskCategory category;
    private Integer bonus;
}
