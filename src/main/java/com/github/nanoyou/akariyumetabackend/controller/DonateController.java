package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.service.DonateGoodsService;
import com.github.nanoyou.akariyumetabackend.service.DonateMoneyService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController

public class DonateController {

    private DonateMoneyService donateMoneyService;
    private DonateGoodsService donateGoodsService;
    private GoodsService goodsService;

    @Autowired
    public DonateController(DonateMoneyService donateMoneyService, DonateGoodsService donateGoodsService, GoodsService goodsService) {
        this.donateMoneyService = donateMoneyService;
        this.donateGoodsService = donateGoodsService;
        this.goodsService = goodsService;
    }

    /**
     * 记录捐钱
     * @param donateMoney 捐助金额
     * @param session session
     * @return 捐助结果
     */
    @PostMapping("/donate/money")
    public Result donateMoney(@RequestBody DonateMoney donateMoney, HttpSession session) {

        donateMoney.setDonatorID(UUID.fromString((String) session.getAttribute(SessionAttr.LOGIN_USER_ID.attr)));



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
            return  Result.builder()
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
     * @param session session
     * @param donateGoods 捐助物品
     * @return 捐助结果
     */
    @PostMapping("/donate/goods")
    public Result getHistory(HttpSession session,@RequestBody DonateGoods donateGoods){
        String donatorID = (String) session.getAttribute(SessionAttr.LOGIN_USER_ID.attr);
        donateGoods.setDonatorID(UUID.fromString(donatorID));
        donateGoods.setCreatedTime(LocalDateTime.now());
        Optional<GoodsInfo> goods = goodsService.findGoodsById(donateGoods.getGoodsID());
        // 物品不存在
        if (!goods.isPresent()) {
            return Result.builder()
                   .ok(false)
                   .message("物品不存在")
                   .code(ResponseCode.PARAM_ERR.value)
                   .build();
        }

        donateGoods.setTotalMoney(donateGoods.getAmount() * goods.get().getUnitPrice());

        DonateGoods result = donateGoodsService.saveDonateGoods(donateGoods);
        if (result == null) {
            return  Result.builder()
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
