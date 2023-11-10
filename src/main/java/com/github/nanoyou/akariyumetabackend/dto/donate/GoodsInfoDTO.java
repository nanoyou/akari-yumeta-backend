package com.github.nanoyou.akariyumetabackend.dto.donate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoDTO {
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
