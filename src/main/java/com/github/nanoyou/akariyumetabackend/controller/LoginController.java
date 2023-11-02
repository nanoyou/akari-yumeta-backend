package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.param.LoginParam;
import com.github.nanoyou.akariyumetabackend.service.LoginService;
import lombok.val;
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
    public HttpResponseEntity login(@RequestBody LoginParam loginParam) {
        val serviceResult = loginService.login(loginParam);
        return new ServiceResultConvertor<>(serviceResult).toHttpResponseEntity();
    }
}
