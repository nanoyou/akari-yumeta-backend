package com.github.nanoyou.akariyumetabackend.middleware;

import com.github.nanoyou.akariyumetabackend.common.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInterceptor extends RoleInterceptor {
    @Autowired
    public AdminInterceptor(ResponseUtil responseUtil, UserService userService) {
        super(responseUtil, userService);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String getRoleName() {
        return "管理员";
    }
}
