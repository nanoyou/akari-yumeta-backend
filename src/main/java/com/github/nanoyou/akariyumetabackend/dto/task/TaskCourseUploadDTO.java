package com.github.nanoyou.akariyumetabackend.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.TaskCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @apiNote POST: /task 创建学习任务
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCourseUploadDTO {
    /**
     * 奖励积分，任务完成给予的积分数
     */
    private Integer bonus;
    /**
     * 任务类别
     */
    private TaskCategory category;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务结束时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    /**
     * 任务开始时间
     */
//    @Value()
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务发布人ID
     */
    private String taskUploaderID;
    /**
     * 视频地址，要给受困儿童显示的视频的地址
     */
    private String videoURL;
}
