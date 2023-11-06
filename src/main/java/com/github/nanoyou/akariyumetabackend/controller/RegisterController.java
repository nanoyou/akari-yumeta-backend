package com.github.nanoyou.akariyumetabackend.controller;

import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.dto.user.RegisterDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        try {

            if (!StringUtils.hasText(registerDTO.getUsername())) {
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.EMPTY_USERNAME.value)
                        .message("用户名不能为空")
                        .data(null)
                        .build();
            }
            if (!StringUtils.hasText(registerDTO.getNickname())) {
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.EMPTY_NICKNAME.value)
                        .message("昵称不能为空")
                        .data(null)
                        .build();
            }

            // 判断密码是否为空
            if (!StringUtils.hasText(registerDTO.getPassword())) {
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.EMPTY_PASSWORD.value)
                        .message("密码不能为空")
                        .data(null)
                        .build();
            }

            // 判断是否选择了自己的性别
            if (registerDTO.getGender() == null) {
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.EMPTY_GENDER.value)
                        .message("必须选择性别")
                        .data(null)
                        .build();
            }

            // 判断是否选择了身份
            if (registerDTO.getRole() == null) {
                return Result.builder().
                        ok(true).
                        code(ResponseCode.EMPTY_ROLE.value).
                        message("必须选择自己的身份").
                        build();
            }

            // 管理员不可以被注册
            if (Role.ADMIN.equals(registerDTO.getRole())) {
                return Result.builder().
                        ok(true).
                        code(ResponseCode.ADMIN_REGISTER_REFUSED.value).
                        message("不能注册管理员账户").
                        build();
            }

            val userDTO = userService.register(registerDTO);

            return Result.builder()
                    .ok(true)
                    .code(ResponseCode.SUCCESS.value)
                    .message("注册成功")
                    .data(userDTO)
                    .build();

        } catch (DataIntegrityViolationException e) {

            return Result.builder()
                    .ok(false)
                    .message("注册失败：用户已存在")
                    .code(ResponseCode.PARAM_ERR.value)
                    .data(null)
                    .build();
        }

    }

}
