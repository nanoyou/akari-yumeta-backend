package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.service.DonateGoodsService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 物品相关控制器
 */
@RestController
public class GoodsController {
    private final GoodsService goodsService;
    private final DonateGoodsService donateGoodsService;

    @Autowired
    public GoodsController(GoodsService goodsService, DonateGoodsService donateGoodsService) {
        this.goodsService = goodsService;
        this.donateGoodsService = donateGoodsService;
    }

    /**
     * 根据描述查找商品
     * @param description 描述
     * @return 商品信息
     */
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
                .data(list.orElse(null))
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
    /**
     * 根据商品ID查找商品
     * @param goodsID 商品ID
     * @return 商品信息
     */
    @GetMapping("/donate/goods/{goodsID}")
    public Result findGoodsById(@PathVariable("goodsID") String goodsID){
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
