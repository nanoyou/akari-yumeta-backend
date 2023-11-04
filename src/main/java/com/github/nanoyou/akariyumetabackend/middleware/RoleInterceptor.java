package com.github.nanoyou.akariyumetabackend.middleware;

import com.github.nanoyou.akariyumetabackend.common.ResponseUtil;
import com.github.nanoyou.akariyumetabackend.common.enumeration.RequestAttr;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.enumeration.Role;
import com.github.nanoyou.akariyumetabackend.common.enumeration.SessionAttr;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import com.github.nanoyou.akariyumetabackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public abstract class RoleInterceptor implements HandlerInterceptor {
    private final ResponseUtil responseUtil;
    @Autowired
    public RoleInterceptor(ResponseUtil responseUtil, UserService userService) {
        this.responseUtil = responseUtil;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var user = (User) request.getAttribute(RequestAttr.LOGIN_USER.attr);
        if (!user.getRole().equals(getRole())) {
            responseUtil.sendJson(response, Result.builder()
                    .ok(false)
                    .code(ResponseCode.UNAUTHORIZED.value)
                    .message("需要" + getRoleName())
                    .build());
            return false;
        }

        return true;
    }

    public abstract Role getRole();
    public abstract String getRoleName();
}
