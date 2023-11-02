package com.github.nanoyou.akariyumetabackend.entity;

import lombok.Data;

import java.util.UUID;

/**
 * Course 课程
 * 属于：学习任务
 * Course是特殊的Task，本系统只有Course作为Task的子类。
 */
@Data
public class Course {
    /**
     * 关联的Task的ID
     */
    private UUID taskID;
    /**
     * 被观看的次数
     */
    private Integer watchedCount;
    /**
     * 视频地址
     * 要给受困儿童显示的视频的地址
     */
    private String videoURL;
    /**
     * 视频时长
     * 单位秒，注意视频时长不等于任务时长
     */
    private Integer videoDuration;
}
