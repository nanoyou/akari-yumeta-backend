package com.github.nanoyou.akariyumetabackend.middleware;

import com.github.nanoyou.akariyumetabackend.common.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChildInterceptor extends RoleInterceptor {
    @Autowired
    public ChildInterceptor(ResponseUtil responseUtil, UserService userService) {
        super(responseUtil, userService);
    }

    @Override
    public Role getRole() {
        return Role.CHILD;
    }

    @Override
    public String getRoleName() {
        return "儿童";
    }
}
