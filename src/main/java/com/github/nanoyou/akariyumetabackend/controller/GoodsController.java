package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;

import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.service.DonateGoodsService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /**
     * 根据描述查找商品（查找物品列表）
     * @param description 描述
     * @return 商品信息
     */
    @GetMapping("/donate/goods")
    public Result getGoodsByDescription(String description) {
        String newDescription = "%" + description + "%";
        var list = goodsService.getGoodByDescription(newDescription).orElse((GoodsInfo[]) null);
        if (list.length == 0) {
            String[] nullData = new String[1];
            nullData[0] = "未找到符合描述的物品";
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.PARAM_ERR.value)
                    .message("未找到符合描述的物品")
                    .data(nullData)
                    .build();
        }
        return Result.builder()
                .ok(true)
                .code(ResponseCode.SUCCESS.value)
                .message("查找物品成功")
                .data(list)
                .build();

    }

    /**
     * 根据商品ID查找商品(查询物品信息)
     * @param goodsID 商品ID
     * @return 商品信息
     */
    @GetMapping("/donate/goods/{goodsID}")
    public Result findGoodsById(@PathVariable("goodsID") UUID goodsID){
        var goods = goodsService.findGoodsById(goodsID);

        if (goods.isPresent()) {
            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .data(goods.orElse(null))
                    .message("查找物品成功")
                    .build();
        }

        return Result.builder()
                .ok(false)
                .code(ResponseCode.PARAM_ERR.value)
               .message("未找到该物品")
               .data(null)
               .build();

    }

}
