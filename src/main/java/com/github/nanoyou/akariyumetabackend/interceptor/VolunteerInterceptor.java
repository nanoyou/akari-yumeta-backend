package com.github.nanoyou.akariyumetabackend.interceptor;

import com.github.nanoyou.akariyumetabackend.common.util.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VolunteerInterceptor extends RoleInterceptor {
    @Autowired
    public VolunteerInterceptor(ResponseUtil responseUtil) {
        super(responseUtil);
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
