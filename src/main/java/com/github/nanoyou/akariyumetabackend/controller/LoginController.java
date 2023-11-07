package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.dto.user.LoginDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result login(@RequestBody LoginDTO loginDTO, HttpSession httpSession) {
        try {
            return userService.login(loginDTO).map(
                    userDTO -> {
                        userDTO.setToken(httpSession.getId());
                        // 保存 Session
                        httpSession.setAttribute(SessionAttr.LOGIN_USER_ID.attr, userDTO.getId());
                        return Result.success("登录成功", userDTO);
                    }
            ).orElse(
                    Result.failed("账户不存在或密码错误", ResponseCode.WRONG_USERNAME_OR_PASSWORD)
            );
        } catch (Exception e) {
            return Result.failed("登录失败：内部服务器错误", ResponseCode.LOGIN_FAIL);
        }

    }

    @RequestMapping(path = "/login/admin", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result adminLogin(@RequestBody LoginDTO loginDTO,
                             HttpSession httpSession) {
        val login = userService.login(loginDTO);

        return login.map(
                userDTO -> {
                    if (!userDTO.getRole().equals(Role.ADMIN)) {
                        return Result.success("非管理员用户，无权访问", ResponseCode.LOGIN_FAIL);
                    }
                    userDTO.setToken(httpSession.getId());
                    httpSession.setAttribute(SessionAttr.LOGIN_USER_ID.attr, userDTO.getId());
                    return Result.success("管理员登录成功", userDTO);
                }
        ).orElse(
                Result.failed("用户名或密码不正确", ResponseCode.WRONG_USERNAME_OR_PASSWORD));
    }

}
