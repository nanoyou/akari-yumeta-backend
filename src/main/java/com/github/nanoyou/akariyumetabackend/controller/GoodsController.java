package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.service.DonateGoodsService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * 物品相关控制器
 */
public class GoodsController {
    private final GoodsService goodsService;
    private final DonateGoodsService donateGoodsService;

    @Autowired
    public GoodsController(GoodsService goodsService, DonateGoodsService donateGoodsService) {
        this.goodsService = goodsService;
        this.donateGoodsService = donateGoodsService;
    }

    @GetMapping("/donate/goods")
    public Result getGoodsByDescription(String description) {
        var list = goodsService.getGoodByDescription(description);
        if (list.isEmpty()) {
            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.PARAM_ERR.value)
                    .message("未找到符合描述的物品")
                    .data(null)
                    .build();
        }
        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("查找物品成功")
                .data(list)
                .build();

    }

    @RequestMapping(path = "/donate/goods", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result donateGoods(@RequestBody DonateGoods donateGoods) {
        if (donateGoods.getAmount() == null || donateGoods.getAmount() <= 0) {
            return Result.builder()
                    .ok(false)
                    .message("捐赠数量不合法")
                    .code(ResponseCode.PARAM_ERR.value)
                    .build();
        }
        DonateGoods result = donateGoodsService.saveDonateGoods(donateGoods);
        return Result.builder()
                .ok(true)
                .message("捐赠成功")
                .code(ResponseCode.SUCCESS.value)
                .data(result)
                .build();
    }

}
