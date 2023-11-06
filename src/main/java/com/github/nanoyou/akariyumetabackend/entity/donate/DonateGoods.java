package com.github.nanoyou.akariyumetabackend.entity.donate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;

/**
 * DonateGoods 捐物关系
 * 属于: 捐助
 */
@Data
public class DonateGoods {
    /**
     * 捐物关系 UUID
     */
    @Id
    private String id;
    /**
     * 捐助数量
     */
    @NotNull
    @Min(1)
    private Long amount;
    /**
     * 创建时间
     */
    @NotNull
    private LocalDateTime createdTime;
    /**
     * 捐助人ID
     */
    @NotNull
    @UUID
    private String donatorID;
    /**
     * 捐助物品ID
     */
    @NotNull
    @UUID
    private String goodsID;
    /**
     * 捐助总钱数，以分为单位
     */
    @NotNull
    @Min(1)
    private Long totalMoney;
    /**
     * 留言祝福
     */
    private String wishes;
}
