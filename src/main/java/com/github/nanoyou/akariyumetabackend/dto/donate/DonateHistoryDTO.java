package com.github.nanoyou.akariyumetabackend.dto.donate;

import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class DonateHistoryDTO {
    private List<DonateGoods> goods;
    private Integer totalGoodsCount;
    private List<DonateMoney> money;
    private Long totalMoney;

}