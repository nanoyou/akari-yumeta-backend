package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.DonateService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController

public class DonateController {

    private final DonateService donateService;
    private final GoodsService goodsService;
    private final UserService userService;

    @Autowired
    public DonateController(DonateService donateService, GoodsService goodsService, UserService userService) {
        this.donateService = donateService;
        this.goodsService = goodsService;
        this.userService = userService;

    }

    /**
     * 记录捐钱（捐钱）
     * @param donateMoney 捐助金额
     * @param session session
     * @return 捐助结果
     */
    @PostMapping("/donate/money")
    public Result donateMoney(@RequestBody DonateMoney donateMoney, HttpSession session) {

        donateMoney.setDonatorID(UUID.fromString((String) session.getAttribute(SessionAttr.LOGIN_USER_ID.attr)));

        User donee = userService.getUser(String.valueOf(donateMoney.getDoneeID())).orElse(null);
        if (donee == null) {
            return Result.builder()
                   .ok(false)
                   .message("捐助对象无效")
                   .code(ResponseCode.PARAM_ERR.value)
                   .build();
        }

        donateMoney.setCreatedTime(LocalDateTime.now());

        if (donateMoney.getAmount() == null || donateMoney.getAmount() <= 0) {
            return Result.builder()
                   .ok(false)
                   .message("捐赠金额不合法")
                   .code(ResponseCode.PARAM_ERR.value)
                   .build();
        }
        DonateMoney result = donateService.saveDonateMoney(donateMoney);

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
     * 捐助物品（捐物）
     * @param session session
     * @param donateGoods 捐助物品
     * @return 捐助结果
     */
    @PostMapping("/donate/goods")
    public Result donateGoods(HttpSession session,@RequestBody DonateGoods donateGoods){
        String donatorID = (String) session.getAttribute(SessionAttr.LOGIN_USER_ID.attr);
        donateGoods.setCreatedTime(LocalDateTime.now());
        Optional<GoodsInfo> goods = goodsService.findById(donateGoods.getGoodsID());
        // 物品不存在
        if (goods.isEmpty()) {
            return Result.builder()
                   .ok(false)
                   .message("物品不存在")
                   .code(ResponseCode.PARAM_ERR.value)
                   .build();
        }

        donateGoods.setTotalMoney(donateGoods.getAmount() * goods.get().getUnitPrice());

        DonateGoods result = donateService.saveDonateGoods(donateGoods);
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
        var goods = goodsService.findById(goodsID);

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


    /**
     * 查询历史捐助记录
     * @param userID
     * @return
     */
    @GetMapping("/donate/{userID}/info")
    public Result getDonateHistory(@PathVariable UUID userID) {
        return Result.builder()
                .ok(true)
                .message("查询成功")
                .code(ResponseCode.SUCCESS.value)
                .data(donateService.getAllDonateHistory(userID))
                .build();
    }

}
