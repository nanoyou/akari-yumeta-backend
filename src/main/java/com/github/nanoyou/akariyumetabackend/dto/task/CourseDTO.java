package com.github.nanoyou.akariyumetabackend.dto.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDTO {
    private String taskID;
    private Integer watchedCount;
    private String videoURL;
    private Integer videoDuration;
}
