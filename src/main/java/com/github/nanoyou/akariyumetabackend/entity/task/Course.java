package com.github.nanoyou.akariyumetabackend.entity.task;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

/**
 * Course 课程
 * 属于：学习任务
 * Course是特殊的Task，本系统只有Course作为Task的子类。
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    /**
     * 关联的Task的ID
     */
    @Id
    @NotNull
    @UUID
    private String taskID;
    /**
     * 被观看的次数
     */
    @NotNull
    @Min(value = 0)
    private Integer watchedCount;
    /**
     * 视频地址
     * 要给受困儿童显示的视频的地址
     */
    @NotNull
    @URL
    @Length(min = 1, max = 4000)
    private String videoURL;
    /**
     * 视频时长
     * 单位秒，注意视频时长不等于任务时长
     */
    @NotNull
    @Min(value = 0)
    private Integer videoDuration;
}
