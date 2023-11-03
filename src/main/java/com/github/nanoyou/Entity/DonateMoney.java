package com.github.nanoyou.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class DonateMoney {
    private String id;
    private String donatorID;
    private String doneeID;
    private int amount;
    private String wishes;
    private String createTime;

}
