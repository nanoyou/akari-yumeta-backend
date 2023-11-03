package com.github.nanoyou.akariyumetabackend.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskCourseSearchDTO {
    /**
     * 任务类别
     */
    private String category;
    /**
     * 搜索内容
     */
    private String search;
    /**
     * 已完成、未完成、正在进行、未开始、已结束
     */
    private List<String> tags;
}
