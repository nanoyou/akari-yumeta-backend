package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.user.RegisterDTO;
import com.github.nanoyou.akariyumetabackend.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    private RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        try {
            var registerUser = User.builder()
                    .username(registerDTO.getUsername())
                    .nickname(registerDTO.getNickname())
                    .role(registerDTO.getRole())
                    .password(registerDTO.getPassword())
                    .gender(registerDTO.getGender())
                    .introduction(registerDTO.getIntroduction())
                    .avatarURL(registerDTO.getAvatarURL())
                    .usageDuration(registerDTO.getUsageDuration())
                    .build();

            // 管理员不可以被注册
            if (Role.ADMIN.equals(registerUser.getRole())) {
                return Result.builder().
                        ok(true).
                        code(ResponseCode.ADMIN_REGISTER_REFUSED.value).
                        message("不能注册管理员账户").
                        build();
            }
            var registerUserDTO = registerService.register(registerUser);
            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("注册成功")
                    .data(registerUserDTO)
                    .build();

        } catch (Exception e) {
            return Result.builder().
                    ok(false).
                    code(ResponseCode.PARAM_ERR.value).
                    data(null).
                    build();
        }

    }

}
