package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.RegisterDTO;
import com.github.nanoyou.akariyumetabackend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    private RegisterService registerService;

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
            return registerService.register(registerUser);
        } catch (Exception e) {
            return Result.builder().
                    ok(false).
                    code(ResponseCode.PARAM_ERR.value).
                    data(null).
                    build();
        }

    }

}
