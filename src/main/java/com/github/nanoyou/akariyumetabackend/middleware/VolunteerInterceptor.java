package com.github.nanoyou.akariyumetabackend.middleware;

import com.github.nanoyou.akariyumetabackend.common.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.common.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VolunteerInterceptor extends RoleInterceptor {
    @Autowired
    public VolunteerInterceptor(ResponseUtil responseUtil, UserService userService) {
        super(responseUtil, userService);
    }

    @Override
    public Role getRole() {
        return Role.VOLUNTEER;
    }

    @Override
    public String getRoleName() {
        return "志愿者";
    }
}
