package com.github.nanoyou.akariyumetabackend.entity.donate;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DonateGoods 捐物关系
 * 属于: 捐助
 */
@Data
public class DonateGoods {
    /**
     * 捐物关系 UUID
     */
    private String id;
    /**
     * 捐助数量
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
     * 捐助物品ID
     */
    private UUID goodsID;
    /**
     * 捐助总钱数，以分为单位
     */
    private Long totalMoney;
    /**
     * 留言祝福
     */
    private String wishes;
}
