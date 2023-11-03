package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result login(@RequestBody LoginDTO loginParam) {
        try {
            var loginUser = loginService.login(loginParam);
            return loginUser.map(
                    userDTO -> Result.builder()
                            .ok(true)
                            .code(ResponseCode.LOGIN_SUCCESS.value)
                            .message("登录成功")
                            .data(userDTO)
                            .build()
            ).orElse(
                    Result.builder()
                            .ok(false)
                            .code(ResponseCode.LOGIN_FAIL.value)
                            .message("账户不存在或密码错误")
                            .data(null)
                            .build()
            );

        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.LOGIN_FAIL.value)
                    .message("内部服务器错误")
                    .build();
        }

    }

}
