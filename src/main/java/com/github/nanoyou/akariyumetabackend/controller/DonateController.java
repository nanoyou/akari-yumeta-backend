package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.donate.GoodsInfoDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.DonateService;
import com.github.nanoyou.akariyumetabackend.service.GoodsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController

public class DonateController {

    private final DonateService donateService;
    private final GoodsService goodsService;

    @Autowired
    public DonateController(DonateService donateService, GoodsService goodsService) {
        this.donateService = donateService;
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
        DonateMoney result = donateService.saveDonateMoney(donateMoney);

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

    /**
     * 根据描述查找商品（查找物品列表）
     * @param description 描述
     * @return 商品信息
     */
    @GetMapping("/donate/goods")
    public Result getGoodsByDescription(String description) {
        String newDescription = "%" + description + "%";
        var list = goodsService.getGoodByDescription(newDescription).orElse((GoodsInfo[]) null);
        assert list != null;
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
    public Result findGoodsById(@PathVariable("goodsID") String goodsID){
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
     * @param userID 被查看人的 ID
     * @return 请求响应
     */
    @GetMapping("/donate/{userID}/info")
    public Result getDonateHistory(@PathVariable String userID) {
        return Result.builder()
                .ok(true)
                .message("查询成功")
                .code(ResponseCode.SUCCESS.value)
                .data(donateService.getAllDonateHistory(userID))
                .build();
    }

    /**
     * 添加一条商品信息
     * @param goodsInfoDTO 商品信息
     * @return 响应
     */
    @RequestMapping(path = "/donate/goodsInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result addDonateGoodsInfo(@RequestBody GoodsInfoDTO goodsInfoDTO) {

        var goodsInfo = GoodsInfo.builder()
                .name(goodsInfoDTO.getName())
                .unitPrice(goodsInfoDTO.getUnitPrice())
                .imageURL(goodsInfoDTO.getImageURL())
                .description(goodsInfoDTO.getDescription()).build();

        goodsInfo = goodsService.saveGoodsInfo(goodsInfo);

        return Result.success("成功添加 1 条商品信息", goodsInfo);
    }

}
