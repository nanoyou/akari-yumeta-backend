package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import com.github.nanoyou.akariyumetabackend.service.DonateMoneyService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.bytecode.enhance.spi.interceptor.BytecodeInterceptorLogging_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/donate/money")
    public Result donateMoney(@RequestBody DonateMoney donateMoney, HttpSession session) {

        // int userId = (int) session.getAttribute()
        if (donateMoney.getAmount() == null || donateMoney.getAmount() <= 0) {
            return Result.builder()
                   .ok(false)
                   .message("捐赠金额不合法")
                   .code(ResponseCode.PARAM_ERR.value)
                   .build();
        }
        DonateMoney result = donateMoneyService.saveDonateMoney(donateMoney);
        return Result.builder()
                .ok(true)
                .message("捐赠成功")
                .code(ResponseCode.SUCCESS.value)
                .data(result)
                .build();
    }


}
