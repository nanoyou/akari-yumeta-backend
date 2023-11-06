package com.github.nanoyou.akariyumetabackend.entity.donate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * 商品描述
     */
    @NotNull
    @NotBlank
    private String description;
    /**
     * 商品图片URL
     */
    @NotNull
    @URL
    private String imageURL;
    /**
     * 商品名称
     */
    @NotNull
    @NotBlank
    private String name;
    /**
     * 商品单价，以分为单位
     */
    @NotNull
    @Min(1)
    private Long unitPrice;
}
