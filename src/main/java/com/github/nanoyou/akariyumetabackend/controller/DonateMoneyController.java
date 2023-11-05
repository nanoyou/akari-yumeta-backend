package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.service.DonateMoneyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
/**
 * 捐助金额控制器
 */
public class DonateMoneyController {

    private DonateMoneyService donateMoneyService;

    @Autowired
    public DonateMoneyController(DonateMoneyService donateMoneyService) {
        this.donateMoneyService = donateMoneyService;
    }

    /**
     * 记录捐钱
     * @param donateMoney 捐助金额
     * @param session session
     * @return 捐助结果
     */
    @PostMapping("/donate/money")
    public Result donateMoney(@RequestBody DonateMoney donateMoney, HttpSession session) {

        // TODO 这个数据库能自动获取时间吗？？？？


        donateMoney.setDonatorID(UUID.fromString((String) session.getAttribute(SessionAttr.LOGIN_USER_ID.attr)));

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

    // @GetMapping("/donate/{userID}/info")
    // public Result getHistory(@PathVariable("userID") String userID){
    //
    // }




}
