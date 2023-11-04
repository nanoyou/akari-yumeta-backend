package com.github.nanoyou.akariyumetabackend.interceptor;

import com.github.nanoyou.akariyumetabackend.common.util.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInterceptor extends RoleInterceptor {
    @Autowired
    public AdminInterceptor(ResponseUtil responseUtil) {
        super(responseUtil);
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
