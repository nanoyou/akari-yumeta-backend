package com.github.nanoyou.akariyumetabackend.dto.task;

import com.github.nanoyou.akariyumetabackend.common.enumeration.TaskRecordStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskRecordDTO {
    private String taskID;
    private String childID;
    private LocalDateTime endTime;
    private LocalDateTime startTime;
    private TaskRecordStatus status;
}
