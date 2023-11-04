package com.github.nanoyou.akariyumetabackend.entity.donate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * GoodsInfo 商品信息
 * 属于: 捐助
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    /**
     * 商品 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品图片URL
     */
    private String imageURL;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品单价，以分为单位
     */
    private Long unitPrice;
}
