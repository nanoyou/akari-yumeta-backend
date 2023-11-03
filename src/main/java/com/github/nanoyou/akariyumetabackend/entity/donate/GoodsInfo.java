package com.github.nanoyou.akariyumetabackend.entity.donate;

import lombok.Data;

import java.util.UUID;

/**
 * GoodsInfo 商品信息
 * 属于: 捐助
 */
@Data
public class GoodsInfo {
    /**
     * 商品 ID
     */
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
