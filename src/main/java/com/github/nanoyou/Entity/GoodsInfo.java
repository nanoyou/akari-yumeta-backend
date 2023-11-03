package com.github.nanoyou.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsInfo {
    private String id;
    private String name;
    private int unitPrice;
    private String description;
    private String imageURL;

}
