package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result login(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        try {
            return loginService.login(loginDTO).map(
                    userDTO -> {
                        // 保存 Session
                        httpSession.setAttribute(SessionAttr.LOGIN_USER_ID.attr, userDTO.getId());
                        return Result.builder()
                                .ok(true)
                                .code(ResponseCode.LOGIN_SUCCESS.value)
                                .message("登录成功")
                                .data(userDTO)
                                .build();
                    }
            ).orElse(
                    Result.builder()
                            .ok(false)
                            .code(ResponseCode.WRONG_USERNAME_OR_PASSWORD.value)
                            .message("账户不存在或密码错误")
                            .data(null)
                            .build()
            );
        } catch (Exception e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.LOGIN_FAIL.value)
                    .message("登录失败：内部服务器错误")
                    .build();
        }

    }

    @RequestMapping(path = "/login/admin", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result adminLogin(@RequestBody LoginDTO loginDTO,
                             HttpSession httpSession) {
        val login = loginService.login(loginDTO);

        return login.map(
                userDTO -> {
                    if (!userDTO.getRole().equals(Role.ADMIN)) {
                        return Result.builder()
                                .ok(false)
                                .code(ResponseCode.LOGIN_FAIL.value)
                                .message("非管理员用户，无权访问")
                                .data(null)
                                .build();
                    }
                    httpSession.setAttribute(SessionAttr.LOGIN_USER_ID.attr, userDTO.getId());
                    return Result.builder()
                            .ok(true)
                            .code(ResponseCode.SUCCESS.value)
                            .message("管理员登录成功")
                            .data(userDTO)
                            .build();
                }
        ).orElse(Result.builder()
                .ok(false)
                .code(ResponseCode.WRONG_USERNAME_OR_PASSWORD.value)
                .message("用户名或密码不正确")
                .data(null)
                .build());
    }

}
