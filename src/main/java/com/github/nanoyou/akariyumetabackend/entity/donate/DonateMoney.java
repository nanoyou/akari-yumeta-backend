package com.github.nanoyou.akariyumetabackend.entity.donate;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DonateMoney 捐款关系
 * 属于: 捐助
 */
@Data
public class DonateMoney {
    /**
     * UUID
     */
    private String id;
    /**
     * 捐助金额，以分为单位
     */
    private Long amount;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 捐助人ID
     */
    private UUID donatorID;
    /**
     * 受赠人ID
     */
    private UUID doneeID;
    /**
     * 留言祝福
     */
    private String wishes;
}
