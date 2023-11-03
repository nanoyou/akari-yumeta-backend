package com.github.nanoyou.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonateGoods {
    private String id;
    private String donatorID;
    private String goodsID;
    private int amount;
    private int totalMoney;
    private String wishes;
    private String createTime;
}
