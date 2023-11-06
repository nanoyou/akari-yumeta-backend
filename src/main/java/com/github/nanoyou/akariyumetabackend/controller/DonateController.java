package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.DonateGoodsService;
import com.github.nanoyou.akariyumetabackend.service.DonateMoneyService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController

public class DonateController {

    private final DonateMoneyService donateMoneyService;
    private final DonateGoodsService donateGoodsService;
    private final GoodsService goodsService;


    @Autowired
    public DonateController(DonateMoneyService donateMoneyService, DonateGoodsService donateGoodsService, GoodsService goodsService) {
        this.donateMoneyService = donateMoneyService;
        this.donateGoodsService = donateGoodsService;
        this.goodsService = goodsService;
    }

    /**
     * 记录捐钱
     *
     * @param donateMoney 捐助金额
     * @param loginUser   自动注入的登录用户
     * @return 捐助结果
     */
    @PostMapping("/donate/money")
    public Result donateMoney(@RequestBody DonateMoney donateMoney, @RequestAttribute("user") User loginUser) {

        assert loginUser != null;
        donateMoney.setDonatorID(loginUser.getId());
        donateMoney.setCreatedTime(LocalDateTime.now());

        if (donateMoney.getAmount() == null || donateMoney.getAmount() <= 0) {
            return Result.builder()
                    .ok(false)
                    .message("捐赠金额不合法")
                    .code(ResponseCode.PARAM_ERR.value)
                    .build();
        }
        DonateMoney result = donateMoneyService.saveDonateMoney(donateMoney);

        if (result == null) {
            return Result.builder()
                    .ok(false)
                    .message("参数异常")
                    .code(ResponseCode.PARAM_ERR.value)
                    .build();
        }


        return Result.builder()
                .ok(true)
                .message("捐赠成功")
                .code(ResponseCode.SUCCESS.value)
                .data(result)
                .build();
    }

    /**
     * 捐助物品
     *
     * @param donateGoods 捐助物品
     * @param loginUser 自动注入的登录用户
     * @return 捐助结果
     */
    @PostMapping("/donate/goods")
    public Result getHistory(@RequestBody DonateGoods donateGoods, @RequestAttribute("user") User loginUser) {

        assert loginUser != null;

        val donatorID = loginUser.getId();
        donateGoods.setDonatorID(donatorID);
        donateGoods.setCreatedTime(LocalDateTime.now());
        Optional<GoodsInfo> goods = goodsService.findGoodsById(donateGoods.getGoodsID());
        // 物品不存在
        if (goods.isEmpty()) {
            return Result.builder()
                    .ok(false)
                    .message("物品不存在")
                    .code(ResponseCode.PARAM_ERR.value)
                    .build();
        }

        donateGoods.setTotalMoney(donateGoods.getAmount() * goods.get().getUnitPrice());

        DonateGoods result = donateGoodsService.saveDonateGoods(donateGoods);
        if (result == null) {
            return Result.builder()
                    .ok(false)
                    .message("参数异常")
                    .data(null)
                    .code(ResponseCode.PARAM_ERR.value)
                    .build();
        }
        return Result.builder()
                .ok(true)
                .message("捐赠成功")
                .data(result)
                .code(ResponseCode.SUCCESS.value)
                .build();
    }


}
